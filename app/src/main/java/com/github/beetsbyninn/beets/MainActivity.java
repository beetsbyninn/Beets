package com.github.beetsbyninn.beets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MusicPlayer mMusicPlayer;
    private Button mBtnPlay;
    private Button mBtnStep;
    private boolean mIsplaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnPlay = (Button)findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(new ButtonPlayListener());
        mMusicPlayer = new MusicPlayer(this);
        mMusicPlayer.initSongMediaPlayer();
        try {
            mMusicPlayer.initStepMediaPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBtnStep = (Button)findViewById(R.id.btnStep);
        mBtnStep.setOnClickListener(new ButtonStepListener());


    }

    private class ButtonPlayListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(mIsplaying == false) {
                mMusicPlayer.playSong();
                mIsplaying = true;
            }else if(mIsplaying){
                mMusicPlayer.stopSong();
                mIsplaying = false;
            }
        }
    }

    private class ButtonStepListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                mMusicPlayer.playStep();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
