package com.example.core_impl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_impl.FilmImpl
import com.example.core_impl.dao.FilmDataAccessObjectImpl

@Database(
    entities = [FilmImpl::class],
    version = FilmDatabase.DatabaseConstants.CURRENT_VERSION_OF_DB,
    exportSchema = false
)

abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDataAccessObject(): FilmDataAccessObjectImpl

    object DatabaseConstants{
        const val CURRENT_VERSION_OF_DB = 1
    }
}