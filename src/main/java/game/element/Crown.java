package game.element;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** A turning dial placed next to clocks that change clock time. */
public class Crown extends ImageView {
    // values
    private double yLast = -1;
    private double dy = 0;

    public Crown() {
        // image
        setImage(new Image(
            getClass().getResource("/sprites/crown.png").toString()
        ));

        // events
        setOnMousePressed(event -> { // log mouse pos
            yLast = event.getY();
        });

        setOnMouseDragged(event -> { // move
            if (yLast == -1) yLast = event.getY();
            dy = event.getY() - yLast;
            yLast = event.getY();
        });

        setOnMouseReleased(event -> { // stop inertia
            dy = 0;
        });
    }

    /**
     * Get the current delta-y.
     * @return Current dy
     */
    public double getDy() {
        return dy;
    }
}
