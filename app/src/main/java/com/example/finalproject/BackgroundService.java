package com.example.finalproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {
    private BroadcastReceiver broadcastReceiver;
    MediaPlayer mp;
    private boolean isForeground = false;

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null && "com.example.ALARM_TRIGGERED".equals(intent.getAction())) {
//            // Handle the alarm triggered broadcast
//
//            mp = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
//            mp.start();
//            Toast.makeText(this, "This is from the service", Toast.LENGTH_LONG).show();
//
//            // Perform actions when the alarm is triggered
//        }
//
//        return START_STICKY;
//    }

    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform the desired actions when the alarm trigger is received
        // For example, you can launch your app's main activity

//        Intent launchIntent = new Intent(this, MainActivity.class);
//        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(launchIntent);

        // Set the service as a foreground service to keep it active
        startForeground(NOTIFICATION_ID, createNotification());

        isForeground = true;


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
            return null;
    }


    @Override
    public void onDestroy() {
        // Unregister the BroadcastReceiver when the service is destroyed
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);

        }


        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }

        stopForegroundServices();
        Log.d("Foreground", "this was activated " );


        super.onDestroy();
    }

    private Notification createNotification() {
        // Create a notification channel for Android 8.0 (API level 26) and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "Foreground Service Channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Create a notification for the foreground service
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle("Foreground Service")
                .setContentText("Your app is running in the background")
//                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_SERVICE);

        // Customize the notification here
        // You can add actions, expandable content, and more
        // Set additional flags to make the notification less noticeable
        builder.setOnlyAlertOnce(true);  // Show the notification silently (no sound or vibration)
        return builder.build();
    }

    private void stopForegroundServices() {
        if (isForeground) {
            // Stop the foreground state and remove the notification
            stopForeground(true);

            // Stop the service
            stopSelf();
            Toast.makeText(this, "Foreground was destroyed ", Toast.LENGTH_LONG).show();

            isForeground = false;
        }
    }



}
