package com.example.barboza_countbook;

import java.util.ArrayList;

/**
 * Counters
 *
 * Model that holds an array of counters. The order is based on the date in
 * which the counter was created, with the most recent counter at the end.
 *
 * Created by sharidanbarboza on 2017-09-25.
 */

public class Counters {;
    private ArrayList<Counter> counterList;

    /**
     * Constructs Counters object
     * @param counterList the counter array (loaded or empty)
     */
    Counters(ArrayList<Counter> counterList) {
        this.counterList = counterList;
    }

    /**
     * Get the counter array
     * @return the counter array
     */
    public ArrayList<Counter> getList() {
        return counterList;
    }

    /**
     * Creates and adds a new counter to the counter array
     * @param name name of the counter
     * @param value initial value of the counter
     * @param comment comment message of the counter (can be empty)
     */
    public void add(String name, int value, String comment) {
        Counter newCounter;
        if (comment.length() > 0) {
            newCounter = new Counter(name, value, comment);
        } else {
            newCounter = new Counter(name, value);
        }
        newCounter.setCurrentDate();
        counterList.add(newCounter);
    }
}
