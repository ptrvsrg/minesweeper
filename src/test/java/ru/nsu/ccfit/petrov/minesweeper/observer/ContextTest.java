package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;

public class ContextTest
    extends Assert {
    private Observable observable;

    @BeforeMethod
    public void setUp() {
        observable = new Observable();
    }

    @Test(description = "check sending game over context")
    void checkGameOverContext() {
        // prepare
        boolean isWinner = true;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                assertTrue(context instanceof GameOverContext);
                assertEquals(((GameOverContext) context).isWinner(), isWinner);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new GameOverContext(isWinner));
    }

    @Test(description = "check sending cell mark context")
    void checkMarkedCellContext() {
        // prepare
        int x = 2;
        int y = 4;
        int markedCellCount = 6;
        boolean isMarked = true;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                assertTrue(context instanceof MarkedCellContext);
                assertEquals(((MarkedCellContext) context).getX(), x);
                assertEquals(((MarkedCellContext) context).getY(), y);
                assertEquals(((MarkedCellContext) context).getMarkedCellCount(), markedCellCount);
                assertEquals(((MarkedCellContext) context).isMarked(), isMarked);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new MarkedCellContext(x, y, markedCellCount, isMarked));
    }

    @Test(description = "check sending cell open context")
    void checkOpenedCellContext() {
        // prepare
        int x = 2;
        int y = 4;
        byte mineAroundCount = 4;
        boolean isMine = true;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                assertTrue(context instanceof OpenedCellContext);
                assertEquals(((OpenedCellContext) context).getX(), x);
                assertEquals(((OpenedCellContext) context).getY(), y);
                assertEquals(((OpenedCellContext) context).getMineAroundCount(), mineAroundCount);
                assertEquals(((OpenedCellContext) context).isMine(), isMine);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new OpenedCellContext(x, y, mineAroundCount, isMine));
    }

    @Test(description = "check sending stopwatch context")
    void checkStopwatchContext() {
        // prepare
        int second = 236;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                assertTrue(context instanceof StopwatchContext);
                assertEquals(((StopwatchContext) context).getSecond(), second);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new StopwatchContext(second));
    }
}