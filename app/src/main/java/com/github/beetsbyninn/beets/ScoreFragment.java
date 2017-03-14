package com.github.beetsbyninn.beets;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Fragment showing the score you got after you finished a song.
 *
 * @author Jonatan and Oscar
 */
public class ScoreFragment extends Fragment {

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
        getScore();
        statsDB = new StatsDB(getActivity());
        return view;
    }

    /**
     * Retrieves the score from the current session and displays it and adds to Database if
     * it's the highest score on the current song.
     */
    public void getScore () {

        if (score != null) {
            tvSong.setText(String.valueOf(score.getSong()));
            if(score.getStat()<50){
                ivThrophy.setImageResource(R.drawable.failtrophy);
            }else if(score.getStat()>=50 && score.getStat()< 75){
                ivThrophy.setImageResource(R.drawable.bronzetrophy);
            }else if(score.getStat()>=75 && score.getStat()< 90){
                ivThrophy.setImageResource(R.drawable.silvertrophy);
            }else if(score.getStat()>=90 ){
                ivThrophy.setImageResource(R.drawable.goldtrophy);
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
            statsDB.addScore(score);
        } else {
            tvScore.setText("No Match Found");
        }
    }


    public void setScore(Score score) {
        this.score = score;
    }


}
