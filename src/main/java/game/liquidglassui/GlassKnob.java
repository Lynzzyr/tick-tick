package game.liquidglassui;

import game.Constants.kLiquidGlass;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

/** A horizontally movable and observable Liquid Glass element. */
public class GlassKnob extends Glass {
    // animations
    private final ScaleTransition bounceOut;
    private final ScaleTransition cancel;

    private final FadeTransition fadeOut;
    private final FadeTransition fadeIn;

    /**
     * Create a Liquid Glass knob.
     * @param width
     * @param height
     * @param color
     */
    public GlassKnob(double width, double height, String color) {
        super(width, height, color, true, true, true, false);

        // animations
        bounceOut = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_SOLID_FADE), this);
        bounceOut.setToX(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);
        bounceOut.setToY(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);
        bounceOut.setToZ(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);

        cancel = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_SOLID_FADE), this);
        cancel.setToX(1.0);
        cancel.setToY(1.0);
        cancel.setToZ(1.0);

        fadeOut = new FadeTransition(Duration.seconds(kLiquidGlass.DUR_SOLID_FADE), getSolid());
        fadeOut.setToValue(0.0);

        fadeIn = new FadeTransition(Duration.seconds(kLiquidGlass.DUR_SOLID_FADE), getSolid());
        fadeIn.setToValue(1.0);

        // custom interpolation
        Interpolator scaleInterpolator = new Interpolator() {
            @Override
            protected double curve(double t) {
                if (t < 0.5) return -(Math.pow(2, 0.5) / 2.0) * Math.pow(-t + 0.5, 0.5) + 0.5;
                else if (t > 0.5) return (Math.pow(2, 0.5) / 2.0) * Math.pow(t - 0.5, 0.5) + 0.5;
                else return 0.5;
            }
        };
        bounceOut.setInterpolator(scaleInterpolator);
        cancel.setInterpolator(scaleInterpolator);

        // configure mouse events
        setOnMousePressed(event -> { // press
            // animations
            cancel.stop();
            bounceOut.playFromStart();

            fadeIn.stop();
            fadeOut.playFromStart();
        });

        setOnMouseReleased(event -> { // let go
            // animations
            bounceOut.stop();
            cancel.playFromStart();

            fadeOut.stop();
            fadeIn.playFromStart();
        });
    }
}
