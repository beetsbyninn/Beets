package com.github.beetsbyninn.beets;

/**
 * Created by Jonte on 13-Mar-17.
 */

public class Score {
    private int stat;
    private String song;

    public Score(int stat, String song){
        this.stat = stat;
        this.song = song;
    }

    public int getStat() {
        return stat;
    }

    public String getSong() {
        return song;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
