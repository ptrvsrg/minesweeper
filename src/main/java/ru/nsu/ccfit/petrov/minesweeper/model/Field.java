package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import lombok.Getter;

public class Field {
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final int mineCount;
    private final CellView[][] cellViewMatrix;
    private final byte[][] mineMatrix;  // -1 is mine
    // another number is the number of mines around

    public Field(int height, int width, int mineCount) {
        if (height < 0 || width < 0) {
            throw new IllegalArgumentException("Incorrect size");
        }
        if (mineCount < 0) {
            throw new IllegalArgumentException("Incorrect mine count");
        }

        this.height = height;
        this.width = width;
        this.mineCount = Math.min(mineCount, height * width);
        this.cellViewMatrix = new CellView[height][width];
        this.mineMatrix = new byte[height][width];

        initCells();
        placeMines();
    }

    private void initCells() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                cellViewMatrix[i][j] = CellView.CLOSED;
                mineMatrix[i][j] = 0;
            }
        }
    }

    private void placeMines() {
        Set<Integer> mineCoordinates = generateUniqueRandomNumbers(mineCount, width * height);

        for (Integer coordinate : mineCoordinates) {
            int x = coordinate % width;
            int y = coordinate / width;
            placeMine(y, x);
        }
    }

    private void placeMine(int y, int x) {
        mineMatrix[y][x] = -1;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (y + i >= 0 && y + i < height && x + j >= 0 && x + j < width
                    && mineMatrix[y + i][x + j] != -1) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    ++mineMatrix[y + i][x + j];
                }
            }
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

    private void checkCoordinates(int y, int x) {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IllegalArgumentException("Incorrect coordinates");
        }
    }

    public CellView getCellView(int y, int x) {
        checkCoordinates(y, x);
        return cellViewMatrix[y][x];
    }

    public byte getMineCountAround(int y, int x) {
        checkCoordinates(y, x);
        return mineMatrix[y][x];
    }

    public void setCellView(int y, int x, CellView cellView) {
        checkCoordinates(y, x);
        cellViewMatrix[y][x] = cellView;
    }

    public boolean isMine(int y, int x) {
        return getMineCountAround(y, x) == -1;
    }

    public boolean areThereMinesAround(int y, int x) {
        return getMineCountAround(y, x) > 0;
    }
}
