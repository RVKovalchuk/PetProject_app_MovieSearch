package com.example.moviesearch.view.notifications

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.ApiConstants
import com.example.domain.Film
import com.example.moviesearch.App
import com.example.moviesearch.R
import com.example.moviesearch.utils.ReminderBroadcast
import com.example.moviesearch.view.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

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

    @RequiresApi(Build.VERSION_CODES.M)
    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dayOfMonth,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        film.timeWatchLater = dateTimeInMillis
                        Completable.create {
                            App.instance.dagger.getDbService().insert(film)
                        }.subscribeOn(Schedulers.io())
                            .subscribe {
                                App.instance.dagger.getDbService().getCashedFilms().observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        Toast.makeText(context, "${it}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }
                TimePickerDialog(context, timeSetListener, currentHour, currentMinute, true).show()
            }, currentYear, currentMonth, currentDay
        ).show()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)

        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateTimeInMillis, pendingIntent)
    }
}