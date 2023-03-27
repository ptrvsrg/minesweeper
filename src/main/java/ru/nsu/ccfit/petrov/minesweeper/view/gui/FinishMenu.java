package ru.nsu.ccfit.petrov.minesweeper.view.gui;

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
import ru.nsu.ccfit.petrov.minesweeper.view.PlayerStatus;

/**
 * The type {@code FinishMenu} is class that describe the finish menu in GUI mode.
 *
 * @author ptrvsrg
 */
public class FinishMenu
    extends JDialog {
    private static final String NEW_GAME_TITLE = "New Game";
    private static final String HIGH_SCORES_TITLE = "High Scores";
    private static final String SAVE_SCORE_TITLE = "Save Score";
    private static final String ABOUT_TITLE = "About";
    private static final String ABOUT_MESSAGE = "Program \"Minesweeper v1.0\"\n"
        + "Designer: prtvsrg\n"
        + "Developer: ptrvsrg\n"
        + "Source code: https://github.com/ptrvsrg/NSU_OOP_Java/tree/master/Task3";
    private static final String PLAYER_NAME_MESSAGE = "Player Name: ";
    private static final String WINNER_TITLE = "Victory";
    private static final String LOSER_TITLE = "R.I.P";
    private static final String WINNER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_winner.png";
    private static final String LOSER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_loser.png";
    private final PlayerStatus playerStatus;
    private final int time;

    /**
     * Instantiates a new FinishMenu.
     *
     * @param owner        the owner of finish menu
     * @param playerStatus the player status
     * @param time         the game time
     */
    public FinishMenu(GameSpace owner, PlayerStatus playerStatus, int time) {
        super(owner, true);
        this.playerStatus = playerStatus;
        this.time = time;

        setTitle((playerStatus == PlayerStatus.WINNER) ? WINNER_TITLE : LOSER_TITLE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed. The
             * close operation can be overridden at this point.
             * In this case, it causes the exit confirmation window to be drawn.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                Utils.exitConfirm((GameSpace) getOwner());
            }
        });

        setContentPane(createContentPane());
        setVisible(true);
    }

    private BackgroundPanel createContentPane() {
        BackgroundPanel contentPane = new BackgroundPanel(
        (playerStatus == PlayerStatus.WINNER) ? WINNER_BACKGROUND_IMAGE_PATH
                                              : LOSER_BACKGROUND_IMAGE_PATH);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(createButtonArea());

        return contentPane;
    }

    private JPanel createButtonArea() {
        JPanel buttonArea = new JPanel();
        buttonArea.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));
        buttonArea.setLayout(new GridLayout(4, 1));
        buttonArea.setBackground(Utils.getTransparentColor());
        buttonArea.add(createNewGameButton());
        buttonArea.add(createHighScoresButton());

        if (playerStatus != PlayerStatus.LOSER){
            buttonArea.add(createSaveScoreButton());
        }

        buttonArea.add(createAboutButton());

        return buttonArea;
    }

    private MenuButton createNewGameButton() {
        MenuButton newGameButton = new MenuButton(NEW_GAME_TITLE);
        newGameButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * In this case, it closes all windows and creates a new start menu frame.
             *
             * @param e the event to be processed
             */
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
        MenuButton highScoresButton = new MenuButton(HIGH_SCORES_TITLE);
        highScoresButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * In this case, it creates a window with a table of results.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoresDialog((GameSpace) getOwner());
            }
        });
        return highScoresButton;
    }

    private MenuButton createSaveScoreButton() {
        MenuButton saveScoreButton = new MenuButton(SAVE_SCORE_TITLE);
        saveScoreButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * In this case, it creates a window for entering the player's name and saves the result to the results table.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(FinishMenu.this,
                                                                PLAYER_NAME_MESSAGE, SAVE_SCORE_TITLE,
                                                                JOptionPane.INFORMATION_MESSAGE);
                ScoreRating.saveScore(new Score(playerName, time));
            }
        });
        return saveScoreButton;
    }

    private MenuButton createAboutButton() {
        MenuButton aboutButton = new MenuButton(ABOUT_TITLE);
        aboutButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * In this case, it creates a help window with information about the application.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(FinishMenu.this, ABOUT_MESSAGE, ABOUT_TITLE,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return aboutButton;
    }
}
