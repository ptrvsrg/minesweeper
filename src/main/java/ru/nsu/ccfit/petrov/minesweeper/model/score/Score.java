package ru.nsu.ccfit.petrov.minesweeper.model.score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Score
{
    private final String playerName;
    private final int time;

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