package ru.nsu.ccfit.petrov.minesweeper.view.text.components;

import java.util.HashMap;
import lombok.Getter;

/**
 * The type {@code CellSymbol} is class that matches a field cell and a print character.
 *
 * @author ptrvsrg
 */
@Getter
public class CellSymbol {
    private static final HashMap<Byte, Character> CELL_SYMBOLS = new HashMap<>();
    private static final char FLAG_SYMBOL = 'F';
    private static final char MINE_SYMBOL = 'X';
    private static final char CLOSED_CELL_SYMBOL = '#';
    private char symbol;

    static {
        CELL_SYMBOLS.put((byte) 0, ' ');
        CELL_SYMBOLS.put((byte) 1, '1');
        CELL_SYMBOLS.put((byte) 2, '2');
        CELL_SYMBOLS.put((byte) 3, '3');
        CELL_SYMBOLS.put((byte) 4, '4');
        CELL_SYMBOLS.put((byte) 5, '5');
        CELL_SYMBOLS.put((byte) 6, '6');
        CELL_SYMBOLS.put((byte) 7, '7');
        CELL_SYMBOLS.put((byte) 8, '8');
    }

    /**
     * Instantiates a new CellSymbol.
     */
    public CellSymbol() {
        this.symbol = CLOSED_CELL_SYMBOL;
    }

    /**
     * Mark cell.
     */
    public void mark() {
        if (symbol == CLOSED_CELL_SYMBOL) {
            symbol = FLAG_SYMBOL;
        } else if (symbol == FLAG_SYMBOL) {
            symbol = CLOSED_CELL_SYMBOL;
        }
    }

    /**
     * Open cell.
     *
     * @param mineAroundCount the mine around count
     */
    public void open(byte mineAroundCount) {
        symbol = CELL_SYMBOLS.get(mineAroundCount);
    }
}
