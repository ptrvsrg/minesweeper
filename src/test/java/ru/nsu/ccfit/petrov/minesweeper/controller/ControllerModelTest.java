package ru.nsu.ccfit.petrov.minesweeper.controller;

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
    private Context context;

    @DataProvider(name = "Create unsupported methods when model is not set")
    private Object[][] createUnsupportedMethodsWhenModelIsNotSet() {
        return new Object[][]{new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.getHeight();
            }
        }}, new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.getWidth();
            }
        }}, new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.getMineCount();
            }
        }}, new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.markCell(1, 1);
            }
        }}, new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.openCell(1, 1);
            }
        }}, new Object[]{new ThrowingRunnable() {
            @Override
            public void run()
                throws Throwable {
                controller.addModelObserver(null);
            }
        }}};
    }

    @Test(description = "Check exceptions when model is not set",
          dataProvider = "Create unsupported methods when model is not set",
          groups = "Model is not set")
    public void checkExceptionWhenModelIsNotSet(ThrowingRunnable method) {
        assertThrows(UnsupportedOperationException.class, method);
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
        assertEquals(controller.getHeight(), HEIGHT);
    }

    @Test(description = "Check width", groups = "Model is set")
    public void checkWidth() {
        assertEquals(controller.getWidth(), WIDTH);
    }

    @Test(description = "Check mine count", groups = "Model is set")
    public void checkMineCount() {
        assertEquals(controller.getMineCount(), MINE_COUNT);
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
                assertTrue(context instanceof MarkedCellContext);
                assertEquals(((MarkedCellContext) context).getX(), x);
                assertEquals(((MarkedCellContext) context).getY(), y);
                assertEquals(((MarkedCellContext) context).getMarkedCellCount(), markedCellCount);
                assertEquals(((MarkedCellContext) context).isMarked(), isMarked);
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
                assertTrue(context instanceof OpenedCellContext);
                assertEquals(((OpenedCellContext) context).getX(), x);
                assertEquals(((OpenedCellContext) context).getY(), y);
            }
        };
        controller.addModelObserver(observer);

        // do
        controller.openCell(y, x);

        // restore
        controller.removeModelObserver(observer);
    }
}