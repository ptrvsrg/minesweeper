package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import ru.nsu.ccfit.petrov.minesweeper.model.Level;
import ru.nsu.ccfit.petrov.minesweeper.model.Model;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.BackgroundPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.MenuButton;

/**
 * The type {@code FinishMenu} is class that describe the start menu in GUI mode.
 *
 * @author ptrvsrg
 */
public class StartMenu
    extends JFrame {
    private static final String TITLE = "Welcome \"Minesweeper\"";
    private static final String BEGINNER_BUTTON_TEXT = "Beginner";
    private static final String INTERMEDIATE_BUTTON_TEXT = "Intermediate";
    private static final String EXPERT_BUTTON_TEXT = "Expert";
    private static final String BACKGROUND_IMAGE_PATH = "/start_menu_background.png";

    /**
     * Instantiates a new StartMenu.
     */
    public StartMenu() {
        setTitle(TITLE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed. The
             * close operation can be overridden at this point.
             *
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                Utils.exitConfirm(StartMenu.this);
            }
        });
        setContentPane(createContentPane());
        setVisible(true);
    }

    private BackgroundPanel createContentPane() {
        BackgroundPanel contentPane = new BackgroundPanel(BACKGROUND_IMAGE_PATH);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(createButtonArea());

        return contentPane;
    }

    private JPanel createButtonArea() {
        JPanel buttonArea = new JPanel();
        buttonArea.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));
        buttonArea.setLayout(new GridLayout(3, 1));
        buttonArea.setBackground(Utils.getTransparentColor());
        buttonArea.add(createLevelButton(BEGINNER_BUTTON_TEXT, Level.BEGINNER));
        buttonArea.add(createLevelButton(INTERMEDIATE_BUTTON_TEXT, Level.INTERMEDIATE));
        buttonArea.add(createLevelButton(EXPERT_BUTTON_TEXT, Level.EXPERT));

        return buttonArea;
    }

    private MenuButton createLevelButton(String levelText, Level level) {
        MenuButton levelButton = new MenuButton(levelText);
        levelButton.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameSpace(new Model(level));
            }
        });

        return levelButton;
    }
}
