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

    private boolean startCheck;
    private int mCurrentMaxScore = 0;
    private double mCurrentScore = 0;
    private long mStartTime;
    private int mBPM;
    private FeedbackListener mFeedBackListener;
    private Timer timer;
    private Timer mDecSoretimer;
    private Timer mStepTimer;
    private Timer mFeedbackTimer;
    private StepBuffer buffer;
    // private Worker worker;
    private MainActivity mListener;
    private Context mContext;
    private double[] perodicArray;
    private Song mSong;
    private int mSongLength;

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
    private static final int EXCELLENT = 60;
    private static final int DOMINATTING = 70;
    private static final int UNSTOPPABLE = 80;
    private static final int RAMPAGE = 90;
    private static final int GODLIKE = 100;
    private boolean mWarmup = false;
    private int mTotalScore;
    /**
     * Constant used for measuring time when step should count as perfect.
     */
    private final double M_PERFECT;

    /**
     * Constant used for measuring time when step should count as good
     */
    private final double M_GOOD;
    private double intervalLength;
    private int mScoreFeedback = 0;


    /**
     * The constructor sets up the threshold with two constants. The constants are used for calculating
     * score.
     * Author Alexander & Patrik
     *
     * @param perfect           Maximum time for a step should be counting as perfect.
     * @param good              Maximum time for a step should be counting as perfect.
     * @param mBPM
     * @param mFeedBackListener A Class implemeting mFeedBackListener.
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
     * Author Alexander & Patrik
     *
     * @param perfect
     * @param good
     */
    public Threshold(double perfect, double good, Song song, MainActivity listener, Context context) {

//        this.mFeedBackListener = mFeedBackListener;
        M_PERFECT = perfect;
        M_GOOD = good;
        mSong = song;
        mBPM = song.getBpm();
        mSongLength = song.getSongLength();
        Log.d("song info", String.valueOf(mSong.getSongLength()) + " " + String.valueOf(mSong.getBpm()));
        buffer = new StepBuffer();
        mListener = listener;
        mFeedBackListener = new FeedbackListener() {

            @Override
            public void post10Sec(double score) {
                Log.d(TAG, "post10Sec: " + score);
            }
        };
        mListener.setThreshold(this);

        mContext = context;
        mVibrator = new Vibrate(mContext);
        mListener.setVibrator(mVibrator);
        perodicArray = getRhythmPeriodicy(mBPM, mSongLength);
        Log.d(TAG, "Threshold: Array" + Arrays.toString(perodicArray));
    }

    /**
     * DUNNO?
     * Author Aleksander & Patricia
     *
     * @param beatsPerMinute
     * @param songLength
     * @return
     */
    public double[] getRhythmPeriodicy(double beatsPerMinute, int songLength) {
        intervalLength = 60.0 / beatsPerMinute;
        int totalBeatsInSong = (int) Math.ceil(songLength / intervalLength);
        double[] periodicyArray = new double[totalBeatsInSong];
        double currentPeriodicy = 0.0;
        for (int i = 0; i < totalBeatsInSong; i++) {
            periodicyArray[i] = currentPeriodicy;
            currentPeriodicy += intervalLength;
        }

        Log.d(TAG, "getRhythmPeriodicy:" + -(intervalLength - M_PERFECT));
        Log.d(TAG, "getRhythmPeriodicy:" + -(intervalLength - M_GOOD));

        return periodicyArray;
    }

    /**
     * Starts the socre counting
     * Author Alexander, Patrik & Ludwig
     *
     * @param startTime A long with a timestamp.
     */
    public void startThreshold(long startTime) {
        timer = new Timer();
        mDecSoretimer = new Timer();
        mFeedbackTimer = new Timer();
        mStartTime = startTime;
        mStepTimer = new Timer();


        timer.schedule(new FeedBackTimer(), 0, 10000);
        mDecSoretimer.schedule(new DecresePoints(), 0, 5000);
        mFeedbackTimer.schedule(new CheckScore(), 0, 7000);
        mStepTimer.schedule(new StepTimer(), 0, (long) (intervalLength * 1000));
        toggleWarmup();
    }

    /**
     * @param stepTimeStamp§ Author Alexander & Patrik
     */
    public void postTimeStamp(long stepTimeStamp) {
        Log.d(TAG, "postTimeStamp: running ");
        try {
            buffer.add(stepTimeStamp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decrese points if a second has passed if the score is less then the set threshold a vibration will start. The vibration is stronger depending on how low the score.
     * Author Ludwig Ninn
     */
    private class DecresePoints extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            Log.d(TAG, "DecresePoints " + mBarValue);

            if (!mWarmup) {
                long difference = Math.abs(mCurrentStep - mLastStep);
                if (1000 >= difference) {
                    mExpoCount++;
                    if (!startCheck) {
                        mBarValue -= 1.25 * mExpoCount;
                    }

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
            } else {
                toggleWarmup(); // Warmup should be set to false after first time
            }
            startCheck = false;
            mLastStep = mCurrentStep;
        }
    }

    /**
     * Update score. If the score is above 100 or below 0 the score is updated to a fixed amount 100 or 0.
     * Author Ludwig Ninn
     */
    private class FeedBackTimer extends TimerTask {

        @Override
        public void run() {
            mListener.update(mCurrentScore);
            double temp = mBarValue + mCurrentScore;
            if (100 >= temp && 0 <= temp) {
                mBarValue += mCurrentScore;
                mListener.update(mBarValue);
                Log.d(TAG, "barvalue inc: " + mBarValue);
            } else {
                if (temp >= 100) {
                    mBarValue = 100;
                    mListener.update(mBarValue);
                    Log.d(TAG, "barvalue 100: " + mBarValue);
                } else if (temp <= 0) {
                    mBarValue = 0;
                    mListener.update(mBarValue);
                    Log.d(TAG, "barvalue 0: " + mBarValue);
                }
            }

            mCurrentScore = 0;

        }
    }

    /**
     * Calculates if the step was on the correct intervall as the beat
     * Author Alexander & Patrik
     */
    private class StepTimer extends TimerTask {

        @Override
        public void run() {
            try {
                mCurrentStep = buffer.remove();
                double currentTimeInSong = (mCurrentStep - mStartTime) / 1000.0;
                //int currentBeat = getBeatInSong(currentTimeInSong);
                //double differenceNext = (perodicArray[currentBeat + 1] % intervalLength) * 1000.0;
                double difference = (currentTimeInSong % intervalLength) * 1000.0;
//                Log.i(TAG, "timeStamp" + timeStamp);
//                Log.i(TAG, "currentTimeInSong: " + currentTimeInSong);
//                Log.i(TAG, "diff " + difference);
//                Log.i(TAG, "nextdiff: " + differenceNext);
                Log.i(TAG, "run: " + difference);
                if (!mWarmup) {
                    if (difference < M_PERFECT || (intervalLength * 1000) - difference <= M_PERFECT) {
                        mCurrentScore += 1;
                        Log.e(TAG, "PERFECT");
                    } else if (difference < M_GOOD || (intervalLength * 1000) - difference <= M_GOOD) {
                        mCurrentScore += 0.75;
                        Log.e(TAG, "GOOD");
                    } else {
                        Log.e(TAG, "FAIL");
                    }
                    mTotalScore+=mCurrentScore;
                }
                double timeInSong = (System.currentTimeMillis() - mStartTime) / 1000.0;
                Log.d("timeInsong",String.valueOf(timeInSong) + " max: " + mSongLength);
                if(((int) timeInSong) >= mSongLength) {
                    pause();
                    Score score = new Score(mTotalScore,mSong.getId());
                    mListener.setSongEnded(true);
                    mListener.songEnded(score);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calculates the current interval length.
     * Author Alexander & Patrik
     *
     * @param stepTime
     * @return
     */
    private int getBeatInSong(double stepTime) {
        int i = (int) (stepTime / intervalLength);
        Log.d(TAG, "getBeatInSong: " + i);
        return i;
    }

    /**
     * Calaculates the time that was paused and start the timers again.
     * Author Ludwig Ninn
     *
     * @param timePause
     */
    public void start(long timePause) {
        long dif = Math.abs(System.currentTimeMillis() - timePause);
        startThreshold(mStartTime + dif);
        Log.d(TAG, "start: " + mBarValue);
    }

    /**
     * Cancels the timer. Used in the pause function.
     */
    public void pause() {
        startCheck = true;
        Log.d(TAG, "pause: " + mBarValue);
        timer.cancel();
        mDecSoretimer.cancel();
        mFeedbackTimer.cancel();

        mStepTimer.cancel();
    }

    public void destory() {
        startCheck = true;
        timer.cancel();
        mDecSoretimer.cancel();
        mFeedbackTimer.cancel();
        mStepTimer.cancel();
    }

    /**
     * Toggles the warmup flag.
     */
    public void toggleWarmup() {
        mWarmup = !mWarmup;
    }

    private class CheckScore extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            int score = (int) mBarValue;

            if (score <= EXCELLENT && score >= 50) {
                Log.d(TAG, "EXCELLENT: " + score);
                if (mScoreFeedback != 1) {
                    mScoreFeedback = 1;
                    mListener.playFeedback(1);
                }

            } else if (score <= DOMINATTING && score > EXCELLENT) {
                if (mScoreFeedback != 2) {
                    mScoreFeedback = 2;
                    mListener.playFeedback(2);
                }

                Log.d(TAG, "DOMINATTING: " + score);
            } else if (score <= UNSTOPPABLE && score > DOMINATTING) {
                Log.d(TAG, "UNSTOPPABLE: " + score);
                if (mScoreFeedback != 3) {
                    mScoreFeedback = 3;
                    mListener.playFeedback(3);
                }
            } else if (score <= RAMPAGE && score > UNSTOPPABLE) {
                Log.d(TAG, "RAMPAGE: " + score);
                if (mScoreFeedback != 4) {
                    mScoreFeedback = 4;
                    mListener.playFeedback(4);
                }
            } else if (score <= GODLIKE && score > RAMPAGE) {
                Log.d(TAG, "GODLIKE: " + score);
                if (mScoreFeedback != 5) {
                    mScoreFeedback = 5;
                    mListener.playFeedback(5);
                }
            }

        }
    }
}
