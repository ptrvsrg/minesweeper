package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.util.Scanner;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;
import ru.nsu.ccfit.petrov.minesweeper.view.text.components.CellSymbol;

/**
 * The type {@code GameSpace} is class that describe the game space in text mode.
 *
 * @author ptrvsrg
 */
public class TextGameSpace
    implements Observer {

    private static final String AVAILABLE_GAME_COMMAND_MESSAGE =
        "Available game commands:"
            + "\n\to <x> <y> - open cell (x, y)"
            + "\n\tm <x> <y> - mark cell (x, y)"
            + "\n\texit - exit the game\n";
    private final Controller controller;
    private final Scanner scanner;
    private final CellSymbol[][] cells;
    private boolean isGameRunning = true;

    /**
     * Instantiates a new GameSpace.
     *
     * @param controller the controller
     * @param scanner    the scanner
     */
    public TextGameSpace(Controller controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;

        controller.addModelObserver(this);
        controller.addStopwatchObserver(this);

        cells = new CellSymbol[controller.getHeight()][controller.getWidth()];
        initCells();

        model.addPropertyChangeListener(this);

        processCommand();
    }

    private void initCells() {
        for (int i = 0; i < controller.getHeight(); ++i) {
            for (int j = 0; j < controller.getWidth(); ++j) {
                cells[i][j] = new CellSymbol();
            }
        }
    }

    private void processCommand() {
        System.out.println(AVAILABLE_GAME_COMMAND_MESSAGE);
        controller.runStopwatch();

        while (isGameRunning) {
            printField();
            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.isEmpty()) {
                continue;
            }

            String[] commandArgs = command.split(" ");

            switch (commandArgs[0]) {
                case "o":
                    openCell(commandArgs);
                    break;
                case "m":
                    markCell(commandArgs);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unknown command");
                    System.out.println(AVAILABLE_GAME_COMMAND_MESSAGE);
            }
        }

        printField();
        new TextFinishMenu(controller, scanner);
    }

    private void openCell(String[] commandArgs) {
        if (commandArgs.length != 3) {
            System.out.println("Incorrect command parameters");
            return;
        }

        try {
            int x = Integer.parseInt(commandArgs[1]);
            int y = Integer.parseInt(commandArgs[2]);
            controller.openCell(y, x);
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect command parameters");
        }
    }

    private void markCell(String[] commandArgs) {
        if (commandArgs.length != 3) {
            System.out.println("Incorrect command parameters");
            return;
        }

        try {
            int x = Integer.parseInt(commandArgs[1]);
            int y = Integer.parseInt(commandArgs[2]);
            controller.markCell(y, x);
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect command parameters");
        }
    }

    private void printTimeCounter() {
        System.out.println("Time: " + Stopwatch.timeToString(stopwatch.getSecond()));
    }

    private void printMarkedCellCounter() {
        System.out.println("Marked mine: " + model.getMarkedCellCount() + "/" + model.getMineCount());
    }

    private void printTopCoordinateLine() {
        System.out.printf("%3s", "");
        for (int i = 0; i < controller.getWidth(); ++i) {
            System.out.printf("%3s", i);
        }

        System.out.println();
    }

    private void printField() {
        printTimeCounter();
        printMarkedCellCounter();
        printTopCoordinateLine();

        for (int i = 0; i < cells.length; ++i) {
            System.out.printf("%3d", i);
            for (int j = 0; j < cells[0].length; ++j) {
                System.out.printf("%3c", cells[i][j].getSymbol());
            }
            System.out.println();
        }
    }


    /**
     * Handles the context of the {@link Observable Observable} object message.
     *
     * @param context the context
     */
    @Override
    public void update(Context context) {
        if (context instanceof MarkedCellContext) {
            CellSymbol cellSymbol = cells[((MarkedCellContext) context).getY()][((MarkedCellContext) context).getX()];
            cellSymbol.mark(((MarkedCellContext) context).isMarked());

            markedCellCounter = ((MarkedCellContext) context).getMarkedCellCount() + " / "
                + controller.getMineCount();
        } else if (context instanceof OpenedCellContext) {
            CellSymbol cellSymbol = cells[((OpenedCellContext) context).getY()][((OpenedCellContext) context).getX()];
            cellSymbol.open(((OpenedCellContext) context).isMine(),
                            ((OpenedCellContext) context).getMineAroundCount());
        } else if (context instanceof StopwatchContext) {
            stopwatchCounter = Stopwatch.timeToString(((StopwatchContext) context).getSecond());
        } else if (context instanceof GameOverContext) {
            controller.stopStopwatch();
            controller.setIsWinner(((GameOverContext) context).isWinner());
            isGameRunning = false;
        }
    }
}
