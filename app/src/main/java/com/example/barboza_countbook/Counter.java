package com.example.barboza_countbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sharidanbarboza on 2017-09-24.
 *
 * Each counter has the following fields. Only the comment field may be left blank.
 * The user must specify name and initVal only.
 */

public class Counter {
    private String name;
    private String date;
    private int currentVal;
    private int initVal;
    private String comment;

    /**
     * Instantiates a new Counter.
     * @param name name of the counter
     * @param initVal the value the counter will be reset to
     */
    Counter(String name, int initVal) {
        this.name = name;
        this.initVal = initVal;
        this.currentVal = initVal;
        this.comment = "";
    }

    /**
     * Instantiates a new Counter.
     * @param name name of the counter
     * @param initVal the value the counter will be reset to
     * @param comment the counter's comment message
     */
    Counter(String name, int initVal, String comment) {
        this.name = name;
        this.initVal = initVal;
        this.currentVal = initVal;
        this.comment = comment;
    }

    /**
     * Gets the counter name.
     * @return the name of the counter
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the counter name.
     * @param name name of the counter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the counter date.
     * @return the counter date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the counter date.
     * @param date the counter date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the counter's date to the current date.
     */
    public void setCurrentDate() {
        String format = "yyyy-MM-dd";
        Date currentDate = new Date();
        this.date = new SimpleDateFormat(format).format(currentDate);
    }

    /**
     * Gets the counter's current value.
     * @return the current value
     */
    public int getCurrentVal() {
        return currentVal;
    }

    /**
     * Sets the counter's current value.
     * @param currentVal the current value
     */
    public void setCurrentVal(int currentVal) {
        this.currentVal = currentVal;
    }

    /**
     * Gets the inital value of the counter.
     * @return the inital value
     */
    public int getInitVal() {
        return initVal;
    }

    /**
     * Sets the initial value of the counter.
     * @param initVal the initial value
     */
    public void setInitVal(int initVal) {
        this.initVal = initVal;
    }

    /**
     * Gets the counter comment.
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the counter comment.
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Representing Counter object in a string
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Increases the current value by one.
     */
    public void increment() {
        currentVal += 1;
    }

    /**
     * Decreases the current value b one.
     * @throws NegativeValueException
     */
    public void decrement() throws NegativeValueException {
        if (currentVal < 1) {
            throw new NegativeValueException();
        } else {
            currentVal -= 1;
        }
    }

}
