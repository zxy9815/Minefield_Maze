package com.minefield.ec327project;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.google.firebase.FirebaseApp;


/**
 * For getting context
 */
public class MyApplication extends MultiDexApplication {
    public static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        MultiDex.install(this);
        FirebaseApp.initializeApp(this);

    }
}
