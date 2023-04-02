package ru.nsu.ccfit.petrov.minesweeper.observer.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code GameOverContext} is class that describe game over message of
 * {@link ru.nsu.ccfit.petrov.minesweeper.observer.Observable Observable} objects.
 *
 * @author ptrvsrg 
 */
@Getter
@AllArgsConstructor
public class GameOverContext
    implements Context {

    private boolean isWinner;
}
