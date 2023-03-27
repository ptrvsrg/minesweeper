package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Font;
import java.util.SortedSet;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import ru.nsu.ccfit.petrov.minesweeper.model.score.Score;
import ru.nsu.ccfit.petrov.minesweeper.model.score.ScoreRating;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;

public class HighScoresDialog
    extends JDialog {

    private final String TITLE = "High Scores";

    public HighScoresDialog(JFrame owner) {
        super(owner, true);
        setTitle(TITLE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel textArea = new JPanel();
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        SortedSet<Score> scores = ScoreRating.getScores();
        for (Score score : scores) {
            JLabel scoreLine = new JLabel(
                score.getUserName() + " - " + Stopwatch.timeToString(score.getTime()) + "\n");
            scoreLine.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            textArea.add(scoreLine);
        }

        add(new JScrollPane(textArea));
        setVisible(true);
    }
}