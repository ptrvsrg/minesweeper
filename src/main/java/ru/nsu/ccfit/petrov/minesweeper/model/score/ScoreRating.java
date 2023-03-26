package ru.nsu.ccfit.petrov.minesweeper.model.score;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class ScoreRating {

    private static final String SCORE_FILE = "/scores.csv";

    private ScoreRating() {
        throw new IllegalStateException("Utility class");
    }

    public static SortedSet<Score> getScores() {
        File scoreFile = new File(getScoreFile());
        if (!scoreFile.exists()) {
            return Collections.emptySortedSet();
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(scoreFile))) {
            List<String[]> readScores = csvReader.readAll();

            Comparator<Score> comparator = (o1, o2) -> {
                if (o1.getTime() == o2.getTime()) {
                    return o1.getUserName().compareTo(o2.getUserName());
                }

                return Integer.compare(o2.getTime(), o1.getTime());
            };

            TreeSet<Score> processedScores = new TreeSet<>(comparator);

            for (String[] score : readScores) {
                if (score.length != 2) {
                    throw new IllegalArgumentException("Incorrect file format");
                }

                processedScores.add(new Score(score[0], Integer.parseInt(score[1])));
            }

            return processedScores;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveScore(Score score) {
        File scoreFile = new File(getScoreFile());
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(scoreFile, true))) {
            csvWriter.writeNext(
                new String[]{score.getUserName(), Integer.toString(score.getTime())});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getScoreFile() {
        return Objects.requireNonNull(ScoreRating.class.getClassLoader().getResource("")).getPath()
            + SCORE_FILE;
    }
}
