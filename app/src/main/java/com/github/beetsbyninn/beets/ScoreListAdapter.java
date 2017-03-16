package com.github.beetsbyninn.beets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Adapter to handle all the scores stored in the Database and present them in a listview.
 * Created by Jonte on 14-Mar-17.
 */

public class ScoreListAdapter extends ArrayAdapter<Score> {
    Context context;
    Score[] data;
    private ArrayList<Song> songlist = new ArrayList<Song>();
    private LayoutInflater inflater = null;
    private TextView tvScore;

    private ScoreListAdapter.ViewHolder viewHolder;

    /**
     * Constructor for SongListAdapter, calls super on ArrayAdapter with supplied list
     * @param context Context
     * @param score ArrayList<Song>
     */
    public ScoreListAdapter(Context context, Score[]score) {
        super(context, 0, score);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get Sensor at position
        Score score = getItem(position);
        // If view is not inflated
        if(convertView == null) {
            // Inflate view with custom list style
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.score_list, parent, false);

            // Init view holder
            viewHolder = new ScoreListAdapter.ViewHolder();
            // Find TextViews in list item style
            viewHolder.tvListScore = (TextView) convertView.findViewById(R.id.tvListScore);

            // Set ViewHolder as tag for inflated view
            convertView.setTag(viewHolder);
        } else {
            // Retrieve tag (ViewHolder)
            viewHolder = (ScoreListAdapter.ViewHolder) convertView.getTag();
        }

        // Set element to Sensor name
        if(score != null) {
            MainActivity activity = (MainActivity)context;
            songlist = activity.getSongList();
            Song song = songlist.get(score.getSongId());
            viewHolder.tvListScore.setText("Song: " + song.getSongTitle() + "\nHighscore: " + score.getStat());



        }
        return convertView;
    }

    /**
     * ViewHolder to retain views, not having to look them up (find) for each re-rendering
     */
    private static class ViewHolder {
        TextView tvListScore;

    }
}
