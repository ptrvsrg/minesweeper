package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;

public class ContextTest
    extends Assertions {
    private Observable observable;

    @BeforeClass
    public void beforeClass() {
        observable = new Observable();
    }

    @Test(description = "Check sending game over context")
    void checkGameOverContext() {
        // prepare
        boolean isWinner = true;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(GameOverContext.class);

                // do
                boolean actualIsWinner = ((GameOverContext) context).isWinner();
                assertThat(actualIsWinner).isEqualTo(isWinner);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new GameOverContext(isWinner));

        // restore
        observable.removeObserver(observer);
    }

    @Test(description = "Check sending cell mark context")
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
                // check
                assertThat(context).isInstanceOf(MarkedCellContext.class);

                // do
                int actualX = ((MarkedCellContext) context).getX();
                int actualY = ((MarkedCellContext) context).getY();
                int actualMarkedCellCount = ((MarkedCellContext) context).getMarkedCellCount();
                boolean actualIsMarked = ((MarkedCellContext) context).isMarked();

                // check
                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
                assertThat(actualMarkedCellCount).isEqualTo(markedCellCount);
                assertThat(actualIsMarked).isEqualTo(isMarked);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new MarkedCellContext(x, y, markedCellCount, isMarked));

        // restore
        observable.removeObserver(observer);
    }

    @Test(description = "Check sending cell open context")
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
                // check
                assertThat(context).isInstanceOf(OpenedCellContext.class);

                // do
                int actualX = ((OpenedCellContext) context).getX();
                int actualY = ((OpenedCellContext) context).getY();
                int actualMineAroundCount = ((OpenedCellContext) context).getMineAroundCount();
                boolean actualIsMine = ((OpenedCellContext) context).isMine();

                // check
                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
                assertThat(actualMineAroundCount).isEqualTo(mineAroundCount);
                assertThat(actualIsMine).isEqualTo(isMine);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new OpenedCellContext(x, y, mineAroundCount, isMine));

        // restore
        observable.removeObserver(observer);
    }

    @Test(description = "Check sending stopwatch context")
    void checkStopwatchContext() {
        // prepare
        int second = 236;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(StopwatchContext.class);

                // do
                int actualSecond = ((StopwatchContext) context).getSecond();

                assertThat(actualSecond).isEqualTo(second);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new StopwatchContext(second));

        // restore
        observable.removeObserver(observer);
    }
}