package game.liquidglassui;

import game.Constants.kLiquidGlass;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

/** Base class defining the core visual structure of a Liquid Glass element. */
public abstract class Glass extends Group {
    // the base layer glass
    private Rectangle glass = null;

    // the colored outline
    private Rectangle outline = null;

    // the main colored fill
    private Rectangle solid = null;

    // light glow that follows cursor
    private Circle halo = null;

    /**
     * Create Glass.
     * @param width The width
     * @param height The height, which also determines pill corner diameter
     * @param color Base color of element
     * @param makeGlass Whether to include glass base layer
     * @param makeOutline Whether to include colored outline
     * @param makeFill Whether to include the main colored fill
     * @param makeHalo Whether to include the light glow
     */
    public Glass(
        double width, double height, String color,
        boolean makeGlass, boolean makeOutline, boolean makeFill, boolean makeHalo
    ) {
        // make glass
        if (makeGlass) {
            glass = new Rectangle(width, height, Color.web(kLiquidGlass.COLOR_GLASS));
            glass.setArcWidth(height);
            glass.setArcHeight(height);
        }

        // make outline
        if (makeOutline) {
            outline = new Rectangle(width, height, null);
            outline.setArcWidth(height);
            outline.setArcHeight(height);
            outline.setStrokeType(StrokeType.INSIDE);
            outline.setStrokeWidth(kLiquidGlass.WIDTH_OUTLINE);
            outline.setStroke(Color.web(color).deriveColor(0, 1, 1, 1)); // make opaque regardless
        }

        // make fill
        if (makeFill) {
            solid = new Rectangle(width, height, Color.web(color));
            solid.setArcWidth(height);
            solid.setArcHeight(height);
        }

        // make halo
        if (makeHalo) {
            halo = new Circle(width * kLiquidGlass.FACTOR_RADIUS_HALO, Color.TRANSPARENT);
            halo.setEffect(new GaussianBlur(63));

            Rectangle mask = new Rectangle(width, height);
            mask.setArcWidth(height);
            mask.setArcHeight(height);
            halo.setClip(mask);
        }

        // add non null components
        ArrayList<Shape> components = new ArrayList<>();
        if (glass != null) components.add(glass);
        if (outline != null) components.add(outline);
        if (solid != null) components.add(solid);
        if (halo != null) components.add(halo);

        getChildren().addAll(components);
    }

    /**
     * Get solid component.
     * @return Solid component Rectangle
     */
    public Rectangle getSolid() {
        return solid;
    }

    /**
     * Get halo component.
     * @return Halo component Circle
     */
    public Circle getHalo() {
        return halo;
    }

    /**
     * Set the center of halo component.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setHaloCenter(double x, double y) {
        halo.setCenterX(x);
        halo.setCenterY(y);
    }
}
