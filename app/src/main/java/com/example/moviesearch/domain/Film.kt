package com.example.moviesearch.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String,
    val poster: String,
    val description: String,
    var isInFavorites: Boolean = false,
    val rating: Double = 0.0
) : Parcelable