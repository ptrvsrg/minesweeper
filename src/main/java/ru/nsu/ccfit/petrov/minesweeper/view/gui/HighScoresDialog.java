package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Font;
import java.util.Map.Entry;
import java.util.SortedMap;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import ru.nsu.ccfit.petrov.minesweeper.model.ScoreRating;
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

        SortedMap<Integer, String> scores = ScoreRating.getScores();
        for (Entry<Integer, String> score : scores.entrySet()) {
            JLabel scoreLine = new JLabel(
                score.getValue() + " - " + Stopwatch.timeToString(score.getKey()) + "\n");
            scoreLine.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            textArea.add(scoreLine);
        }

        add(new JScrollPane(textArea));
        setVisible(true);
    }
}
