package com.example.barboza_countbook;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * CustomAdapter
 *
 * Builds a custom adapter that extends ArrayAdapter for displaying the
 * list view of counters on the main opening screen.
 *
 * Created by sharidanbarboza on 2017-09-25.
 */

public class CustomAdapter extends ArrayAdapter<Counter> {

    private ArrayList<Counter> counters;

    /**
     * Constructs a custom adapter.
     * @param context the application context
     * @param resource the resource layout ID
     * @param counters the array list of counters
     */
    public CustomAdapter(Context context, int resource, ArrayList<Counter> counters) {
        super(context, resource, counters);
        this.counters = counters;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set. The
     * View will be inflated from an XML layout file.
     * @param position the index of the item in the adapter data set
     * @param view the list item view in the list view array adapter
     * @param viewGroup the parent that this view will eventually be attached to
     * @return the list item view
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Taken from https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
        // 2017-09-25
        View v = view;
        if (v == null) {
            // Get view from the XML file
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.list_item, null);
        }

        Counter counter = counters.get(position);

        // Set text of counter fields
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
