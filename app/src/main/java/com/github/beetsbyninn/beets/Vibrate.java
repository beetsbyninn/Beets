package com.github.beetsbyninn.beets;

import android.content.Context;
import android.os.Vibrator;

/**
 * Vibrate class provides the project with an instantiatable class that can trigger certain vibration patterns.
 *
 * @author Alexander Johansson (AF2015)
 */
public class Vibrate {
    private Context mContext;
    private Vibrator mVibrator;

    // Constant vibration types
    public static final int VIBRATION_FIRST =1;
    public static final int VIBRATION_SECOND = 2;
    public static final int VIBRATION_THIRD = 3;
    public static final int VIBRATION_FOURTH = 4;
    public static final int VIBRATE_END = 5;


    // TODO: Patterns are not thoroughly tested, needs calibration
    // Constant vibration patterns
   // private static final long[] VIBRATION_PATTERN_FAIL = {0, 100, 50, 100, 50, 100, 50};
   // private static final long[] VIBRATION_PATTERN_GOOD = {0, 100, 30, 100, 30, 100, 30, 70, 50, 150};
   // private static final long[] VIBRATION_PATTERN_PERFECT = {0, 50, 20, 50, 100, 100, 100, 100, 50, 70, 50, 70, 50, 150};
    private static final long[] VIBRATION_PATTERN_FIRST = {0, 100,175,200,250};
    private static final long[] VIBRATION_PATTERN_SECOND = {0, 300, 375,400,450};
    private static final long[] VIBRATION_PATTERN_THIRD = {0, 600,675,700,750};
    private static final long[] VIBRATION_PATTERN_FOURTH = {0, 800,875,900,950};
    private static final long[] VIBRATION_PATTERN_END = {0,2000 ,3000,4000,5000};


    /**
     * Vibrate constructor initiates a Vibrator from system services, requires application context
     * @param context
     */
    public Vibrate(Context context) {
        mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Method checks if the vibrator exists
     * @return boolean true if exists, false if not
     */
    public boolean hasVibrator() {
        return mVibrator.hasVibrator();
    }

    /**
     * Method triggers a vibration that will be provided milliseconds long
     * @param milliseconds long
     */
    public void vibrate(long milliseconds) {
        mVibrator.vibrate(milliseconds);
    }

    /**
     * Method triggers a vibration pattern
     * @param pattern long[]
     * @param repeat int
     */
    public void vibrate(long[] pattern, int repeat) {
        mVibrator.vibrate(pattern, repeat);
    }

    /**
     * Method triggers a preset vibration pattern defined by constants, available constants are:
     *
     * VIBRATION_PATTERN_FAIL
     * VIBRATION_PATTERN_GOOD
     * VIBRATION_PATTERN_PERFECT
     * VIBRATION_PATTERN_START
     * VIBRATION_PATTERN_END
     * VIBRATION_PATTERN_CONTINUOUS
     *
     * @param constant int
     */
    public void vibrate(int constant) {
        switch(constant) {

            case VIBRATION_FIRST:
                mVibrator.vibrate(VIBRATION_PATTERN_FIRST, -1);
                break;
            case VIBRATION_SECOND:
                mVibrator.vibrate(VIBRATION_PATTERN_SECOND, -1);
                break;
            case VIBRATION_THIRD:
                mVibrator.vibrate(VIBRATION_PATTERN_THIRD, -1);
                break;
            case VIBRATION_FOURTH:
                mVibrator.vibrate(VIBRATION_PATTERN_FOURTH, -1);
                break;
            case VIBRATE_END:
                mVibrator.vibrate(VIBRATION_PATTERN_END, -1);
                break;

        }
    }

    /**
     * Method cancels current vibration
     */
    public void cancel() {
        mVibrator.cancel();
    }
}
