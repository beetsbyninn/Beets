package com.github.beetsbyninn.beets;

/**
 * Created by Jonte on 13-Mar-17.
 */

public class Score {
    private int stat;
    private int songId;

    public Score(int stat, int songId){
        this.stat = stat;
        this.songId = songId;
    }

    public int getStat() {
        return stat;
    }

    public int getSongId() {
        return songId;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Override
    public String toString() {
        return "Score{" +
                "stat=" + stat +
                ", songId=" + songId +
                '}';
    }
}
