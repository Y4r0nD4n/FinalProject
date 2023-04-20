package com.example.finalproject;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.widget.Toast;

import static android.content.Context.ACTIVITY_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
//            boolean isRunning = false;
//            String string = intent.getExtras().getString("extra");
//
//        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
//        {
//
//            if(Music.class.getName().equals(service.service.getClassName())){
//                isRunning = true;
//            }
//        }
//        Intent mIntent = new Intent(context, Music.class);
//        if (string.equals("on") && !isRunning){
//            context.startService(mIntent);
//            MainActivity.activeAlarm = intent.getExtras().getString("active");
//        }else if(string.equals("off")){
//            context.stopService(mIntent);
//            MainActivity.activeAlarm = "";
//        }

        mp= MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();

    }




}
