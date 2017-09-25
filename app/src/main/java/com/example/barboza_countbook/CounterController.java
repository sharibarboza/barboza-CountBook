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

/**
 * Created by sharidanbarboza on 2017-09-24.
 */

public class CounterController {

    private static final String FILENAME = "file.sav";
    private Context context;
    private ArrayList<Counter> counterList;

    CounterController(Context context) {
        this.context = context;
    }

    public void configCounterList() {
        loadFromFile();
    }

    public ArrayList<Counter> getCounterList() {
        return counterList;
    }

    public void addNewCounter(Counter counter) {
        counterList.add(counter);
        saveInFile();
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylist
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            counterList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counterList = new ArrayList<Counter>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(counterList, out);
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
