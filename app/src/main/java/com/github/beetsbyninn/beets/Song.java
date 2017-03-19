package com.github.beetsbyninn.beets;

/**
 * Song-class holds song data
 *
 * @author Alexander Johansson (AF2015).
 */
public class Song {
    private String mSongTitle;
    private String mSongArtist;
    private double maxScore;
    private int mId;
    private int mBpm;
    private int mSongLength;
    private int mResourceId;

    public Song(String title, String artist, int bpm, int songLength, int resourceId, int id) {
        mSongTitle = title;
        mSongArtist = artist;
        mBpm = bpm;
        mSongLength = songLength;
        maxScore = songLength/(60.0/bpm);
        mResourceId = resourceId;
        mId = id;
    }

    /**
     * Returns song title.
     * @return
     */
    public String getSongTitle() {
        return mSongTitle;
    }

    /**
     * Returns song artist.
     * @return
     */
    public String getSongArtist() {
        return mSongArtist;
    }

    /**
     * Returns beats per minut.
     * @return
     */
    public int getBpm() {
        return mBpm;
    }

    /**
     * Returns snglength.
     * @return
     */
    public int getSongLength() {
        return mSongLength;
    }

    /**
     * Returns resource id.
     * @return
     */
    public int getResourceId() {
        return mResourceId;
    }

    /**
     * Returns id
     * @return
     */
    public int getId(){
        return mId;
    }

    public double getMaxScore(){
        return maxScore;
    }
}
