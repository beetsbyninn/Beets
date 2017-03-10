package com.github.beetsbyninn.beets;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

/**
 * The class listens for sensor events.
 */
public class SensorHandler implements SensorEventListener {
    private static final String TAG = "SensorHandler";
    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mStepDetector, mProximitySensor;
    private boolean mSensorFlag;
    private StepDetectorListener mListener;
    private long lastStepTimeStamp;
    private ProximityScreenDetector mProximityDetector;

    /**
     * Sets a context reference.
     * @param c
     */
    public SensorHandler(Context c, StepDetectorListener listener, ProximityScreenDetector coverDetector) {
        mContext = c;
        mListener = listener;
        mProximityDetector = coverDetector;
        onCreate();
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
    public void initSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (mStepDetector == null) {
            mSensorFlag = false;
        } else {
            mSensorFlag = true;
        }

        if (mProximitySensor == null) {
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
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if ((sensorEvent.timestamp - lastStepTimeStamp) > 30000000L) {
                mListener.onStepDetected();
            }
            lastStepTimeStamp = sensorEvent.timestamp;
        }

        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            mProximityDetector.onCoverDetected(sensorEvent.values[0]);
        }
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
        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    /**
     * Should be called i onDestory calls on classes that has a reference to the object.
     */
    public void onDestroy() {
        mSensorManager.unregisterListener(this);
    }
}
