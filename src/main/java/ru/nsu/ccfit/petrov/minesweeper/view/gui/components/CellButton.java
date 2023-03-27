package ru.nsu.ccfit.petrov.minesweeper.view.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import lombok.Getter;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.GameSpace;

/**
 * The type {@code CellButton} is class for handling actions on cells.
 *
 * @author ptrvsrg
 */
@Getter
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
    private final int fieldX;
    private final int fieldY;

    /**
     * Instantiates a new Cell button with coordinates (fieldX, fieldY).
     *
     * @param fieldY the field y
     * @param fieldX the field x
     */
    public CellButton(int fieldY, int fieldX) {
        super();
        this.fieldY = fieldY;
        this.fieldX = fieldX;

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
                GameSpace.flagIcon.getScaledInstance(3 * size.width / 4, 3 * size.height / 4,
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
                GameSpace.mineIcon.getScaledInstance(3 * size.width / 4, 3 * size.height / 4,
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
