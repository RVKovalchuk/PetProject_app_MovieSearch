package com.example.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val COUNT_OF_BACKSTACK_ONE = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
        supportFragmentManager()
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
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_placeholder, FavoritesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, R.string.title_menu_watch_later, Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, R.string.title_menu_selections, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
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
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }
}

