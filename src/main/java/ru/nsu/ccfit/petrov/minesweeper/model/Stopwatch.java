package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.TimerTask;


public class Stopwatch
{
    private int second = -1;
    private boolean isRunning = false;
    private java.util.Timer timer;

    private class StopwatchTask
        extends TimerTask {
        @Override
        public void run() {
            ++second;
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

    public void clear() {
        second = 0;
    }

    public int getSecond() {
        return second;
    }
}
