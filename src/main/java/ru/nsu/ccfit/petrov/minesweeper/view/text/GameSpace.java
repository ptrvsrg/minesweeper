package ru.nsu.ccfit.petrov.minesweeper.view.text;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Scanner;
import ru.nsu.ccfit.petrov.minesweeper.model.Model;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.view.text.components.CellSymbol;
import ru.nsu.ccfit.petrov.minesweeper.view.PlayerStatus;

/**
 * The type {@code GameSpace} is class that describe the game space in text mode.
 *
 * @author ptrvsrg
 */
public class GameSpace
    implements PropertyChangeListener {

    private static final String AVAILABLE_GAME_COMMAND_MESSAGE =
        "Available game commands:"
            + "\n\to <x> <y> - open cell (x, y)"
            + "\n\tm <x> <y> - mark cell (x, y)"
            + "\n\texit - exit the game\n";
    private final Scanner scanner;
    private final Model model;
    private final Stopwatch stopwatch;
    private final CellSymbol[][] cells;
    private boolean isGameRuning = true;
    private PlayerStatus playerStatus;

    /**
     * Instantiates a new GameSpace.
     *
     * @param scanner the scanner
     * @param model   the model
     */
    public GameSpace(Scanner scanner, Model model) {
        this.scanner = scanner;
        this.model = model;
        this.stopwatch = new Stopwatch();

        cells = new CellSymbol[model.getHeight()][model.getWidth()];
        initCells();

        model.addPropertyChangeListener(this);

        System.out.println(AVAILABLE_GAME_COMMAND_MESSAGE);
        stopwatch.run();
        processCommand();
    }

    private void initCells() {
        for (int i = 0; i < model.getHeight(); ++i) {
            for (int j = 0; j < model.getWidth(); ++j) {
                cells[i][j] = new CellSymbol();
            }
        }
    }

    private void processCommand() {
        while (isGameRuning) {
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
        new FinishMenu(scanner, playerStatus, stopwatch.getSecond());
    }

    private void openCell(String[] commandArgs) {
        if (commandArgs.length != 3) {
            System.out.println("Incorrect command parameters");
            return;
        }

        try {
            int x = Integer.parseInt(commandArgs[1]);
            int y = Integer.parseInt(commandArgs[2]);
            model.openCell(y, x);
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
            model.markCell(y, x);
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
        for (int i = 0; i < model.getWidth(); ++i) {
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
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source and the property that has
     *            changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();

        switch (propertyName) {
            case Model.MARKED_CELL_VIEW_PROPERTY:
                Point point = (Point) evt.getNewValue();
                cells[point.y][point.x].mark();
                break;
            case Model.OPENED_CELL_VIEW_PROPERTY:
                point = (Point) evt.getNewValue();
                cells[point.y][point.x].open(model.getMineCountAround(point.y, point.x));
                break;
            case Model.IS_WINNER_PROPERTY:
                stopwatch.stop();
                isGameRuning = false;
                playerStatus = Boolean.TRUE.equals(evt.getNewValue()) ? PlayerStatus.WINNER : PlayerStatus.LOSER;
                break;
            default:
                throw new IllegalArgumentException("Unexpected property change");
        }
    }
}
