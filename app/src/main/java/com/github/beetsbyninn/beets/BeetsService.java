package com.github.beetsbyninn.beets;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The service listens for events from the sensor handler.
 */
public class BeetsService extends Service implements StepDetectorListener {
    private static final String TAG = "BeetsService";
    private SensorHandler sensorHandler = new SensorHandler(this,this);
    private LocalBinder mBinder = new LocalBinder();
    private MusicPlayer mMusicPlayer;
    private ArrayList timeStamps = new ArrayList();
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
     * Themethod createas a new threshold object that should count the score of the song.
     * @param bpm
     *      The bpm of the current song.
     * @param startTime
     *      The start time of the song.
     */
    public void startSong(int bpm, long startTime) {
        sensorHandler.registerListener();
        mThreshold = new Threshold(50.0, 125.0, 128, 184, mListener,this);
        mThreshold.startThreshold(startTime);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        sensorHandler.onCreate();
        mMusicPlayer = new MusicPlayer(this);
        try {
            mMusicPlayer.initStepMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
