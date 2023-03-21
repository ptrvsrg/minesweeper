package ru.nsu.ccfit.petrov.minesweeper.model.score;

public class Score
{
    private final int fieldWidth;
    private final int fieldHeight;
    private final int mineCount;
    private final int gameTime;

    public Score(int fieldWidth, int fieldHeight, int mineCount, int gameTime) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.mineCount = mineCount;
        this.gameTime = gameTime;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getMineCount() {
        return mineCount;
    }

    public int getGameTime() {
        return gameTime;
    }
}
