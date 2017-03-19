package com.github.beetsbyninn.beets;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The service listens for events from the sensor handler.
 */
public class BeetsService extends Service implements StepDetectorListener, ProximityScreenDetector {
    private static final String TAG = "BeetsService";
    private SensorHandler sensorHandler;
    private LocalBinder mBinder = new LocalBinder();
    private MainActivity mListener;

    private Threshold mThreshold;

    public BeetsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;

    }

    /**
     * Themethod creates a new threshold object that should count the score of the song.
     * @param song
     *      Current song to start
     * @param startTime
     *      The start time of the song.
     */
    public void startSong(Song song, long startTime) {
        Log.d(TAG, "startSong: " + song.getSongTitle() + " " + song.getSongArtist());
        sensorHandler.registerListener();
        mThreshold = new Threshold(50.0, 125.0, song, mListener, this);
        mThreshold.startThreshold(startTime);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sensorHandler = new SensorHandler(this, this, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorHandler.onDestroy();
    }

    @Override
    public void onStepDetected() {
        Log.d(TAG, "onStepDetected: ");
        stepTaken();
    }

    /**
     * The method tells the threshold that a step is taken.
     */
    private void stepTaken() {
        long currentTime = System.currentTimeMillis();
        mThreshold.postTimeStamp(currentTime);
    }

    /**
     * Method that turn on/ turn off the screen depending if something is in front of the
     * proximity sensor
     * @param mLuxValueFromSensor
     */
    @Override
    public void onCoverDetected(float mLuxValueFromSensor) {

        PowerManager.WakeLock mWakeLock = null;
        PowerManager mPowerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
        if(mLuxValueFromSensor == 0f) {

           // Toast.makeText(getApplicationContext(), "Proximity on", Toast.LENGTH_LONG).show();
            if (mWakeLock == null) {
                mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "incall");
            }
            if (!mWakeLock.isHeld()) {
                mWakeLock.acquire();


            }
        }else if(mLuxValueFromSensor > 0f){
           //Toast.makeText(getApplicationContext(), "Proximity off", Toast.LENGTH_LONG).show();
            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.release();
            }

        }
    }


    /**
     *  Returns a reference to the service.
     */
    public class LocalBinder extends Binder {
        BeetsService getService() {
            return BeetsService.this;
        }
    }

    /**
     * Set the activity that wants to read steps. The Activity implements a interface called ChangeListner to change the activity.
     * @param listener
     */
    public void setListenerActivity(MainActivity listener) {
        mListener = listener;
        mListener.setSensorHandler(sensorHandler);
    }
}
