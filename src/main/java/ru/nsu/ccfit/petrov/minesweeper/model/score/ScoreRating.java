package ru.nsu.ccfit.petrov.minesweeper.model.score;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ScoreRating
{
    private ScoreRating() {
        throw new IllegalStateException("Utility class");
    }

    private static final File scoreFile = new File(ScoreRating.class
                                                       .getClassLoader()
                                                       .getResource("score.csv")
                                                       .getFile());
    public static void addScore(Score score) {
        try(CSVReader csvReader = new CSVReader(new FileReader(scoreFile));
            CSVWriter csvWriter = new CSVWriter(new FileWriter(scoreFile))) {
            List<String[]> allLines = csvReader.readAll();

            for (String[] args : allLines) {
                if (Integer.getInteger(args[0]) == score.getFieldWidth() &&
                    Integer.getInteger(args[1]) == score.getFieldHeight() &&
                    Integer.getInteger(args[2]) == score.getMineCount() &&
                    Integer.getInteger(args[3]) < score.getGameTime()) {
                    args[3] = ((Integer) score.getGameTime()).toString();
                    break;
                }
            }

            csvWriter.writeAll(allLines);
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Score> getScores() {
        try(CSVReader csvReader = new CSVReader(new FileReader(scoreFile))) {
            List<Score> allScores = new ArrayList<>();
            List<String[]> allLines = csvReader.readAll();

            for (String[] args : allLines)
                allScores.add(new Score(Integer.getInteger(args[0]),
                                        Integer.getInteger(args[1]),
                                        Integer.getInteger(args[2]),
                                        Integer.getInteger(args[3])));

            return allScores;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteScores() {
        try {
            Files.delete(scoreFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
