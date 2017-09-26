package com.example.barboza_countbook;

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

    public void add(Counter counter) {
        counterList.add(counter);
        counterMap.put(counter, counter.getName());
    }

    private void setCounterMap() {
        counterMap = new LinkedHashMap<Counter,String>();
        for (Counter counter : counterList) {
            counterMap.put(counter, counter.getName());
        }
    }

}
