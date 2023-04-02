package ru.nsu.ccfit.petrov.minesweeper.observer.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code OpenedCellContext} is class that describe cell open message of
 * {@link ru.nsu.ccfit.petrov.minesweeper.observer.Observable Observable} objects.
 *
 * @author ptrvsrg
 */
@Getter
@AllArgsConstructor
public class OpenedCellContext
    implements Context {
    private int x;
    private int y;
    private byte mineAroundCount;
    private boolean isMine;
}
