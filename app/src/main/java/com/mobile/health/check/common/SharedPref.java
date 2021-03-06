package com.mobile.health.check.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KEERTHINI on 8/12/2016.
 */

public class SharedPref {

    private static String STRING_PREF = "Clutto";
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        shared = context.getSharedPreferences(STRING_PREF, Context.MODE_PRIVATE);

    }

    public void setString(String key, String value) {
        editor = shared.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public void setBoolean(String key, boolean value) {
        editor = shared.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return shared.getString(key, null);
    }

    public void setInt(String key, int value) {
        editor = shared.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return shared.getBoolean(key, false);
    }

    public int getInt(String key) {
        return shared.getInt(key, 0);
    }

    public void clearAll() {
        editor = shared.edit();
        editor.clear().commit();
    }
}

