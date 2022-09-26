package com.example.core_impl.module

import android.content.Context
import com.example.core_api.DbService
import com.example.core_impl.DataService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FilmDatabaseModule {
    @Singleton
    @Provides
    fun provideFilmDao(context: Context): DbService =
        DataService(context = context)
}