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
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.BackgroundPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.MenuButton;

/**
 * The type {@code FinishMenu} is class that describe the finish menu in GUI mode.
 *
 * @author ptrvsrg
 */
public class GUIFinishMenu
    extends JDialog {

    private static final String NEW_GAME_TITLE = "New Game";
    private static final String HIGH_SCORES_TITLE = "High Scores";
    private static final String SAVE_SCORE_TITLE = "Save Score";
    private static final String ABOUT_TITLE = "About";
    private static final String ABOUT_MESSAGE =
        "Program \"Minesweeper v1.0\"\n" + "Designer: prtvsrg\n" + "Developer: ptrvsrg\n"
            + "Source code: https://github.com/ptrvsrg/NSU_OOP_Java/tree/master/Task3";
    private static final String PLAYER_NAME_MESSAGE = "Player Name: ";
    private static final String WINNER_TITLE = "Victory";
    private static final String LOSER_TITLE = "R.I.P";
    private static final String WINNER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_winner.png";
    private static final String LOSER_BACKGROUND_IMAGE_PATH = "/finish_menu_background_loser.png";
    private final Controller controller;

    /**
     * Instantiates a new FinishMenu.
     *
     * @param owner      the owner
     * @param controller the controller
     */
    public GUIFinishMenu(GUIGameSpace owner, Controller controller) {
        super(owner, true);
        this.controller = controller;

        setTitle((controller.getIsWinner()) ? WINNER_TITLE : LOSER_TITLE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowClosingListener());

        setContentPane(createContentPane());
        setVisible(true);
    }

    private BackgroundPanel createContentPane() {
        BackgroundPanel contentPane = new BackgroundPanel(
            (controller.getIsWinner()) ? WINNER_BACKGROUND_IMAGE_PATH
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
        buttonArea.add(createMenuButton(NEW_GAME_TITLE, new NewGameButtonListener()));
        buttonArea.add(createMenuButton(HIGH_SCORES_TITLE, new HighScoreButtonListener()));

        if (controller.getIsWinner()) {
            buttonArea.add(createMenuButton(SAVE_SCORE_TITLE, new SaveScoreButtonListener()));
        }

        buttonArea.add(createMenuButton(ABOUT_TITLE, new AboutButtonListener()));

        return buttonArea;
    }

    private MenuButton createMenuButton(String title, ActionListener listener) {
        MenuButton menuButton = new MenuButton(title);
        menuButton.addActionListener(listener);
        return menuButton;
    }

    private class NewGameButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs. In this case, it closes all windows and creates a new
         * start menu frame.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            getOwner().dispose();
            controller.removeModel();
            new GUIStartMenu(controller);
        }
    }

    private class HighScoreButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs. In this case, it creates a window with a table of
         * results.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            new HighScoresDialog((GUIGameSpace) getOwner(), controller);
        }
    }

    private class SaveScoreButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs. In this case, it creates a window for entering the
         * player's name and saves the result to the results table.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String playerName = JOptionPane.showInputDialog(GUIFinishMenu.this, PLAYER_NAME_MESSAGE,
                                                            SAVE_SCORE_TITLE,
                                                            JOptionPane.INFORMATION_MESSAGE);
            if (playerName != null && !playerName.isEmpty()) {
                controller.saveScore(playerName);
            }
        }
    }

    private class AboutButtonListener
        implements ActionListener {

        /**
         * Invoked when an action occurs. In this case, it creates a help window with information
         * about the application.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(GUIFinishMenu.this, ABOUT_MESSAGE, ABOUT_TITLE,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class WindowClosingListener
        extends WindowAdapter {

        /**
         * Invoked when a window is in the process of being closed. The close operation can be
         * overridden at this point. In this case, it causes the exit confirmation window to be
         * drawn.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosing(WindowEvent e) {
            Utils.exitConfirm((GUIGameSpace) getOwner());
        }
    }
}
