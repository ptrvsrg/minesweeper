package ru.nsu.ccfit.petrov.minesweeper;

import ru.nsu.ccfit.petrov.minesweeper.clparser.CLParser;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.GUIStartMenu;
import ru.nsu.ccfit.petrov.minesweeper.view.text.TextStartMenu;

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
            Controller controller = new Controller();
            switch (uiMode) {
                case "GUI":
                    new GUIStartMenu(controller);
                    break;
                case "text":
                    new TextStartMenu(controller);
                    break;
                default:
                    throw new IllegalArgumentException("No such UI mode found\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}