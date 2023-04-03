package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import lombok.Getter;

/**
 * The type {@code Field} is class for work with minefield.
 *
 * @author ptrvsrg
 */
public class Field {
    private static final int MINE = -1;
    private static final Random randomizer = new Random();
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final int mineCount;
    private final CellView[][] cellViewMatrix;
    private final byte[][] mineMatrix;  // -1 is mine
                                        // another number is the number of mines around

    /**
     * Instantiates a new Field.
     *
     * @param height    the height
     * @param width     the width
     * @param mineCount the mine count
     * @throws IllegalArgumentException if height, width or mine count have negative value
     * */
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
                boolean isCentralPoint = (i == 0 && j == 0);
                boolean isXWithinBoundaries = (x + j >= 0) && (x + j < width);
                boolean isYWithinBoundaries = (y + i >= 0) && (y + i < height);
                if (!isCentralPoint && isXWithinBoundaries && isYWithinBoundaries && !isMine(y + i, x + j)) {
                    ++mineMatrix[y + i][x + j];
                }
            }
        }
    }

    private Set<Integer> generateUniqueRandomNumbers(int count, int bound) {
        Set<Integer> nums = new HashSet<>();

        int numCount = 0;
        while (count != numCount) {
            int num = randomizer.nextInt(bound);

            if (!nums.contains(num)) {
                nums.add(num);
                ++numCount;
            }
        }

        return nums;
    }

    /**
     * Gets view of cell with coordinates (x, y).
     *
     * @param y the y coordinates
     * @param x the x coordinates
     * @return the cell view
     * @throws IllegalArgumentException if coordinates are outside field
     */
    public CellView getCellView(int y, int x) {
        checkCoordinates(y, x);
        return cellViewMatrix[y][x];
    }

    /**
     * Sets view of cell with coordinates (x, y).
     *
     * @param y the y coordinates
     * @param x the x coordinates
     * @throws IllegalArgumentException if coordinates are outside field
     */
    public void setCellView(int y, int x, CellView cellView) {
        checkCoordinates(y, x);
        cellViewMatrix[y][x] = cellView;
    }

    /**
     * Gets mine count around cell with coordinates (x, y).
     *
     * @param y the y coordinates
     * @param x the x coordinates
     * @throws IllegalArgumentException if coordinates are outside field
     */
    public byte getMineCountAround(int y, int x) {
        checkCoordinates(y, x);
        return mineMatrix[y][x];
    }

    private void checkCoordinates(int y, int x) {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IllegalArgumentException("Incorrect coordinates");
        }
    }

    /**
     * Is cell with coordinates (x, y) mine?
     *
     * @param y the y coordinates
     * @param x the x coordinates
     * @return {@code true} - cell with coordinates (x, y) is mine,
     * {@code false} - cell with coordinates (x, y) is not mine
     */
    public boolean isMine(int y, int x) {
        return getMineCountAround(y, x) == MINE;
    }

    /**
     * Are there mines around cell with coordinates (x, y)?
     *
     * @param y the y coordinates
     * @param x the x coordinates
     * @return {@code true} - there are mines around,
     * {@code false} - there are no mines around
     */
    public boolean areThereMinesAround(int y, int x) {
        return getMineCountAround(y, x) > 0;
    }
}
