package com.example.finalproject;

import static com.example.finalproject.AlarmsOperations.SchedulingAlarm.cancelAlarm;
import static com.example.finalproject.AlarmsOperations.SchedulingAlarm.getAlarm_time_Hour;
import static com.example.finalproject.AlarmsOperations.SchedulingAlarm.getAlarm_time_Minute;
import static com.example.finalproject.AlarmsOperations.SchedulingAlarm.scheduleAlarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.finalproject.AlarmsOperations.SchedulingAlarm;

import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    TextView time_input_update_TV;
    EditText name_input_update /*on_or_off_input_update*/;
    Button update_button;
    Activity activity;
    int hour, minutes;


    String name_input_update_insert, time_input_update_insert, on_or_off_input_update_insert, repeat_input_insert;

    String name_update, time_update, on_or_off_update, repeat_update, position;

//    Button button_SU, button_MO, button_TU, button_WE, button_TH, button_FR, button_SA;
//    boolean flag_button_SU = false, flag_button_MO = false, flag_button_TU = false,
//            flag_button_WE = false, flag_button_TH = false, flag_button_FR = false, flag_button_SA = false;


    String savedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input_update = findViewById(R.id.name_input_update);
//        time_input_update = findViewById(R.id.time_input_update);
        time_input_update_TV = findViewById(R.id.time_input_update_TV);
//        on_or_off_input_update = findViewById(R.id.on_or_off_input_update);


        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(this);
        time_input_update_TV.setOnClickListener(this);
        getAndSetIntentData();

        savedTime= getIntent().getStringExtra("alarm_time");


//        button_SU = findViewById(R.id.button_SU);
//        button_MO = findViewById(R.id.button_MO);
//        button_TU = findViewById(R.id.button_TU);
//        button_WE = findViewById(R.id.button_WE);
//        button_TH = findViewById(R.id.button_TH);
//        button_FR = findViewById(R.id.button_FR);
//        button_SA = findViewById(R.id.button_SA);
//
//        button_SU.setOnClickListener(this);
//        button_MO.setOnClickListener(this);
//        button_TU.setOnClickListener(this);
//        button_WE.setOnClickListener(this);
//        button_TH.setOnClickListener(this);
//        button_FR.setOnClickListener(this);
//        button_SA.setOnClickListener(this);



    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("alarm_name") && getIntent().hasExtra("alarm_time") && getIntent().hasExtra("alarm_on_or_off")){
            name_update = getIntent().getStringExtra("alarm_name");
            time_update = getIntent().getStringExtra("alarm_time");
            on_or_off_update = getIntent().getStringExtra("alarm_on_or_off");
            repeat_update = getIntent().getStringExtra("repeat");
            position = getIntent().getStringExtra("position");


            name_input_update.setHint(name_update);
            time_input_update_TV.setText(time_update);
//            setRepeat();
//            on_or_off_input_update.setHint(on_or_off_update);
        }else{
            Toast.makeText(this,"NO DATA",Toast.LENGTH_SHORT).show();
        }
    }

