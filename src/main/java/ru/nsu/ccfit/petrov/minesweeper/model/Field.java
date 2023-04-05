package ru.nsu.ccfit.petrov.minesweeper.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import lombok.Getter;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observable;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;

/**
 * The type {@code Field} is class for work with minefield.
 *
 * @author ptrvsrg
 */
public class Field
    extends Observable {

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

    @Getter
    private int markedCellCount = 0;
    private int openedCellCount = 0;
    private boolean isGameOver = false;

    /**
     * Instantiates a new Field.
     *
     * @param height    the height
     * @param width     the width
     * @param mineCount the mine count
     * @throws IllegalArgumentException if height, width or mine count have negative value
     */
    public Field(int height, int width, int mineCount) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Incorrect size");
        }
        if (mineCount < 0) {
            throw new IllegalArgumentException("Incorrect mine count");
        }

        this.height = height;
        this.width = width;
        this.mineCount = Math.min(mineCount, height * width - 1);
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
        mineMatrix[y][x] = MINE;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                boolean isCentralPoint = (i == 0 && j == 0);
                boolean isXWithinBoundaries = (x + j >= 0) && (x + j < width);
                boolean isYWithinBoundaries = (y + i >= 0) && (y + i < height);
                if (!isCentralPoint && isXWithinBoundaries && isYWithinBoundaries
                    && mineMatrix[y + i][x + j] != MINE) {
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
     * Marks cell with coordinates (x, y). Also notifies listeners when model fields change.
     *
     * @param y the y coordinate
     * @param x the x coordinate
     */
    public void markCell(int y, int x) {
        checkCoordinates(y, x);
        CellView oldCellView = cellViewMatrix[y][x];
        int oldMarkedCellCount = markedCellCount;

        if (oldCellView == CellView.CLOSED && oldMarkedCellCount < mineCount) {
            cellViewMatrix[y][x] = CellView.MARKED;
            ++markedCellCount;
        } else if (oldCellView == CellView.MARKED) {
            cellViewMatrix[y][x] = CellView.CLOSED;
            --markedCellCount;
        }

        // Send notification to listeners
        if (oldCellView != cellViewMatrix[y][x]) {
            notifyObservers(new MarkedCellContext(x, y, markedCellCount,
                                                  cellViewMatrix[y][x] == CellView.MARKED));
        }
    }

    /**
     * Opens cell with coordinates (x, y). Also notifies listeners when model fields change.
     *
     * @param y the y coordinate
     * @param x the x coordinate
     */
    public void openCell(int y, int x) {
        checkCoordinates(y, x);
        CellView oldCellView = cellViewMatrix[y][x];
        if (oldCellView == CellView.CLOSED) {
            cellViewMatrix[y][x] = CellView.OPENED;
            if (mineMatrix[y][x] != MINE) {
                ++openedCellCount;
            }

            // Send notification to listeners
            notifyObservers(
                new OpenedCellContext(x, y, mineMatrix[y][x], mineMatrix[y][x] == MINE));
        }

        checkGameOver(y, x);

        if (mineMatrix[y][x] == 0) {
            openNeighbourCells(y, x);
        }
    }

    private void checkGameOver(int y, int x) {
        boolean areAllCellsOpened = openedCellCount == height * width - mineCount;
        boolean isOpenedMine =
            (mineMatrix[y][x] == MINE) && (cellViewMatrix[y][x] == CellView.OPENED);
        if (!isGameOver && (isOpenedMine || areAllCellsOpened)) {
            isGameOver = true;
            openAllMines();

            // Send notification to listeners
            notifyObservers(new GameOverContext(areAllCellsOpened));
        }
    }

    private void openNeighbourCells(int y, int x) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                boolean isXWithinBoundaries = (x + j >= 0) && (x + j < width);
                boolean isYWithinBoundaries = (y + i >= 0) && (y + i < height);

                if (isXWithinBoundaries && isYWithinBoundaries && mineMatrix[y + i][x + j] != MINE
                    && cellViewMatrix[y + i][x + j] == CellView.CLOSED) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    openCell(y + i, x + j);
                }
            }
        }
    }

    private void openAllMines() {
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (mineMatrix[i][j] == MINE && cellViewMatrix[i][j] != CellView.OPENED) {
                    openCell(i, j);
                }
            }
        }
    }

    private void checkCoordinates(int y, int x) {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new IllegalArgumentException("Incorrect coordinates");
        }
    }
}
