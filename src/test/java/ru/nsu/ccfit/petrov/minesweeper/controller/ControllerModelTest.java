package ru.nsu.ccfit.petrov.minesweeper.controller;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;

public class ControllerModelTest
    extends ControllerTest {

    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final int MINE_COUNT = 10;

    @DataProvider(name = "Create unsupported methods when model is not set")
    private Object[][] createUnsupportedMethodsWhenModelIsNotSet() {
        return new Object[][]{new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.getHeight();
            }
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.getWidth();
            }
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.getMineCount();
            }
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.markCell(1, 1);
            }
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.openCell(1, 1);
            }
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.addModelObserver(null);
            }
        }}};
    }

    @Test(description = "Check exceptions when model is not set",
          dataProvider = "Create unsupported methods when model is not set",
          groups = "Model is not set")
    public void checkExceptionWhenModelIsNotSet(ThrowingCallable method) {
        assertThatThrownBy(method).isInstanceOf(UnsupportedOperationException.class);
    }

    @BeforeGroups("Model is set")
    public void setModel() {
        controller.setModel(Level.BEGINNER);
    }

    @AfterGroups("Model is set")
    public void removeModel() {
        controller.removeModel();
    }

    @Test(description = "Check height", groups = "Model is set")
    public void checkHeight() {
        int actual = controller.getHeight();
        assertThat(actual).isEqualTo(HEIGHT);
    }

    @Test(description = "Check width", groups = "Model is set")
    public void checkWidth() {
        int actual = controller.getWidth();
        assertThat(actual).isEqualTo(WIDTH);
    }

    @Test(description = "Check mine count", groups = "Model is set")
    public void checkMineCount() {
        int actual = controller.getMineCount();
        assertThat(actual).isEqualTo(MINE_COUNT);
    }

    @Test(description = "Check marking of cell", groups = "Model is set")
    public void checkMarkCell() {
        // prepare
        int x = 1;
        int y = 2;
        int markedCellCount = 1;
        boolean isMarked = true;
        Observer observer = new Observer() {
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
        controller.addModelObserver(observer);

        // do
        controller.markCell(y, x);

        // restore
        controller.removeModelObserver(observer);
    }

    @Test(description = "Check opening of cell", groups = "Model is set")
    public void checkOpenCell() {
        // prepare
        int x = 1;
        int y = 2;
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(OpenedCellContext.class);

                // do
                int actualX = ((OpenedCellContext) context).getX();
                int actualY = ((OpenedCellContext) context).getY();

                // check
                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
            }
        };
        controller.addModelObserver(observer);

        // do
        controller.openCell(y, x);

        // restore
        controller.removeModelObserver(observer);
    }
}