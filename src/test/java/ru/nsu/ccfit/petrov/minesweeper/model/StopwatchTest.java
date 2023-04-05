package ru.nsu.ccfit.petrov.minesweeper.model;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.Observer;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.StopwatchContext;

public class StopwatchTest
    extends Assertions {

    private Stopwatch stopwatch;

    @BeforeClass
    public void beforeClass() {
        stopwatch = new Stopwatch();
    }

    @Test(description = "Check notification observers",
          groups = "Stopwatch tests")
    public void checkNotifyObservers()
        throws InterruptedException {
        // prepare
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                // check
                assertThat(context).isInstanceOf(StopwatchContext.class);

                int actualSecond = ((StopwatchContext) context).getSecond();
                assertThat(actualSecond).isZero();
            }
        };
        stopwatch.addObserver(observer);

        // do
        stopwatch.run();
        Thread.sleep(500);
        stopwatch.stop();

        // check
        int actualSecond = stopwatch.getSecond();
        assertThat(actualSecond).isZero();

        // restore
        stopwatch.removeObserver(observer);
    }

    @Test(description = "Check method \"timeToString\" when time is correct",
          groups = "Stopwatch tests")
    public void checkTimeToStringWhenTimeIsCorrect() {
        // prepare
        String expectedTimeString = "1:14:52";
        int second = 4492;

        // do
        String actualTimeString = Stopwatch.timeToString(second);

        // check
        assertThat(actualTimeString).isEqualTo(expectedTimeString);
    }

    @Test(description = "Check method \"timeToString\" when time is negative",
          groups = "Stopwatch tests")
    public void checkTimeToStringWhenTimeIsNegative() {
        assertThatThrownBy(new ThrowingCallable() {
            @Override
            public void call()
                throws Throwable {
                Stopwatch.timeToString(-1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
    }
}