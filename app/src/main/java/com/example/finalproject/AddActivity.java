package com.example.finalproject;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Locale;



public class AddActivity extends AppCompatActivity implements View.OnClickListener {


    EditText name_input, time_input/*, on_or_off_input*/;
    Button add_button;
    TextView time_input_TV;
    int hour, minutes;
    int hour_now, minute_now;
    Calendar cal = Calendar.getInstance();
    String name_input_insert, time_input_insert, on_or_off_input_insert, repeat_input_insert;

    Button button_SU, button_MO, button_TU, button_WE, button_TH, button_FR, button_SA;
    boolean flag_button_SU = false, flag_button_MO = false, flag_button_TU = false,
            flag_button_WE = false, flag_button_TH = false, flag_button_FR = false, flag_button_SA = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        minute_now = cal.get(Calendar.MINUTE);
        hour_now = cal.get(Calendar.HOUR_OF_DAY);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        //String timeCurrently = hour_now+":"+minute_now;

        name_input = findViewById(R.id.name_input);
//        on_or_off_input = findViewById(R.id.on_or_off_input);

        add_button = findViewById(R.id.add_button);

        time_input_TV = findViewById(R.id.time_input_TV);
        time_input_TV.setText(/*timeCurrently  - works just the same*/currentTime);

        add_button.setOnClickListener(this);
        time_input_TV.setOnClickListener(this);
        time_input_TV.performClick();

        button_SU = findViewById(R.id.button_SU);
        button_MO = findViewById(R.id.button_MO);
        button_TU = findViewById(R.id.button_TU);
        button_WE = findViewById(R.id.button_WE);
        button_TH = findViewById(R.id.button_TH);
        button_FR = findViewById(R.id.button_FR);
        button_SA = findViewById(R.id.button_SA);

        button_SU.setOnClickListener(this);
        button_MO.setOnClickListener(this);
        button_TU.setOnClickListener(this);
        button_WE.setOnClickListener(this);
        button_TH.setOnClickListener(this);
        button_FR.setOnClickListener(this);
        button_SA.setOnClickListener(this);



    }

    void checkAndSetInsertValues_add(){
        switch(name_input.getText().toString()){
            case "":
                name_input_insert = "My Alarm";
                break;
            default:
                name_input_insert = name_input.getText().toString();

        }

        repeat_input_insert = "0000000";
        checkRepeat();
        on_or_off_input_insert ="on";
        time_input_insert = time_input_TV.getText().toString();

    }

     void checkRepeat() {

         if (flag_button_SU) {
             repeat_input_insert = "1" + repeat_input_insert.substring(0 + 1);
         } if (flag_button_MO) {
             repeat_input_insert = repeat_input_insert.substring(0, 1) + "1" + repeat_input_insert.substring(1 + 1);
         } if (flag_button_TU) {
             repeat_input_insert = repeat_input_insert.substring(0, 2) + "1" + repeat_input_insert.substring(2 + 1);
         } if (flag_button_WE) {
             repeat_input_insert = repeat_input_insert.substring(0, 3) + "1" + repeat_input_insert.substring(3 + 1);
         } if (flag_button_TH) {
             repeat_input_insert = repeat_input_insert.substring(0, 4) + "1" + repeat_input_insert.substring(4 + 1);
         } if (flag_button_FR) {
             repeat_input_insert = repeat_input_insert.substring(0, 5) + "1" + repeat_input_insert.substring(5 + 1);
         } if(flag_button_SA){
             repeat_input_insert = repeat_input_insert.substring(0, 6) + "1" ;
         }


     }


    @Override
    public void onClick(View view) {
        if (view == add_button) {
            checkAndSetInsertValues_add();
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
            myDB.add(name_input_insert.trim(),
                    time_input_insert.trim(),
                    on_or_off_input_insert.trim(),
                    repeat_input_insert);

//// refresh database and main activity (and send to)

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("refresh",1);

            startActivity(intent);
        }

        if (view == time_input_TV){
            TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                    hour = selectedHour;
                    minutes = selectedMinute;

                    time_input_TV.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minutes));
                }
            };

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minutes,true);

            timePickerDialog.setTitle("Select Time");
            timePickerDialog.updateTime(hour_now, minute_now);
            timePickerDialog.show();
        }

        if(view == button_SU || view == button_MO || view == button_TU
                || view == button_WE || view == button_TH || view == button_FR || view == button_SA){

            if (view == button_SU) {
                flag_button_SU = !flag_button_SU;

            } else if (view == button_MO) {
                flag_button_MO = !flag_button_MO;
            } else if (view == button_TU) {
                flag_button_TU = !flag_button_TU;
            } else if (view == button_WE) {
                flag_button_WE = !flag_button_WE;
            } else if (view == button_TH) {
                flag_button_TH = !flag_button_TH;
            } else if (view == button_FR) {
                flag_button_FR = !flag_button_FR;
            } else {
                flag_button_SA = !flag_button_SA;
            }
//            if(view == button_SU){
//                if(flag_button_SU) {
//                    flag_button_SU = false;
//                }else
//                    flag_button_SU = true;
//            }
//            else if(view == button_MO){
//                if(flag_button_MO) {
//                    flag_button_MO = false;
//                }else
//                    flag_button_MO = true;
//            }
//            else if(view == button_TU){
//                if(flag_button_TU) {
//                    flag_button_TU = false;
//                }else
//                    flag_button_TU = true;
//            }
//            else if(view == button_WE){
//                if(flag_button_WE) {
//                    flag_button_WE = false;
//                }else
//                    flag_button_WE = true;
//            }
//            else if(view == button_TH){
//                if(flag_button_TH) {
//                    flag_button_TH = false;
//                }else
//                    flag_button_TH = true;
//            }
//            else if(view == button_FR){
//                if(flag_button_FR) {
//                    flag_button_FR = false;
//                }else
//                    flag_button_FR = true;
//            }
//            else {
//                if(flag_button_SA) {
//                    flag_button_SA = false;
//                    button_SA.setBackgroundColor(Color.RED);
//                }else {
//                    flag_button_SA = true;
////                    button_SA.setBackground(Color.GREEN);
//                }
//            }
        }
    }

}