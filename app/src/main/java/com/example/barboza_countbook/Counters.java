package com.example.barboza_countbook;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by sharidanbarboza on 2017-09-25.
 */

public class Counters {
    private LinkedHashMap<Counter,String> counterMap;
    private ArrayList<Counter> counterList;

    Counters(ArrayList<Counter> counterList) {
        this.counterList = counterList;
        setCounterMap();
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
        counterMap.put(counter, counter.getName());
    }

    /**
     * Deletes a counter from array list and linked hash map
     * @param position the index of the counter in the array
     */
    public void delete(int position) {
        Counter counter = counterList.get(position);
        counterList.remove(position);
        counterMap.remove(counter);
    }
    
    private void setCounterMap() {
        counterMap = new LinkedHashMap<Counter,String>();
        for (Counter counter : counterList) {
            counterMap.put(counter, counter.getName());
        }
    }

}
