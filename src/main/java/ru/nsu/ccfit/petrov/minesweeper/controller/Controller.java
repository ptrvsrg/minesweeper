package ru.nsu.ccfit.petrov.minesweeper.controller;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.petrov.minesweeper.model.Field;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.model.score.Score;
import ru.nsu.ccfit.petrov.minesweeper.model.score.ScoreRating;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;

/**
 * The type {@code Controller} is class that provides interaction between the view and the model,
 * stores intermediate results.
 *
 * @author ptrvsrg
 */
public class Controller {

    private Field field = null;
    private final Stopwatch stopwatch = new Stopwatch();
    @Getter
    @Setter
    private Boolean isWinner = null;

    /**
     * Sets model.
     *
     * @param level the level
     */
    public void setField(Level level) {
        switch (level) {
            case BEGINNER:
                this.field = new Field(9, 9, 10);
                break;
            case INTERMEDIATE:
                this.field = new Field(16, 16, 40);
                break;
            case EXPERT:
                this.field = new Field(16, 30, 99);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }
    }

    /**
     * Removes model.
     */
    public void removeField() {
        field = null;
    }

    /**
     * Gets field height.
     *
     * @return the field height
     */
    public int getHeight() {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        return field.getHeight();
    }

    /**
     * Gets field width.
     *
     * @return the field width
     */
    public int getWidth() {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        return field.getWidth();
    }

    /**
     * Gets mine count in the field.
     *
     * @return the mine count in the field
     */
    public int getMineCount() {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        return field.getMineCount();
    }

    /**
     * Opens cell on the field.
     *
     * @param y the y coordinate
     * @param x the x coordinate
     */
    public void openCell(int y, int x) {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        field.openCell(y, x);
    }

    /**
     * Marks cell on the field.
     *
     * @param y the y coordinates
     * @param x the x coordinates
     */
    public void markCell(int y, int x) {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        field.markCell(y, x);
    }

    /**
     * Adds model observer.
     *
     * @param observer the observer
     */
    public void addModelObserver(Observer observer) {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        field.addObserver(observer);
    }

    /**
     * Removes model observer.
     *
     * @param observer the observer
     */
    public void removeModelObserver(Observer observer) {
        if (field == null) {
            throw new UnsupportedOperationException();
        }

        field.removeObserver(observer);
    }

    /**
     * Runs stopwatch.
     */
    public void runStopwatch() {
        stopwatch.run();
    }

    /**
     * Stops stopwatch.
     */
    public void stopStopwatch() {
        stopwatch.stop();
    }

    /**
     * Adds stopwatch observer.
     *
     * @param observer the observer
     */
    public void addStopwatchObserver(Observer observer) {
        stopwatch.addObserver(observer);
    }

    /**
     * Removes stopwatch observer.
     *
     * @param observer the observer
     */
    public void removeStopwatchObserver(Observer observer) {
        stopwatch.removeObserver(observer);
    }

    /**
     * Saves score in score rating.
     *
     * @param playerName the player name
     */
    public void saveScore(String playerName) {
        ScoreRating.saveScore(new Score(playerName, stopwatch.getSecond()));
    }

    /**
     * Gets score rating.
     *
     * @return the score rating
     */
    public List<Entry<String, Integer>> getScoreRating() {
        ArrayList<Entry<String, Integer>> scoreRating = new ArrayList<>();

        for (Score score : ScoreRating.getScoreRating()) {
            scoreRating.add(new SimpleEntry<>(score.getPlayerName(), score.getGameTime()));
        }

        return scoreRating;
    }
}
