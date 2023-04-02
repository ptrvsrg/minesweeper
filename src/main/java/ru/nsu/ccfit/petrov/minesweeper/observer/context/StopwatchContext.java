package ru.nsu.ccfit.petrov.minesweeper.observer.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code StopwatchContext} is class that describe stopwatch message of
 * {@link ru.nsu.ccfit.petrov.minesweeper.observer.Observable Observable} objects.
 *
 * @author ptrvsrg
 */
@Getter
@AllArgsConstructor
public class StopwatchContext
    implements Context {
    private int second;
}
