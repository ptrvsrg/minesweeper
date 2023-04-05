package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.GameOverContext;

public class GameOverContextTest
    extends ContextTest {

    @Test(description = "Check sending game over context",
          groups = "Context tests")
    void checkGameOverContext() {
        // prepare
        boolean isWinner = true;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(GameOverContext.class);

                // do
                boolean actualIsWinner = ((GameOverContext) context).isWinner();
                assertThat(actualIsWinner).isEqualTo(isWinner);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new GameOverContext(isWinner));

        // restore
        observable.removeObserver(observer);
    }
}