package com.github.beetsbyninn.beets;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * SongListAdapter
 *
 * @author Alexander Johansson (AF2015)
 */
public class SongListAdapter extends ArrayAdapter<Song> {
    private ViewHolder viewHolder;

    /**
     * Constructor for SongListAdapter, calls super on ArrayAdapter with supplied list
     * @param context Context
     * @param songs ArrayList<Song>
     */
    public SongListAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get Sensor at position
        Song song = getItem(position);
        // If view is not inflated
        if(convertView == null) {
            // Inflate view with custom list style
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);

            // Init view holder
            viewHolder = new ViewHolder();
            // Find TextViews in list item style
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvArtist = (TextView) convertView.findViewById(R.id.artist);
            viewHolder.tvBpm = (TextView) convertView.findViewById(R.id.song_bpm);
            viewHolder.tvSongLength = (TextView) convertView.findViewById(R.id.song_length);

            // Set ViewHolder as tag for inflated view
            convertView.setTag(viewHolder);
        } else {
            // Retrieve tag (ViewHolder)
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set element to Sensor name
        if(song != null) {
            viewHolder.tvTitle.setText(song.getSongTitle());
            viewHolder.tvArtist.setText(song.getSongArtist());
            viewHolder.tvBpm.setText(Integer.toString(song.getBpm()));

            int totalSecs = song.getSongLength();
            int minutes = (totalSecs % 3600) / 60;
            int seconds = totalSecs % 60;
            String timeString = String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
            viewHolder.tvSongLength.setText(timeString);
        }
        return convertView;
    }

    /**
     * ViewHolder to retain views, not having to look them up (find) for each re-rendering
     */
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvArtist;
        TextView tvBpm;
        TextView tvSongLength;
    }
}
