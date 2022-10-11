package com.example.myworkmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker (context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    companion object{
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION = 1
    }
    override fun doWork(): Result {
        Log.d("doWork", "doWork: success function called")
        showNotification()
        return Result.success()
    }

    fun showNotification(){
        val oneTimeAlarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My Worker")
            .setContentText("Notification Using WorkManager")
            .setPriority(Notification.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSound(oneTimeAlarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = "channel name"
            val channelDescription = "channel description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDescription
            }
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(applicationContext)){
            notify(NOTIFICATION, notification.build())
        }
    }
}