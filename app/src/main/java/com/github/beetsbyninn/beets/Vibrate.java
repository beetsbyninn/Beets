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
    public static final int VIBRATE_FAIL = 1;
    public static final int VIBRATE_GOOD = 2;
    public static final int VIBRATE_PERFECT = 3;

    public static final int VIBRATE_START = 4;
    public static final int VIBRATE_END = 5;
    public static final int VIBRATE_CONTINUOUS_PATTERN = 6;

    // TODO: Patterns are not thoroughly tested, needs calibration
    // Constant vibration patterns
    private static final long[] VIBRATION_PATTERN_FAIL = {0, 100, 50, 100, 50, 100, 50};
    private static final long[] VIBRATION_PATTERN_GOOD = {0, 100, 30, 100, 30, 100, 30, 70, 50, 150};
    private static final long[] VIBRATION_PATTERN_PERFECT = {0, 50, 20, 50, 100, 100, 100, 100, 50, 70, 50, 70, 50, 150};

    private static final long[] VIBRATION_PATTERN_START = {0, 300, 100, 300, 100, 300};
    private static final long[] VIBRATION_PATTERN_END = {0, 1000};
    private static final long[] VIBRATION_PATTERN_CONTINUOUS = {0, 400, 1000};

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
            case VIBRATE_FAIL:
                mVibrator.vibrate(VIBRATION_PATTERN_FAIL, -1);
                break;
            case VIBRATE_GOOD:
                mVibrator.vibrate(VIBRATION_PATTERN_GOOD, -1);
                break;
            case VIBRATE_PERFECT:
                mVibrator.vibrate(VIBRATION_PATTERN_PERFECT, -1);
                break;
            case VIBRATE_START:
                mVibrator.vibrate(VIBRATION_PATTERN_START, -1);
                break;
            case VIBRATE_END:
                mVibrator.vibrate(VIBRATION_PATTERN_END, -1);
                break;
            case VIBRATE_CONTINUOUS_PATTERN:
                mVibrator.vibrate(VIBRATION_PATTERN_CONTINUOUS, 0);
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
