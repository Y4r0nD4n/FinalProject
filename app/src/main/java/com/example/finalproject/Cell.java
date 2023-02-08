package com.example.finalproject;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class Cell {
    private String time;
    private String name;
    private boolean onORoff;

    private Context context;
    private Activity activity;
    private ArrayList alarm_name;
    private ArrayList alarm_time;
    private ArrayList alarm_on_or_off;


    private ArrayList repeat;

    public Cell( ArrayList alarm_name, ArrayList alarm_time, /*boolean onORoff*/ArrayList alarm_on_or_off,ArrayList repeat) {
        this.alarm_name = alarm_name;
        this.alarm_time = alarm_time;
        this.alarm_on_or_off = alarm_on_or_off;
        this.repeat = repeat;
    }
    public ArrayList getRepeat() {
        return repeat;
    }

    public void setRepeat(ArrayList repeat) {
        this.repeat = repeat;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(ArrayList alarm_name) {
        this.alarm_name = alarm_name;
    }

    public ArrayList getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(ArrayList alarm_time) {
        this.alarm_time = alarm_time;
    }

    public ArrayList getAlarm_on_or_off() {
        return alarm_on_or_off;
    }

    public void setAlarm_on_or_off(ArrayList alarm_on_or_off) {
        this.alarm_on_or_off = alarm_on_or_off;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public boolean isOnORoff() {
        return onORoff;
    }

    public boolean isCellOnOrOff(int position) {
        if (alarm_on_or_off.get(position).toString().equals("on"))
            return true;

        return false;
    }

    public void ChangeInfoTime(int position, String newTime){
        alarm_time.add(position,newTime);
    }

    public void ChangeInfoSwitch(int position, String newOnOrOff){
            alarm_on_or_off.add(position, newOnOrOff);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnORoff(boolean onORoff) {
        this.onORoff = onORoff;
    }

}
