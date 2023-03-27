package ru.nsu.ccfit.petrov.minesweeper;

import org.apache.commons.cli.ParseException;
import ru.nsu.ccfit.petrov.minesweeper.clparser.CLParser;

public class Main {
    public static void main(String[] args) {
        CLParser clParser = new CLParser();

        try {
            if (!clParser.parse(args))
                return;
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String uiMode = clParser.getUIMode();
        if (uiMode == null) {
            uiMode = "GUI";
        }

        try {
            switch (uiMode) {
                case "GUI":
                    new ru.nsu.ccfit.petrov.minesweeper.view.gui.StartMenu();
                    break;
                case "text":
                    new ru.nsu.ccfit.petrov.minesweeper.view.text.StartMenu();
                    break;
                default:
                    throw new IllegalArgumentException("No such UI mode found\n");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}