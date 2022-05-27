package com.example.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.moviesearch.fragments.*
import com.example.moviesearch.recyclerView.FILM_BUNDLE_KEY
import com.example.moviesearch.recyclerView.Film
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val COUNT_OF_BACKSTACK_ONE = 1

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

        bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favorite -> {
                    val tag = "favorite"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: FavoritesFragment(), tag)
                    true
                }
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: HomeFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment?: SelectionsFragment(), tag)
                    true
                }
                else -> false
            }
        }
    }

    //метод для проверки существования фрагмента
    private fun checkFragmentExistence(tag : String) : Fragment? = supportFragmentManager.findFragmentByTag(tag)

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

