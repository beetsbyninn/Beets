package com.github.beetsbyninn.beets;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

/**
 * The class listens for sensor events.
 */
public class SensorHandler implements SensorEventListener {
    private static final String TAG = "SensorHandler";
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mStepDetector;
    private boolean mSensorFlag;

    /**
     * Sets a context reference.
     * @param c
     */
    public SensorHandler(Context c) {
        mContext = c;
    }

    /**
     * Should be called when onCreate gets called from the service. It initializes the sensors used.
     */
    public void onCreate() {
        initSensor();
    }

    /**
     * Checks if the sensor is available and if the sensors are available the stepdector is initlised.
     */
    private void initSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (mStepDetector == null) {
            Toast.makeText(mContext, "Step detector not available", Toast.LENGTH_SHORT).show();
            mSensorFlag = false;
        } else {
            mSensorFlag = true;
        }

    }

    /**
     *
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: Step Detected");
    }

    /**
     *
     * @param sensor
     * @param i
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Registers the sensors
     */
    public void registerListener() {
        mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Should be called i onDestory calls on classes that has a reference to the object.
     */
    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }
}
