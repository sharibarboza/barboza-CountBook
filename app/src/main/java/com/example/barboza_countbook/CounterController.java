package com.example.barboza_countbook;

import android.content.Context;

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
     * Initializes counter array either by loading from a file or creating an empty one
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
     * Gets a specific counter according to its position in the array
     * @param position index of counter in counter array
     * @return Counter object
     */
    public Counter getCounter(int position) {
        return counters.getList().get(position);
    }

    /**
     * Call the Counters model to add a new counter
     * @param name the counter name
     * @param value the counter's initial value
     * @param comment the counter's comment
     */
    public void addCounter(String name, int value, String comment) {
        counters.add(name, value, comment);
        saveInFile();
    }

    /**
     * Set the counter's new values
     * @param counter the Counter to be edited
     * @param name the counter's name
     * @param current the counter's current value
     * @param value the counter's initial value
     * @param comment the counter's message
     */
    public void editCounter(Counter counter, String name, int current, int value, String comment) {
        if (!name.equals(counter.getName())) {
            counter.setName(name);
        }
        if (current != counter.getCurrentVal()) {
            counter.setCurrentVal(current);
        }
        if (value != counter.getInitVal()) {
            counter.setInitVal(value);
        }
        if (!comment.equals(counter.getComment())) {
            counter.setComment(comment);
        }
        counter.setCurrentDate();
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
        counter.setCurrentDate();
        saveInFile();
    }

    /**
     * Increments a counter's current value
     * @param counter Counter object
     */
    public void incrementCounter(Counter counter) {
        counter.increment();
        counter.setCurrentDate();
        saveInFile();
    }

    /**
     * Decrements a counter's current value.
     * @param counter Counter object
     */
    public boolean decrementCounter(Counter counter) {
        boolean canDecrement;
        try {
            counter.decrement();
            counter.setCurrentDate();
            saveInFile();
            canDecrement = true;
        } catch (NegativeValueException e) {
            canDecrement = false;
        }
        return canDecrement;
    }

    /**
     * Responsible for loading the saved counter data. If there is nothing saved,
     * then created a brand new counter array.
     * @return the counter array
     */
    private ArrayList<Counter> loadFromFile() {
        ArrayList<Counter> counterList;

        // Taken from https://github.com/ta301fall2017/lonelyTwitter
        // 2017-9-24
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Modified from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylist
            // 2017-9-25
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counterList = new ArrayList<Counter>();
        }
        return counterList;
    }

    /**
     * Save the counter array into the file.
     */
    private void saveInFile() {
        // Taken from https://github.com/ta301fall2017/lonelyTwitter
        // 2017-9-24
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
