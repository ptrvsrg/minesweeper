package ru.nsu.ccfit.petrov.minesweeper.model;

import lombok.Getter;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observable;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;

/**
 * The type {@code Model} is class for description game logic.
 *
 * @author ptrvsrg
 */
public class Model
    extends Observable {

    private final Field field;
    @Getter
    private int markedCellCount = 0;
    private int openedCellCount = 0;
    private boolean isGameOver = false;

    /**
     * Instantiates a new Model.
     *
     * @param height    the field height
     * @param width     the field width
     * @param mineCount the mine count on minefield
     */
    public Model(int height, int width, int mineCount) {
        this.field = new Field(height, width, mineCount);
    }

    /**
     * Gets field height.
     *
     * @return the field height
     */
    public int getHeight() {
        return field.getHeight();
    }

    /**
     * Gets field width.
     *
     * @return the field width
     */
    public int getWidth() {
        return field.getWidth();
    }

    /**
     * Gets mine count in field.
     *
     * @return the mine count
     */
    public int getMineCount() {
        return field.getMineCount();
    }

    /**
     * Gets mine count around cell with coordinates (x, y).
     *
     * @param y the y coordinate
     * @param x the x coordinate
     * @return the mine count around cell with coordinates (x, y)
     */
    public byte getMineCountAround(int y, int x) {
        return field.getMineCountAround(y, x);
    }

    /**
     * Marks cell with coordinates (x, y). Also notifies listeners when model fields change.
     *
     * @param y the y coordinate
     * @param x the x coordinate
     */
    public void markCell(int y, int x) {
        CellView oldCellView = field.getCellView(y, x);
        int oldMarkedCellCount = markedCellCount;

        if (oldCellView == CellView.CLOSED && oldMarkedCellCount < field.getMineCount()) {
            field.setCellView(y, x, CellView.MARKED);
            ++markedCellCount;
        } else if (oldCellView == CellView.MARKED) {
            field.setCellView(y, x, CellView.CLOSED);
            --markedCellCount;
        }

        // Send notification to listeners
        if (oldCellView != field.getCellView(y, x)) {
            notifyObservers(new MarkedCellContext(x, y, markedCellCount,
                                                  field.getCellView(y, x) == CellView.MARKED));
        }
    }

    /**
     * Opens cell with coordinates (x, y). Also notifies listeners when model fields change.
     *
     * @param y the y coordinate
     * @param x the x coordinate
     */
    public void openCell(int y, int x) {
        CellView oldCellView = field.getCellView(y, x);
        if (oldCellView == CellView.CLOSED) {
            field.setCellView(y, x, CellView.OPENED);
            if (!field.isMine(y, x)) {
                ++openedCellCount;
            }

            // Send notification to listeners
            notifyObservers(
                new OpenedCellContext(x, y, field.getMineCountAround(y, x), field.isMine(y, x)));
        }

        boolean areAllCellsOpened =
            openedCellCount == field.getHeight() * field.getWidth() - field.getMineCount();
        boolean isOpenedMine = field.isMine(y, x) && field.getCellView(y, x) == CellView.OPENED;
        if (!isGameOver && (isOpenedMine || areAllCellsOpened)) {
            isGameOver = true;
            openAllMines();

            // Send notification to listeners
            notifyObservers(new GameOverContext(areAllCellsOpened));
        }

        if (!field.isMine(y, x) && !field.areThereMinesAround(y, x)) {
            openNeighbourCells(y, x);
        }
    }

    private void openNeighbourCells(int y, int x) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                boolean isXWithinBoundaries = (x + j >= 0) && (x + j < field.getWidth());
                boolean isYWithinBoundaries = (y + i >= 0) && (y + i < field.getHeight());

                if (isXWithinBoundaries && isYWithinBoundaries && !field.isMine(y + i, x + j)
                    && field.getCellView(y + i, x + j) == CellView.CLOSED) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    openCell(y + i, x + j);
                }
            }
        }
    }

    private void openAllMines() {
        for (int i = 0; i < field.getHeight(); ++i) {
            for (int j = 0; j < field.getWidth(); ++j) {
                if (field.isMine(i, j) && field.getCellView(i, j) != CellView.OPENED) {
                    openCell(i, j);
                }
            }
        }
    }
}
