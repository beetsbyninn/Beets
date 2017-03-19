package com.github.beetsbyninn.beets;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Shows the meny.
 * A simple {@link Fragment} subclass.
 */
public class MenyFragment extends Fragment {
    private TextView tvBeets;
    private MainActivity mainActivty;
    private Button buttonExit;
    private Button buttonHighScore;
    private Button buttonStartGame;

    public MenyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meny, container, false);
        tvBeets = (TextView) view.findViewById(R.id.tvBeets);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "street cred.ttf");
        tvBeets.setTypeface(typeFace);
        mainActivty = (MainActivity) getActivity();

        buttonStartGame = (Button) view.findViewById(R.id.buttonStartGame);
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivty.initSongListFragment();
            }
        });
        buttonExit = (Button) view.findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivty.finish();
            }
        });
        buttonHighScore = (Button) view.findViewById(R.id.buttonHighScore);
        buttonHighScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivty.initHighScore();

            }
        });
        return view;
    }

}
