package com.github.beetsbyninn.beets;

import org.junit.Test;

import java.util.Random;

/**
 * Created by patriklarsson on 2017-03-02.
 */

public class ThresHoldTest {

    public static int perfectMargin = 80;

    public static int goodMargin = 65;

    @Test
    public void test() {
        Random rand = new Random();
        listener mFeedBackListener = new listener();
        Threshold threshold = new Threshold(100, 250, 120, mFeedBackListener);
        for (int i = 0; i < 20; i++) {

        }

    }

    private class listener implements FeedbackListener{

        @Override
        public void post10Sec(double score) {

        }
    }

}
