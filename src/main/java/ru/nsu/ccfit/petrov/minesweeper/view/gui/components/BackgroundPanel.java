package ru.nsu.ccfit.petrov.minesweeper.view.gui.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.StartMenu;

public class BackgroundPanel
    extends JPanel {

    private final Image backgroundImage;

    public BackgroundPanel(String backgroundImagePath) {
        this.backgroundImage = Toolkit.getDefaultToolkit()
                                      .getImage(StartMenu.class.getResource(backgroundImagePath));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
