package com.example.popularmovies.util;


import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceUtils {

    private static final String PREFERENCE = "PREFERENCE";
    private static final String BOOL_CONSTANT = "BOOL_CONSTANT";
    private static final String INT_CONSTANT = "INT_CONSTANT";

    private PreferenceUtils(){}

    public static boolean getBoolPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(BOOL_CONSTANT, true);
    }

    public static void setBoolPreference(Context context, boolean bool) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE, context.MODE_PRIVATE).edit();
        editor.putBoolean(BOOL_CONSTANT, bool);
        editor.commit();
    }

    public static int getIntPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, context.MODE_PRIVATE);
        return sharedPreferences.getInt(INT_CONSTANT, -1);
    }

    public static void setIntPreference(Context context, int integer) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCE, context.MODE_PRIVATE).edit();
        editor.putInt(INT_CONSTANT, integer);
        editor.commit();
    }
}