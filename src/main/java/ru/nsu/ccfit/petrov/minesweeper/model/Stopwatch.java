package ru.nsu.ccfit.petrov.minesweeper.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;


public class Stopwatch
{
    public static final String STOPWATCH_PROPERTY = "stopwatchProperty";
    private int second = -1;
    private boolean isRunning = false;
    private Timer timer;
    private PropertyChangeSupport propertyChangeSupport;

    private class StopwatchTask
        extends TimerTask {
        @Override
        public void run() {
            ++second;
            propertyChangeSupport.firePropertyChange(STOPWATCH_PROPERTY, second - 1, second);
        }
    }

    public void run() {
        if (isRunning)
            return;

        isRunning = true;
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new StopwatchTask(), 0, 1000);
    }

    public void stop() {
        if (!isRunning)
            return;

        isRunning = false;
        timer.cancel();
        timer.purge();
    }

    public int getSecond() {
        return Math.max(second, 0);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport == null) {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }

        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public static String timeToString(int seconds) {
        return String.format("%d:%02d:%02d", seconds / 3600, seconds / 60 % 60, seconds % 60);
    }
}
