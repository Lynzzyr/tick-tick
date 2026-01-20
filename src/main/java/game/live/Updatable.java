package game.live;

/** Convenience interface for all classes requiring live update functionality. */
public interface Updatable {

    /**
     * Method to call every time the superclass needs an update.
     * @param delta Time delta in seconds with nanosecond precision
     */
    void update(double delta);

    /** Stop this instance's updater. Removes references to updater and allows garbage collection. */
    void stopUpdater();

}
