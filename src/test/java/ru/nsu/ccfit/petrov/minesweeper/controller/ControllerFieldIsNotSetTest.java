package ru.nsu.ccfit.petrov.minesweeper.controller;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ControllerFieldIsNotSetTest
    extends ControllerTest {

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
        }}, new Object[]{new ThrowingCallable() {
            @Override
            public void call() {
                controller.removeModelObserver(null);
            }
        }}};
    }

    @Test(description = "Check exceptions when model is not set",
          dataProvider = "Create unsupported methods when model is not set",
          groups = "Controller tests")
    public void checkExceptionWhenModelIsNotSet(ThrowingCallable method) {
        assertThatThrownBy(method).isInstanceOf(UnsupportedOperationException.class);
    }
}
