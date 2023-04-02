package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;

public class ObserverTest
    extends Assert {
    private Observable observable;

    @BeforeMethod
    public void setUp() {
        observable = new Observable();
    }

    @Test(description = "check method \"notify\"")
    void checkNotify() {
        // prepare
        final int[] messageCount = {0};
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                ++messageCount[0];
            }
        };
        observable.addObserver(observer);
        // do
        observable.notifyObservers(new Context() {});
        // check
        assertEquals(messageCount[0], 1);

        // prepare
        observable.removeObserver(observer);
        // do
        observable.notifyObservers(new Context() {});
        // check
        assertEquals(messageCount[0], 1);
    }
}