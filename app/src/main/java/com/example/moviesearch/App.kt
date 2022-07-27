package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.di.AppComponent
import com.example.moviesearch.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this

        dagger = DaggerAppComponent.factory().create(this)
    }


    companion object {
        lateinit var instance: App
            private set
    }
}