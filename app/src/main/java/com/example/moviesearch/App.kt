package com.example.moviesearch

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.moviesearch.di.AppComponent
import com.example.moviesearch.di.DaggerAppComponent
import com.example.moviesearch.view.notifications.NotificationConstants

class App : Application() {
    lateinit var dagger: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this

        dagger = DaggerAppComponent.factory().create(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                NotificationConstants.CHANNEL_ID,
                NotificationConstants.CHANNEL_NAME,
                importance
            )
            mChannel.description = NotificationConstants.CHANNEL_DESCRIPTION
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    companion object {
        lateinit var instance: App
            private set
    }
}