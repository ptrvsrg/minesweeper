package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;

public class FieldMarkMethodTest
    extends FieldMethodTest {

    @Test(description = "Check cell marking when exception is thrown",
          dataProvider = "Create incorrect coordinates",
          groups = "Field tests")
    public void checkMarkCellWhenExceptionIsThrown(int y, int x) {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call() {
                field.markCell(y, x);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test(description = "Check cell marking",
          groups = "Field tests")
    public void checkMarkCell() {
        // prepare
        int x = 2;
        int y = 3;
        int markedCellCount = 1;
        boolean isMarked = true;
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(MarkedCellContext.class);

                int actualX = ((MarkedCellContext) context).getX();
                int actualY = ((MarkedCellContext) context).getY();
                int actualMarkedCellCount = ((MarkedCellContext) context).getMarkedCellCount();
                boolean actualIsMarked = ((MarkedCellContext) context).isMarked();

                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
                assertThat(actualMarkedCellCount).isEqualTo(markedCellCount);
                assertThat(actualIsMarked).isEqualTo(isMarked);
            }
        };
        field.addObserver(observer);

        // do
        field.markCell(y, x);

        // restore
        field.removeObserver(observer);
    }

    @Test(description = "Check removing cell marking",
          groups = "Field tests")
    public void checkRemoveCellMarking() {
        // prepare
        int x = 2;
        int y = 3;
        int markedCellCount = 0;
        boolean isMarked = false;
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(MarkedCellContext.class);

                int actualX = ((MarkedCellContext) context).getX();
                int actualY = ((MarkedCellContext) context).getY();
                int actualMarkedCellCount = ((MarkedCellContext) context).getMarkedCellCount();
                boolean actualIsMarked = ((MarkedCellContext) context).isMarked();

                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
                assertThat(actualMarkedCellCount).isEqualTo(markedCellCount);
                assertThat(actualIsMarked).isEqualTo(isMarked);
            }
        };
        field.addObserver(observer);

        // do
        field.markCell(y, x);

        // restore
        field.removeObserver(observer);
    }
}