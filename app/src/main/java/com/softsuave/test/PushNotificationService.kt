package com.softsuave.test

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.graphics.drawable.toIcon
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushNotificationService : FirebaseMessagingService() {

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title
        val text = remoteMessage.notification?.body

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "YOUR_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.test_logo)
            .setColorized(true)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(this).notify(System.currentTimeMillis().toInt(), notification.build())

    }

    override fun onNewToken(token: String) {
        applicationContext.getSharedPreferences(
            "notificationTest", Context.MODE_PRIVATE
        ).edit {
            putString("token", token)
        }
        super.onNewToken(token)
    }
}