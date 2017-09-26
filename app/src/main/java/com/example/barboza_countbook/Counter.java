package com.example.barboza_countbook;

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
     * When the counter is first instantiated, the date will be when the counter was created.
     * Later on, the date will represent each time the current value changes.
     * @param date the counter date
     */
    public void setDate(String date) {
        this.date = date;
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

}
