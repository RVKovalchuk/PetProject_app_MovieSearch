package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.di.AppComponent

class App : Application() {
    lateinit var dagger: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this

       dagger = DaggerAppComponent.create()
    }


    companion object {
        lateinit var instance: App
            private set
    }
}