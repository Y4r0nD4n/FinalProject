package com.example.finalproject.AlarmsOperations;

//import static com.example.finalproject.AlarmReceiver.isRunning;

//import static com.example.finalproject.AlarmReceiver.startBackgroundService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.finalproject.AlarmReceiver;
import com.example.finalproject.BackgroundService;
import com.example.finalproject.Cell;
import com.example.finalproject.MyDatabaseHelper;
import com.example.finalproject.ObjectSharedPreferences.MyObject;
import com.example.finalproject.ObjectSharedPreferences.MyObjectListManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SchedulingAlarm {



    public static void scheduleAlarm (Calendar calendar,Context context,int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE );
//
//        if (alarmManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            } else {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            }
//        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        AlarmReceiver.setPendingIntent(pendingIntent);


        startBackgroundService(context);

        Toast.makeText(context, "Alarm set ", Toast.LENGTH_LONG).show();



    }

    public static void startBackgroundService(Context context) {
        Intent intent = new Intent(context, BackgroundService.class);
        context.startService(intent);
    }

    public static void cancelAlarm (Calendar calendar,Context context,int id){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE );
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, "Alarm canceled ", Toast.LENGTH_LONG).show();

        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context);
        List<String> keys = databaseHelper.getSavedKeys();

        if (keys.size()==0) {
            Intent serviceIntent = new Intent(context, BackgroundService.class);
            context.stopService(serviceIntent);
            Toast.makeText(context, "keys.isEmpty() -> stopService ", Toast.LENGTH_LONG).show();

        }
    }

    public static int getAlarm_time_Minute(String s){
        String[] parts = s.split(":", 2);
        String P2 = parts[1];
        Log.d(P2, "getAlarm_time_Minute: P2= "+P2);
        int min =Integer.parseInt(P2);
        return min;
    }
    public static int getAlarm_time_Hour(String s){
        String[] parts = s.split(":", 2);
        String P1 = parts[0];
        Log.d(P1, "getAlarm_time_Hour: P1= "+P1);
        int hour =Integer.parseInt(P1);
        return hour;
    }


}
