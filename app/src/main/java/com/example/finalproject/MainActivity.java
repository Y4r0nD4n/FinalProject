package com.example.finalproject;

import static com.example.finalproject.AlarmReceiver.isAlarmRunning;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.AlarmsOperations.SchedulingAlarm;
import com.example.finalproject.Interface.RecyclerViewInterface;
import com.example.finalproject.Tasks.BarcodeScanActivity;
import com.example.finalproject.Tasks.Rewriting;
import com.example.finalproject.Tasks.Shake;
import com.example.finalproject.adapters.CellAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {

    Button clearalldata;
    TextView ScheduledAlarmsText;
//    public static String activeAlarm = "";
//    private  static final int REQUEST_CODE = 1000;
    RecyclerView recyclerview;
    FloatingActionButton add_button;

    ArrayList<Cell> cells = new ArrayList<>();

    CellAdapter cellAdapter;
    Calendar cal ;
    Calendar calendar = Calendar.getInstance();

    int hour, minutes;


    MyDatabaseHelper myDB;
    ArrayList<String> alarm_name, alarm_time, alarm_on_or_off;

//    String refresh;
////    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScheduledAlarmsText = findViewById(R.id.ScheduledAlarmsText);
        nextAlarm();

        clearalldata = findViewById(R.id.clearalldata);
        clearalldata.setOnClickListener(this);

        recyclerview = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.AddBtn);

        add_button.setOnClickListener(this);

        myDB = new MyDatabaseHelper(MainActivity.this);
        alarm_name =new ArrayList<>();
        alarm_time =new ArrayList<>();
        alarm_on_or_off =new ArrayList<>();
//        repeat =new ArrayList<>();


        recyclerview = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerview.setLayoutManager (layoutManager);

        cellAdapter  = new CellAdapter(cells, this);
        recyclerview.setAdapter(cellAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerview);

        storeDataInArrays();
//        BrakeTimeToInt("12:56");
//        Log.d("check hour_something", "value: "+hour_something);
//        Log.d("check minute_something", "value: "+minute_something);

//        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, this, alarm_time, alarm_name, alarm_on_or_off, this);
//        recyclerview.setAdapter(customAdapter);
//        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    if(isAlarmRunning()){
        Intent intent = new Intent(MainActivity.this, Rewriting.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"NO DATA", Toast.LENGTH_LONG).show();
//            empty_imageview.setVisibility(View.VISIBLE);
//            no_data.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                alarm_time.add(cursor.getString(0));
                alarm_name.add(cursor.getString(1));
                alarm_on_or_off.add(cursor.getString(2));
               // repeat.add(cursor.getString(3));

                //set the cell
                cells.add(new Cell(alarm_name, alarm_time, alarm_on_or_off));

            }
//            empty_imageview.setVisibility(View.GONE);
//            no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this,UpdateActivity.class);

        intent.putExtra("alarm_name",String.valueOf(alarm_name.get(position)));
        intent.putExtra("alarm_time",String.valueOf(alarm_time.get(position)));
        intent.putExtra("alarm_on_or_off",String.valueOf(alarm_on_or_off.get(position)));
        //intent.putExtra("repeat",String.valueOf(repeat.get(position)));
        intent.putExtra("position",String.valueOf(position));


        startActivity(intent);
    }

    @Override
    public void onButtonTimeClick(Button btnTime, int position) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                if (!myDB.isAlarmExists(formattedTime)) {
                    Calendar c = Calendar.getInstance();

                    int h = SchedulingAlarm.getAlarm_time_Hour(btnTime.getText().toString());
                    int m = SchedulingAlarm.getAlarm_time_Minute(btnTime.getText().toString());
                    c.set(Calendar.HOUR_OF_DAY, h);
                    c.set(Calendar.MINUTE, m);
                    c.set(Calendar.SECOND, 0);

                    if (c.before(Calendar.getInstance())) {
                        c.add(Calendar.DATE, 1);
                    }


                    int id = h * 100 + m;
                    SchedulingAlarm.cancelAlarm(c, MainActivity.this, id);

                    hour = selectedHour;
                    minutes = selectedMinute;

                    btnTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));


                    String name = String.valueOf(alarm_name.get(position));
                    String on_or_off = String.valueOf(alarm_on_or_off.get(position));
//                String repeatt = String.valueOf(repeat.get(position));
                    String time = btnTime.getText().toString();
                    String time_position = String.valueOf(alarm_time.get(position));

                    Cell currentCell = cells.get(position);
                    currentCell.ChangeInfoTime(position, time);


                    MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                    myDB.updateData(name.trim(),
                            time.trim(),
                            on_or_off.trim(),
                            time_position);


                    String flag = currentCell.isCellOnOrOff(position) ? "on" : "off";
                    if (flag.equals("on")) {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        calendar.set(Calendar.SECOND, 0);
                        if (calendar.before(Calendar.getInstance())) {
                            calendar.add(Calendar.DATE, 1);
                        }

                        int id2 = selectedHour * 100 + selectedMinute;
                        Log.d("IDSET2", "onClick: IDSET " + id2);

                        SchedulingAlarm.scheduleAlarm(calendar, MainActivity.this, id2);
                        nextAlarm();

                    }


                }
                else
                    Toast.makeText(MainActivity.this, "An alarm with the same time already exists", Toast.LENGTH_SHORT).show();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minutes,true);

        timePickerDialog.setTitle("Select Time");
//        String.valueOf(alarm_time.get(position));

        cal = Calendar.getInstance();
        int minute_now = cal.get(Calendar.MINUTE);
        int hour_now = cal.get(Calendar.HOUR_OF_DAY);
        timePickerDialog.updateTime(hour_now, minute_now);

        timePickerDialog.show();
    }
