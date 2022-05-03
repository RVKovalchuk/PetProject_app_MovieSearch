package com.example.moviesearch

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(val title: String, val poster: Int, val description: String, var isInFavorites: Boolean = false) : Parcelable

const val FILM_BUNDLE_KEY = "film"