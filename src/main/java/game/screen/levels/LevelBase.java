package game.screen.levels;

import game.Appearance;
import game.Constants;
import game.Constants.kUI;
import game.Constants.kGameplay;
import game.State;
import game.element.Clock;
import game.handlers.AudioHandler;
import game.handlers.ScreenHandler;
import game.handlers.TypeHandler;
import game.liquidglassui.GlassButton;
import game.live.StopwatchUpdater;
import game.live.Updatable;
import game.screen.ScreenBase;
import game.screen.ScreenType;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;

/** Abstract convenience class for all levels specifically. */
public abstract class LevelBase extends ScreenBase implements Updatable {
    // values
    private final int level;

    private final ScreenType nextScreen;

    private final LocalTime targetTime;

    // elements
    private final Clock clock;

    private final StopwatchUpdater updater;

    // animations
    private final FadeTransition fadeIn;
    private final FadeTransition fadeOut;
    private final SequentialTransition completeSeq;

    /**
     * Create a base for a level.
     * @param level Level number
     * @param nextScreen The screen to advance to upon level completion
     * @param initialTime Initial time of clock
     * @param targetTime Target time of clock
     * @param isClockRunning Whether the clock actively ticks
     * @param tooltip A tooltip to display, set "" if blank
     */
    public LevelBase(
        int level, ScreenType nextScreen,
        LocalTime initialTime, LocalTime targetTime, boolean isClockRunning,
        String tooltip
    ) {
        setOpacity(0.0); // fade in raises to 1.0

        // values
        this.level = level;

        this.nextScreen = nextScreen;

        this.targetTime = targetTime;

        // background
        ImageView bg = new ImageView(new Image(
            getClass().getResource(State.getEffectiveAppearance() == Appearance.LIGHT // check appearance
                ? "/sprites/bg_game_light.png"
                : "/sprites/bg_game_dark.png"
            ).toString(),
            Constants.kApp.SCENE_WIDTH, Constants.kApp.SCENE_HEIGHT, true, false // scale to window
        ));

        // animations
        fadeIn = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeIn.setToValue(1.0);

        fadeOut = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeOut.setToValue(0.0);

        PauseTransition completeStall = new PauseTransition(Duration.seconds(kUI.DUR_LEVELS_COMPLETE_PAUSE));

        FadeTransition completeFadeOut = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE));
        completeFadeOut.setToValue(0.0);

        completeSeq = new SequentialTransition(this, completeStall, completeFadeOut);

        // buttons
        GlassButton backButton = new GlassButton(
            kUI.WIDTH_BUTTON_ICON, kUI.HEIGHT_BUTTON_ICON,
            "\uDBC0\uDD89", TypeHandler.getFFIcons(), kUI.TEXTSIZE_BUTTON_ICONS,
            kUI.COLOR_BUTTON_SECONDARY, kUI.COLOR_LIGHT,
            () -> ScreenHandler.goTo(ScreenType.HOME, fadeOut)
        );
        backButton.setLayoutX(kUI.POS_LEVELS_BUTTON_BACK[0]);
        backButton.setLayoutY(kUI.POS_LEVELS_BUTTON_BACK[1]);

        // clock
        clock = new Clock(kUI.RADIUS_LEVELS_CLOCK, State.getEffectiveAppearance(), initialTime, isClockRunning);
        clock.setLayoutX(kUI.POS_CLOCK_LEVEL[0]);
        clock.setLayoutY(kUI.POS_CLOCK_LEVEL[1]);

        // text
        Text levelNumText = new Text(kUI.POS_TEXT_LEVEL_NUM[0], kUI.POS_TEXT_LEVEL_NUM[1], Integer.toString(this.level));
        levelNumText.setFill(Color.web(kUI.COLOR_LIGHT));
        levelNumText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_LEVEL));

        Text targetText = new Text(kUI.POS_TEXT_LEVEL_TARGET[0], kUI.POS_TEXT_LEVEL_TARGET[1], "set to\n" + this.targetTime.toString());
        targetText.setFill(Color.web(kUI.COLOR_LIGHT));
        targetText.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_LEVEL_BODY));

        // add all
        getChildren().addAll(bg, backButton, clock, levelNumText, targetText);

        if (!tooltip.isEmpty()) {
            Text tooltipText = new Text(kUI.POS_TEXT_LEVEL_TOOLTIP[0], kUI.POS_TEXT_LEVEL_TOOLTIP[1], tooltip);
            tooltipText.setFill(Color.web(kUI.COLOR_LIGHT));
            tooltipText.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_LEVEL_BODY));

            getChildren().add(tooltipText);
        }

        // updater
        updater = new StopwatchUpdater(this::update);
        updater.start();
    }

    /**
     * Get the level's clock.
     * @return The clock
     */
    public Clock getClock() {
        return clock;
    }

    /**
     * Called in level's internal updater after level complete check.
     * Define any custom updater functionality here.
     */
    public abstract void onUpdate();

    @Override
    public void update(double delta) {
        // check if complete
        LocalTime currTime = clock.getTime(); // get time of this frame

        if (
            (currTime.equals(targetTime) || currTime.isAfter(targetTime)) &&
            currTime.isBefore(targetTime.plusSeconds(kGameplay.TOLERANCE_TARGET)) // tolerance check
        ) {
            clock.stopUpdater();
            stopUpdater();

            AudioHandler.playSfxOk();
            State.setHighestLevelUnlocked(level + 1); // unlock next level

            ScreenHandler.goTo(nextScreen, completeSeq); // advance screen
        }

        // execute custom functionality
        onUpdate();
    }

    @Override
    public void stopUpdater() {
        updater.stop();
    }

    @Override
    public void enter() {
        fadeIn.playFromStart();

        requestFocus();
    }

    @Override
    public void exit() {
        clock.stopUpdater();
        stopUpdater();
    }
}
