package game.handlers;

import game.screen.*;
import game.screen.levels.*;
import game.Constants.kApp;
import game.screen.ScreenBase;
import game.screen.ScreenType;
import javafx.animation.Animation;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Global handler managing Screens.
 * Encapsulates loading and unloading of individual screens with static methods.
 */
public class ScreenHandler {
    // this root is only a container containing a single Pane that is the current screen
    private static final Pane root = new Pane();
    static {
        root.setPrefSize(kApp.SCENE_WIDTH, kApp.SCENE_HEIGHT);
        root.setBackground(Background.fill(Color.BLACK));
    }

    // METHODS

    /**
     * Get next screen based on ScreenType.
     * @param nextScreenType Specified ScreenType
     * @return Next ScreenBase
     */
    private static ScreenBase getNextScreen(ScreenType nextScreenType) {
        return switch (nextScreenType) { // determine next screen from type enum
            case SPLASH    -> new Splash();
            case HOME      -> new Home();
            case SELECTION -> new Selection();
            case LEVEL01   -> new Level01();
            case LEVEL02   -> new Level02();
            case LEVEL03   -> new Level03();
            case END       -> new End();
            default        -> null;
        };
    }

    /**
     * Get the current managed root.
     * @return The current managed root
     */
    public static Pane getRoot() {
        return root;
    }

    /**
     * Offload current screen and load new screen.
     * Upon leaving the current screen, the exit() method is called.
     * @param nextScreenType The screen type to switch to
     */
    public static void goTo(ScreenType nextScreenType) {
        ScreenBase nextScreen = getNextScreen(nextScreenType);

        // only do something when next screen is a valid type
        if (nextScreen != null) {
            // attempt call exiter of current screen
            try {
                if (root.getChildren().getFirst() instanceof ScreenBase currentScreen) { // always true; for casting only
                    currentScreen.exit();
                }
            } catch (Exception ignored) {} // ignoring IndexOutOfBoundsException if children is in initial empty state

            // switch
            root.getChildren().clear();
            root.getChildren().add(nextScreen);

            // call enterer of new screen
            if (root.getChildren().getFirst() instanceof ScreenBase currentScreen) { // always true; for casting only
                nextScreen.enter();
            }
        }
    }

    /**
     * Offload current screen and load new screen.
     * Upon leaving the current screen, the exit animation is played then the exit() method is called.
     * @param nextScreenType The screen type to switch to
     * @param exitAnimation An animation to play and wait for before calling exit()
     */
    public static void goTo(ScreenType nextScreenType, Animation exitAnimation) {
        ScreenBase nextScreen = getNextScreen(nextScreenType);

        // only do something when next screen is a valid type
        if (nextScreen != null) {
            // configure switch
            exitAnimation.setOnFinished(event -> { // change screens only after end animation finishes
                // attempt call exiter of current screen
                try {
                    if (root.getChildren().getFirst() instanceof ScreenBase currentScreen) { // always true; for casting only
                        currentScreen.exit();
                    }
                } catch (Exception ignored) {} // ignoring IndexOutOfBoundsException if children is in initial empty state

                // switch
                root.getChildren().clear();
                root.getChildren().add(nextScreen);

                // call enterer of new screen
                if (root.getChildren().getFirst() instanceof ScreenBase currentScreen) { // always true; for casting only
                    nextScreen.enter();
                }
            });

            // execute full exit sequence
            exitAnimation.playFromStart();
        }
    }
}