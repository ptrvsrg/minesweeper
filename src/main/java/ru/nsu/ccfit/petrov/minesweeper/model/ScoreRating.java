package ru.nsu.ccfit.petrov.minesweeper.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class ScoreRating {

    private static final String SCORE_FILE = "/scores.csv";

    private ScoreRating() {
        throw new IllegalStateException("Utility class");
    }

    public static SortedMap<Integer, String> getScores() {
        File scoreFile = new File(getScoreFile());
        if (!scoreFile.exists()) {
            return Collections.emptySortedMap();
        }

        try (CSVReader csvReader = new CSVReader(new FileReader(scoreFile))) {
            List<String[]> readScores = csvReader.readAll();
            TreeMap<Integer, String> processedScores = new TreeMap<>();

            for (String[] score : readScores) {
                if (score.length != 2) {
                    throw new IllegalArgumentException("Incorrect file format");
                }

                processedScores.put(Integer.valueOf(score[0]), score[1]);
            }

            return processedScores;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveScore(String userName, int time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time is negative");
        }

        File scoreFile = new File(getScoreFile());
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(scoreFile, true))) {
            csvWriter.writeNext(new String[]{Integer.toString(time), userName});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getScoreFile() {
        return Objects.requireNonNull(ScoreRating.class.getClassLoader().getResource("")).getPath()
            + SCORE_FILE;
    }
}
