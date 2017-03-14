package com.github.beetsbyninn.beets;

/**
 * Song-class holds song data
 *
 * @author Alexander Johansson (AF2015).
 */
public class Song {
    private String mSongTitle;
    private String mSongArtist;

    private int mBpm;
    private int mSongLength;
    private int mResourceId;

    public Song(String title, String artist, int bpm, int songLength, int resourceId) {
        mSongTitle = title;
        mSongArtist = artist;
        mBpm = bpm;
        mSongLength = songLength;
        mResourceId = resourceId;
    }

    public String getSongTitle() {
        return mSongTitle;
    }

    public String getSongArtist() {
        return mSongArtist;
    }

    public int getBpm() {
        return mBpm;
    }

    public int getSongLength() {
        return mSongLength;
    }

    public int getResourceId() {
        return mResourceId;
    }
}
