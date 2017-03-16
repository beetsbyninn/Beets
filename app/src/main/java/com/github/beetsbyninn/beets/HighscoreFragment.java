package com.github.beetsbyninn.beets;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A fragment displaying the hisghscores for every song.
 *
 * @author Jonatan Wahlstedt
 */
public class HighscoreFragment extends Fragment {
    private ListView lvScores;
    private StatsDB db;
    private TextView tvTitle;


    public HighscoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);
        db = new StatsDB(getActivity());
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "street cred.ttf");
        tvTitle = (TextView) view.findViewById(R.id.tvHsTitle);
        tvTitle.setTypeface(typeFace);
        lvScores = (ListView) view.findViewById(R.id.lvScores);
        Score[]scores = db.getAllScores();
        if(scores!=null) {
            lvScores.setAdapter(new ScoreListAdapter(getActivity(), db.getAllScores()));
        }else{
            tvTitle.setText("No Highscores yet!");
        }
        ImageView img = (ImageView) view.findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.initMeny();
            }
        });
        return view;
    }

}
