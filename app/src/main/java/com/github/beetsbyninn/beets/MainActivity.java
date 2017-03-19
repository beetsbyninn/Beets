package com.github.beetsbyninn.beets;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private long timePause;
    private Threshold threshold;
    private SensorHandler sensorHandler;
    private Vibrate mVibrator;
    private static final String TAG = "MainActivity";
    private BeetsService mBeetsService;
    private boolean mBound = false;
    private BeetsServiceConnection mServiceConnection;
    private GaugeFragment gaugeFragment;
    private SongListFragment songListFragment;
    public static ArrayList<Song> mSongList;
    private boolean songEnded = false;
    private ScoreFragment scoreFragment;

    private Song mSong;

    private MusicPlayer mMusicPlayer;
    private boolean mIsplaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSongs();
        Log.d(TAG, "onCreate: SERVICE CONNECTION");
        bindService();
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(songEnded){
            unbind();
            setFragment(scoreFragment,false);
            songEnded = false;
        }
    }


    public void initHighScore(){
        HighscoreFragment  highscoreFragment = new HighscoreFragment();
        setFragment(highscoreFragment, true);
    }

    public void initMeny(){
        MenyFragment  menyFragment = new MenyFragment();
        setFragment(menyFragment, false);
    }

    public void initGaugeFragment() {
        mMusicPlayer = new MusicPlayer(this, mSong);
        mMusicPlayer.initSongMediaPlayer();


        gaugeFragment = new GaugeFragment();
        setFragment(gaugeFragment, false);
    }

    public void initSongs(){
        mSongList = new ArrayList<>();
        mSongList.add(new Song("Shut up and dance", "WALK THE MOON", 128, 195, R.raw.shutup,0));
        mSongList.add(new Song("Call me maybe", "Carly Rae Jepsen", 120, 184, R.raw.callmemaybe,1));
        mSongList.add(new Song("Casin", "Glue70", 104, 79, R.raw.testsongglue,2));
        mSongList.add(new Song("Mary had a little boy", "SNAP!", 121, 217, R.raw.mary,3));
        mSongList.add(new Song("Sex Bomb", "Tom Jones", 123, 210, R.raw.sexbomb,4));



    }




    public void initSongListFragment() {

        songListFragment = new SongListFragment();
        setFragment(songListFragment, false);
    }


    /**
     * Sets a reference to the service
     * @param beetsService
     *      A Reference to the service.
     */
    public void setService(BeetsService beetsService) {
        mBeetsService = beetsService;
    }

    /**
     * Sets a boolean flag if the service is bound.
     * @param bound
     */
    public void setBound(boolean bound) {
        mBound = bound;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound) {
            unbindService(mServiceConnection);
        }
    }

    /**
     * Tries to bind the service.
     */
   public void bindService() {
        mServiceConnection = new BeetsServiceConnection(this);
        Intent intent = new Intent(this, BeetsService.class);
        if (bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "bindService: Service connection succeeded");
            initMeny();
         /*   Log.d(TAG, "bindService: song " + (mSong == null ? "true" : "false"));
            if(mSong != null) {
                Log.d(TAG, "bindService: gauge song title" + mSong.getSongTitle());
                initMeny();
            } else {
                Log.d(TAG, "bindService: list fragment");
                initSongListFragment();
            }*/
        } else {
            Toast.makeText(mBeetsService, "Service connection failed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "bindService: Service connection failed");
        }
    }



    /**
     *
     */
    public void initialise() {
        if(!mIsplaying) {
            mBeetsService.startSong(mSong, System.currentTimeMillis());
            mMusicPlayer.playSong();
            mIsplaying = true;
        } else {
            mMusicPlayer.stopSong();
            mIsplaying = false;
        }
    }


    public void setFragment(Fragment fragment, boolean backstack) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        if (backstack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    /**
     * Updates score with runOnUiThread.
     * Author Ludwig Ninn
     * @param score
     */
    public void update(final double score){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gaugeFragment.updateScore(score);
            }
        });
    }

    /**
     * Pauses the following:
     * Threshold
     * vibrator
     * SensorHandler
     * MusicPlayer
     * Also take a timestamp when the pause is started.
     * Author Ludwig Ninn
     */
    public void pause(){
        timePause = System.currentTimeMillis();
        threshold.pause();
        mVibrator.cancel();
        sensorHandler.pause(false);
        mMusicPlayer.stopSong();
    }

    /**
     * Starts the following:
     * Threshold
     * MusicPlayer
     * Also takes the timePuase from the pause method and sends it to threshold to calculate the amount of time that was paused.
     * Author Ludwig Ninn
     */
    public void start(){
        threshold.start(timePause);
        sensorHandler.pause(true);
        mMusicPlayer.playSong();
    }

    public void setVibrator(Vibrate mVibrator) {
        this.mVibrator = mVibrator;
    }

    public void setSensorHandler(SensorHandler sensorHandler) {
        this.sensorHandler = sensorHandler;
    }
    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }
    public void checkStartPause(){
        gaugeFragment.checkStartPause();
    }

    public void setSong(Song song) {
        mSong = song;
    }

    public ArrayList<Song> getSongList(){

        return mSongList;
    }

    public void playFeedback(int i) {
        try {
            mMusicPlayer.playFeedback(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    public void setSongEnded(boolean b) {
        songEnded = b;
    }

    public void songEnded(Score score) {
        unbind();
        scoreFragment = new ScoreFragment();
        scoreFragment.setScore(score);
        if (gaugeFragment.isScreenOn()) {
            setFragment(scoreFragment, false);
        } else {
            songEnded = true;
        }

    }

    public void unbind(){

        sensorHandler.onDestroy();
        mMusicPlayer.stopSong();
        mServiceConnection.stop();
        if(threshold!=null){
            threshold.destory();
        }



    }
}
