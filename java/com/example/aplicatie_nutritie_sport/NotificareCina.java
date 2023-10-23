package com.example.aplicatie_nutritie_sport;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class NotificareCina extends BroadcastReceiver {
    private static final String CHANNEL_ID = "this.is.my.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);//on tap this activity will open

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(300, PendingIntent.FLAG_UPDATE_CURRENT);//getting the pendingIntent

        Notification.Builder builder = new Notification.Builder(context);//building the notification

        Notification notification = builder.setContentTitle("Aplicatie de nutritie si sport")
                .setContentText("Este timpul pentru cina!")
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.dumbbell)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//below creating notification channel, because of androids latest update, O is Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(300, notification);
    }
}

