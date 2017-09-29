package com.example.barboza_countbook;

import android.app.Application;
import android.content.Context;

/**
 * CounterApplication
 *
 * Creates a singleton pattern, so the activities are only referring to one
 * instance of the controller. This allows only one object to be used when keeping
 * track of the data.
 *
 * Created by sharidanbarboza on 2017-09-25.
 */

public class CounterApplication extends Application {

    transient private static CounterController counterController = null;

    /**
     * Instantiates the controller. Returns the current controller or creates
     * a new one if there is none.
     * @param context the application context
     * @return the Counter controller object
     */
    public static CounterController getCounterController(Context context) {
        if (counterController == null) {
            counterController = new CounterController(context);
        }
        return counterController;
    }
}
