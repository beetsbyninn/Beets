package com.github.beetsbyninn.beets;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GaugeFragment extends Fragment {

    /**
     * Constant used for setting max bar value.
     */
    private final int maxBarValue=100;

    private MainActivity mainActivty;
    private int barValue;
    private TextView tvBar;
    private Button mBtnPlay;

    public GaugeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gauge, container, false);
        tvBar = (TextView)view.findViewById(R.id.tvBar);
        mainActivty = (MainActivity)getActivity();
        mBtnPlay = (Button)view.findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(new ButtonPlayListener());


        return view;
    }

    /**
     * Sets the bar value.
     * @param score
     */
    public void updateScore(double score) {
        if(barValue<=maxBarValue&&barValue>=0){
            barValue+=score;
            tvBar.setText(""+barValue+"/"+maxBarValue);
        }

    }

    private class ButtonPlayListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mainActivty.initalizaise();
        }
    }

}
