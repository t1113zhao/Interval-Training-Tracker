package com.example.t54zhao.hiittracker.models;

public abstract class TimeSpan {
    public abstract int getTime();
    public abstract void setTime(int newTime);
    public abstract int getTimeRemaining();

    /**
     * Total time of timespan in milliseconds
     */
    protected int time;

    /**
     * Time remaining of timespan in milliseconds
     */
    protected int timeRemaining;

    /**
     * Decrement the time remaining
     * @return
     * true if operation successful
     */
    public abstract boolean decrementTimeRemaining();

    /**
     * Resets the time remaining.
     */
    public abstract void resetTimeRemaining();

}
