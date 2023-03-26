package ru.nsu.ccfit.petrov.minesweeper.model.score;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Score
{
    private final String userName;
    private final int time;
}