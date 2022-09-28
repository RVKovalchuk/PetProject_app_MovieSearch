package com.example.moviesearch.view.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.ApiConstants
import com.example.domain.Film
import com.example.moviesearch.R
import com.example.moviesearch.view.MainActivity

object NotificationHelper {
    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            NotificationConstants.REQUEST_CODE,
            mIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_menu_watch_later)
            setContentTitle(NotificationConstants.NOTIFICATION_TITLE_WATCH_LATER)
            setContentText(film.description)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }
        val notificationManager = NotificationManagerCompat.from(context)


        Glide.with(context)
            .asBitmap()
            .load(ApiConstants.BASE_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    notificationManager.notify(film.id, builder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        notificationManager.notify(film.id, builder.build())
    }
}