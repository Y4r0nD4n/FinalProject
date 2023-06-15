package com.example.finalproject.ObjectSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MyObjectListManager {
    private static final String PREF_NAME = "my_object_list";
    private static final String KEY_OBJECT_LIST = "object_list";

    private SharedPreferences sharedPreferences;

    public MyObjectListManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveObjectList(List<MyObject> objectList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objectList);
        editor.putString(KEY_OBJECT_LIST, json);
        editor.apply();
    }

    public List<MyObject> getObjectList() {
        String json = sharedPreferences.getString(KEY_OBJECT_LIST, "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyObject>>(){}.getType();
        return gson.fromJson(json, type);
    }
}