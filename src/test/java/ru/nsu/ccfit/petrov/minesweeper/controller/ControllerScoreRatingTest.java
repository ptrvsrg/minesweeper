package ru.nsu.ccfit.petrov.minesweeper.controller;

import java.util.AbstractMap.SimpleEntry;
import lombok.var;
import org.testng.annotations.Test;

public class ControllerScoreRatingTest
    extends ControllerTest {

    @Test(description = "Check saving and getting score")
    public void checkSaveAndGetScore() {
        String playerName = "player";
        controller.saveScore(playerName);
        var scores = controller.getScoreRating();

        assertTrue(scores.contains(new SimpleEntry<>(playerName, 0)));
    }
}
