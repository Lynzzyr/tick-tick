package game.element;

import game.Appearance;
import game.State;
import game.live.StopwatchUpdater;
import game.Constants.kUI;
import game.Constants.kClock;
import game.live.Updatable;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.time.LocalTime;
import java.util.ArrayList;

/** A 12-hour clock. */
public class Clock extends Group implements Updatable {
    // values
    private LocalTime time; // seconds with nanosecond precision

    private boolean running; // whether actively ticking

    private Appearance appearance;
    private Color colMain;

    // objects
    private final ArrayList<Rectangle> bigticks = new ArrayList<>();
    private final ArrayList<Rectangle> smallticks = new ArrayList<>();

    private final Polygon hour;
    private final Polygon minute;

    private final Rotate rotHour;
    private final Rotate rotMinute;
    private final Rotate rotSecond;

    private final StopwatchUpdater updater;

    /** Construct a new clock.
     * @param radius Clock radius
     * @param appearance Color mode of clock
     * @param initialTime Initial time of clock
     * @param isRunning Whether the clock actively ticks
     */
    public Clock(double radius, Appearance appearance, LocalTime initialTime, boolean isRunning) {
        // values
        time = initialTime;

        running = isRunning;

        // set correct main color
        this.appearance = appearance;

        colMain = null;
        switch (this.appearance) {
            case LIGHT -> colMain = Color.web(kUI.COLOR_DARK);
            case DARK  -> colMain = Color.web(kUI.COLOR_LIGHT);
        }

        // create 12 hourly ticks
        for (int i = 0; i < 12; i++) {
            Rectangle tick = new Rectangle(
                radius * kClock.WIDTH_BIGTICK, radius * kClock.HEIGHT_BIGTICK,
                colMain
            );
            tick.setX(radius - (radius * kClock.WIDTH_BIGTICK / 2));
            tick.setY(radius * kClock.PADDING);
            tick.getTransforms().add(new Rotate(
                30 * i, // 30 degrees per big tick
                radius, radius
            ));
            bigticks.add(tick);
        }
        getChildren().addAll(bigticks);

        // create 48 minute small ticks
        for (int i = 0; i < 60; i++) {
            // skip if in place of big tick
            if (i % 5 == 0) continue;

            Rectangle tick = new Rectangle(
                radius * kClock.WIDTH_SMALLTICK, radius * kClock.HEIGHT_SMALLTICK,
                colMain
            );
            tick.setX(radius - (radius * kClock.WIDTH_SMALLTICK / 2));
            tick.setY(radius * kClock.PADDING);
            tick.getTransforms().add(new Rotate(
                6 * i, // 6 degrees per small tick
                radius, radius
            ));
            smallticks.add(tick);
        }
        getChildren().addAll(smallticks);

        // hour hand
        hour = new Polygon(
            0, 0,
            radius * kClock.WIDTH_HOUR_TIP, 0,
            radius * (kClock.WIDTH_HOUR_TIP + (kClock.WIDTH_HOUR_BASE - kClock.WIDTH_HOUR_TIP) / 2), radius * kClock.HEIGHT_HOUR,
            radius * -(kClock.WIDTH_HOUR_BASE - kClock.WIDTH_HOUR_TIP) / 2, radius * kClock.HEIGHT_HOUR
        );
        hour.setFill(colMain);
        hour.setLayoutX(radius - (radius * kClock.WIDTH_HOUR_TIP / 2));
        hour.setLayoutY(radius - (radius * kClock.HEIGHT_HOUR) + (radius * kClock.BASE_OFFSET_HOUR));
        rotHour = new Rotate(
            ((time.toSecondOfDay() / 3600.0) % 12) * 30,
            // different pivot calculation because position was set with layout relocation
            radius * kClock.WIDTH_HOUR_TIP / 2, radius * (kClock.HEIGHT_HOUR - kClock.BASE_OFFSET_HOUR)
        );
        hour.getTransforms().add(rotHour);

        // minute hand
        minute = new Polygon(
            0, 0,
            radius * kClock.WIDTH_MINUTE_TIP, 0,
            radius * (kClock.WIDTH_MINUTE_TIP + (kClock.WIDTH_MINUTE_BASE - kClock.WIDTH_MINUTE_TIP) / 2), radius * kClock.HEIGHT_MINUTE,
            radius * -(kClock.WIDTH_MINUTE_BASE - kClock.WIDTH_MINUTE_TIP) / 2, radius * kClock.HEIGHT_MINUTE
        );
        minute.setFill(colMain);
        minute.setLayoutX(radius - (radius * kClock.WIDTH_MINUTE_TIP / 2));
        minute.setLayoutY(radius - (radius * kClock.HEIGHT_MINUTE) + (radius * kClock.BASE_OFFSET_MINUTE));
        rotMinute = new Rotate(
            time.getMinute() * 6,
            radius * kClock.WIDTH_MINUTE_TIP / 2, radius * (kClock.HEIGHT_MINUTE - kClock.BASE_OFFSET_MINUTE)
        );
        minute.getTransforms().add(rotMinute);

        // second hand
        Rectangle secondBar = new Rectangle(
            radius * kClock.WIDTH_SECOND, radius * kClock.HEIGHT_SECOND,
            Color.web(kUI.COLOR_ACCENT)
        );
        secondBar.setX(radius * (kClock.RADIUS_SECOND_DOT - kClock.WIDTH_SECOND / 2));
        secondBar.setY(radius * kClock.RADIUS_SECOND_DOT);
        Circle secondDot = new Circle(
            radius * kClock.RADIUS_SECOND_DOT, radius * kClock.RADIUS_SECOND_DOT,
            radius * kClock.RADIUS_SECOND_DOT,
            Color.web(kUI.COLOR_ACCENT)
        );
        rotSecond = new Rotate(
            (time.getSecond() + time.getNano() / 1_000_000_000.0) * 6,
            radius * kClock.RADIUS_SECOND_DOT,
            radius * (kClock.RADIUS_SECOND_DOT + kClock.HEIGHT_SECOND - kClock.BASE_OFFSET_SECOND)
        );
        Group second = new Group(secondBar, secondDot);
        second.setLayoutX(radius - (radius * kClock.RADIUS_SECOND_DOT));
        second.setLayoutY(radius - (radius * (kClock.HEIGHT_SECOND + kClock.RADIUS_SECOND_DOT)) + (radius * kClock.BASE_OFFSET_SECOND));
        second.getTransforms().add(rotSecond);

        // add
        getChildren().addAll(hour, minute, second);

        // configure stopwatch
        updater = new StopwatchUpdater(this::update);
        updater.start();
    }

