package game.live;

import javafx.animation.AnimationTimer;

import java.util.function.Consumer;

/** Convenience class extending AnimationTimer with time delta constructor. */
public class StopwatchUpdater extends AnimationTimer {
    // last measurement
    private long last = 0;

    // updater
    private final Consumer<Double> callback;

    /** Configure updater.
     * @param callback Consumer to call with new delta time each frame
     */
    public StopwatchUpdater(Consumer<Double> callback) {
        this.callback = callback;
    }

    @Override
    public void handle(long now) {
        if (last == 0) last = now; // initial measurement

        // update
        callback.accept((now - last) / 1_000_000_000.0);

        last = now;
    }
}
