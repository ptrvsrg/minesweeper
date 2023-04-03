package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observable;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;


/**
 * The type {@code Stopwatch} is class for measuring time.
 *
 * @author ptrvsrg
 */
public class Stopwatch
    extends Observable
{
    @Getter
    private int second = 0;
    private boolean isRunning = false;
    private Timer timer;

    private class StopwatchTask
        extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            ++second;
            notifyObservers(new StopwatchContext(second));
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
