package com.minefield.ec327project;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LightSensor extends AppCompatActivity {
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startSensor();
    }

    /**
     * 启动传感器。
     */
    public void startSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (mSensorManager == null || mSensor == null) {
            throw new UnsupportedOperationException("设备不支持");
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
                /**
                 * 传感器传回的值。
                 */
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


