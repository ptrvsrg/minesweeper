package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;

public class FieldTest
    extends Assertions {

    private static final int HEIGHT = 9;
    private static final int WIDTH = 10;
    private static final int MINE_COUNT = 11;
    private Field field;

    @BeforeClass
    public void beforeClass() {
        field = new Field(HEIGHT, WIDTH, MINE_COUNT);
    }

    @DataProvider(name = "Create incorrect coordinates")
    private Object[][] createIncorrectCoordinates() {
        return new Object[][]{new Object[]{HEIGHT, WIDTH - 1},
                              new Object[]{HEIGHT - 1, WIDTH},
                              new Object[]{-1, WIDTH - 1},
                              new Object[]{HEIGHT - 1, -1}};
    }

    @Test(description = "Check cell marking when exception is thrown",
          dataProvider = "Create incorrect coordinates")
    public void checkMarkCellWhenExceptionIsThrown(int y, int x) {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call() {
                field.markCell(y, x);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test(description = "Check cell marking")
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

                // prepare
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
        field.addObserver(observer);

        // do
        field.markCell(y, x);

        // restore
        field.removeObserver(observer);
    }

    @Test(description = "Check removing cell marking")
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

                // prepare
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
        field.addObserver(observer);

        // do
        field.markCell(y, x);

        // restore
        field.removeObserver(observer);
    }

    @Test(description = "Check cell opening when exception is thrown",
          dataProvider = "Create incorrect coordinates")
    public void checkOpenCellWhenExceptionIsThrown(int y, int x) {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call() {
                field.openCell(y, x);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }
}