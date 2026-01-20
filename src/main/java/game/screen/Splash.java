package game.screen;

import game.Constants.kApp;
import game.Constants.kUI;
import game.handlers.AudioHandler;
import game.handlers.ScreenHandler;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/** The brief splash screen. */
public class Splash extends ScreenBase {
    public Splash() {
        setBackground(Background.fill(Color.BLACK));

        StackPane centered = new StackPane();
        centered.setPrefSize(kApp.SCENE_WIDTH, kApp.SCENE_HEIGHT);

        // splash image
        ImageView splash = new ImageView(new Image(
            getClass().getResource("/sprites/splash.png").toString()
        ));
        splash.setOpacity(0.0); // starts transparent

        centered.getChildren().add(splash);

        // animations
        FadeTransition stall = new FadeTransition(Duration.seconds(kUI.DUR_SPLASH_STALL));
        stall.setByValue(0.0);
        stall.setOnFinished(event -> { // play splash sound only once logo begins appearing
            AudioHandler.playSfxSplash();
        });

        FadeTransition fadein = new FadeTransition(Duration.seconds(kUI.DUR_SPLASH_FADE));
        fadein.setToValue(1.0);

        FadeTransition stay = new FadeTransition(Duration.seconds(kUI.DUR_SPLASH_STAY));
        stay.setByValue(1.0); // doesn't change anything

        FadeTransition fadeout = new FadeTransition(Duration.seconds(kUI.DUR_SPLASH_FADE));
        fadeout.setToValue(0.0);

        SequentialTransition fadeSeq = new SequentialTransition(splash, stall, fadein, stay, fadeout);
        fadeSeq.setOnFinished(event -> ScreenHandler.goTo(ScreenType.HOME));

        // add
        getChildren().add(centered);

        // play sequence
        fadeSeq.playFromStart();
    }

    @Override
    public void enter() {}

    @Override
    public void exit() {
        AudioHandler.playMusic();
    }
}
