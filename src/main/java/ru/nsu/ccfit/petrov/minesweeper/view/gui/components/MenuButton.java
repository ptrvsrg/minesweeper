package ru.nsu.ccfit.petrov.minesweeper.view.gui.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class MenuButton
    extends JButton {
    public MenuButton(String text) {
        super();
        setText(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        setForeground(Color.WHITE);
    }
}
