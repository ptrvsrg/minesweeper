package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.util.Map.Entry;
import java.util.Scanner;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;

/**
 * The type {@code FinishMenu} is class that describe the finish menu in text mode.
 *
 * @author ptrvsrg
 */
public class TextFinishMenu {
    private static final String WINNER_AVAILABLE_MENU_COMMAND_MESSAGE = "Available menu commands:"
            + "\n\tnew_game - start a new game"
            + "\n\thigh_scores - show high score table"
            + "\n\tsave_score - save current game score"
            + "\n\tabout - display information about the game"
            + "\n\texit - exit the game";
    private static final String LOSER_AVAILABLE_MENU_COMMAND_MESSAGE = "Available menu commands:"
            + "\n\tnew_game - start a new game"
            + "\n\thigh_scores - show high score table"
            + "\n\tabout - display information about the game"
            + "\n\texit - exit the game";
    private static final String ABOUT_MESSAGE = "Program \"Minesweeper v1.0\"\n"
            + "Designer: prtvsrg\n" + "Developer: ptrvsrg\n"
            + "Source code: https://github.com/ptrvsrg/NSU_OOP_Java/tree/master/Task3";
    private static final String LOSER_MESSAGE = "\n"
            + "██╗░░░██╗░█████╗░██╗░░░██╗  ░██╗░░░░░░█████╗░░██████╗███████╗\n"
            + "╚██╗░██╔╝██╔══██╗██║░░░██║  ░██║░░░░░██╔══██╗██╔════╝██╔════╝\n"
            + "░╚████╔╝░██║░░██║██║░░░██║  ░██║░░░░░██║░░██║╚█████╗░█████╗░░\n"
            + "░░╚██╔╝░░██║░░██║██║░░░██║  ░██║░░░░░██║░░██║░╚═══██╗██╔══╝░░\n"
            + "░░░██║░░░╚█████╔╝╚██████╔╝  ░███████╗╚█████╔╝██████╔╝███████╗\n"
            + "░░░╚═╝░░░░╚════╝░░╚═════╝░  ░╚══════╝░╚════╝░╚═════╝░╚══════╝\n";

    private static final String WINNER_MESSAGE = "\n"
            + "██╗░░░██╗░█████╗░██╗░░░██╗   ░██╗░░░░░░░██╗░█████╗░███╗░░██╗\n"
            + "╚██╗░██╔╝██╔══██╗██║░░░██║   ░██║░░██╗░░██║██╔══██╗████╗░██║\n"
            + "░╚████╔╝░██║░░██║██║░░░██║   ░╚██╗████╗██╔╝██║░░██║██╔██╗██║\n"
            + "░░╚██╔╝░░██║░░██║██║░░░██║   ░░████╔═████║░██║░░██║██║╚████║\n"
            + "░░░██║░░░╚█████╔╝╚██████╔╝   ░░╚██╔╝░╚██╔╝░╚█████╔╝██║░╚███║\n"
            + "░░░╚═╝░░░░╚════╝░░╚═════╝░   ░░░╚═╝░░░╚═╝░░░╚════╝░╚═╝░░╚══╝\n";
    private final Controller controller;
    private final Scanner scanner;

    /**
     * Instantiates a new FinishMenu.
     *
     * @param controller the controller
     * @param scanner    the scanner
     */
    public TextFinishMenu(Controller controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;

        printPlayerStatus();
        printAvailableCommands();
        processCommand();
    }

    private void printPlayerStatus() {
        System.out.println(controller.getIsWinner() ? WINNER_MESSAGE : LOSER_MESSAGE);
    }

    private void printAvailableCommands() {
        System.out.println(controller.getIsWinner() ? WINNER_AVAILABLE_MENU_COMMAND_MESSAGE
                                                    : LOSER_AVAILABLE_MENU_COMMAND_MESSAGE);
    }

    private void processCommand() {
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty()) {
                continue;
            }

            switch (command) {
                case "new_game":
                    return;
                case "high_scores":
                    showScores();
                    break;
                case "about":
                    showAbout();
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "save_score": {
                    if (controller.getIsWinner()) {
                        saveScore();
                        break;
                    }
                }
                default:
                    System.out.println("Unknown command");
                    printAvailableCommands();
            }
        }
    }

    private void showScores() {
        System.out.printf("| %20s | %20s |%n", "PLAYER NAME", "TIME");

        for (Entry<String, Integer> score : controller.getScoreRating()) {
            System.out.printf("| %20s | %20s |%n", score.getKey(),
                              Stopwatch.timeToString(score.getValue()));
        }
    }

    private void saveScore() {
        System.out.println("Enter player name:");
        System.out.print("> ");
        String playerName = scanner.nextLine().trim();
        controller.saveScore(playerName);
    }

    private void showAbout() {
        System.out.println(ABOUT_MESSAGE);
    }
}
