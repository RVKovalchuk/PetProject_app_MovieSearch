package com.example.moviesearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.moviesearch.R
import com.example.moviesearch.domain.FILM_BUNDLE_KEY
import com.example.moviesearch.domain.Film
import com.example.moviesearch.view.fragments.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val COUNT_OF_BACKSTACK_ONE = 1
private const val TAG_FAVORITE = "favorite"
private const val TAG_HOME = "home"
private const val TAG_WATCH_LATER = "watch later"
private const val TAG_SELECTION = "selection"


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
        initNavigation()
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
        val topAppBar: MaterialToolbar = findViewById<MaterialToolbar>(R.id.top_app_bar_tool)
        val bottomBar: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_app_bar)

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, R.string.title_menu_settings, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
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


}
