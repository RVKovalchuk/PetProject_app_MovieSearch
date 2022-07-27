package com.example.moviesearch.data.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
/*

class PreferencesProvider(context: Context) {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        if (preferences.getBoolean(SharedPreferencesConstants.KEY_FIRST_LAUNCH, false)) {
            preferences.edit() {
                putString(
                    SharedPreferencesConstants.KEY_DEFAULT_CATEGORY,
                    SharedPreferencesConstants.DEFAULT_CATEGORY
                )
            }
            preferences.edit() {
                putBoolean(SharedPreferencesConstants.KEY_FIRST_LAUNCH, false)
            }
        }
    }

    fun saveDefaultCategory(category: String) {
        preferences.edit() {
            putString(SharedPreferencesConstants.KEY_DEFAULT_CATEGORY, category)
        }
    }

    fun getCategory(): String {
        return preferences.getString(
            SharedPreferencesConstants.KEY_DEFAULT_CATEGORY,
            SharedPreferencesConstants.DEFAULT_CATEGORY
        ) ?: SharedPreferencesConstants.DEFAULT_CATEGORY
    }
}*/
