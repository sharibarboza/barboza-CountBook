package com.example.barboza_countbook;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by sharidanbarboza on 2017-09-25.
 */

public class Counters {;
    private ArrayList<Counter> counterList;

    Counters(ArrayList<Counter> counterList) {
        this.counterList = counterList;
    }

    public ArrayList<Counter> getList() {
        return counterList;
    }

    /**
     * Adds a counter to the array list and linked hash map
     * @param counter a Counter object
     */
    public void add(Counter counter) {
        counterList.add(counter);
    }

    /**
     * Deletes a counter from array list and linked hash map
     * @param position the index of the counter in the array
     */
    public void delete(int position) {
        counterList.remove(position);
    }

    /**
     * Gets a counter at the specific position
     * @param position the index of the counter in the array
     * @return a Counter object
     */
    public Counter get(int position) {
        return counterList.get(position);
    }

}
