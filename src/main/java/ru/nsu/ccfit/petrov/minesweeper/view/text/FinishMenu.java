package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.util.Scanner;
import java.util.SortedSet;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.model.score.Score;
import ru.nsu.ccfit.petrov.minesweeper.model.score.ScoreRating;
import ru.nsu.ccfit.petrov.minesweeper.view.PlayerStatus;

public class FinishMenu {

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
    private final Scanner scanner;
    private final PlayerStatus playerStatus;
    private final int time;

    public FinishMenu(Scanner scanner, PlayerStatus playerStatus, int time) {
        this.scanner = scanner;
        this.playerStatus = playerStatus;
        this.time = time;

        printPlayerStatus();
        printAvailableCommands();
        processCommand();
    }

    private void printPlayerStatus() {
        System.out.println((playerStatus == PlayerStatus.WINNER) ? WINNER_MESSAGE : LOSER_MESSAGE);
    }

    private void printAvailableCommands() {
        System.out.println((playerStatus == PlayerStatus.WINNER) ? WINNER_AVAILABLE_MENU_COMMAND_MESSAGE
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
                case "save_score":
                {
                    if (playerStatus == PlayerStatus.WINNER) {
                        saveScore(scanner);
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
        SortedSet<Score> scores = ScoreRating.getScores();

        System.out.printf("| %20s | %20s |%n", "PLAYER NAME", "TIME");

        for (Score score : scores) {
            System.out.printf("| %20s | %20s |%n", score.getPlayerName(),
                              Stopwatch.timeToString(score.getTime()));
        }
    }

    private void saveScore(Scanner scanner) {
        System.out.println("Enter player name:");
        System.out.print("> ");
        String playerName = scanner.nextLine().trim();
        ScoreRating.saveScore(new Score(playerName, time));
    }

    private void showAbout() {
        System.out.println(ABOUT_MESSAGE);
    }
}
