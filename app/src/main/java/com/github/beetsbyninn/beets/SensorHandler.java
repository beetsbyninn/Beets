package com.github.beetsbyninn.beets;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class SensorHandler implements SensorEventListener {
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mStepDetector;
    private boolean mSensorFlag;

    public SensorHandler(Context c) {
        mContext = c;
        mSensorManager =  (SensorManager)c.getSystemService(Context.SENSOR_SERVICE);
    }

    public void onResume() {
        if (!mSensorFlag) {

        }
    }

    public void onDestory() {
        mSensorManager.unregisterListener(this);
        mSensorFlag = false;
    }

    public void onPause() {

    }

    private void initSensor() {
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (mStepDetector == null) {
            Toast.makeText(mContext, "Step detector not available", Toast.LENGTH_SHORT).show();
            mSensorFlag = false;
        } else {
            mSensorFlag = true;
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