    /**
     * Add seconds to clock's internal time.
     * @param seconds Seconds to add with nanosecond precision
     */
    public void addSeconds(double seconds) {
        time = time.plusNanos((long) Math.floor(seconds * 1_000_000_000.0));
    }

    /**
     * Subtract seconds from clock's internal time.
     * @param seconds Seconds to subtract with nanosecond precision
     */
    public void subtractSeconds(double seconds) {
        time = time.minusNanos((long) Math.floor(seconds * 1_000_000_000.0));
    }

    /**
     * Get this clock's internal time.
     * @return Time of clock
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Set new appearance for clock.
     * @param newAppearance New appearance
     */
    public void setAppearance(Appearance newAppearance) {
        // change main color
        switch (newAppearance) {
            case LIGHT -> colMain = Color.web(kUI.COLOR_DARK);
            case DARK  -> colMain = Color.web(kUI.COLOR_LIGHT);
        }

        // change fills
        for (Rectangle tick : bigticks) tick.setFill(colMain);
        for (Rectangle tick : smallticks) tick.setFill(colMain);

        hour.setFill(colMain);
        minute.setFill(colMain);
    }

    @Override
    public void update(double delta) {
        if (running) { // increment time if running
            LocalTime newTime = time.plusNanos((long) (delta * 1_000_000_000.0));
            if (newTime.getHour() >= 12) newTime = newTime.minusHours(12); // 12-hour limit lock
            time = newTime;
        }

        // update angles
        rotHour.setAngle(((time.toSecondOfDay() / 3600.0) % 12) * 30);
        rotMinute.setAngle(time.getMinute() * 6);
        rotSecond.setAngle((time.getSecond() + time.getNano() / 1_000_000_000.0) * 6);
    }

    @Override
    public void stopUpdater() {
        updater.stop();
    }
}