//    StringBuffer stringBuffer;
    @Override
    public void onSwitchClick(int position) {

//        String name = String.valueOf(alarm_name.get(position));
//        String repeatt = String.valueOf(repeat.get(position));
        String time = String.valueOf(alarm_time.get(position));
//        String time_position = String.valueOf(alarm_time.get(position));

//
//        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
//         stringBuffer = null;
//        Cursor cursor = myDatabaseHelper.getOnOrOff(time);
//
//
//        if (cursor.getCount()!=0){
//            stringBuffer = new StringBuffer();
//            while (cursor.moveToNext()){
//                stringBuffer = stringBuffer.append(cursor.getString(0));
//            }
//        }
        Cell currentCell = cells.get(position);

        String flag = currentCell.isCellOnOrOff(position)? "on":"off";
//        String flag1 = String.valueOf(stringBuffer);

        if(flag.equals("on")){
            Log.d("SwitchState", "The Switch is " + flag);
//            BtnSwitch.setChecked(true);
            flag = "off";
        }
        else{
            Log.d("SwitchState", "The Switch is " + flag);
            flag = "on";


        }


//        Cell currentCell = cells.get(position);
//        String flag1 = currentCell.isCellOnOrOff(position)? "off":"on";
//
        currentCell.ChangeInfoSwitch(position, flag);


        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
        myDB.updateSwitchData(time.trim(),flag);
//        myDB.updateData(name.trim(),
//                time.trim(),
//                flag.trim(),
//               time_position);


    
//        final  Intent serviceIntent = new Intent(this, AlarmReceiver.class);
        Calendar c = Calendar.getInstance();
        int h = SchedulingAlarm.getAlarm_time_Hour(time);
        int m =  SchedulingAlarm.getAlarm_time_Minute(time);
        c.set(Calendar.HOUR_OF_DAY, h);
        c.set(Calendar.MINUTE,m );
        c.set(Calendar.SECOND,0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1);
        }

        int id = h*100+m;

        if (flag.equals("off")) {

            SchedulingAlarm.cancelAlarm(c,MainActivity.this, id);
            nextAlarm();

//            serviceIntent.putExtra("extra","off");
        }
        else {
            SchedulingAlarm.scheduleAlarm(c, MainActivity.this, id);
            nextAlarm();

//
//            serviceIntent.putExtra("extra","on");

        }

    }

    //    Context context = this;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            final ColorDrawable background = new ColorDrawable(Color.RED);
            View itemView =  viewHolder.itemView;


            background.setBounds(0, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
            background.draw(c);

//                Drawable icon = ContextCompat.getDrawable(context, R.drawable.garbage_bin);
//                icon.setBounds(1, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
//                icon.draw(c);
//            Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24);
//            icon.setBounds(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop(), viewHolder.itemView.getLeft(), viewHolder.itemView.getBottom() );
////// compute top and left margin to the view bounds
////            icon.setBounds(viewHolder.itemView.getRight() - iconHorizontalMargin, top, viewHolder.itemView.getRight() - iconHorizontalMargin, top + icon.getIntrinsicHeight());
//            icon.draw(c);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            cells.remove(viewHolder.getAdapterPosition());
            cellAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            View itemView =  viewHolder.itemView;
            Button btnTime = itemView.findViewById(R.id.btnTime);

            String position_time = btnTime.getText().toString();


            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
            myDB.delete( position_time);


            // Cancel the deleted alarm
            Calendar c = Calendar.getInstance();
            int h = SchedulingAlarm.getAlarm_time_Hour(position_time);
            int m =  SchedulingAlarm.getAlarm_time_Minute(position_time);
            c.set(Calendar.HOUR_OF_DAY, h);
            c.set(Calendar.MINUTE,m );
            c.set(Calendar.SECOND,0);

            if(c.before(Calendar.getInstance())){
                c.add(Calendar.DATE,1);
            }

            int id = h*100+m;
//            AlarmScheduler.cancelAlarm(context, position_time);
            SchedulingAlarm.cancelAlarm(c,MainActivity.this, id);

            nextAlarm();
            Intent serviceIntent = new Intent(MainActivity.this, BackgroundService.class);
            MainActivity.this.stopService(serviceIntent);
        }
    };



    @Override
    public void onClick(View v) {
        if(v == add_button){
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }
        if(v == clearalldata){
            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
            myDB.clearAllData();
            recreate();

        }


    }



    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertDialog =new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = getLayoutInflater();

        final View v = layoutInflater.inflate(R.layout.dialog_alert, null);

        alertDialog.setView(v);

        Button yes, no;

        yes = v.findViewById(R.id.yes_btn);
        no = v.findViewById(R.id.no_btn);

        AlertDialog alert = alertDialog.create();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
                finishAffinity ();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });



        alert.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.about){
            Intent intent = new Intent(this,About.class);

            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    public void nextAlarm(){
        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(this);
        List<String> savedKeys = databaseHelper.getSavedKeys();


        String smallestTime = null;

// Iterate through the list of saved keys
        for (String key : savedKeys) {
            if (smallestTime == null) {
                smallestTime = key;
            } else {
                smallestTime = getSmallerTime(smallestTime, key);
            }
        }
//        Toast.makeText(this, "key "+smallestTime, Toast.LENGTH_LONG).show();
        if( smallestTime ==  null)
            smallestTime ="never";

        ScheduledAlarmsText.setText("The next alarm is at "+ smallestTime);
    }

    private static String getSmallerTime(String time1, String time2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);

            if (date1.before(date2)) {
                return time1;
            } else {
                return time2;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parse exception if necessary
        }

        return ""; // Return a default value if comparison fails
    }}

