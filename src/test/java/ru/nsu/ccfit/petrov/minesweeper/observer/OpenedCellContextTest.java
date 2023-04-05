package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;

public class OpenedCellContextTest
    extends ContextTest {

    @Test(description = "Check sending cell open context",
          groups = "Context tests")
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
}