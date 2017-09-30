package com.example.barboza_countbook;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * CounterValidator
 *
 * This is a utility class with static methods only. It contains common helper
 * methods that validate a counter's fields.
 *
 * Created by sharidanbarboza on 2017-09-26.
 */

public final class CounterValidator {

    /**
     * Private constructor
     * Prevents the class from being constructed
     */
    private CounterValidator() {}

    /**
     * Main validation method for validating a counter's input fields
     * @param context activity context to get field strings
     * @param inputName EditText view of name input field
     * @param inputValue EditText view of initial value input field
     * @param inputCurrent EditText view of current value field (set to null for add activities)
     * @return true if all fields are valid
     */
    public static boolean validate(Context context, EditText inputName, EditText inputValue,
                                   EditText inputCurrent) {
        boolean valid;
        String tempName = inputName.getText().toString();
        String tempInit = inputValue.getText().toString().trim();
        String tempCurrent = null;

        if (inputCurrent != null) {
            tempCurrent = inputCurrent.getText().toString().trim();
        }

        if (tempName.isEmpty()) {
            String name_req_str = context.getString(R.string.name_required);
            Toast.makeText(context, name_req_str, Toast.LENGTH_SHORT).show();
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
     * @param context activity context for getting toast strings
     * @param value the string input to validate
     * @param countType The type of value (inital or current) for displaying in toast messages
     * @return true if the string is a valid integer
     */
    public static boolean validateValue(Context context, String value, String countType) {
        boolean valid;

        if (value.isEmpty()) {
            String val_req_str = context.getString(R.string.value_required);
            Toast.makeText(context, countType + val_req_str, Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!isInteger(value)) {
            String val_num_str = context.getString(R.string.is_num);
            Toast.makeText(context, countType + val_num_str, Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (Integer.parseInt(value) < 0) {
            String val_pos_str = context.getString(R.string.is_positive);
            Toast.makeText(context, countType + val_pos_str, Toast.LENGTH_SHORT).show();
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
        boolean isInteger;
        try {
            Integer.parseInt(input);
            isInteger = true;
        } catch (NumberFormatException e) {
            isInteger = false;
        }
        return isInteger;
    }
}
