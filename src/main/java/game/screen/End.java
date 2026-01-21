package game.screen;

import game.Constants.kApp;
import game.Constants.kUI;
import game.State;
import game.handlers.AudioHandler;
import game.handlers.ScreenHandler;
import game.handlers.TypeHandler;
import game.liquidglassui.GlassButton;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/** The end screen. */
public class End extends ScreenBase {
    // animations
    private final FadeTransition fadeIn;

    public End() {
        setOpacity(0.0); // fade in raises to 1.0

        // get appearance
        String bgFile = "";
        Color bodyTextFill = null;
        switch (State.getEffectiveAppearance()) {
            case LIGHT -> {
                bgFile = "/sprites/bg_game_light.png";
                bodyTextFill = Color.web(kUI.COLOR_DARK);
            }
            case DARK -> {
                bgFile = "/sprites/bg_game_dark.png";
                bodyTextFill = Color.web(kUI.COLOR_LIGHT);
            }
        }

        // background
        ImageView bg = new ImageView(new Image(
            getClass().getResource(bgFile).toString(),
            kApp.SCENE_WIDTH, kApp.SCENE_HEIGHT, true, false // scale to window
        ));

        // text
        Text heroText = new Text(kUI.POS_END_TEXT_HERO[0], kUI.POS_END_TEXT_HERO[1], "sweet!");
        heroText.setFill(Color.web(kUI.COLOR_ACCENT));
        heroText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_END_HERO));

        Text bodyText = new Text(kUI.POS_END_TEXT_BODY[0], kUI.POS_END_TEXT_BODY[1], "you completed all\nthe levels");
        bodyText.setFill(bodyTextFill);
        bodyText.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_END_BODY));

        // fade animations
        fadeIn = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeIn.setToValue(1.0);

        // buttons
        GlassButton returnButton = new GlassButton(
            kUI.WIDTH_BUTTON_LONG, kUI.HEIGHT_BUTTON_LONG,
            "return home", TypeHandler.getFFBold(), kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> ScreenHandler.goTo(ScreenType.HOME)
        );
        returnButton.setLayoutX(kUI.POS_END_BUTTON_RETURN[0]);
        returnButton.setLayoutY(kUI.POS_END_BUTTON_RETURN[1]);

        // add
        getChildren().addAll(bg, heroText, bodyText, returnButton);
    }

    @Override
    public void enter() {
        setOpacity(0.0); // fade in raises to 1.0
        fadeIn.playFromStart();

        State.setGameEnded(true);

        AudioHandler.stopMusic();
    }

    @Override
    public void exit() {
        AudioHandler.playMusic();
    }
}
