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
    
    public synchronized long remove() throws InterruptedException {

        if (stepTimeStamps.isEmpty()) {
            return -1;
        } else {
            long timetamp = stepTimeStamps.remove(0);
            return timetamp;
        }
    }

    public synchronized void add(long timeStamp) throws InterruptedException {
        stepTimeStamps.add(timeStamp);
    }
}
