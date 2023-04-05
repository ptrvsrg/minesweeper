package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;

public class StopwatchContextTest
    extends ContextTest {

    @Test(description = "Check sending stopwatch context",
          groups = "Context tests")
    void checkStopwatchContext() {
        // prepare
        int second = 236;
        Observer observer = new Observer() {
            // check
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(StopwatchContext.class);

                // do
                int actualSecond = ((StopwatchContext) context).getSecond();

                assertThat(actualSecond).isEqualTo(second);
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new StopwatchContext(second));

        // restore
        observable.removeObserver(observer);
    }
}