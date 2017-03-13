package com.github.beetsbyninn.beets;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    private TextView tvSong, tvScore;
    private ImageView ivThrophy;

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
        return view;
    }

    public void getScore () {
        StatsDB dbHandler = new StatsDB(getActivity());
        Score score = dbHandler.getScore("dance-with-me");

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
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tvScore.setText(valueAnimator.getAnimatedValue().toString());

                }
            });
            valueAnimator.start();
        } else {
            tvScore.setText("No Match Found");
        }
    }


}
