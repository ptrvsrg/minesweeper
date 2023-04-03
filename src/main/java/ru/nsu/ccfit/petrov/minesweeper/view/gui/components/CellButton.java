package ru.nsu.ccfit.petrov.minesweeper.view.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import lombok.Getter;

/**
 * The type {@code CellButton} is class for handling actions on cells.
 *
 * @author ptrvsrg
 */
public class CellButton
    extends JButton {
    private static final Color[] NUMBER_COLORS = {Color.BLACK,
                                                  Color.RED,
                                                  Color.MAGENTA,
                                                  Color.ORANGE,
                                                  Color.GREEN,
                                                  Color.CYAN,
                                                  Color.YELLOW,
                                                  Color.PINK};
    private static final Color NOT_OPENED_CELL_COLOR = Color.BLUE;
    private static final Color OPENED_CELL_COLOR = Color.WHITE;
    @Getter
    private final int fieldX;
    @Getter
    private final int fieldY;
    private final BufferedImage mineIcon;
    private final BufferedImage flagIcon;

    /**
     * Instantiates a new Cell button with coordinates (fieldX, fieldY).
     *
     * @param fieldY   the field y
     * @param fieldX   the field x
     * @param mineIcon the mine icon
     * @param flagIcon the flag icon
     */
    public CellButton(int fieldY, int fieldX, BufferedImage mineIcon, BufferedImage flagIcon) {
        super();
        this.fieldY = fieldY;
        this.fieldX = fieldX;
        this.mineIcon = mineIcon;
        this.flagIcon = flagIcon;

        setFocusPainted(false);
        setBackground(NOT_OPENED_CELL_COLOR);
    }

    /**
     * Mark cell.
     */
    public void mark() {
        if (getIcon() == null) {
            Dimension size = getSize();
            setIcon(new ImageIcon(
                flagIcon.getScaledInstance(3 * size.width / 4, 3 * size.height / 4,
                                           Image.SCALE_DEFAULT)));
        } else {
            setIcon(null);
        }
    }

    /**
     * Open cell.
     *
     * @param mineAroundCount the mine around count
     */
    public void open(byte mineAroundCount) {
        setBackground(OPENED_CELL_COLOR);
        if (mineAroundCount == -1) {
            Dimension size = getSize();
            setIcon(new ImageIcon(
                mineIcon.getScaledInstance(3 * size.width / 4, 3 * size.height / 4,
                                           Image.SCALE_DEFAULT)));
        } else if (mineAroundCount > 0) {
            Dimension size = getSize();
            setIcon(null);
            setText(Integer.toString(mineAroundCount));
            setFont(new Font(Font.DIALOG, Font.BOLD, size.height / 2));
            setForeground(NUMBER_COLORS[mineAroundCount - 1]);
        }
    }
}
