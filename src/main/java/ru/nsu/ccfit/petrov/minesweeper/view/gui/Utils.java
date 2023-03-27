package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The type {@code Utils} is util class for GUI mode.
 *
 * @author ptrvsrg
 */
public class Utils {
    private static final String EXIT_CONFIRM_TITLE = "Confirmation";
    private static final String EXIT_CONFIRM_MESSAGE = "Are you sure?";

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Draw the exit confirmation window.
     *
     * @param owner the owner
     */
    public static void exitConfirm(JFrame owner) {
        int res = JOptionPane.showConfirmDialog(owner, EXIT_CONFIRM_MESSAGE, EXIT_CONFIRM_TITLE,
                                                JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            owner.dispose();
        }
    }

    /**
     * Gets transparent color.
     *
     * @return the transparent color
     */
    public static Color getTransparentColor () {
        return new Color(0, 0, 0, 0);
    }
}
