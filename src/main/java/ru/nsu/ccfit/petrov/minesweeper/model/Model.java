package ru.nsu.ccfit.petrov.minesweeper.model;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.NonNull;

public class Model {
    private final @NonNull Field field;
    private int markedCellCount = 0;
    private int openedCellCount = 0;
    private boolean isGameOver = false;
    private PropertyChangeSupport propertyChangeSupport = null;
    public static final String MARKED_CELL_VIEW_PROPERTY = "markedCellViewProperty";
    public static final String OPENED_CELL_VIEW_PROPERTY = "openedCellViewProperty";
    public static final String MARKED_CELL_COUNT_PROPERTY = "markedCellCountProperty";
    public static final String IS_WINNER_PROPERTY = "isWinnerProperty";

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
                throw new NullPointerException();
        }
    }

    public int getHeight() {
        return field.getHeight();
    }

    public int getWidth() {
        return field.getWidth();
    }

    public int getMineCount() {
        return field.getMineCount();
    }

    public int getMarkedCellCount() {
        return markedCellCount;
    }

    public byte getMineCountAround(int y, int x) {
        return field.getMineCountAround(y, x);
    }

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

        if (oldCellView != field.getCellView(y, x)) {
            propertyChangeSupport.firePropertyChange(MARKED_CELL_VIEW_PROPERTY, null,
                                                     new Point(x, y));
            propertyChangeSupport.firePropertyChange(MARKED_CELL_COUNT_PROPERTY, oldMarkedCellCount,
                                                     markedCellCount);
        }
    }

    public void openCell(int y, int x) {
        CellView oldCellView = field.getCellView(y, x);
        if (oldCellView == CellView.CLOSED) {
            field.setCellView(y, x, CellView.OPENED);
            ++openedCellCount;
            propertyChangeSupport.firePropertyChange(OPENED_CELL_VIEW_PROPERTY, null,
                                                     new Point(x, y));
        }

        boolean areAllCellsOpened =
            openedCellCount == field.getHeight() * field.getWidth() - field.getMineCount();
        if (!isGameOver && (field.isMine(y, x) || areAllCellsOpened)) {
                isGameOver = true;
                openAllMines();
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
                if (y + i >= 0 && y + i < field.getHeight() && x + j >= 0
                    && x + j < field.getWidth()
                    && field.getCellView(y + i, x + j) == CellView.CLOSED && !field.isMine(y + i,
                                                                                           x + j)) {
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (propertyChangeSupport == null) {
            propertyChangeSupport = new PropertyChangeSupport(this);
        }

        propertyChangeSupport.addPropertyChangeListener(listener);
    }
}
