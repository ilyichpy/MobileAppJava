package com.example.cattleapp.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.cattleapp.activities.MainActivity;

public class FeedingAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String channelId = "feeding_channel";
        String channelName = "Feeding Notifications";
        String animalName = intent.getStringExtra("animalName");

        NotificationChannel channel = new NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setDescription("Уведомления о кормлении животных");

        NotificationManager notificationManager = context.getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(context.getApplicationContext(), "feeding_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Напоминание о кормлении")
                .setContentText("Время кормить: " + (animalName != null ? animalName : "животное"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        try {
            notificationManager.notify(1, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
