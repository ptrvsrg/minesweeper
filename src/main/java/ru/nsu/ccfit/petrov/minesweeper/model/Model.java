package ru.nsu.ccfit.petrov.minesweeper.model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.Getter;

/**
 * The type {@code Model} is class for description game logic.
 *
 * @author ptrvsrg
 */
public class Model {
    private final Field field;
    @Getter
    private int markedCellCount = 0;
    private int openedCellCount = 0;
    private boolean isGameOver = false;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    /**
     * The constant MARKED_CELL_VIEW_PROPERTY is constant for {@link PropertyChangeListener}.
     */
    public static final String MARKED_CELL_VIEW_PROPERTY = "markedCellViewProperty";
    /**
     * The constant OPENED_CELL_VIEW_PROPERTY is constant for {@link PropertyChangeListener}.
     */
    public static final String OPENED_CELL_VIEW_PROPERTY = "openedCellViewProperty";
    /**
     * The constant MARKED_CELL_COUNT_PROPERTY is constant for {@link PropertyChangeListener}.
     */
    public static final String MARKED_CELL_COUNT_PROPERTY = "markedCellCountProperty";
    /**
     * The constant IS_WINNER_PROPERTY is constant for {@link PropertyChangeListener}.
     */
    public static final String IS_WINNER_PROPERTY = "isWinnerProperty";

    /**
     * Instantiates a new Model.
     *
     * @param level the level
     * @throws IllegalArgumentException if level does not belong to {@link ru.nsu.ccfit.petrov.minesweeper.model.Level Level}
     */
    public Model(Level level) {
        switch (level) {
            case BEGINNER:
                field = new Field(9, 9, 10);
                break;
            case INTERMEDIATE:
                field = new Field(16, 16, 40);
                break;
            case EXPERT:
                field = new Field(16, 30, 99);
                break;
            default:
                throw new IllegalArgumentException("Incorrect game level");
        }
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
     * Mark cell with coordinates (x, y). Also notifies listeners when model fields change.
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
            propertyChangeSupport.firePropertyChange(MARKED_CELL_VIEW_PROPERTY, null,
                                                     new Point(x, y));
            propertyChangeSupport.firePropertyChange(MARKED_CELL_COUNT_PROPERTY, oldMarkedCellCount,
                                                     markedCellCount);
        }
    }

    /**
     * Open cell with coordinates (x, y). Also notifies listeners when model fields change.
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
            propertyChangeSupport.firePropertyChange(OPENED_CELL_VIEW_PROPERTY, null,
                                                     new Point(x, y));
        }

        boolean areAllCellsOpened =
            openedCellCount == field.getHeight() * field.getWidth() - field.getMineCount();
        boolean isOpenedMine = field.isMine(y, x) && field.getCellView(y, x) == CellView.OPENED;
        if (!isGameOver && (isOpenedMine || areAllCellsOpened)) {
            isGameOver = true;
            openAllMines();

            // Send notification to listeners
            propertyChangeSupport.firePropertyChange(IS_WINNER_PROPERTY, null,
                                                     areAllCellsOpened);
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

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * The same listener object may be added more than once, and will be called
     * as many times as it is added.
     * If {@code listener} is null, no exception is thrown and no action
     * is taken.
     *
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
