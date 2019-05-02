package com.minefield.ec327project;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class LoseScreen extends Activity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 2500);

    }



}
