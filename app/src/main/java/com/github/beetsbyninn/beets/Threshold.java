package com.github.beetsbyninn.beets;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;
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
    private Timer timer;
    private Timer mStepTimer;
    private StepBuffer buffer;
    private Worker worker;
    private MainActivity mListener;
    private Context mContext;
    private double[] perodicArray;


    /**
     * Constant used for measuring time when step should count as perfect.
     */
    private final double M_PERFECT;

    /**
     * Constant used for measuring time when step should count as good
     */
    private final double M_GOOD;
    private double intervalLength;


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
    public Threshold(double perfect, double good, int mBPM, FeedbackListener mFeedBackListener) {
        this.mBPM = mBPM;
        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;

        buffer = new StepBuffer();
    }

    /**
     * Constructor is only used for testing.
     * @param perfect
     * @param good
     * @param mBPM
     */
    public Threshold(double perfect, double good, int mBPM,int songLength,MainActivity mListener, Context context) {
        this.mBPM = mBPM;
//        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;
        buffer = new StepBuffer();
        this.mListener=mListener;
        mFeedBackListener = new FeedbackListener() {

            @Override
            public void post10Sec(double score) {
                Log.d(TAG, "post10Sec: " + score);
            }
        };
        timer = new Timer();
        mContext = context;

        perodicArray = getRhythmPeriodicy(mBPM, songLength);
        Log.d(TAG, "Threshold: Array" + Arrays.toString(perodicArray));
    }


    public double[] getRhythmPeriodicy(double beatsPerMinute, int songLength) {
        intervalLength = 60.0 / beatsPerMinute;
        int totalBeatsInSong = (int)Math.ceil(songLength / intervalLength);
        double[] periodicyArray = new double[totalBeatsInSong];
        double currentPeriodicy = 0.0;
        for(int i = 0; i < totalBeatsInSong; i++) {
            periodicyArray[i] = currentPeriodicy;
            currentPeriodicy += intervalLength;
        }

        Log.d(TAG, "getRhythmPeriodicy:" + -(intervalLength - M_PERFECT));
        Log.d(TAG, "getRhythmPeriodicy:" + -(intervalLength - M_GOOD));

        return periodicyArray;
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
//        worker = new Worker();
//        worker.start();
        mStepTimer = new Timer();
        mStepTimer.schedule(new StepTimer(), 0, (long)(intervalLength * 1000));
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
//            mFeedBackListener.post10Sec(mCurrentScore / mBeatsInInterval);
            mListener.update(mCurrentScore);
            mCurrentScore = 0;

        }
    }

    private class StepTimer extends TimerTask {

        @Override
        public void run() {
            try {
                long timeStamp = buffer.remove();
                double currentTimeInSong = (timeStamp - mStartTime) / 1000.0;
                int currentBeat = getBeatInSong(currentTimeInSong);
                double differenceNext = (perodicArray[currentBeat + 1]  % intervalLength) * 1000.0;
                double difference = (currentTimeInSong % intervalLength) * 1000.0;
//                Log.i(TAG, "timeStamp" + timeStamp);
//                Log.i(TAG, "currentTimeInSong: " + currentTimeInSong);
//                Log.i(TAG, "diff " + difference);
//                Log.i(TAG, "nextdiff: " + differenceNext);
                Log.i(TAG, "run: " + difference);
                if (difference < M_PERFECT || (intervalLength * 1000) - difference <= M_PERFECT) {
                    Log.e(TAG, "PERFECT");
                } else if (difference < M_GOOD || (intervalLength * 1000) - difference <= M_GOOD) {

                    Log.e(TAG, "GOOD");
                } else {
                    Log.e(TAG, "FAIL");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getBeatInSong(double stepTime) {
        int i = (int) (stepTime / intervalLength);
        Log.d(TAG, "getBeatInSong: " + i);

        return i;
    }

    /**
     * A Thread that is  processing step data from buffer.
     */
    private class Worker extends Thread {
        private long mLastStepTime = 0;
        private static final String TAG = "Worker";
        boolean running;
        private Vibrate mVibrator;

        @Override
        public void start() {
            super.start();
            running = true;
            mVibrator = new Vibrate(mContext);
        }

        public void cancel() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
//                if (mLastStepTime != 0) {
//                    Log.e(TAG, "run: lastStepTime" + mLastStepTime);
//                    try {
//                        long currentStep = buffer.remove();
//                        long difference = Math.abs( 500 -(currentStep - mLastStepTime));
//                        Log.d(TAG, "currentStep: " + currentStep);
//                        Log.d(TAG, "lastStepTime: " + mLastStepTime);
//                        Log.d(TAG, "difference: " + difference);
//                        if (difference <= M_PERFECT) {
//                            mCurrentScore += 1;
//                            mVibrator.vibrate(Vibrate.VIBRATE_PERFECT);
//                            Log.e(TAG, "PERFECT");
//                        } else if(difference <= M_GOOD) {
//                            mCurrentScore += 0.75;
//                            mVibrator.vibrate(Vibrate.VIBRATE_GOOD);
//                            Log.e(TAG, "GOOD");
//                        } else {
//                            mCurrentScore -= 1.25;
//                            mVibrator.vibrate(Vibrate.VIBRATE_FAIL);
//                            Log.e(TAG, "FAIL");
//                        }
//
//
//                        mLastStepTime = currentStep;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    mLastStepTime = System.currentTimeMillis();
//                }


            }
        }
    }
}
