package com.example.barboza_countbook;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sharidanbarboza on 2017-09-25.
 *
 * Taken from https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 * 2017-09-25
 */

public class CustomAdapter extends ArrayAdapter<Counter> {

    private ArrayList<Counter> counters;

    public CustomAdapter(Context context, int resource, ArrayList<Counter> counters) {
        super(context, resource, counters);
        this.counters = counters;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_item, null);
        }

        Counter counter = counters.get(position);

        if (counter != null) {
            TextView nameView = v.findViewById(R.id.nameView);
            TextView currentCountView = v.findViewById(R.id.currentCountView);
            TextView dateView = v.findViewById(R.id.dateView);

            if (nameView != null) {
                nameView.setText(counter.getName());
            }

            if (currentCountView != null) {
                currentCountView.setText(String.valueOf(counter.getCurrentVal()));
            }

            if (dateView != null) {
                dateView.setText(counter.getDate());
            }

        }

        return v;
    }
}
