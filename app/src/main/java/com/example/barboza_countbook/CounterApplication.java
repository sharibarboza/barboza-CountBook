package com.example.barboza_countbook;

import android.app.Application;
import android.content.Context;

/**
 * Created by sharidanbarboza on 2017-09-25.
 */

public class CounterApplication extends Application {

    transient private static CounterController counterController = null;

    public static CounterController getCounterController(Context context) {
        if (counterController == null) {
            counterController = new CounterController(context);
        }
        return counterController;
    }
}
