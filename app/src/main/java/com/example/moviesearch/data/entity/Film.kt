package com.example.moviesearch.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "cashed_films", indices = [Index(value = ["title"], unique = true)])

data class Film(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster: String?,
    @ColumnInfo(name = "description") val description: String,
    var isInFavorites: Boolean = false,
    @ColumnInfo(name = "vote_average") val rating: Double = 0.0
) : Parcelable