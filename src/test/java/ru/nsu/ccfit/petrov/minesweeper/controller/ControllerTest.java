package ru.nsu.ccfit.petrov.minesweeper.controller;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;

public class ControllerTest
    extends Assertions {

    protected Controller controller;

    @BeforeClass
    public void beforeClass() {
        controller = new Controller();
    }
}
