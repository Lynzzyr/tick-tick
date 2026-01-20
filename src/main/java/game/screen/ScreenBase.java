package game.screen;

import game.Constants.kApp;
import javafx.scene.layout.Pane;

/**
 * Common abstract definition for all screens.
 * Note that screens are Nodes themselves.
 */
public abstract class ScreenBase extends Pane {
    public ScreenBase() {
        setPrefSize(kApp.SCENE_WIDTH, kApp.SCENE_HEIGHT);
    }

    /** Call once as the first block of code that runs after entering this screen. */
    public abstract void enter();

    /** Call once as the last block of code that runs before leaving this screen. */
    public abstract void exit();
}
