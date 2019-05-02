package com.minefield.ec327project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


/**
 *
 */
public class SpUtils {

    @SuppressLint("ApplySharedPref")
    public static void putBoolean(String name, boolean msg) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        Sp.edit().putBoolean(name, msg).commit();
    }

    public static boolean getBoolean(String name, boolean def) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        return Sp.getBoolean(name, def);
    }

    @SuppressLint("ApplySharedPref")
    public static void putString(String name, String msg) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        Sp.edit().putString(name, msg).commit();
    }

    public static String getString(String name, String def) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        if (Sp.getString(name, def).isEmpty())
            return def;
        return Sp.getString(name, def);
    }

    @SuppressLint("ApplySharedPref")
    public static void putInt(String name, int msg) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        Sp.edit().putInt(name, msg).commit();
    }

    public static Integer getInt(String name, int def) {
        SharedPreferences Sp = MyApplication.mAppContext.getSharedPreferences("Sp", Context.MODE_PRIVATE);
        return Sp.getInt(name, def);
    }

}
