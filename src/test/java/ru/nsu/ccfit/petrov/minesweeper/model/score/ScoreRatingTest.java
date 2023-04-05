package ru.nsu.ccfit.petrov.minesweeper.model.score;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.SortedSet;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ScoreRatingTest
    extends Assertions {

    private File getScoreFile() {
        String scoreFilePath =
            Objects.requireNonNull(ScoreRating.class.getClassLoader().getResource("")).getPath()
                + ScoreRating.SCORE_FILE;
        return new File(scoreFilePath);
    }

    private void deleteScoreFile() {
        File scoreFile = getScoreFile();
        scoreFile.delete();
    }

    @Test(description = "Check getting score when score file is empty",
          groups = "Score rating tests")
    public void checkGetScoreWhenScoreFileIsEmpty() {
        // prepare
        deleteScoreFile();

        // do
        SortedSet<Score> scores = ScoreRating.getScoreRating();

        // check
        assertThat(scores).isEmpty();
    }

    @Test(description = "Check saving and getting score",
          groups = "Score rating tests")
    public void checkSaveAndGetScore() {
        // prepare
        String playerName = "player";
        int gameTime = 1234;
        Score score = new Score(playerName, gameTime);

        // do
        ScoreRating.saveScore(score);
        SortedSet<Score> scores = ScoreRating.getScoreRating();

        // check
        assertThat(scores).contains(score);
    }

    private void writeToScoreFile(String[] entry) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(getScoreFile()))) {
            csvWriter.writeNext(entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DataProvider(name = "Create incorrect CSV file entry")
    private Object[][] createIncorrectCsvFileEntry() {
        return new Object[][]{new Object[]{new String[]{"playerName", "123", "Good"}},
                              new Object[]{new String[]{"playerName"}},
                              new Object[]{new String[]{"playerName", "Good"}},
                              new Object[]{new String[]{"playerName", "3.14"}}};
    }

    @Test(description = "Check getting score when CSV file entry is incorrect",
          dataProvider = "Create incorrect CSV file entry",
          groups = "Score rating tests")
    public void checkGetScoreWhenCsvFileEntryIsIncorrect(String[] entry) {
        // prepare
        writeToScoreFile(entry);

        // check
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call()
                throws Throwable {
                ScoreRating.getScoreRating();
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }
}