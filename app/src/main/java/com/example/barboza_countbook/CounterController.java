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
 * CounterController
 *
 * The controller, which is responsible for handling events between the view activities
 * and the counter models. The controller translates to the models what to change based
 * on actions made from the views. In turn, it relays back to the views the correct and
 * updated data to be shown.
 *
 * Created by sharidanbarboza on 2017-09-24.
 */

public class CounterController {

    private static final String FILENAME = "file.sav";
    private Context context;
    private Counters counters;

    /**
     * Constructs the counter controller.
     * @param context the application context
     */
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

    public void updateCounters() {
        saveInFile();
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
     * @param counter Counter object
     */
    public void resetCounter(Counter counter) {
        counter.setCurrentVal(counter.getInitVal());
        saveInFile();
    }

    /**
     * Increments a counter's current value
     * @param counter Counter object
     */
    public void incrementCounter(Counter counter) {
        int oldValue = counter.getCurrentVal();
        counter.setCurrentVal(oldValue + 1);
        saveInFile();
    }

    /**
     * Decrements a counter's current value.
     * Throws an exception when attempting to decrement to negative values.
     * @param counter Counter object
     */
    public void decrementCounter(Counter counter) throws NegativeValueException {
        int oldValue = counter.getCurrentVal();
        if (oldValue < 1) {
            throw new NegativeValueException();
        } else {
            counter.setCurrentVal(oldValue - 1);
        }
    }

    /**
     * Gets a specific counter according to its position in the array
     * @param position index of counter in counter array
     * @return Counter object
     */
    public Counter getCounter(int position) {
        return counters.get(position);
    }

    /**
     * Responsible for loading the saved counter data. If there is nothing saved,
     * then created a brand new counter array.
     * @return the counter array
     */
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

    /**
     * Save the counter array into the file.
     */
    public void saveInFile() {
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
