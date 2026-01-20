package game.liquidglassui;

import game.Constants.kLiquidGlass;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

/** A horizontally movable and observable Liquid Glass element. */
public class GlassKnob extends Glass {
    // values
    private double xLast = -1;
    private double dx = 0;

    // animations
    private final ScaleTransition bounceOut;
    private final ScaleTransition cancel;

    private final FadeTransition fadeOut;
    private final FadeTransition fadeIn;

    /**
     * Create a Liquid Glass knob.
     * @param width Width of knob
     * @param height Height of knob
     * @param color Base color of knob
     */
    public GlassKnob(double width, double height, String color) {
        super(width, height, color, true, true, true, false);

        // animations
        bounceOut = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_SOLID_FADE), this);
        bounceOut.setToX(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);
        bounceOut.setToY(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);
        bounceOut.setToZ(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);

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
            // delta calculation
            if (xLast == -1) xLast = event.getSceneX();
            dx = event.getSceneX() - xLast;
            xLast = event.getSceneX();

            // animations
            cancel.stop();
            bounceOut.playFromStart();

            fadeIn.stop();
            fadeOut.playFromStart();
        });

        setOnMouseReleased(event -> { // let go
            // hard reset delta
            dx = 0;

            // animations
            bounceOut.stop();
            cancel.playFromStart();

            fadeOut.stop();
            fadeIn.playFromStart();
        });
    }

    /**
     * Get the current delta-x.
     * @return Current dx
     */
    public double getDx() {
        return dx;
    }
}
