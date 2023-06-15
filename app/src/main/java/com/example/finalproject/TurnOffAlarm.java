package com.example.finalproject;

import static com.example.finalproject.AlarmReceiver.isAlarmRunning;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.finalproject.ObjectSharedPreferences.MyObject;
import com.example.finalproject.ObjectSharedPreferences.MyObjectListManager;

import java.util.List;

public class TurnOffAlarm extends AppCompatActivity {

    private PendingIntent pendingIntent;
//    boolean isRunning1 = AlarmReceiver.isAlarmRunning();

    Switch turn_off;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_off_alarm);



        Intent intent = getIntent();
        if (intent != null) {
            pendingIntent = intent.getParcelableExtra("pendingIntent");
        }

        turn_off = findViewById(R.id.turn_off);
        turn_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                    // Check if the alarm is running
                    if (isAlarmRunning()) {
                        // Stop the alarm
                        AlarmReceiver.stopAlarm();

                        Intent serviceIntent = new Intent(TurnOffAlarm.this, BackgroundService.class);
                        TurnOffAlarm.this.stopService(serviceIntent);

//                        MyDatabaseHelper myDB = new MyDatabaseHelper(TurnOffAlarm.this);
//                        myDB.updateSwitchData(rebuildId( pendingIntent.getIntentSender().getCreatorUid()), "off");

//                        rebuildId( pendingIntent.getIntentSender().getCreatorUid());
//
//
//                        MyObjectListManager objectListManager = new MyObjectListManager(TurnOffAlarm.this);
//
//                        // Retrieve the list of objects
//                        List<MyObject> retrievedList = objectListManager.getObjectList();
//
//                        // Display the retrieved list
//                        for (MyObject object : retrievedList) {
//                            Log.d("MyObject", "ID: " + object.getId() + ", Name: " + object.getName());
//                        }


                        Intent newActivityIntent = new Intent(TurnOffAlarm.this, MainActivity.class);
//                       finish(); // Close the current activity if needed
                        startActivity(newActivityIntent);

                        // Additional logic here if needed


                    }

            }
        });


    }

    public  String rebuildId(int id){

        String s = String.valueOf(id);

        Toast.makeText(this, "this is the ID "+id, Toast.LENGTH_LONG).show();

        return s;
    }

    @Override
    public void onBackPressed() {
        // Check if the alarm is active
        if (isAlarmRunning()) {
            // Do nothing, prevent going back to the previous activity
        } else {
            super.onBackPressed(); // Call the super implementation to allow normal back navigation
        }
    }

//    @Override
//    public void onClick(View view) {
//
//    }

}