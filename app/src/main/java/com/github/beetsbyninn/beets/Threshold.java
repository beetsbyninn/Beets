package com.github.beetsbyninn.beets;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author Patrik Larsson, Ludwig Ninn
 */
public class Threshold {
    private static final String TAG = "Threshold";
    
    private int mCurrentMaxScore = 0;
    private double mCurrentScore = 0;
    private long mStartTime;
    private int mBPM;
    private long mLastStepTime;
    private FeedbackListener mFeedBackListener;
    private int mBeatsInInterval;
    private Timer timer;


    /**
     * Constant used for measuring time when step should count as perfect.
     */
    private final int M_PERFECT;

    /**
     * Constant used for measuring time when step should count as good
     */
    private final int M_GOOD;


    /**
     * The constructor sets up the threshold with two constants. The constants are used for calculating
     * score.
     * @param perfect
     *      Maximum time for a step should be counting as perfect.
     * @param good
     *      Maximum time for a step should be counting as perfect.
     * @param mBPM
     * @param mFeedBackListener
     *      A Class implemeting mFeedBackListener.
     *
     */
    public Threshold(int perfect, int good, int mBPM, FeedbackListener mFeedBackListener) {
        this.mBPM = mBPM;
        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;

        mBeatsInInterval = (mBPM / (60)) * 10;
    }

    /**
     * Constructor is only used for testing.
     * @param perfect
     * @param good
     * @param mBPM
     */
    public Threshold(int perfect, int good, int mBPM) {
        this.mBPM = mBPM;
//        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;

        mBeatsInInterval = (mBPM / (60)) * 10;
        Log.d(TAG, "Threshold: " + mBeatsInInterval);

        mFeedBackListener = new FeedbackListener() {

            @Override
            public void post10Sec(double score) {
                Log.d(TAG, "post10Sec: " + score);
            }
        };
        timer = new Timer();
    }

    /**
     * Starts the socre counting    
     * @param startTime
     *      A long with a timestamp.
     */
    public void startThreshold(long startTime) {
        mStartTime = startTime;
        mLastStepTime = startTime; // First step should be at time 0 in song.
        timer.schedule(new FeedBackTimer(), 0, 10000);
        Log.d(TAG, "startThreshold: ");
    }

    /**
     *
     * @param stepTimeStamp
     */
    public void postTimeStamp(long stepTimeStamp) {
        Log.d(TAG, "stepTimeStamp: " + stepTimeStamp);
        Log.d(TAG, "stepTimeStamp: " + mLastStepTime);
        long difference = Math.abs(stepTimeStamp - mLastStepTime);
        Log.d(TAG, "postTimeStamp: " + difference);
        if (difference <= M_PERFECT) {
            mCurrentScore += 1;
            Log.e(TAG, "postTimeStamp: PERFECT: " + mCurrentScore + "/10");
        } else if(difference <= M_GOOD) {
            mCurrentScore += 0.75;
            Log.e(TAG, "postTimeStamp: GOOD: " + mCurrentScore + "/10");
        } else {
            mCurrentScore -= 1.25;
            Log.e(TAG, "postTimeStamp: FAIL: " + mCurrentScore + "/10");
        }
        mLastStepTime = stepTimeStamp;
    }

    private class FeedBackTimer extends TimerTask {

        @Override
        public void run() {
            mFeedBackListener.post10Sec(mCurrentScore / mBeatsInInterval);
            mCurrentScore = 0;
        }
    }
}
