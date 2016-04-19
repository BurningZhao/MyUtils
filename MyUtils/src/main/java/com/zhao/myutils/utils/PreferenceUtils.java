package com.zhao.myutils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * PreferencesUtils, easy to get or put data
 */
public class PreferenceUtils {

    /**
     * put string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     */
    public static void setString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(key, value).apply();
    }

    /**
     * get string preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param key   The name of the preference to retrieve
     * @param value Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, value);
    }

    /**
     * put boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getPrefBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param key   The name of the preference to retrieve
     * @param value Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, value);
    }


    /**
     * put int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     */
    public static void setInt(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param key   The name of the preference to retrieve
     * @param value Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, value);
    }

    /**
     * put float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     */
    public static void setFloat(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putFloat(key, value).apply();
    }

    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param key   The name of the preference to retrieve
     * @param value Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, value);
    }

    /**
     * put long preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     */
    public static void setLong(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param key   The name of the preference to retrieve
     * @param value Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, value);
    }

    public static void clearPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.apply();
    }
}
