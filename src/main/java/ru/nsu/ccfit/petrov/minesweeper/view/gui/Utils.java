package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }
    public static void exitConfirm(JFrame owner) {
        int res = JOptionPane.showConfirmDialog(owner, "Are you sure?", "Confirmation",
                                                JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            owner.dispose();
        }
    }

    public static Color getTransparentColor () {
        return new Color(0, 0, 0, 0);
    }
}
