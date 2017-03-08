package com.github.beetsbyninn.beets;

import android.content.Context;
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
    private MainActivity mListener;
    private Context mContext;

    private long mLastStep;
    private long mCurrentStep;
    private Vibrate mVibrator;
    private double mBarValue = 50;
    private int mExpoCount;
    public static final double VIBRATE_FIRST_FAIL = 40;
    public static final double VIBRATE_SECOND_FAIL = 30;
    public static final double VIBRATE_THIRD_FAIL = 20;
    public static final double VIBRATE_FOURTH_FAIL = 10;
    public static final double VIBRATE_FIFTH_FAIL = 0;
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
     *
     * @param perfect           Maximum time for a step should be counting as perfect.
     * @param good              Maximum time for a step should be counting as perfect.
     * @param mBPM
     * @param mFeedBackListener A Class implemeting mFeedBackListener.
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
     *
     * @param perfect
     * @param good
     * @param mBPM
     */
    public Threshold(int perfect, int good, int mBPM, MainActivity mListener, Context context) {
        this.mBPM = mBPM;
//        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;
        buffer = new StepBuffer();
        mBeatsInInterval = (mBPM / (60)) * 10;
        Log.d(TAG, "Threshold: " + mBeatsInInterval);
        this.mListener = mListener;
        mFeedBackListener = new FeedbackListener() {

            @Override
            public void post10Sec(double score) {
                Log.d(TAG, "post10Sec: " + score);
            }
        };
        timer = new Timer();
        mContext = context;
        mVibrator = new Vibrate(mContext);

    }

    /**
     * Starts the socre counting
     *
     * @param startTime A long with a timestamp.
     */
    public void startThreshold(long startTime) {
        mStartTime = startTime;
        timer.schedule(new FeedBackTimer(), 0, 10000);
        timer.schedule(new DecresePoints(), 0, 5000);
        Log.d(TAG, "startThreshold: ");
        worker = new Worker();
        worker.start();
    }

    /**
     * @param stepTimeStamp§
     */
    public void postTimeStamp(long stepTimeStamp) {
        Log.d(TAG, "postTimeStamp: running ");
        try {
            buffer.add(stepTimeStamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DecresePoints extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            Log.d(TAG, "DecresePoints " + mBarValue);

            long difference = Math.abs(mCurrentStep - mLastStep);
            if (1000 >= difference) {
                mExpoCount++;
                mBarValue -= 1.25 * mExpoCount;
                if (mBarValue < VIBRATE_FIRST_FAIL && mBarValue > VIBRATE_SECOND_FAIL) {
                    mVibrator.vibrate(Vibrate.VIBRATION_FIRST);
                    Log.d(TAG, "Value1: " + mBarValue);
                } else if (mBarValue < VIBRATE_SECOND_FAIL && mBarValue > VIBRATE_THIRD_FAIL) {
                    Log.d(TAG, "Value2: " + mBarValue);
                    mVibrator.vibrate(Vibrate.VIBRATION_SECOND);
                } else if (mBarValue < VIBRATE_THIRD_FAIL && mBarValue > VIBRATE_FOURTH_FAIL) {
                    Log.d(TAG, "Value3: " + mBarValue);
                    mVibrator.vibrate(Vibrate.VIBRATION_THIRD);
                } else if (mBarValue < VIBRATE_FOURTH_FAIL && mBarValue > VIBRATE_FIFTH_FAIL) {
                    Log.d(TAG, "Value4: " + mBarValue);
                    mVibrator.vibrate(Vibrate.VIBRATION_FOURTH);
                } else if (mBarValue < VIBRATE_FIFTH_FAIL) {
                    Log.d(TAG, "Value5: " + mBarValue);
                    mVibrator.vibrate(Vibrate.VIBRATE_END);
                }
            } else {
                mExpoCount = 0;
            }

            mLastStep = mCurrentStep;

        }
    }

    private class FeedBackTimer extends TimerTask {

        @Override
        public void run() {
            mFeedBackListener.post10Sec(mCurrentScore / mBeatsInInterval);
            double temp = mBarValue + mCurrentScore;
            if (100 >= temp && 0 <= temp){
                mBarValue += mCurrentScore;
                mListener.update(mBarValue);
                Log.d(TAG, "barvalue inc: " + mBarValue);
            }else{
                if(temp >= 100){
                    mBarValue = 100;
                    mListener.update(mBarValue);
                    Log.d(TAG, "barvalue 100: " + mBarValue);
                }else if( temp <= 0){
                    mBarValue = 0;
                    mListener.update(mBarValue);
                    Log.d(TAG, "barvalue 0: " + mBarValue);
                }
            }

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
                        mCurrentStep = buffer.remove();
                        long difference = Math.abs(500 - (mCurrentStep - mLastStepTime));
                        Log.d(TAG, "currentStep: " + mCurrentStep);
                        Log.d(TAG, "lastStepTime: " + mLastStepTime);
                        Log.d(TAG, "difference: " + difference);
                        if (difference <= M_PERFECT) {
                            mCurrentScore += 1;
                            //    mVibrator.vibrate(Vibrate.VIBRATE_PERFECT);
                            Log.e(TAG, "PERFECT");
                        } else if (difference <= M_GOOD) {
                            mCurrentScore += 0.75;
                            //   mVibrator.vibrate(Vibrate.VIBRATE_GOOD);
                            Log.e(TAG, "GOOD");
                        } else {
                            //  mVibrator.vibrate(Vibrate.VIBRATE_FAIL);
                            Log.e(TAG, "FAIL");
                        }


                        mLastStepTime = mCurrentStep;
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
