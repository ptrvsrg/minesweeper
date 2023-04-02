package ru.nsu.ccfit.petrov.minesweeper.observer;

import java.util.ArrayList;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;

/**
 * The type {@code Observable} is class that stores
 * {@link ru.nsu.ccfit.petrov.minesweeper.observer.Observer listeners} and notifies them if its
 * state change.
 *
 * @author ptrvsrg
 */
public class Observable {

    private ArrayList<Observer> observers = null;

    /**
     * Adds observer.
     *
     * @param observer the observer
     */
    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }

        observers.add(observer);
    }

    /**
     * Removes observer.
     *
     * @param observer the observer
     */
    public void removeObserver(Observer observer) {
        if (observers == null) {
            return;
        }

        observers.remove(observer);
    }

    /**
     * Notifies observers.
     *
     * @param context the context
     */
    public void notifyObservers(Context context) {
        if (observers == null) {
            return;
        }

        for (Observer observer : observers) {
            observer.update(context);
        }
    }
}
