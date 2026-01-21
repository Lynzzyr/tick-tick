package game.liquidglassui;

import game.Constants.kLiquidGlass;
import game.live.StopwatchUpdater;
import game.live.Updatable;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.function.BooleanSupplier;

/** A switch with a knob that sets a boolean. */
public class GlassSwitch extends Group implements Updatable {
    // values
    private final double snapPoint; // on true

    // animations
    private final FillTransition colorChangeTrue;
    private final FillTransition colorChangeFalse;

    // elements
    private final GlassKnob knob;

    private final StopwatchUpdater updater;

    /**
     * Create a Liquid Glass switch.
     * @param stateSupplier Supplier that supplies state upon creation
     * @param callbackFalse Callback to trigger on false
     * @param callbackTrue Callback to trigger on true
     */
    public GlassSwitch(BooleanSupplier stateSupplier, Runnable callbackFalse, Runnable callbackTrue) {
        // values
        snapPoint = kLiquidGlass.WIDTH_SWITCH - 2 * kLiquidGlass.MARGIN_KNOB - kLiquidGlass.WIDTH_SWITCH_KNOB;

        // base
        Rectangle container = new Rectangle(kLiquidGlass.WIDTH_SWITCH, kLiquidGlass.HEIGHT_SWITCH);
        container.setArcWidth(kLiquidGlass.HEIGHT_SWITCH);
        container.setArcHeight(kLiquidGlass.HEIGHT_SWITCH);

        // animations
        colorChangeTrue = new FillTransition(Duration.seconds(kLiquidGlass.DUR_SWITCH_COLOR_CHANGE), container);
        colorChangeTrue.setToValue(Color.web(kLiquidGlass.COLOR_SWITCH_CONTAINER_YES));

        colorChangeFalse = new FillTransition(Duration.seconds(kLiquidGlass.DUR_SWITCH_COLOR_CHANGE), container);
        colorChangeFalse.setToValue(Color.web(kLiquidGlass.COLOR_SWITCH_CONTAINER));

        TranslateTransition snap = new TranslateTransition(Duration.seconds(kLiquidGlass.DUR_KNOB_SNAP));
        snap.setInterpolator(Interpolator.EASE_OUT);

        // knob
        knob = new GlassKnob(
            kLiquidGlass.WIDTH_SWITCH_KNOB, kLiquidGlass.HEIGHT_SWITCH - 2 * kLiquidGlass.MARGIN_KNOB,
            kLiquidGlass.COLOR_SWITCH_KNOB,
            () -> {
                if (isTrueCloser()) {
                    snap.setToX(snapPoint);
                    snap.playFromStart();

                    callbackTrue.run();
                } else {
                    snap.setToX(0);
                    snap.playFromStart();

                    callbackFalse.run();
                }
            }
        );
        knob.setLayoutX(kLiquidGlass.MARGIN_KNOB);
        knob.setLayoutY(kLiquidGlass.MARGIN_KNOB);

        snap.setNode(knob); // give knob to translation transition

        // initial setup
        if (stateSupplier.getAsBoolean()) {
            container.setFill(Color.web(kLiquidGlass.COLOR_SWITCH_CONTAINER_YES));
            knob.setTranslateX(snapPoint);
        } else {
            container.setFill(Color.web(kLiquidGlass.COLOR_SWITCH_CONTAINER));
        }

        // add
        getChildren().addAll(container, knob);

        // updater
        updater = new StopwatchUpdater(this::update);
        updater.start();
    }

    private boolean isTrueCloser() {
        double currTx = knob.getTranslateX(); // get current translate x
        return snapPoint - currTx < currTx;
    }

    @Override
    public void update(double delta) {
        double currTx = knob.getTranslateX(); // get current translate x

        // clamp knob to snap points
        if (currTx < 0) knob.setTranslateX(0);
        if (currTx > snapPoint) knob.setTranslateX(snapPoint);

        // color change near snap points
        if (
            currTx <= kLiquidGlass.MARGIN_SWITCH_COLOR_CHANGE &&
            colorChangeFalse.getStatus() == Animation.Status.STOPPED
        ) {
            colorChangeTrue.stop();
            colorChangeFalse.playFromStart();
        } else if (
            currTx >= snapPoint - kLiquidGlass.MARGIN_SWITCH_COLOR_CHANGE &&
            colorChangeTrue.getStatus() == Animation.Status.STOPPED
        ) {
            colorChangeFalse.stop();
            colorChangeTrue.playFromStart();
        }
    }

    @Override
    public void stopUpdater() {
        updater.stop();
    }
}
