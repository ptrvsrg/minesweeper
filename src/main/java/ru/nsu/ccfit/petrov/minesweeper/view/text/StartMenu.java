package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.util.Scanner;
import ru.nsu.ccfit.petrov.minesweeper.model.Level;
import ru.nsu.ccfit.petrov.minesweeper.model.Model;

public class StartMenu {

    private static final String HELLO_MESSAGE = "\n"
        + "███╗░░░███╗██╗███╗░░██╗███████╗░██████╗░██╗░░░░░░░██╗███████╗███████╗██████╗░███████╗██████╗░\n"
        + "████╗░████║██║████╗░██║██╔════╝██╔════╝░██║░░██╗░░██║██╔════╝██╔════╝██╔══██╗██╔════╝██╔══██╗\n"
        + "██╔████╔██║██║██╔██╗██║█████╗░░╚█████╗░░╚██╗████╗██╔╝█████╗░░█████╗░░██████╔╝█████╗░░██████╔╝\n"
        + "██║╚██╔╝██║██║██║╚████║██╔══╝░░░╚═══██╗░░████╔═████║░██╔══╝░░██╔══╝░░██╔═══╝░██╔══╝░░██╔══██╗\n"
        + "██║░╚═╝░██║██║██║░╚███║███████╗██████╔╝░░╚██╔╝░╚██╔╝░███████╗███████╗██║░░░░░███████╗██║░░██║\n"
        + "╚═╝░░░░░╚═╝╚═╝╚═╝░░╚══╝╚══════╝╚═════╝░░░░╚═╝░░░╚═╝░░╚══════╝╚══════╝╚═╝░░░░░╚══════╝╚═╝░░╚═╝\n";
    private static final String CHOOSE_LEVEL_MESSAGE = "Choose level:"
        + "\n\t1 - beginner"
        + "\n\t2 - intermediate"
        + "\n\t3 - expert";
    private static final String EXIT_MESSAGE = "To exit the game enter \"exit\"";

    public StartMenu() {
        System.out.println(HELLO_MESSAGE);
        System.out.println(EXIT_MESSAGE);
        processCommand();
    }

    private void processCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(CHOOSE_LEVEL_MESSAGE);
                System.out.print("> ");
                String command = scanner.nextLine().trim();

                if (command.isEmpty()) {
                    continue;
                }

                switch (command) {
                    case "1":
                    case "beginner":
                        new GameSpace(scanner, new Model(Level.BEGINNER));
                        break;
                    case "2":
                    case "intermediate":
                        new GameSpace(scanner, new Model(Level.INTERMEDIATE));
                        break;
                    case "3":
                    case "expert":
                        new GameSpace(scanner, new Model(Level.EXPERT));
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Unknown command");
                        System.out.println(CHOOSE_LEVEL_MESSAGE);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
