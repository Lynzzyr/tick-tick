package game.liquidglassui;

import game.Constants.kLiquidGlass;
import game.Constants.kUI;
import game.handlers.TypeHandler;
import game.live.StopwatchUpdater;
import game.live.Updatable;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/** A bar with a knob that sets a value. */
public class GlassTab extends Group implements Updatable {
    // values
    private final int length; // number of options
    private final double labelWidth;
    private final ArrayList<Double> snapPoints = new ArrayList<>(); // relative to knob Tx

    // elements
    private final GlassKnob knob;

    private final StopwatchUpdater updater;

    /**
     * Create a Liquid Glass tab.
     * @param width Width of tab
     * @param height Height of tab
     * @param options List of option labels
     * @param callbacks List of callbacks to trigger, must be the same length and order as options
     */
    public GlassTab(double width, double height, List<String> options, List<Runnable> callbacks) {
        // values
        length = options.size();
        labelWidth = (width - 2 * kLiquidGlass.MARGIN_KNOB) / length;

        // base
        Rectangle container = new Rectangle(width, height, Color.web(kLiquidGlass.COLOR_TAB_CONTAINER));
        container.setArcWidth(height);
        container.setArcHeight(height);

        // snap
        TranslateTransition snap = new TranslateTransition(Duration.seconds(kLiquidGlass.DUR_KNOB_SNAP));
        snap.setInterpolator(Interpolator.EASE_OUT);
        snap.setToX(0.0); // initially

        // knob
        knob = new GlassKnob(
            (width - 2 * kLiquidGlass.MARGIN_KNOB) / length,
            height - 2 * kLiquidGlass.MARGIN_KNOB,
            kLiquidGlass.COLOR_TAB_KNOB,
            () -> {
                int nearestIndex = getNearestSnapPointIndex();

                snap.setToX(snapPoints.get(nearestIndex));
                snap.playFromStart();

                callbacks.get(nearestIndex).run();
            }
        );
        knob.setLayoutX(kLiquidGlass.MARGIN_KNOB);
        knob.setLayoutY(kLiquidGlass.MARGIN_KNOB);

        snap.setNode(knob);

        // options
        ArrayList<Label> labels = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Label l = new Label(options.get(i));
            l.setPrefSize(labelWidth, height);
            l.setLayoutX(labelWidth * i + kLiquidGlass.MARGIN_KNOB);
            l.setFont(Font.loadFont(TypeHandler.getFFBold(), kLiquidGlass.FACTOR_TEXTSIZE_TAB * height));
            l.setTextFill(Color.web(kUI.COLOR_LIGHT));
            l.setAlignment(Pos.CENTER);
            l.setMouseTransparent(true);

            labels.add(l);
            snapPoints.add(labelWidth * i);
        }

        // add
        getChildren().addAll(container, knob);
        getChildren().addAll(labels);

        // updater
        updater = new StopwatchUpdater(this::update);
        updater.start();
    }

    private int getNearestSnapPointIndex() {
        double lowestDistance = 100_000; // arbitrary just to get started
        int nearestIndex = 0; // arbitary

        // loop through all points
        for (int i = 0; i < length; i++) {
            double distanceTo = Math.abs(knob.getTranslateX() - snapPoints.get(i));
            if (distanceTo < lowestDistance) {
                lowestDistance = distanceTo;
                nearestIndex = i;
            }
        }

        return nearestIndex;
    }

    @Override
    public void update(double delta) {
        // clamp knob to sides
        if (knob.getTranslateX() < 0) knob.setTranslateX(0);
        if (knob.getTranslateX() > labelWidth * (length - 1)) knob.setTranslateX(labelWidth * (length - 1));
    }

    @Override
    public void stopUpdater() {
        updater.stop();
    }
}
