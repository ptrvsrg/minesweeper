package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import ru.nsu.ccfit.petrov.minesweeper.controller.Controller;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observable;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.OpenedCellContext;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.CellButton;

/**
 * The type {@code GameSpace} is class that describe the game space in GUI mode.
 *
 * @author ptrvsrg
 */
public class GUIGameSpace
    extends JFrame
    implements Observer {

    private static final String TITLE = "Minesweeper";
    private static final String MINE_ICON_PATH = "/mine.png";
    private static final String FLAG_ICON_PATH = "/flag.png";
    private static final String STOPWATCH_ICON_PATH = "/stopwatch.png";
    private static final BufferedImage flagIcon;
    private static final BufferedImage stopwatchIcon;
    private final Controller controller;
    private final CellButton[][] cells;
    private final JPanel statusPanel;
    private final JLabel markedCellCounter;
    private final JLabel stopwatchCounter;

    static {
        try {
            flagIcon = ImageIO.read(
                Objects.requireNonNull(GUIGameSpace.class.getResourceAsStream(FLAG_ICON_PATH)));
            stopwatchIcon = ImageIO.read(Objects.requireNonNull(
                GUIGameSpace.class.getResourceAsStream(STOPWATCH_ICON_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instantiates a new GameSpace.
     *
     * @param controller the controller
     */
    public GUIGameSpace(Controller controller) {
        this.controller = controller;
        cells = new CellButton[controller.getHeight()][controller.getWidth()];

        controller.addModelObserver(this);
        controller.addStopwatchObserver(this);

        // Create marked cell counter
        markedCellCounter = new JLabel();
        initCounter(markedCellCounter, flagIcon);

        // Create stopwatch counter
        stopwatchCounter = new JLabel();
        initCounter(stopwatchCounter, stopwatchIcon);

        // Create status panel
        statusPanel = new JPanel();
        initStatusPanel();

        // Create field panel
        JPanel fieldPanel = new JPanel(
            new GridLayout(controller.getHeight(), controller.getWidth()));
        initCells(fieldPanel);

        // Set up game space
        setTitle(TITLE);
        setSize(calculateSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowClosingListener());
        add(statusPanel, BorderLayout.NORTH);
        add(fieldPanel);
        setVisible(true);
        controller.runStopwatch();
    }

    private void initCounter(JLabel counter, BufferedImage mineIcon) {
        counter.setIcon(new ImageIcon(mineIcon.getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        counter.setText("0 / " + controller.getMineCount());
        counter.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        counter.setVerticalAlignment(JLabel.CENTER);
        counter.setHorizontalAlignment(JLabel.CENTER);
    }

    private void initStatusPanel() {
        statusPanel.setLayout(new GridLayout(1, 3));
        statusPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        statusPanel.add(markedCellCounter);
        statusPanel.add(createNewGameButton());
        statusPanel.add(stopwatchCounter);
    }

    private JButton createNewGameButton() {
        JButton newGameButton = new JButton();
        newGameButton.setText("New Game");
        newGameButton.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        newGameButton.setFocusPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setBorderPainted(false);
        newGameButton.addActionListener(new NewGameButtonListener());

        return newGameButton;
    }

    private void initCells(JPanel fieldPanel) {
        BufferedImage mineIcon;
        try {
            mineIcon = ImageIO.read(
                Objects.requireNonNull(GUIGameSpace.class.getResourceAsStream(MINE_ICON_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                cells[i][j] = new CellButton(i, j, mineIcon, flagIcon);
                cells[i][j].addMouseListener(new CellButtonListener());
                fieldPanel.add(cells[i][j]);
            }
        }
    }

    private Dimension calculateSize() {
        int fieldPanelHeight =
            (controller.getWidth() < controller.getHeight()) ? 800 / controller.getWidth()
                * controller.getHeight() : 800;
        int fieldPanelWidth =
            (controller.getWidth() > controller.getHeight()) ? 800 / controller.getHeight()
                * controller.getWidth() : 800;

        return new Dimension(fieldPanelWidth, fieldPanelHeight + statusPanel.getHeight());
    }

    /**
     * Handles the context of the {@link Observable Observable} object message.
     *
     * @param context the context
     */
    @Override
    public void update(Context context) {
        if (context instanceof MarkedCellContext) {
            CellButton cellButton = cells[((MarkedCellContext) context).getY()][((MarkedCellContext) context).getX()];
            cellButton.mark(((MarkedCellContext) context).isMarked());

            markedCellCounter.setText(((MarkedCellContext) context).getMarkedCellCount() + " / "
                                          + controller.getMineCount());
        } else if (context instanceof OpenedCellContext) {
            CellButton cellButton = cells[((OpenedCellContext) context).getY()][((OpenedCellContext) context).getX()];
            cellButton.open(((OpenedCellContext) context).isMine(),
                            ((OpenedCellContext) context).getMineAroundCount());
        } else if (context instanceof StopwatchContext) {
            stopwatchCounter.setText(
                Stopwatch.timeToString(((StopwatchContext) context).getSecond()));
        } else if (context instanceof GameOverContext) {
            controller.stopStopwatch();
            controller.setIsWinner(((GameOverContext) context).isWinner());
            new GUIFinishMenu(this, controller);
        }
    }


    private class CellButtonListener
        extends MouseAdapter {

        /**
         * Invoked when a mouse button has been pressed on a component. In this case, pressing the
         * left button opens the cell, pressing the right button marks the cell.
         *
         * @param e the event to be processed
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                CellButton cellButton = (CellButton) e.getComponent();
                controller.openCell(cellButton.getFieldY(), cellButton.getFieldX());
            } else if (SwingUtilities.isRightMouseButton(e)) {
                CellButton cellButton = (CellButton) e.getComponent();
                controller.markCell(cellButton.getFieldY(), cellButton.getFieldX());
            }
        }
    }

    private class NewGameButtonListener
        implements ActionListener {
        
        /**
         * Invoked when an action occurs.
         * In this case, it closes all windows and creates a new start menu frame.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            controller.removeModel();
            controller.stopStopwatch();
            new GUIStartMenu(controller);
        }
    }
    
    private class WindowClosingListener
        extends WindowAdapter {
        
        /**
         * Invoked when a window is in the process of being closed. The
         * close operation can be overridden at this point.
         * In this case, it causes the exit confirmation window to be drawn.
         *
         * @param e the event to be processed
         */
        @Override
        public void windowClosing(WindowEvent e) {
            Utils.exitConfirm(GUIGameSpace.this);
        }
    }
}
