package com.example.moviesearch.di.modules

import android.content.Context
import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.data.db.DbHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDbHelper(context: Context) = DbHelper(context)

    @Provides
    @Singleton
    fun provideRepository(dbHelper: DbHelper) = MainRepository(dbHelper)
}