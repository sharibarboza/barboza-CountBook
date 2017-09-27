package com.example.barboza_countbook;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by sharidanbarboza on 2017-09-24.
 */

public class CounterController {

    private static final String FILENAME = "file.sav";
    private Context context;
    private Counters counters;

    CounterController(Context context) {
        this.context = context;
    }

    /**
     * Initalizes counter array either by loading from a file or creating an empty one
     */
    public void configCounters() {
        ArrayList<Counter> counterList = loadFromFile();
        counters = new Counters(counterList);
    }

    /**
     * Returns the array list of counters
     * @return the array list of counters
     */
    public Counters getCounters() {
        return counters;
    }

    /**
     * Adds a new counter
     * @param counter Counter object
     */
    public void addCounter(Counter counter) {
        counters.add(counter);
        saveInFile();
    }

    /**
     * Deletes a counter
     * @param position index of counter in counter array
     */
    public void deleteCounter(int position) {
        counters.delete(position);
        saveInFile();
    }

    /**
     * Resets a counter
     * @param position index of counter in counter array
     */
    public void resetCounter(int position) {
        counters.reset(position);
    }

    /**
     * Get a specific counter according to its position in the array
     * @param position index of counter in counter array
     * @return Counter object
     */
    public Counter getCounter(int position) {
        return counters.get(position);
    }

    private ArrayList<Counter> loadFromFile() {
        ArrayList<Counter> counterList;
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Modified from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylist
            // 2017-9-25
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counterList = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return counterList;
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counters.getList(), out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
