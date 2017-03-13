package com.github.beetsbyninn.beets;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    private TextView tvSong, tvScore;

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
        getScore();
        return view;
    }

    public void getScore () {
        StatsDB dbHandler = new StatsDB(getActivity());
        Score score = dbHandler.getScore(productBox.getText().toString());

        if (score != null) {
            tvSong.setText(String.valueOf(score.getSong()));
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
