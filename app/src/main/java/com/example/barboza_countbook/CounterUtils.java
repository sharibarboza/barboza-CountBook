package com.example.barboza_countbook;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CounterUtils
 *
 * This is a utility class with static methods only. It contains common helper
 * methods that initalize or validate a counter's fields.
 *
 * Created by sharidanbarboza on 2017-09-26.
 */

public final class CounterUtils {

    /**
     * Private constructor
     * Prevents the class from being constructed
     */
    private CounterUtils() {}

    /**
     * Create a new date by taking the current date and converting it to a formatted string
     * @param context application context to get the date format
     * @return a formatted date string
     */
    public static String initalizeDate(Context context) {
        Date currentDate = new Date();
        return new SimpleDateFormat(context.getString(R.string.date_format)).format(currentDate);
    }

    /**
     * Main validation method for validating a counter's input fields
     * @param context application context to get field strings
     * @param inputName EditText view of name input field
     * @param inputValue EditText view of initial value input field
     * @param inputCurrent EditText view of current value field (set to null for add activities)
     * @return true if all fields are valid
     */
    public static boolean validate(Context context, EditText inputName, EditText inputValue, EditText inputCurrent) {
        boolean valid;
        String tempName = inputName.getText().toString();
        String tempInit = inputValue.getText().toString().trim();
        String tempCurrent = null;

        if (inputCurrent != null) {
            tempCurrent = inputCurrent.getText().toString().trim();
        }

        if (tempName.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.name_required),
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!validateValue(context, tempInit, "Initial ")) {
            valid = false;
        } else if (tempCurrent != null && !validateValue(context, tempCurrent, "Current ")) {
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }

    /**
     * Helper validation method for validating an integer
     * Ensures that an input integer is non-empty, a valid number, and non-negative
     * @param context application context for getting toast strings
     * @param value the string input to validate
     * @param countType The type of value (inital or current) for displaying in toast messages
     * @return true if the string is a valid integer
     */
    public static boolean validateValue(Context context, String value, String countType) {
        boolean valid;

        if (value.isEmpty()) {
            Toast.makeText(context, countType + context.getString(R.string.value_required),
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!isInteger(value)) {
            Toast.makeText(context, countType + context.getString(R.string.is_num),
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (Integer.parseInt(value) < 0) {
            Toast.makeText(context, countType + context.getString(R.string.is_positive),
                    Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

    /**
     * Checks whether a string is an integer
     * @param input a string to be validated
     * @return true if string input can be converted to an integer
     */
    public static boolean isInteger(String input) {
        boolean isInteger = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            isInteger = false;
        }
        return isInteger;
    }
}