//    private void setRepeat() {
////        repeat_update;
//
//        if (repeat_update.charAt(0) == '1') {flag_button_SU = !flag_button_SU;}
//        if (repeat_update.charAt(1) == '1') {flag_button_MO = !flag_button_MO;}
//        if (repeat_update.charAt(2) == '1') {flag_button_TU = !flag_button_TU;}
//        if (repeat_update.charAt(3) == '1') {flag_button_WE = !flag_button_WE;}
//        if (repeat_update.charAt(4) == '1') {flag_button_TH = !flag_button_TH;}
//        if (repeat_update.charAt(5) == '1') {flag_button_FR = !flag_button_FR;}
//        if (repeat_update.charAt(6) == '1') {flag_button_SA = !flag_button_SA;}
//
//
//    }
//    void checkRepeat() {
//
//        if (flag_button_SU) {
//            repeat_input_insert = "1" + repeat_input_insert.substring(0 + 1);
//        } if (flag_button_MO) {
//            repeat_input_insert = repeat_input_insert.substring(0, 1) + "1" + repeat_input_insert.substring(1 + 1);
//        } if (flag_button_TU) {
//            repeat_input_insert = repeat_input_insert.substring(0, 2) + "1" + repeat_input_insert.substring(2 + 1);
//        } if (flag_button_WE) {
//            repeat_input_insert = repeat_input_insert.substring(0, 3) + "1" + repeat_input_insert.substring(3 + 1);
//        } if (flag_button_TH) {
//            repeat_input_insert = repeat_input_insert.substring(0, 4) + "1" + repeat_input_insert.substring(4 + 1);
//        } if (flag_button_FR) {
//            repeat_input_insert = repeat_input_insert.substring(0, 5) + "1" + repeat_input_insert.substring(5 + 1);
//        } if(flag_button_SA){
//            repeat_input_insert = repeat_input_insert.substring(0, 6) + "1" ;
//        }
//
//
//    }


    void checkAndSetInsertValues_update(){

            switch(name_input_update.getText().toString()){
                case "":
                    name_input_update_insert = name_input_update.getHint().toString();
                    break;
                default:
                    name_input_update_insert = name_input_update.getText().toString();

            }
//            switch(on_or_off_input_update.getText().toString()){
//                case "":
//                    on_or_off_input_update_insert = on_or_off_input_update.getHint().toString();
//                    break;
//                default:
//                    on_or_off_input_update_insert = on_or_off_input_update.getText().toString();
//            }
        repeat_input_insert = "0000000";
//        checkRepeat();
//        repeat_input_insert = repeat_update;
        on_or_off_input_update_insert = on_or_off_update;
        time_input_update_insert = time_input_update_TV.getText().toString();

    }

    @Override
    public void onClick(View view) {
        if(view == update_button){

            if(!savedTime.equals(time_input_update_TV.getText().toString())) {
                Calendar c = Calendar.getInstance();

                int h = SchedulingAlarm.getAlarm_time_Hour(savedTime);
                int m = SchedulingAlarm.getAlarm_time_Minute(savedTime);
                c.set(Calendar.HOUR_OF_DAY, h);
                c.set(Calendar.MINUTE, m);
                c.set(Calendar.SECOND, 0);
                if(c.before(Calendar.getInstance())){
                    c.add(Calendar.DATE,1);
                }

                int id = h * 100 + m;
                SchedulingAlarm.cancelAlarm(c, this, id);



                Calendar calendar = Calendar.getInstance();

                int h2 = SchedulingAlarm.getAlarm_time_Hour(time_input_update_TV.getText().toString());
                int m2 = SchedulingAlarm.getAlarm_time_Minute(time_input_update_TV.getText().toString());
                calendar.set(Calendar.HOUR_OF_DAY, h2);
                calendar.set(Calendar.MINUTE, m2);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.before(Calendar.getInstance())){
                    calendar.add(Calendar.DATE,1);
                }

                int id2 = h2 * 100 + m2;
                SchedulingAlarm.scheduleAlarm(c, this, id2);

            }


            checkAndSetInsertValues_update();
        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
        myDB.updateData(name_input_update_insert.trim(),
                time_input_update_insert.trim(),
                on_or_off_input_update_insert.trim(),
                time_update);



//// refresh database and main activity (and send to)

            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("refresh",1);
//            activity.startActivityForResult(intent, 1);

            startActivity(intent);

        }
        if (view == time_input_update_TV){

            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minutes = selectedMinute;

                    time_input_update_TV.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minutes));
                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minutes,true);

            timePickerDialog.setTitle("Select Time");
            timePickerDialog.show();


        }


    }

//    private void makeAlarmOn() {
//        if(on_or_off_update.equals("off")){
//            on_or_off_update="on";
//        }else{
//
//        }
//    }
}