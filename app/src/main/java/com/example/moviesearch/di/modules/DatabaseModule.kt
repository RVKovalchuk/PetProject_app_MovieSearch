package com.example.moviesearch.di.modules

import android.content.Context
import androidx.room.Room
import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.data.dataAccessObject.FilmDataAccessObject
import com.example.moviesearch.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "film_db"
    ).build().filmDataAccessObject()

    @Provides
    @Singleton
    fun provideRepository(filmDataAccessObject: FilmDataAccessObject) =
        MainRepository(filmDataAccessObject = filmDataAccessObject)
}