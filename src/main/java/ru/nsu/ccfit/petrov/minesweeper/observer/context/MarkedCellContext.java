package ru.nsu.ccfit.petrov.minesweeper.observer.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code MarkedCellContext} is class that describe cell mark message of
 * {@link ru.nsu.ccfit.petrov.minesweeper.observer.Observable Observable} objects.
 *
 * @author ptrvsrg
 */
@Getter
@AllArgsConstructor
public class MarkedCellContext
    implements Context {
    private int x;
    private int y;
    private int markedCellCount;
    private boolean isMarked;
}
