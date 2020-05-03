package com.example.easyplan;

import android.app.Activity;
import android.content.Context;
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

    public Object getSharedPrefC(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(key, "");
        return json;
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

    public void setSharedPrefC(String key, Context context, Object item) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public SharedPreferences sharedPref(Activity activity) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        return appSharedPrefs;
    }

    public void deleteSharedPref(String key, Activity activity) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

}
