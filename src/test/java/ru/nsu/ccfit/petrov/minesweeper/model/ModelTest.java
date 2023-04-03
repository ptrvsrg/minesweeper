package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.MarkedCellContext;

public class ModelTest
    extends Assertions {

    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final int MINE_COUNT = 10;
    private final Model model = new Model(HEIGHT, WIDTH, MINE_COUNT);

    @Test(description = "Check marking cell")
    public void checkMarkCell() {
        final boolean[] updateCalls = {false, false};
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                if (context instanceof MarkedCellContext) {
                    updateCalls[0] = true;
                    assertThat(((MarkedCellContext) context).getX()).isZero();
                    assertThat(((MarkedCellContext) context).getY()).isZero();
                    assertThat(((MarkedCellContext) context).getMarkedCellCount()).isEqualTo(1);
                } else {
                    fail("Incorrect context");
                }
            }
        };

        model.addObserver(observer);
        model.markCell(0, 0);

        model.removeObserver(observer);
        model.markCell(0, 0);

        assertThat(updateCalls[0]).isTrue();
        assertThat(updateCalls[1]).isFalse();
    }
}