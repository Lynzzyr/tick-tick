package game.screen;

import game.Appearance;
import game.Constants.kApp;
import game.Constants.kUI;
import game.State;
import game.element.Clock;
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

import java.time.LocalTime;

/** The level selection screen. */
public class Selection extends ScreenBase {
    // elements
    private final Clock clock;

    // animations
    private final FadeTransition fadeIn;
    private final FadeTransition fadeOut;

    public Selection() {
        // background
        ImageView bg = new ImageView(new Image(
            getClass().getResource(State.getEffectiveAppearance() == Appearance.LIGHT // check appearance
                ? "/sprites/bg_home_light.png"
                : "/sprites/bg_home_dark.png"
            ).toString()
        ));

        // fade animations
        fadeIn = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeIn.setToValue(1.0);

        fadeOut = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeOut.setToValue(0.0);

        // buttons
        GlassButton backButton = new GlassButton(
            kUI.WIDTH_BUTTON_ICON, kUI.HEIGHT_BUTTON_ICON,
            "\uDBC0\uDD89", TypeHandler.getFFIcons(), kUI.TEXTSIZE_BUTTON_ICONS,
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> ScreenHandler.goTo(ScreenType.HOME, fadeOut)
        );
        backButton.setLayoutX(kUI.POS_SELECTION_BUTTON_BACK[0]);
        backButton.setLayoutY(kUI.POS_SELECTION_BUTTON_BACK[1]);

        GlassButton level01button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            "1", TypeHandler.getFFRegular(), kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            () -> ScreenHandler.goTo(ScreenType.LEVEL01, fadeOut)
        );
        level01button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL01[0]);
        level01button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL01[1]);

        GlassButton level02button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 2
                ? "2"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 2
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 2
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL02, fadeOut)
                : () -> {}
        );
        level02button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL02[0]);
        level02button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL02[1]);

        GlassButton level03button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 3
                ? "3"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 3
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 3
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL03, fadeOut)
                : () -> {}
        );
        level03button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL03[0]);
        level03button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL03[1]);

        GlassButton level04button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 4
                ? "4"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 4
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 4
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL04, fadeOut)
                : () -> {}
        );
        level04button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL04[0]);
        level04button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL04[1]);

        GlassButton level05button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 5
                ? "5"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 5
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 5
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL05, fadeOut)
                : () -> {}
        );
        level05button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL05[0]);
        level05button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL05[1]);

        GlassButton level06button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 6
                ? "6"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 6
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 6
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL06, fadeOut)
                : () -> {}
        );
        level06button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL06[0]);
        level06button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL06[1]);

        GlassButton level07button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 7
                ? "7"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 7
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 7
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL02, fadeOut)
                : () -> {}
        );
        level07button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL07[0]);
        level07button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL07[1]);

        GlassButton level08button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 8
                ? "8"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 8
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 8
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL08, fadeOut)
                : () -> {}
        );
        level08button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL08[0]);
        level08button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL08[1]);

        GlassButton level09button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 9
                ? "9"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 9
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 9
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL09, fadeOut)
                : () -> {}
        );
        level09button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL09[0]);
        level09button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL09[1]);

        GlassButton level10button = new GlassButton(
            kUI.WIDTH_BUTTON_SELECTION_LEVEL, kUI.HEIGHT_BUTTON_SELECTION_LEVEL,
            State.getHighestLevelUnlocked() >= 10
                ? "10"
                : "\uDBC0\uDFA0", // lock
            State.getHighestLevelUnlocked() >= 10
                ? TypeHandler.getFFRegular()
                : TypeHandler.getFFIcons(),
            kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN_ALT, kUI.COLOR_LIGHT,
            State.getHighestLevelUnlocked() >= 10
                ? () -> ScreenHandler.goTo(ScreenType.LEVEL10, fadeOut)
                : () -> {}
        );
        level10button.setLayoutX(kUI.POS_SELECTION_BUTTON_LEVEL10[0]);
        level10button.setLayoutY(kUI.POS_SELECTION_BUTTON_LEVEL10[1]);

        // text
        Text selectText = new Text(kUI.POS_TEXT_SELECTION[0], kUI.POS_TEXT_SELECTION[1], "select level");
        selectText.setFill(Color.web(kUI.COLOR_LIGHT));
        selectText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_LEVEL));

        // clock
        clock = new Clock(kUI.RADIUS_HOME_CLOCK, State.getEffectiveAppearance(), LocalTime.now(), true);
        clock.setLayoutX(kUI.POS_CLOCK_HOME[0]);
        clock.setLayoutY(kUI.POS_CLOCK_HOME[1]);

        // add
        getChildren().addAll(
            bg,
            backButton,
            level01button, level02button, level03button, level04button, level05button,
            level06button, level07button, level08button, level09button, level10button,
            selectText, clock
        );
    }

    @Override
    public void enter() {
        setOpacity(0.0); // fade in raises to 1.0
        fadeIn.playFromStart();
    }

    @Override
    public void exit() {
        clock.stopUpdater();
    }
}
