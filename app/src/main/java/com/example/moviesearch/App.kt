package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.data.MainRepository
import com.example.moviesearch.domain.Interactor

class App : Application() {
    lateinit var repository: MainRepository
    lateinit var interactor: Interactor

    override fun onCreate() {
        super.onCreate()
        instance = this
        repository = MainRepository()
        interactor = Interactor(repository)
    }

    companion object {
        lateinit var instance: App
            private set
    }
}