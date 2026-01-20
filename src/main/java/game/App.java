package game;

import game.handlers.AudioHandler;
import game.handlers.ScreenHandler;
import game.screen.ScreenType;
import game.Constants.kApp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Tick, Tick */
public final class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // preload all audio clips
        AudioHandler.preloadAudio();

        // set scene
        Scene scene = new Scene(ScreenHandler.getRoot());

        // first screen is home
        ScreenHandler.goTo(ScreenType.SPLASH);

        // stage things
        stage.setScene(scene);
        stage.setTitle(kApp.NAME);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        AudioHandler.stopMusic();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
