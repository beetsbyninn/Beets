package com.github.beetsbyninn.beets;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Handles the Mediaplayer used for playing songs and the sound when taking a step.
 * @author Jonatan Wahlstedt, Oscar Sandberg, Song implementation by Alexander Johansson
 */

public class MusicPlayer {

    private Context mMainActivity;
    private Song mSong;
    private static final int EXCELLENT = 1;
    private static final int DOMINATTING = 2;
    private static final int UNSTOPPABLE = 3;
    private static final int RAMPAGE = 4;
    private static final int GODLIKE = 5;
    private static final int BRONZESOUND = 6;
    private static final int SILVERSOUND= 7;
    private static final int GOLDSOUND= 8;
    private static final int FAILSOUND = 9;
    private static final int STARTSOUND = 10;
    private static final String TAG = "MusicPlayer";
    private MediaPlayer mSongMediaPlayer;
    private MediaPlayer mFeedbackExecellentMediaPlayer;
    private MediaPlayer mFeedbackDominattingMediaPlayer;
    private MediaPlayer mFeedbackUnsstoppableMediaPlayer;
    private MediaPlayer mFeedbackRampageMediaPlayer;
    private MediaPlayer mFeedbackGodlikeMediaPlayer;
    private MediaPlayer mFeedbackbottomfeederMediaPlayer;
    private MediaPlayer mFeedbackbronzeMediaPlayer;
    private MediaPlayer mFeedbacksilverMediaPlayer;
    private MediaPlayer mFeedbackgoldMediaPlayer;
    private MediaPlayer mFeedbackstartMediaPlayer;
    private MediaPlayer mFeedbackfailMediaPlayer;


    public MusicPlayer(Context mainActivity, Song song) {
        mMainActivity = mainActivity;
        mSong = song;
    }

    /**
     * Initializes the StepMediaPlayer and the SongMediaplayer
     */
    public void initSongMediaPlayer() {
        mSongMediaPlayer = MediaPlayer.create(mMainActivity, mSong.getResourceId());
        try {
            initFeedbackMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Starts the SongMediaplayer and plays the initialized song.
     */
    public void playSong(){
        if(!mSongMediaPlayer.isPlaying()){
            mSongMediaPlayer.start();
            try {
                playFeedback(10);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes feedback
     * @throws IOException
     */
    public void initFeedbackMediaPlayer() throws IOException {
        mFeedbackExecellentMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.excellent);
        mFeedbackExecellentMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackDominattingMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.dominating);
        mFeedbackDominattingMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackUnsstoppableMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.unstoppable);
        mFeedbackUnsstoppableMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackRampageMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.rampage);
        mFeedbackRampageMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackGodlikeMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.godlike);
        mFeedbackGodlikeMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackbottomfeederMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.godlike);
        mFeedbackbottomfeederMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackbronzeMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.bronzesound);
        mFeedbackbronzeMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbacksilverMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.silversound);
        mFeedbacksilverMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackgoldMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.goldsound);
        mFeedbackgoldMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackfailMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.failsound);
        mFeedbackfailMediaPlayer.setVolume(0.9f,0.9f);
        mFeedbackstartMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.startsound);
        mFeedbackstartMediaPlayer.setVolume(1.0f,1.0f);



    }

    /**
     * Uses Mediaplayer to play a sound then the user takes a step.
     * @throws IOException
     */
    public void playFeedback(int threshold) throws IOException {
        switch (threshold) {
            case EXCELLENT:
                Log.d(TAG, "EXCELLENT: " + threshold);
                mFeedbackExecellentMediaPlayer.start();
                break;
            case DOMINATTING:
                Log.d(TAG, "DOMINATTING: " + threshold);
                mFeedbackDominattingMediaPlayer.start();
                break;
            case UNSTOPPABLE:
                Log.d(TAG, "UNSTOPPABLE: " + threshold);
                mFeedbackUnsstoppableMediaPlayer.start();
                break;
            case RAMPAGE:
                Log.d(TAG, "RAMPAGE: " + threshold);
                mFeedbackRampageMediaPlayer.start();
                break;
            case GODLIKE:
                Log.d(TAG, "GODLIKE: " + threshold);
                mFeedbackGodlikeMediaPlayer.start();
                break;
            case BRONZESOUND:
                Log.d(TAG, "Bronze: " );
                mFeedbackbronzeMediaPlayer.start();
                break;
            case SILVERSOUND:
                Log.d(TAG, "SILVER: " );
                mFeedbacksilverMediaPlayer.start();
                break;
            case GOLDSOUND:
                Log.d(TAG, "GOLD: " );
                mFeedbackgoldMediaPlayer.start();
                break;
            case FAILSOUND:
                Log.d(TAG, "FAIL: " );
                mFeedbackfailMediaPlayer.start();
                break;
            case STARTSOUND:
                Log.d(TAG, "START: " );
                mFeedbackstartMediaPlayer.start();
                break;


        }

    }
    /**
     * Stops the SongMediaplayer by pausing the song.
     */
    public void stopSong(){
        if(mSongMediaPlayer.isPlaying()){
            mSongMediaPlayer.pause();
            //mSongMediaPlayer.reset();
        }
    }
}
