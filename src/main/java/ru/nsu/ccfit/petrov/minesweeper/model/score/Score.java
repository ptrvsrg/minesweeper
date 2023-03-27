package ru.nsu.ccfit.petrov.minesweeper.model.score;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The type {@code Score} is class for saving player name and him game time.
 *
 * @author ptrvsrg
 */
@Getter
@AllArgsConstructor
public class Score
{
    private final String playerName;
    private final int time;

    /**
     * Gets comparator for compare objects of type {@code Score}.
     *
     * @return {@code Score} comparator
     */
    public static Comparator<Score> getComparator() {
        return new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                if (o1.getTime() == o2.getTime()) {
                    return o1.getPlayerName().compareTo(o2.getPlayerName());
                }

                return Integer.compare(o1.getTime(), o2.getTime());
            }
        };
    }
}