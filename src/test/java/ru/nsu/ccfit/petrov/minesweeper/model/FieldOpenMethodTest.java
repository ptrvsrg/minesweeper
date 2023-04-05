package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;

public class FieldOpenMethodTest
    extends FieldMethodTest {

    @Test(description = "Check cell opening when exception is thrown",
          dataProvider = "Create incorrect coordinates",
          groups = "Field tests")
    public void checkOpenCellWhenExceptionIsThrown(int y, int x) {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call() {
                field.openCell(y, x);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private byte[][] getMineMatrix()
        throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field mineMatrixField = Field.class.getDeclaredField("mineMatrix");
        mineMatrixField.setAccessible(true);

        return (byte[][]) mineMatrixField.get(field);
    }

    private void setMineMatrix(byte[][] newMineMatrix)
        throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field mineMatrixField = Field.class.getDeclaredField("mineMatrix");
        mineMatrixField.setAccessible(true);
        mineMatrixField.set(field, newMineMatrix);
    }

    @Test(description = "Check opening cell when cell is not mine",
          groups = "Field tests")
    void checkOpenCellWhenCellIsNotMine()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        int x = 0;
        int y = 0;
        byte[][] oldMineMatrix = getMineMatrix();
        byte[][] newMineMatrix = new byte[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                newMineMatrix[i][j] = 0;
            }
        }
        newMineMatrix[y][x] = 1;
        setMineMatrix(newMineMatrix);
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(OpenedCellContext.class);

                // prepare
                int actualX = ((OpenedCellContext) context).getX();
                int actualY = ((OpenedCellContext) context).getY();
                int actualMineAroundCount = ((OpenedCellContext) context).getMineAroundCount();
                boolean actualIsMine = ((OpenedCellContext) context).isMine();

                // check
                assertThat(actualX).isEqualTo(x);
                assertThat(actualY).isEqualTo(y);
                assertThat(actualMineAroundCount).isEqualTo(newMineMatrix[y][x]);
                assertThat(actualIsMine).isFalse();
            }
        };
        field.addObserver(observer);

        // do
        field.openCell(y, x);

        // restore
        field.removeObserver(observer);
        setMineMatrix(oldMineMatrix);
    }

    @Test(description = "Check opening cell when cell is mine",
          groups = "Field tests")
    void checkOpenCellWhenCellIsMine()
        throws NoSuchFieldException, IllegalAccessException {
        // prepare
        int x = 0;
        int y = 0;
        byte[][] oldMineMatrix = getMineMatrix();
        byte[][] newMineMatrix = new byte[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                newMineMatrix[i][j] = 0;
            }
        }
        newMineMatrix[y][x] = -1;
        setMineMatrix(newMineMatrix);
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                if (context instanceof OpenedCellContext) {
                    // prepare
                    int actualX = ((OpenedCellContext) context).getX();
                    int actualY = ((OpenedCellContext) context).getY();
                    int actualMineAroundCount = ((OpenedCellContext) context).getMineAroundCount();
                    boolean actualIsMine = ((OpenedCellContext) context).isMine();

                    // check
                    assertThat(actualX).isEqualTo(x);
                    assertThat(actualY).isEqualTo(y);
                    assertThat(actualMineAroundCount).isEqualTo(newMineMatrix[y][x]);
                    assertThat(actualIsMine).isTrue();
                } else if (context instanceof GameOverContext) {
                    boolean actualIsWinner = ((GameOverContext) context).isWinner();
                    assertThat(actualIsWinner).isFalse();
                } else {
                    fail("Unexpected context");
                }
            }
        };
        field.addObserver(observer);

        // do
        field.openCell(y, x);

        // restore
        field.removeObserver(observer);
        setMineMatrix(oldMineMatrix);
    }
}