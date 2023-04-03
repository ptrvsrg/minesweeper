package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.util.Scanner;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.controller.Level;

/**
 * The type {@code FinishMenu} is class that describe the start menu in text mode.
 *
 * @author ptrvsrg
 */
public class TextStartMenu {

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
    private final Controller controller;

    /**
     * Instantiates a new StartMenu.
     */
    public TextStartMenu(Controller controller) {
        this.controller = controller;

        System.out.println(HELLO_MESSAGE);
        processCommand();
    }

    private void processCommand() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(CHOOSE_LEVEL_MESSAGE);
                System.out.println(EXIT_MESSAGE);
                System.out.print("> ");
                String command = scanner.nextLine().trim();

                if (command.isEmpty()) {
                    continue;
                }

                switch (command) {
                    case "1":
                    case "beginner":
                        controller.setModel(Level.BEGINNER);
                        new TextGameSpace(controller, scanner);
                        break;
                    case "2":
                    case "intermediate":
                        controller.setModel(Level.INTERMEDIATE);
                        new TextGameSpace(controller, scanner);
                        break;
                    case "3":
                    case "expert":
                        controller.setModel(Level.EXPERT);
                        new TextGameSpace(controller, scanner);
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
