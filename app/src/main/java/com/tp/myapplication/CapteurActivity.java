package com.tp.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class CapteurActivity extends AppCompatActivity implements SensorEventListener {

    final String TAG = "sensor";

    SensorManager mSensorManager;
    private Sensor mAccelerometer; //Accelerometre
    private Sensor mLightSensor; //Capteur de lumiere

    private TextView accSensorText; //Accelerometre
    private TextView lightSensorText; //Capteur de lumiere

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        accSensorText= (TextView) findViewById(R.id.capteur1); //Accelerometre
        lightSensorText= (TextView) findViewById(R.id.capteur2); //Capteur de lumiere

        sensorDetection();

        if (mSensorManager == null) {
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
        if (mSensorManager != null) {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

    }

    void sensorDetection() {
        SensorManager mSensorManager;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        if (deviceSensors != null && !deviceSensors.isEmpty()) {
            for (Sensor mySensor : deviceSensors) {
                Log.v(TAG, "info : " + mySensor.toString());
            }
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
                Log.v(TAG, "info:Accelerometer_found_!");
            } else {
                Log.v(TAG, "info:Accelerometer_not_found");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Many sensors return 3 values , one for each axis .
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            // La valeur de la lumière
            float lv = sensorEvent.values[0];
            lightSensorText.setText(" TimeAcc = " + sensorEvent.timestamp + "\n Light value = " + lv);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float Ax = sensorEvent.values[0];
            float Ay = sensorEvent.values[1];
            float Az = sensorEvent.values[2];

            // Do something with this sensor value .
            accSensorText.setText(" TimeAcc = " + sensorEvent.timestamp + "\n Ax = " + Ax + " " + "\n Ay = " + Ay + " " + "\n Az = " + Az);
            Log.v(TAG, " TimeAcc = " + sensorEvent.timestamp + " Ax = " + Ax + " " + " Ay = " + Ay + " " + " Az = " + Az);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}