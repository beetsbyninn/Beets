package com.github.beetsbyninn.beets;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patriklarsson on 2017-03-07.
 */

public class StepBuffer {
    private static final String TAG = "StepBuffer";
    private List<Long> stepTimeStamps = new ArrayList<>();
    private long lastStepTime = 0;

    public synchronized long remove() throws InterruptedException {
        while (stepTimeStamps.isEmpty()) {
            wait();
        }

        long timetamp = stepTimeStamps.remove(0);
        notifyAll();
        return timetamp;
    }

    public synchronized void add(long timeStamp) throws InterruptedException {
        while (stepTimeStamps.size() >= 2) {
            wait();
        }
        stepTimeStamps.add(timeStamp);
        lastStepTime = timeStamp;
        notifyAll();
    }
}
