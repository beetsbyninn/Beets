package com.github.beetsbyninn.beets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter to handle all the scores stored in the Database and present them in a listview.
 * Created by Jonte on 14-Mar-17.
 */

public class ScoreListAdapter extends BaseAdapter {
    Context context;
    Score[] data;
    private LayoutInflater inflater = null;

    public ScoreListAdapter(Context context, Score[] data) {
        this.context = context;
        this.data = data;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = inflater.inflate(R.layout.score_list, null);
        return null;
    }
}
