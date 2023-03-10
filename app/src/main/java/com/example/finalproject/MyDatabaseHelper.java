package com.example.finalproject;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "AlarmLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_Library";

    private static final String COLUMN_TIME = "alarm_time";
    private static final String COLUMN_NAME = "alarm_name";
    private static final String COLUMN_ON_OR_OFF ="alarm_on_or_off";
    private static final String COLUMN_REPEAT ="repeat";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_TIME + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ON_OR_OFF + " TEXT, " +
                COLUMN_REPEAT + " TEXT);";
        db.execSQL(query);
//להכניס לטבלה את הערכים
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onUpgrade(db,1,1);

        onCreate(db);
    }

    void add(String name, String time, String onoroff, String repeat){
        SQLiteDatabase db = this.getWritableDatabase();
//        onUpgrade(db,1,1);
        //להשתמש למחיקת הטבלה

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_ON_OR_OFF, onoroff);
        cv.put(COLUMN_REPEAT, repeat);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void delete(String position_time){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "alarm_time=?", new String[]{position_time});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }



    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<Cell> list = new ArrayList<Cell>();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

//        cursor.getString(0);
//        if (cursor.moveToFirst()) {
//            do {
//                // on below line we are adding the data from cursor to our array list.
//                list.add(new Cell(cursor.getString(1),
//                        cursor.getString(2),
//                        (cursor.getString(3).equals("off")) ? false : true)
//                        );
//            } while (cursor.moveToNext());
//            // moving our cursor to next.
//        }
        return cursor;
    }
//    MyDatabaseHelper myDB;
//
//    boolean checkPrimaryKey(String time){
//
//        Cursor cursor = myDB.readAllData();
//            int pos=0;
//        while (cursor.moveToNext()){
//            if(time == ){
//
//            }
//        }
//        return false;
//    }


    void updateData(String name, String time, String on_off, String repeat ,String position_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_ON_OR_OFF, on_off);
        cv.put(COLUMN_REPEAT, repeat);

        long result = db.update(TABLE_NAME, cv, "alarm_time=?", new String[]{position_time});
        if(result == -1){
            Toast.makeText(context, "Failed",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Updated Successfully!(VWAT)",
                    Toast.LENGTH_SHORT).show();
        }

    }


}

