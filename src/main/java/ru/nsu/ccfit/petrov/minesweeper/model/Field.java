package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Field
{
    private final int mineCount;
    private final StatusCell[][] statusCells;
    private final boolean[][] mineCells;

    public Field() {
        this.mineCount = 10;
        this.statusCells = new StatusCell[9][9];
        this.mineCells = new boolean[9][9];

        initCells();
        placeMines();
    }

    public Field(int width, int height, int mineCount) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Incorrect field sizes");
        }
        if (mineCount > width * height) {
            throw new IllegalArgumentException("More mines than cells");
        }

        this.mineCount = mineCount;
        this.statusCells = new StatusCell[height][width];
        this.mineCells = new boolean[height][width];

        initCells();
        placeMines();
    }
    
    private void initCells() {
        for (int i = 0; i < getHeight(); ++i) {
            for (int j = 0; j < getWidth(); ++j) {
                statusCells[i][j] = StatusCell.CLOSED;
                mineCells[i][j] = false;
            }
        }
    }

    private void placeMines() {
        Set<Integer> mineCoordinates = generateUniqueRandomNumbers(mineCount, getWidth() * getHeight());

        for (Integer coordinate : mineCoordinates) {
            int x = coordinate % getWidth();
            int y = coordinate / getWidth();
            mineCells[y][x] = true;
        }
    }

    private Set<Integer> generateUniqueRandomNumbers(int count, int bound) {
        Random randomGenerator = new Random();
        Set<Integer> nums = new HashSet<>();

        int numCount = 0;
        while (count != numCount) {
            int num = randomGenerator.nextInt(bound);

            if (!nums.contains(num)) {
                nums.add(num);
                ++numCount;
            }
        }

        return nums;
    }

    public int getHeight() {
        return statusCells.length;
    }

    public int getWidth() {
        return statusCells[0].length;
    }

    public void mark(int x, int y) {
        statusCells[y][x] = StatusCell.MARKED;
    }

    public boolean open(int x, int y) {
        statusCells[y][x] = StatusCell.OPENED;
        return mineCells[y][x];
    }
}
