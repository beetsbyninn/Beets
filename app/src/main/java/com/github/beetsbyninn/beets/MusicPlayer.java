package com.github.beetsbyninn.beets;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Handles the Mediaplayer used for playing songs and the sound when taking a step.
 * @author Jonatan Wahlstedt, Oscar Sandberg, Song implementation by Alexander Johansson
 */

public class MusicPlayer {
    private MediaPlayer mSongMediaPlayer;
    private Context mMainActivity;
    private Song mSong;

    public MusicPlayer(Context mainActivity, Song song) {
        mMainActivity = mainActivity;
        mSong = song;
    }

    /**
     * Initializes the StepMediaPlayer and the SongMediaplayer
     */
    public void initSongMediaPlayer() {
        mSongMediaPlayer = MediaPlayer.create(mMainActivity, mSong.getResourceId());
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
}
