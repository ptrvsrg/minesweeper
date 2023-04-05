package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class FieldMethodTest
    extends Assertions {

    protected static final int HEIGHT = 11;
    protected static final int WIDTH = 10;
    protected static final int MINE_COUNT = 9;
    protected Field field;

    @BeforeClass
    public void beforeClass() {
        field = new Field(HEIGHT, WIDTH, MINE_COUNT);
    }

    @DataProvider(name = "Create incorrect coordinates")
    protected Object[][] createIncorrectCoordinates() {
        return new Object[][]{new Object[]{HEIGHT, WIDTH - 1},
                              new Object[]{HEIGHT - 1, WIDTH},
                              new Object[]{-1, WIDTH - 1},
                              new Object[]{HEIGHT - 1, -1}};
    }
}
