package com.github.beetsbyninn.beets;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

/**
 *
 */
public class SensorHandler implements SensorEventListener {
    private static final String TAG = "SensorHandler";
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mStepDetector;
    private boolean mSensorFlag;

    /**
     *
     * @param c
     */
    public SensorHandler(Context c) {
        mContext = c;
    }


    public void onCreate() {
        initSensor();
    }

    /**
     *
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

    public void registerListener() {
        mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_UI);
    }

    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }
}
