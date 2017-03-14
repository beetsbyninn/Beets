package com.github.beetsbyninn.beets;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SongListFragment extends Fragment {
    private ArrayList<Song> mSongList;

    public SongListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSongList = new ArrayList<>();
        mSongList.add(new Song("Shut up and dance", "WALK THE MOON", 128, 184, R.raw.shutup));
        mSongList.add(new Song("Call me maybe", "Carly Rae Jepsen", 120, 194, R.raw.callmemaybe));

        return inflater.inflate(R.layout.fragment_songlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        SongListAdapter listAdapter = new SongListAdapter(getActivity(), mSongList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // move to gauge
                MainActivity activity = (MainActivity) getActivity();
                Song song = (Song) parent.getItemAtPosition(position);
                activity.setSong(song);
                activity.initGaugeFragment();
            }
        });
    }
}
