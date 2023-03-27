package ru.nsu.ccfit.petrov.minesweeper.view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import ru.nsu.ccfit.petrov.minesweeper.model.Model;
import ru.nsu.ccfit.petrov.minesweeper.model.Stopwatch;
import ru.nsu.ccfit.petrov.minesweeper.view.PlayerStatus;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.components.CellButton;

public class GameSpace
    extends JFrame
    implements PropertyChangeListener {

    private static final String TITLE = "Minesweeper";
    private static final String MINE_ICON_PATH = "/mine.png";
    private static final String FLAG_ICON_PATH = "/flag.png";
    private static final String STOPWATCH_ICON_PATH = "/stopwatch.png";
    public static final BufferedImage mineIcon;
    public static final BufferedImage flagIcon;
    public static final BufferedImage stopwatchIcon;
    private final Model model;
    private final Stopwatch stopwatch;
    private final CellButton[][] cells;
    private final JPanel statusPanel;
    private final JLabel markedCellCounter;
    private final JLabel stopwatchCounter;

    static {
        try {
            mineIcon = ImageIO.read(
                Objects.requireNonNull(GameSpace.class.getResourceAsStream(MINE_ICON_PATH)));
            flagIcon = ImageIO.read(
                Objects.requireNonNull(GameSpace.class.getResourceAsStream(FLAG_ICON_PATH)));
            stopwatchIcon = ImageIO.read(
                Objects.requireNonNull(GameSpace.class.getResourceAsStream(STOPWATCH_ICON_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GameSpace(Model model) {
        this.model = model;
        stopwatch = new Stopwatch();
        cells = new CellButton[model.getHeight()][model.getWidth()];

        // Observe for model and stopwatch
        model.addPropertyChangeListener(this);
        stopwatch.addPropertyChangeListener(this);

        // Create marked cell counter
        markedCellCounter = new JLabel();
        initCounter(markedCellCounter, flagIcon);

        // Create stopwatch
        stopwatchCounter = new JLabel();
        initCounter(stopwatchCounter, stopwatchIcon);

        // Create status panel
        statusPanel = new JPanel();
        initStatusPanel();

        // Create field panel
        JPanel fieldPanel = new JPanel(new GridLayout(model.getHeight(), model.getWidth()));
        initCells(fieldPanel);

        // Set up game space
        setTitle(TITLE);
        setSize(calculateSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Utils.exitConfirm(GameSpace.this);
            }
        });
        add(statusPanel, BorderLayout.NORTH);
        add(fieldPanel);
        setVisible(true);

        // Run stopwatch
        stopwatch.run();
    }

    private void initCounter(JLabel counter, BufferedImage mineIcon) {
        counter.setIcon(
            new ImageIcon(mineIcon.getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
        counter.setText(model.getMarkedCellCount() + " / " + model.getMineCount());
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
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartMenu();
            }
        });

        return newGameButton;
    }

    private void initCells(JPanel fieldPanel) {
        for (int i = 0; i < cells.length; ++i) {
            for (int j = 0; j < cells[0].length; ++j) {
                cells[i][j] = new CellButton(i, j);
                cells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            CellButton cellButton = (CellButton) e.getComponent();
                            model.openCell(cellButton.getFieldY(), cellButton.getFieldX());
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            CellButton cellButton = (CellButton) e.getComponent();
                            model.markCell(cellButton.getFieldY(), cellButton.getFieldX());
                        }
                    }
                });
                fieldPanel.add(cells[i][j]);
            }
        }
    }

    private Dimension calculateSize() {
        int fieldPanelHeight =
            (model.getWidth() < model.getHeight()) ? 800 / model.getWidth() * model.getHeight()
                                                   : 800;
        int fieldPanelWidth =
            (model.getWidth() > model.getHeight()) ? 800 / model.getHeight() * model.getWidth()
                                                   : 800;

        return new Dimension(fieldPanelWidth, fieldPanelHeight + statusPanel.getHeight());
    }

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
            case Model.MARKED_CELL_COUNT_PROPERTY:
                markedCellCounter.setText(
                    model.getMarkedCellCount() + " / " + model.getMineCount());
                break;
            case Model.IS_WINNER_PROPERTY:
                stopwatch.stop();
                new FinishMenu(this,
                               Boolean.TRUE.equals(evt.getNewValue()) ? PlayerStatus.WINNER : PlayerStatus.LOSER,
                               stopwatch.getSecond());
                break;
            case Stopwatch.STOPWATCH_PROPERTY:
                stopwatchCounter.setText(Stopwatch.timeToString((Integer)evt.getNewValue()));
                break;
            default:
                throw new IllegalArgumentException("Unexpected property change");
        }
    }
}
