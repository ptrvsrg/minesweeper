package ru.nsu.ccfit.petrov.minesweeper.controller;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.model.Field;

public class ControllerSetFieldMethodTest
    extends ControllerTest {

    @DataProvider(name = "Create level parameters")
    private Object[][] createLevelParameters() {
        return new Object[][]{new Object[]{Level.BEGINNER, 9, 9, 10},
                              new Object[]{Level.INTERMEDIATE, 16, 16, 40},
                              new Object[]{Level.EXPERT, 16, 30, 99}};
    }

    private Field getField()
        throws NoSuchFieldException, IllegalAccessException {
        java.lang.reflect.Field fieldField = Controller.class.getDeclaredField("field");
        fieldField.setAccessible(true);
        return (Field) fieldField.get(controller);
    }

    @Test(description = "Check field setting by level",
          dataProvider = "Create level parameters",
          groups = "Controller tests")
    public void checkSetFieldByLevel(Level level, int height, int width, int mineCount)
        throws NoSuchFieldException, IllegalAccessException {
        // do
        controller.setField(level);

        // check
        Field field = getField();
        int actualHeight = field.getHeight();
        int actualWidth = field.getWidth();
        int actualMineCount = field.getMineCount();

        assertThat(actualHeight).isEqualTo(height);
        assertThat(actualWidth).isEqualTo(width);
        assertThat(actualMineCount).isEqualTo(mineCount);
    }
}
