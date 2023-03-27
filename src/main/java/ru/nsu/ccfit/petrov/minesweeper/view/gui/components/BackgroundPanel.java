package ru.nsu.ccfit.petrov.minesweeper.view.gui.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import ru.nsu.ccfit.petrov.minesweeper.view.gui.StartMenu;

/**
 * The type {@code ContentPane} is class that describes the background of the frame.
 *
 * @author ptrvsrg
 */
public class BackgroundPanel
    extends JPanel {
    private final Image backgroundImage;

    /**
     * Instantiates a new Background panel.
     *
     * @param backgroundImagePath the background image path
     */
    public BackgroundPanel(String backgroundImagePath) {
        this.backgroundImage = Toolkit.getDefaultToolkit()
                                      .getImage(StartMenu.class.getResource(backgroundImagePath));
    }

    /**
     * Calls the UI delegate's paint method, if the UI delegate is non-<code>null</code>.  We pass
     * the delegate a copy of the
     * <code>Graphics</code> object to protect the rest of the
     * paint code from irrevocable changes (for example, <code>Graphics.translate</code>).
     * <p>
     * If you override this in a subclass you should not make permanent changes to the passed in
     * <code>Graphics</code>. For example, you should not alter the clip <code>Rectangle</code> or
     * modify the transform. If you need to do these operations you may find it easier to create a
     * new
     * <code>Graphics</code> from the passed in
     * <code>Graphics</code> and manipulate it. Further, if you do not
     * invoke super's implementation you must honor the opaque property, that is if this component
     * is opaque, you must completely fill in the background in an opaque color. If you do not honor
     * the opaque property you will likely see visual artifacts.
     * <p>
     * The passed in <code>Graphics</code> object might have a transform other than the identify
     * transform installed on it.  In this case, you might get unexpected results if you
     * cumulatively apply another transform.
     *
     * @param g the <code>Graphics</code> object to protect
     * @see #paint
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
