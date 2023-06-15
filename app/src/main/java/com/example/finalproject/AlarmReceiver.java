package com.example.finalproject;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import static android.content.Context.ACTIVITY_SERVICE;

//import static com.example.finalproject.MainActivity.changeFromOnToOffAfterAlarm;

import androidx.core.content.ContextCompat;

import com.example.finalproject.Tasks.Rewriting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    private static boolean isRunning = false;
    private static PendingIntent pendingIntent;



    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String s = dateFormat.format(calendar.getTime()).toString();

        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        myDB.updateSwitchData(s, "off");


        // vibrator
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);

        // play ringtone
        ringtone.play();

        isRunning = true;

        long vibrationDuration = 2000; // Duration of vibration in milliseconds
        long waitDuration = 1000; // Duration of wait in milliseconds

        // Start the initial vibration
        vibrator.vibrate(vibrationDuration);

        // Start the new activity and pass the PendingIntent as an extra
        Intent newActivityIntent = new Intent(context, Rewriting.class);
        newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        newActivityIntent.putExtra("pendingIntent", pendingIntent);
        context.startActivity(newActivityIntent);

        // Schedule the next vibration with a delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                vibrator.cancel(); // Stop the current vibration

                // Start the next vibration if `isRunning` is still true
                //boolean isRunning =true;
                if (isRunning) {
                    vibrator.vibrate(vibrationDuration);
                    // Schedule the next wait with a delay
                    handler.postDelayed(this, vibrationDuration + waitDuration);
                } else {
                    ringtone.stop(); // Stop the ringtone if `isRunning` is false
                }

                // Additional logic here if needed

            }
        }, vibrationDuration);

        // Additional logic here if needed


    }
    public static void stopAlarm() {
        isRunning = false;
    }
    public static void setPendingIntent(PendingIntent intent) {
        pendingIntent = intent;
    }
    public static boolean isAlarmRunning() {
        return isRunning;
    }


}
