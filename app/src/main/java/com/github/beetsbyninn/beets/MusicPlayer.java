package com.github.beetsbyninn.beets;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Handles the Mediaplayer used for playing songs and the sound when taking a step.
 * Created by Jonatan Wahlstedt and Oscar Sandberg on 23-Feb-17.
 */

public class MusicPlayer {
    private MediaPlayer mSongMediaPlayer;
    private MediaPlayer mStepMediaPlayer;
    private Context mMainActivity;

    public MusicPlayer(Context mainActivity){
        mMainActivity = mainActivity;
    }

    /**
     * Initializes the StepMediaPlayer and the SongMediaplayer
     */
    public void initSongMediaPlayer() {
        mSongMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.callmemaybe);


    }

    /**
     * Initializes the StepMediaPlayer and sets the volume on the sound.
     * @throws IOException
     */
    public void initStepMediaPlayer() throws IOException {
        mStepMediaPlayer = MediaPlayer.create(mMainActivity, R.raw.ride);
        mStepMediaPlayer.setVolume(0.9f,0.9f);

    }

    /**
     * Starts the SongMediaplayer and plays the initialized song.
     */
    public void playSong(){
        if(!mSongMediaPlayer.isPlaying()){
            mSongMediaPlayer.start();
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

    /**
     * Uses Mediaplayer to play a sound then the user takes a step.
     * @throws IOException
     */
    public void playStep() throws IOException {
        if(!mStepMediaPlayer.isPlaying()){
            mStepMediaPlayer.start();
        }else if(mStepMediaPlayer.isPlaying()){
            mStepMediaPlayer.stop();
            mStepMediaPlayer.release();
            initStepMediaPlayer();
            mStepMediaPlayer.start();
        }
    }
}
