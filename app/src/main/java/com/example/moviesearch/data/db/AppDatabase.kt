package com.example.moviesearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesearch.data.dataAccessObject.FilmDataAccessObject
import com.example.moviesearch.data.entity.Film

@Database(entities = [Film::class], version = DatabaseConstants.CURRENT_VERSION_OF_DB, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDataAccessObject() : FilmDataAccessObject
}