package com.minefield.ec327project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageButton;

//import static android.hardware.Sensor.TYPE_LIGHT;


/**public class main extends AppCompatActivity{
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        sensorManager = (sensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(this, "No light sensor", Toast.LENGTH_SHORT).show();
            finish();
        }

        maxValue = lightSensor.getMaximumRange();


    }
}
*/


public class MainActivity extends Activity implements OnClickListener {

    private ImageButton buttonPLAY;
    //private Button buttonLEVELS;
    //private Button buttonHOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPLAY = findViewById(R.id.buttonMainPLAY);

        buttonPLAY.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case(R.id.buttonMainPLAY):{
                Intent playIntent = new Intent(v.getContext(), HomeScreen.class);
                startActivity(playIntent);

            }
        }
    }

    /*
    private void launchResultActivity(double total, double tipPercent)
    {
        double tip = total * tipPercent;
        double grandTotal = total + tip;

        Intent resultActivity = new Intent(MainActivity.this, Result.class);

        /*Since this method is private, if we want the Result Activity/class to access it's members (the strings TAG_TIP and TAG_GRAND_TOTAL),
         *we can "push" members from the Main Acivity/class to Result, much like how a friend function can "pull" private members from objects

        resultActivity.putExtra(TAG_TIP, tip);
        resultActivity.putExtra(TAG_GRAND_TOTAL, grandTotal);

        Log.d(TAG_DEBUG, "Tip: " + tip);
        Log.d(TAG_DEBUG, "Grand Total: " + grandTotal);

        //Launches the new activity
        startActivity(resultActivity);
    }

    */
}

