package com.example.moviesearch.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.domain.Film
import com.example.moviesearch.utils.ConnectionChecker
import com.example.moviesearch.R
import com.example.moviesearch.view.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
        initNavigation()

        receiver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filters)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == COUNT_OF_BACKSTACK_ONE) {
            super.onBackPressed()
            AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_MovieSearch_DialogAlert))
                .setTitle(R.string.title)
                .setIcon(R.drawable.ic_alert_dialog_exit)
                .setPositiveButton(R.string.button_positive) { _, _ ->
                    finish()
                }
                .setNegativeButton(R.string.button_negative) { _, _ ->
                    supportFragmentManager()
                }
                .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun initNavigation() {
        val bottomBar: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_app_bar)

        //переключение фрагментов по нажатию на кнопки Bottom bar
        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favorite -> {
                    val fragment = checkFragmentExistence(TAG_FAVORITE)
                    changeFragment(fragment ?: FavoritesFragment(), TAG_FAVORITE)
                    true
                }
                R.id.home -> {
                    val fragment = checkFragmentExistence(TAG_HOME)
                    changeFragment(fragment ?: HomeFragment(), TAG_HOME)
                    true
                }
                R.id.watch_later -> {
                    val fragment = checkFragmentExistence(TAG_WATCH_LATER)
                    changeFragment(fragment ?: WatchLaterFragment(), TAG_WATCH_LATER)
                    true
                }
                R.id.selections -> {
                    val fragment = checkFragmentExistence(TAG_SELECTION)
                    changeFragment(fragment ?: SelectionsFragment(), TAG_SELECTION)
                    true
                }
                else -> false
            }
        }
    }

    //метод для проверки существования фрагмента
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    //метод для замены фрагментов по нажатию кнопок нижнего меню
    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    private fun supportFragmentManager() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(FILM_BUNDLE_KEY, film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val COUNT_OF_BACKSTACK_ONE = 1
        private const val TAG_FAVORITE = "favorite"
        private const val TAG_HOME = "home"
        private const val TAG_WATCH_LATER = "watch later"
        private const val TAG_SELECTION = "selection"
        const val FILM_BUNDLE_KEY = "film"
    }
}

