package com.github.beetsbyninn.beets;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * author Patrik Larsson, Ludwig Ninn, Alexander Johansson.
 */
public class Threshold {
    private static final String TAG = "Threshold";

    private int mCurrentMaxScore = 0;
    private double mCurrentScore = 0;
    private long mStartTime;
    private int mBPM;
    private FeedbackListener mFeedBackListener;
    private int mBeatsInInterval;
    private Timer timer;
    private StepBuffer buffer;
    private Worker worker;


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

        buffer = new StepBuffer();
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
        buffer = new StepBuffer();
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
        timer.schedule(new FeedBackTimer(), 0, 10000);
        Log.d(TAG, "startThreshold: ");
        worker = new Worker();
        worker.start();
    }

    /**
     *
     * @param stepTimeStamp§
     */
    public void postTimeStamp(long stepTimeStamp) {
        Log.d(TAG, "postTimeStamp: running " );
        try {
            buffer.add(stepTimeStamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class FeedBackTimer extends TimerTask {

        @Override
        public void run() {
            mFeedBackListener.post10Sec(mCurrentScore / mBeatsInInterval);
            mCurrentScore = 0;
        }
    }

    /**
     * A Thread that is  processing step data from buffer.
     */
    private class Worker extends Thread {
        private long mLastStepTime = 0;
        private static final String TAG = "Worker";
        boolean running;

        @Override
        public void start() {
            super.start();
            running = true;
        }

        public void cancel() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                if (mLastStepTime != 0) {
                    Log.e(TAG, "run: lastStepTime" + mLastStepTime);
                    try {
                        long currentStep = buffer.remove();
                        long difference = Math.abs( 500 -(currentStep - mLastStepTime));
                        Log.d(TAG, "currentStep: " + currentStep);
                        Log.d(TAG, "lastStepTime: " + mLastStepTime);
                        Log.d(TAG, "difference: " + difference);
                        if (difference <= M_PERFECT) {
                            mCurrentScore += 1;
                            Log.e(TAG, "PERFECT");
                        } else if(difference <= M_GOOD) {
                            mCurrentScore += 0.75;
                            Log.e(TAG, "GOOD");
                        } else {
                            mCurrentScore -= 1.25;
                            Log.e(TAG, "FAIL");
                        }
                        mLastStepTime = currentStep;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    mLastStepTime = System.currentTimeMillis();
                }
            }
        }
    }
}
