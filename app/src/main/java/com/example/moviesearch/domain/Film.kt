package com.example.moviesearch.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(val title: String, val poster: Int, val description: String, var isInFavorites: Boolean = false, val rating: Int = 0) : Parcelable

const val FILM_BUNDLE_KEY = "film"
