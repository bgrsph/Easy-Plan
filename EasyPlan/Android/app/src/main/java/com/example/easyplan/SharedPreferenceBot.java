package com.example.easyplan;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceBot {

    public SharedPreferenceBot() {

    }

    public Object getSharedPref(String key, Activity activity) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(key, "");
        return json;
    }

    public void setSharedPref(String key, Activity activity, Object item) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

}
