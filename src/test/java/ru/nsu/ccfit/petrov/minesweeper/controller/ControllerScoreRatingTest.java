package ru.nsu.ccfit.petrov.minesweeper.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import org.testng.annotations.Test;

public class ControllerScoreRatingTest
    extends ControllerTest {

    @Test(description = "Check saving and getting score",
          groups = "Controller tests")
    public void checkSaveAndGetScore() {
        // prepare
        String playerName = "player";
        controller.saveScore(playerName);
        List<Entry<String, Integer>> scores = controller.getScoreRating();

        // do
        boolean isContained = scores.contains(new SimpleEntry<>(playerName, 0));

        // check
        assertThat(isContained).isTrue();
    }
}
