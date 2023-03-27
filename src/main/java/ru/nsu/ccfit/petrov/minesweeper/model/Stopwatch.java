package ru.nsu.ccfit.petrov.minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;


/**
 * The type {@code Stopwatch} is class for measuring time.
 *
 * @author ptrvsrg
 */
public class Stopwatch
{
    /**
     * The constant STOPWATCH_PROPERTY is constant for {@link PropertyChangeListener}.
     */
    public static final String STOPWATCH_PROPERTY = "stopwatchProperty";
    private int second = -1;
    private boolean isRunning = false;
    private Timer timer;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private class StopwatchTask
        extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            ++second;
            propertyChangeSupport.firePropertyChange(STOPWATCH_PROPERTY, second - 1, second);
        }
    }

    /**
     * Runs stopwatch.
     */
    public void run() {
        if (isRunning)
            return;

        isRunning = true;
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new StopwatchTask(), 0, 1000);
    }

    /**
     * Stops stopwatch.
     */
    public void stop() {
        if (!isRunning)
            return;

        isRunning = false;
        timer.cancel();
        timer.purge();
    }

    /**
     * Gets the elapsed time between calls to {@link Stopwatch#run()} and {@link Stopwatch#stop()}
     *
     * @return the second
     */
    public int getSecond() {
        return Math.max(second, 0);
    }

    /**
     * Adds property change listener.
     *
     * @param listener the property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Converts time to string.
     *
     * @param seconds the seconds
     * @return the string representation of time
     * @throws IllegalArgumentException if second count is negative
     */
    public static String timeToString(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Negative time");
        }

        return String.format("%d:%02d:%02d", seconds / 3600, seconds / 60 % 60, seconds % 60);
    }
}
