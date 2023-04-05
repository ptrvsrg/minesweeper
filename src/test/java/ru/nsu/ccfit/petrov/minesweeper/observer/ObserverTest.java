package ru.nsu.ccfit.petrov.minesweeper.observer;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.ccfit.petrov.minesweeper.observer.context.Context;

public class ObserverTest
    extends Assertions {
    private Observable observable;

    @BeforeClass
    public void beforeClass() {
        observable = new Observable();
    }

    @Test(description = "Check notify with listener",
          groups = "Observer tests")
    void checkNotifyWithListener() {
        // prepare
        final int[] messageCount = {0};
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                ++messageCount[0];
            }
        };
        observable.addObserver(observer);

        // do
        observable.notifyObservers(new Context() {});

        // check
        assertThat(messageCount[0]).isEqualTo(1);
    }

    @Test(description = "Check notify without listener",
          dependsOnMethods = "checkNotifyWithListener",
          groups = "Observer tests")
    void checkNotifyWithoutListener() {
        // prepare
        final int[] messageCount = {0};
        Observer observer = new Observer() {
            @Override
            public void update(Context context) {
                ++messageCount[0];
            }
        };
        observable.removeObserver(observer);

        // do
        observable.notifyObservers(new Context() {});

        // check
        assertThat(messageCount[0]).isZero();
    }
}