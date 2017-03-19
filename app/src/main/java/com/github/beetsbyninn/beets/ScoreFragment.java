package com.github.beetsbyninn.beets;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Fragment showing the score you got after you finished a song.
 *
 * @author Jonatan and Oscar
 */
public class ScoreFragment extends Fragment {
    private static final String TAG = "ScoreFragment";
    private TextView tvSong, tvScore;
    private ImageView ivThrophy;
    private Score score;
    private StatsDB statsDB;

    public ScoreFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_score, container, false);
        tvSong = (TextView)view.findViewById(R.id.tvSongName);
        tvScore = (TextView)view.findViewById(R.id.tvStat);
        ivThrophy = (ImageView)view.findViewById(R.id.ivTrophy);
        statsDB = new StatsDB(getActivity());
        ImageView img = (ImageView) view.findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
                getActivity().finish();

            }
        });
        getScore();

        return view;
    }

    /**
     * Retrieves the score from the current session and displays it and adds to Database if
     * it's the highest score on the current song.
     */
    public void getScore () {

        if (score != null) {
            MainActivity activity = (MainActivity) getActivity();
            ArrayList<Song> mSonglist= activity.getSongList();
            mSonglist.get(score.getSongId());
            tvSong.setText(String.valueOf(score.getSongId()));
            if(score.getStat()<50){
                ivThrophy.setImageResource(R.drawable.failtrophy);
                activity.playFeedback(9);
            }else if(score.getStat()>=50 && score.getStat()< 75){
                ivThrophy.setImageResource(R.drawable.bronzetrophy);
                activity.playFeedback(6);
            }else if(score.getStat()>=75 && score.getStat()< 90){
                ivThrophy.setImageResource(R.drawable.silvertrophy);
                activity.playFeedback(7);
            }else if(score.getStat()>=90 ){
                ivThrophy.setImageResource(R.drawable.goldtrophy);
                activity.playFeedback(8);
            }
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, score.getStat());
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tvScore.setText(valueAnimator.getAnimatedValue().toString());

                }
            });
            valueAnimator.start();
            // TODO: Kolla om det är den högsta poängen på nuvarande låt, såfall ska den läggas till i DB.
            int highScore = statsDB.getHighScore(score.getSongId());
            if(highScore == 0){
                statsDB.addScore(score);
                tvScore.append(" New Highscore!!");
                Log.d("first score ", String.valueOf(score.getStat()));
            }
            else if(highScore<score.getStat()){
                tvScore.append(" New Highscore!!");
                statsDB.updateHighscore(score);
                Log.d("update score ", String.valueOf(score.getStat()) + " ,songId" + String.valueOf(score.getSongId()));
            }

        } else {
            tvScore.setText("No Match Found");
        }
    }

    /**
     * Set the score.
     * @param score
     */
    public void setScore(Score score) {
        this.score = score;
    }


}
