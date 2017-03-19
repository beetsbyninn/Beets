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

    /**
     * Return stats.
     * @return
     */
    public int getStat() {
        return stat;
    }

    /**
     * Return songid.
     * @return
     */
    public int getSongId() {
        return songId;
    }


    /**
     * Sets the stat.
     * @param stat
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * Sets the song id.
     * @param songId
     */
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
