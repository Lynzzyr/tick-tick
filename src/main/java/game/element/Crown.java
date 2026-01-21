package game.element;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** A turning dial placed next to clocks that change clock time. */
public class Crown extends ImageView {
    // values
    private double lastSy; // last position
    private double dy; // delta y

    public Crown() {
        // image
        setImage(new Image(
            getClass().getResource("/sprites/crown.png").toString()
        ));

        // configure mouse events
        setOnMousePressed(event -> { // press
            // get last position
            lastSy = event.getSceneY();
        });

        setOnMouseDragged(event -> { // move while press
            // calculate delta y
            dy = event.getSceneY() - lastSy;
            lastSy = event.getSceneY();
        });

        setOnMouseReleased(event -> { // let go
            // reset momentum
            dy = 0;
        });
    }

    /**
     * Get the current delta y.
     * @return Current dy
     */
    public double getDy() {
        return dy;
    }
}
