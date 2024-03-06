package com.softsuave.test;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    @SuppressLint("MissingPermission")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.Gymity",
                    "com.Gymity",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(
                getApplicationContext(),
                MainActivity.class
        );
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );


        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "com.Gymity")
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat.from(getApplicationContext()).notify(Integer.parseInt(String.valueOf(System.currentTimeMillis())), notification.build());
    }
}