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
import ru.nsu.ccfit.petrov.minesweeper.model.Model;
import ru.nsu.ccfit.petrov.minesweeper.model.Model.Level;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.BackgroundPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.MenuButton;

public class StartMenu
    extends JFrame {

    private static final String TITLE = "Welcome \"Minesweeper\"";
    private static final String BEGINNER_BUTTON_TEXT = "Beginner";
    private static final String INTERMEDIATE_BUTTON_TEXT = "Intermediate";
    private static final String EXPERT_BUTTON_TEXT = "Expert";
    private static final String BACKGROUND_IMAGE_PATH = "/start_menu_background.png";

    public StartMenu() {
        setTitle(TITLE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Utils.exitConfirm(StartMenu.this);
            }
        });

        JPanel buttonArea = new JPanel();
        buttonArea.setPreferredSize(new Dimension(getWidth() / 2, getHeight() / 2));
        buttonArea.setLayout(new GridLayout(3, 1));
        buttonArea.setBackground(Utils.getTransparentColor());
        buttonArea.add(createLevelButton(BEGINNER_BUTTON_TEXT, Level.BEGINNER));
        buttonArea.add(createLevelButton(INTERMEDIATE_BUTTON_TEXT, Level.INTERMEDIATE));
        buttonArea.add(createLevelButton(EXPERT_BUTTON_TEXT, Level.EXPERT));

        BackgroundPanel contentPane = new BackgroundPanel(BACKGROUND_IMAGE_PATH);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(buttonArea);

        setContentPane(contentPane);
        setVisible(true);
    }

    private MenuButton createLevelButton(String levelText, Level level) {
        MenuButton levelButton = new MenuButton(levelText);
        levelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameSpace(new Model(level));
            }
        });

        return levelButton;
    }
}
