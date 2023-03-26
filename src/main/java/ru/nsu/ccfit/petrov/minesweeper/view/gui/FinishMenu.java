package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import ru.nsu.ccfit.petrov.minesweeper.model.score.Score;
import ru.nsu.ccfit.petrov.minesweeper.model.score.ScoreRating;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.BackgroundPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.MenuButton;

public class FinishMenu
    extends JDialog {

    private final String NEW_GAME_TEXT = "New Game";
    private final String HIGH_SCORES_TEXT = "High Scores";
    private final String SAVE_SCORE_TEXT = "Save Score";
    private final String PLAYER_NAME_TEXT = "Player Name: ";
    private final String WINNER_TITLE = "Victory";
    private final String LOSER_TITLE = "R.I.P";
    private final String WINNER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_winner.png";
    private final String LOSER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_loser.png";
    private final int time;

    public enum PlayerStatus {
        WINNER, LOSER
    }

    public FinishMenu(GameSpace owner, PlayerStatus playerStatus, int time) {
        super(owner, true);
        this.time = time;

        setTitle((playerStatus == PlayerStatus.WINNER) ? WINNER_TITLE : LOSER_TITLE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Utils.exitConfirm((GameSpace) getOwner());
            }
        });

        JPanel buttonArea = new JPanel();
        buttonArea.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));
        buttonArea.setLayout(new GridLayout(3, 1));
        buttonArea.setBackground(new Color(0, 0, 0, 0));
        buttonArea.add(createNewGameButton());
        buttonArea.add(createHighScoresButton());

        if (playerStatus == PlayerStatus.WINNER) {
            buttonArea.add(createSaveScoreButton());
        }

        BackgroundPanel contentPane = new BackgroundPanel(
            (playerStatus == PlayerStatus.WINNER) ? WINNER_BACKGROUND_IMAGE_PATH
                                                  : LOSER_BACKGROUND_IMAGE_PATH);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(buttonArea);

        setContentPane(contentPane);
        setVisible(true);
    }

    private MenuButton createNewGameButton() {
        MenuButton newGameButton = new MenuButton(NEW_GAME_TEXT);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                getOwner().dispose();
                new StartMenu();
            }
        });
        return newGameButton;
    }

    private MenuButton createHighScoresButton() {
        MenuButton highScoresButton = new MenuButton(HIGH_SCORES_TEXT);
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresDialog((GameSpace) getOwner());
            }
        });
        return highScoresButton;
    }

    private MenuButton createSaveScoreButton() {
        MenuButton saveScoreButton = new MenuButton(SAVE_SCORE_TEXT);
        saveScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(FinishMenu.this,
                                                                PLAYER_NAME_TEXT, SAVE_SCORE_TEXT,
                                                                JOptionPane.INFORMATION_MESSAGE);
                ScoreRating.saveScore(new Score(playerName, time));
            }
        });
        return saveScoreButton;
    }
}
