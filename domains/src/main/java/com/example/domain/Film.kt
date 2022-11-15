package com.example.domain

import android.os.Parcelable

//Интерфейс общего для модулей класса
interface Film: Parcelable {
    val id: Int
    val title: String?
    val poster: String?
    val description: String?
    var isInFavorites: Boolean
    val rating: Double
    var timeWatchLater : Long
}