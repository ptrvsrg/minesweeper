package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;

public class ContextTest
    extends Assertions {
    protected Observable observable;

    @BeforeClass
    public void beforeClass() {
        observable = new Observable();
    }
}