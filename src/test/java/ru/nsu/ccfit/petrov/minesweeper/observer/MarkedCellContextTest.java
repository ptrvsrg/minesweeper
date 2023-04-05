package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;

public class MarkedCellContextTest
    extends ContextTest {

    @Test(description = "Check sending cell mark context",
          groups = "Context tests")
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
}