package com.minefield.ec327project;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//We tried to implement a light sensor but implementation sometimes causes app to crash


public class LightSensor extends AppCompatActivity {
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startSensor();
    }

    /**
     * start sensor
     */
    public void startSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (mSensorManager == null || mSensor == null) {
            throw new UnsupportedOperationException("Device not supported");
        }

        boolean isRegister = mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (!isRegister) {
            throw new UnsupportedOperationException();
        }
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {

                float value = sensorEvent.values[0];
                System.out.println(value);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(mSensorEventListener);
    }
}


