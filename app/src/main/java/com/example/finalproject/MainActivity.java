package com.example.finalproject;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Interface.RecyclerViewInterface;
import com.example.finalproject.adapters.CellAdapter;
import com.example.finalproject.adapters.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {


    RecyclerView recyclerview;
    FloatingActionButton add_button;

    ArrayList<Cell> cells = new ArrayList<>();

    CellAdapter cellAdapter;
    Calendar cal ;

    int hour, minutes;


    MyDatabaseHelper myDB;
    ArrayList<String> alarm_name, alarm_time, alarm_on_or_off, repeat;

    String refresh;

//    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerview = findViewById(R.id.recyclerview);
        add_button = findViewById(R.id.AddBtn);

        add_button.setOnClickListener(this);

        myDB = new MyDatabaseHelper(MainActivity.this);
        alarm_name =new ArrayList<>();
        alarm_time =new ArrayList<>();
        alarm_on_or_off =new ArrayList<>();
        repeat =new ArrayList<>();


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



    }
    void refreshActivity(){
        if(getIntent().hasExtra("refresh"))
            refresh = getIntent().getStringExtra("refresh");
                if(refresh == "1"){
                            recreate();
                        }

        }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            recreate();
////            startActivity(getIntent());
////            finish();
////            overridePendingTransition(0, 0);
//        }
//    }

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
                repeat.add(cursor.getString(3));

                //set the cell
                cells.add(new Cell(alarm_name, alarm_time, alarm_on_or_off, repeat));

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
        intent.putExtra("repeat",String.valueOf(repeat.get(position)));
        intent.putExtra("position",String.valueOf(position));


        startActivity(intent);
    }

    @Override
    public void onButtonTimeClick(Button btnTime, int position) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minutes = selectedMinute;

                btnTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minutes));



                String name = String.valueOf(alarm_name.get(position));
                String on_or_off = String.valueOf(alarm_on_or_off.get(position));
                String repeatt = String.valueOf(repeat.get(position));
                String time = btnTime.getText().toString();
                String time_position = String.valueOf(alarm_time.get(position));

                Cell currentCell = cells.get(position);
                currentCell.ChangeInfoTime(position, time);


                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.updateData(name.trim(),
                        time.trim(),
                        on_or_off.trim(),
                        repeatt.trim(),
                        time_position);


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

    @Override
    public void onSwitchClick(int position, String flag) {

        String name = String.valueOf(alarm_name.get(position));
        String repeatt = String.valueOf(repeat.get(position));
        String time = String.valueOf(alarm_time.get(position));
        String time_position = String.valueOf(alarm_time.get(position));

//        String on_or_off = String.valueOf(alarm_on_or_off.get(position));


        Cell currentCell = cells.get(position);
        currentCell.ChangeInfoSwitch(position, flag);


        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
        myDB.updateData(name.trim(),
                time.trim(),
                flag.trim(),
                repeatt.trim(),
                time_position);


    }

//    void BrakeTimeToInt(String time){
////        String s;
////        String[] splitTime = time.split(":");
//        int count=0;
//        for(String s : time.split(",")){
//            if(count == 0){
//                hour_something=Integer.parseInt(s);
//                count++;
//            }
//            else
//                minute_something=Integer.parseInt(s);
//
//        }
//    }

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
            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
            myDB.delete( btnTime.getText().toString());

        }
    };



    @Override
    public void onClick(View v) {
        if(v == add_button){
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }

    }
}