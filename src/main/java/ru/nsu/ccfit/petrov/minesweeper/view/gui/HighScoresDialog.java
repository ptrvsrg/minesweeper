package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Font;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;

/**
 * The type {@code HighScoresDialog} is class that describe a dialog window with a table of records.
 *
 * @author ptrvsrg
 */
public class HighScoresDialog
    extends JDialog {
    private static final String TITLE = "High Scores";
    private static final int FONT_SIZE = 20;
    private static final int BORDER_INSET = 10;
    private final Controller controller;

    /**
     * Instantiates a new HighScoresDialog.
     *
     * @param owner      the owner
     * @param controller the controller
     */
    public HighScoresDialog(JFrame owner, Controller controller) {
        super(owner, true);
        this.controller = controller;

        setTitle(TITLE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        add(new JScrollPane(createTextArea()));
        setVisible(true);
    }

    private JPanel createTextArea() {
        JPanel textArea = new JPanel();
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        textArea.setBorder(new EmptyBorder(BORDER_INSET, BORDER_INSET, BORDER_INSET, BORDER_INSET));

        for (Entry<String, Integer> score : controller.getScoreRating()) {
            JLabel scoreLine = new JLabel(
                score.getKey() + " - " + Stopwatch.timeToString(score.getValue()) + "\n");
            scoreLine.setFont(new Font(Font.DIALOG, Font.BOLD, FONT_SIZE));
            textArea.add(scoreLine);
        }

        return textArea;
    }
}
