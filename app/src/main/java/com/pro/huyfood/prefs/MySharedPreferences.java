package com.pro.huyfood.prefs;

import android.content.Context;

public class MySharedPreferences {
    private static final String FRUITY_DROID_PREFERENCES = "MY_PREFERENCES";
    private Context mContext;

    private MySharedPreferences() {
    }

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putLongValue(String key, long n) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, n);
        editor.apply();
    }

    public long getLongValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getLong(key, 0);
    }

    public void putIntValue(String key, int n) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, n);
        editor.apply();
    }

    public int getIntValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getInt(key, 0);
    }

    public void putStringValue(String key, String s) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public String getStringValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getString(key, "");
    }

    public String getStringValue(String key, String defaultValue) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getString(key, defaultValue);
    }

    public void putBooleanValue(String key, Boolean b) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getBoolean(key, false);
    }

    public void putFloatValue(String key, float f) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.apply();
    }

    public float getFloatValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                FRUITY_DROID_PREFERENCES, 0);
        return pref.getFloat(key, 0.0f);
    }
}
