package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FieldConstructorTest
    extends Assertions {

    @DataProvider(name = "Create incorrect constructor parameters")
    private Object[][] createIncorrectConstructorParameters() {
        return new Object[][]{new Object[]{0, 1, 1},
                              new Object[]{-1, 1, 1},
                              new Object[]{1, 0, 1},
                              new Object[]{1, -1, 1},
                              new Object[]{1, 1, -1}};
    }

    @Test(description = "Check constructor exceptions",
          dataProvider = "Create incorrect constructor parameters",
          groups = "Field tests")
    public void checkConstructorExceptions(int height, int width, int mineCount) {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call() {
                new Field(height, width, mineCount);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
