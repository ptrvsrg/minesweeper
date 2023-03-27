package ru.nsu.ccfit.petrov.minesweeper;

import ru.nsu.ccfit.petrov.minesweeper.clparser.CLParser;

public class Main {
    public static void main(String[] args) {
        CLParser clParser = new CLParser();
        if (!clParser.parse(args)) {
            return;
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
            e.printStackTrace();
        }
    }
}