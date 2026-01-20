package game.liquidglassui;

import game.Constants.kLiquidGlass;
import javafx.animation.FillTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.InputStream;

/** A Liquid Glass button. */
public class GlassButton extends Glass {
    // animations
    private final SequentialTransition bounceOut; // involves expand -> settle
    private final SequentialTransition cancel; // involves shrink -> counteract -> normalize

    private final FillTransition haloFadeIn;
    private final FillTransition haloFadeOut;

    /**
     * Create a Liquid Glass button.
     * @param width The width
     * @param height The height, which also determines pill corner diameter
     * @param text Text displayed on button
     * @param font Font InputStream
     * @param fontSize Size of font in pts
     * @param baseColor Color of button base
     * @param textColor Color of text
     * @param callback The action of the button upon click
     */
    public GlassButton(
        double width, double height,
        String text, InputStream font, int fontSize,
        String baseColor, String textColor,
        Runnable callback
    ) {
        super(width, height, baseColor + "80", false, true, true, true); // 50% opacity base col

        // text
        Label label = new Label(text);
        label.setPrefSize(width, height);
        label.setFont(Font.loadFont(font, fontSize));
        label.setTextFill(Color.web(textColor));
        label.setAlignment(Pos.CENTER);

        getChildren().add(label);

        // animations
        ScaleTransition expand = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_BOUNCEOUT_EXPAND), this);
        expand.setToX(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);
        expand.setToY(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);
        expand.setToZ(kLiquidGlass.SCALE_BOUNCEOUT_EXPAND);

        ScaleTransition settle = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_BOUNCEOUT_SETTLE), this);
        settle.setToX(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);
        settle.setToY(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);
        settle.setToZ(kLiquidGlass.SCALE_BOUNCEOUT_SETTLE);

        bounceOut = new SequentialTransition(expand, settle); // configure bounce out sequence

        ScaleTransition shrink = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_CANCEL_SHRINK), this);
        shrink.setToX(kLiquidGlass.SCALE_CANCEL_SHRINK);
        shrink.setToY(kLiquidGlass.SCALE_CANCEL_SHRINK);
        shrink.setToZ(kLiquidGlass.SCALE_CANCEL_SHRINK);

        ScaleTransition counteract = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_CANCEL_COUNTERACT), this);
        counteract.setToX(kLiquidGlass.SCALE_CANCEL_COUNTERACT);
        counteract.setToY(kLiquidGlass.SCALE_CANCEL_COUNTERACT);
        counteract.setToZ(kLiquidGlass.SCALE_CANCEL_COUNTERACT);

        ScaleTransition normalize = new ScaleTransition(Duration.seconds(kLiquidGlass.DUR_CANCEL_NORMALIZE), this);
        normalize.setToX(1.0);
        normalize.setToY(1.0);
        normalize.setToZ(1.0);

        cancel = new SequentialTransition(shrink, counteract, normalize); // configure cancel sequence

        haloFadeIn = new FillTransition(Duration.seconds(kLiquidGlass.DUR_HALO_FADEIN), getHalo());
        haloFadeIn.setToValue(Color.web(kLiquidGlass.COLOR_HALO));

        haloFadeOut = new FillTransition(Duration.seconds(kLiquidGlass.DUR_HALO_FADEOUT), getHalo());
        haloFadeOut.setToValue(Color.TRANSPARENT);

        // configure mouse events
        setOnMousePressed(event -> { // press
            // animation
            if (getScaleX() > kLiquidGlass.SCALE_CANCEL_SHRINK) cancel.stop();
            bounceOut.playFromStart();

            // halo
            haloFadeOut.stop();
            haloFadeIn.playFromStart();

            setHaloCenter(
                width / 2 - (width / 2 - event.getX()) * kLiquidGlass.FACTOR_DRAG_HALO,
                height / 2 - (height / 2 - event.getY()) * kLiquidGlass.FACTOR_DRAG_HALO
            );
        });

        setOnMouseDragged(event -> { // pressed and moved
            // halo
            setHaloCenter(
                width / 2 - (width / 2 - event.getX()) * kLiquidGlass.FACTOR_DRAG_HALO,
                height / 2 - (height / 2 - event.getY()) * kLiquidGlass.FACTOR_DRAG_HALO
            );
        });

        setOnMouseReleased(event -> { // let go outside
            // halo
            haloFadeIn.stop();
            haloFadeOut.playFromStart();

            // animation
            if (getScaleX() < kLiquidGlass.SCALE_BOUNCEOUT_EXPAND) bounceOut.stop();
            cancel.playFromStart();
        });

        setOnMouseClicked(event -> { // let go inside therefore activated
            // callback
            callback.run();

            // halo
            haloFadeIn.stop();
            haloFadeOut.playFromStart();

            // animation
            if (getScaleX() < kLiquidGlass.SCALE_BOUNCEOUT_EXPAND) bounceOut.stop();
            cancel.playFromStart();
        });
    }
}
