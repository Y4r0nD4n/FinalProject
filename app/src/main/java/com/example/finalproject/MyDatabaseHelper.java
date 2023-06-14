package com.example.finalproject;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
//    private static final String COLUMN_REPEAT ="repeat";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_TIME + " TEXT PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ON_OR_OFF + " TEXT);";
        db.execSQL(query);
//להכניס לטבלה את הערכים
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onUpgrade(db,1,1);

        onCreate(db);
    }

    void add(String name, String time, String onoroff){
        SQLiteDatabase db = this.getWritableDatabase();
//        onUpgrade(db,1,1);
        //להשתמש למחיקת הטבלה

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_ON_OR_OFF, onoroff);
//        cv.put(COLUMN_REPEAT, repeat);
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
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor getOnOrOff ( String time){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select alarm_on_or_off from my_Library where alarm_time = ?", new String[]{time}, null );
        return cursor;
    }


    void updateData(String name, String time, String on_off ,String position_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TIME, time);
//        cv.put(COLUMN_ON_OR_OFF, on_off);
//        cv.put(COLUMN_REPEAT, repeat);

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
    void clearAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,1);
    }

    void updateSwitchData(String time, String on_off) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        Toast.makeText(context, "this is on or off: "+on_off,
                Toast.LENGTH_SHORT).show();

        cv.put(COLUMN_ON_OR_OFF, on_off);

//        String[] parts = time.split(":", 2);
//        String P1 = parts[0];
//        Log.d(P1, "getAlarm_time_Hour: P1= "+P1);
//        int hour =Integer.parseInt(P1);
//        String P2 = parts[1];
//        Log.d(P2, "getAlarm_time_Minute: P2= "+P2);
//        int min =Integer.parseInt(P2);


//        String whereClause = COLUMN_TIME + " = '" + hour + ":" + min + "'";
//        Cursor cursor = db.rawQuery("Select * from my_Library where alarm_time = ?",new String []{time});
//
//        String whereClause = COLUMN_TIME + " = '" + time + "'";
//        long result = db.update(TABLE_NAME, cv, whereClause,null);
        long result = db.update("my_Library",cv,"alarm_time=?",new String[]{time});


        if (result == -1) {
            Toast.makeText(context, "Failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!(Switch)"+" "+on_off,
                    Toast.LENGTH_SHORT).show();

        }

//        if (cursor.getCount()>0)
//        {
//            if(result==-1)
//                return false;
//            else
//                return true;
//        }
//        else
//            return false;
//    }
    }
}

