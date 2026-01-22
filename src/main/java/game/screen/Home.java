package game.screen;

import game.State;
import game.Appearance;
import game.Constants.kApp;
import game.Constants.kUI;
import game.element.Clock;
import game.handlers.AudioHandler;
import game.handlers.ScreenHandler;
import game.handlers.TypeHandler;
import game.liquidglassui.GlassButton;
import game.liquidglassui.GlassSwitch;
import game.liquidglassui.GlassTab;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.List;

/** The all-in-one home screen that includes main menu, settings, and credits. */
public class Home extends ScreenBase {
    // elements
    private final Pane elevator;

    private final GlassTab settingsAppearanceTab;
    private final GlassSwitch settingsMusicSwitch;
    private final GlassSwitch settingsSfxSwitch;

    private final Clock clock;

    // animations
    private final TranslateTransition toSettings;
    private final TranslateTransition toCredits;
    private final TranslateTransition returnHome;

    private final FadeTransition fadeIn;
    private final FadeTransition fadeOut;

    public Home() {
        // background
        Image bgImgLight = new Image(getClass().getResource("/sprites/bg/bg_home_light.png").toString());
        Image bgImgDark = new Image(getClass().getResource("/sprites/bg/bg_home_dark.png").toString());

        ImageView bg = new ImageView();
        switch (State.getEffectiveAppearance()) {
            case LIGHT -> bg.setImage(bgImgLight);
            case DARK  -> bg.setImage(bgImgDark);
        }

        // clock
        clock = new Clock(kUI.RADIUS_HOME_CLOCK, State.getEffectiveAppearance(), LocalTime.now(), true);
        clock.setLayoutX(kUI.POS_CLOCK_HOME[0]);
        clock.setLayoutY(kUI.POS_CLOCK_HOME[1]);

        // initial all-in-one setup
        elevator = new Pane();
        elevator.setPrefSize(kApp.SCENE_WIDTH, kApp.SCENE_HEIGHT * 3); // 3 scenes worth of menus in 1

        // elevator animations
        toSettings = new TranslateTransition(Duration.seconds(kUI.DUR_HOME_ELEVATOR_MOVE), elevator);
        toSettings.setToY(kApp.SCENE_HEIGHT);

        toCredits = new TranslateTransition(Duration.seconds(kUI.DUR_HOME_ELEVATOR_MOVE), elevator);
        toCredits.setToY(-kApp.SCENE_HEIGHT); // even further up

        returnHome = new TranslateTransition(Duration.seconds(kUI.DUR_HOME_ELEVATOR_MOVE), elevator);
        returnHome.setToY(0);

        // custom interpolation curve
        Interpolator elevatorInterpolator = new Interpolator() {
            @Override
            protected double curve(double t) {
                return -Math.pow(t - 1, 4) + 1; // f(t) = -(x - 1)^4 + 1
            }
        };
        toSettings.setInterpolator(elevatorInterpolator);
        toCredits.setInterpolator(elevatorInterpolator);
        returnHome.setInterpolator(elevatorInterpolator);

        // fade animation
        fadeIn = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeIn.setToValue(1.0);

        fadeOut = new FadeTransition(Duration.seconds(kUI.DUR_SCREEN_FADE), this);
        fadeOut.setToValue(0.0);

        // logo
        Text titleText = new Text(kUI.POS_HOME_TITLE[0], kUI.POS_HOME_TITLE[1], kApp.NAME.toLowerCase());
        titleText.setFill(Color.web(kUI.COLOR_LIGHT));
        titleText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_HOME_TITLE));

        // settings headers
        Text settingsAppearanceText = new Text(
            kUI.POS_TEXT_SETTINGS_APPEARANCE[0], kUI.POS_TEXT_SETTINGS_APPEARANCE[1] - kApp.SCENE_HEIGHT,
            "appearance"
        );
        settingsAppearanceText.setFill(Color.web(kUI.COLOR_LIGHT));
        settingsAppearanceText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_SETTINGS));

        Text settingsMusicText = new Text(
            kUI.POS_TEXT_SETTINGS_MUSIC[0], kUI.POS_TEXT_SETTINGS_MUSIC[1] - kApp.SCENE_HEIGHT,
            "music"
        );
        settingsMusicText.setFill(Color.web(kUI.COLOR_LIGHT));
        settingsMusicText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_SETTINGS));

        Text settingsSfxText = new Text(
            kUI.POS_TEXT_SETTINGS_SFX[0], kUI.POS_TEXT_SETTINGS_SFX[1] - kApp.SCENE_HEIGHT,
            "sfx"
        );
        settingsSfxText.setFill(Color.web(kUI.COLOR_LIGHT));
        settingsSfxText.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_SETTINGS));

        // settings Liquid Glass elements
        GlassButton settingsBackButton = new GlassButton( // back
            kUI.WIDTH_BUTTON_ICON, kUI.HEIGHT_BUTTON_ICON,
            "\uDBC0\uDD88", TypeHandler.getFFIcons(), kUI.TEXTSIZE_BUTTON_ICONS, // chevron.down
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> {
                toSettings.stop();
                returnHome.playFromStart();
            }
        );
        settingsBackButton.setLayoutX(kUI.POS_SETTINGS_BUTTON_BACK[0]);
        settingsBackButton.setLayoutY(kUI.POS_SETTINGS_BUTTON_BACK[1] - kApp.SCENE_HEIGHT);

        settingsAppearanceTab = new GlassTab(
            kUI.WIDTH_TAB_SETTINGS_APPEARANCE, kUI.HEIGHT_TAB_SETTINGS_APPEARANCE,
            List.of("system", "light", "dark"),
            () -> State.getRawAppearance().ordinal(),
            List.of(
                () -> {
                    State.setAppearance(Appearance.SYSTEM);
                    switch (State.getEffectiveAppearance()) {
                        case LIGHT -> bg.setImage(bgImgLight);
                        case DARK  -> bg.setImage(bgImgDark);
                    }
                    clock.setAppearance(State.getEffectiveAppearance());
                },
                () -> {
                    State.setAppearance(Appearance.LIGHT);
                    bg.setImage(bgImgLight);
                    clock.setAppearance(Appearance.LIGHT);
                },
                () -> {
                    State.setAppearance(Appearance.DARK);
                    bg.setImage(bgImgDark);
                    clock.setAppearance(Appearance.DARK);
                }
            )
        );
        settingsAppearanceTab.setLayoutX(kUI.POS_SETTINGS_TAB_APPEARANCE[0]);
        settingsAppearanceTab.setLayoutY(kUI.POS_SETTINGS_TAB_APPEARANCE[1] - kApp.SCENE_HEIGHT);

        settingsMusicSwitch = new GlassSwitch(
            State::isMusicOn,
            () -> {
                State.setMusicOn(false);
                AudioHandler.stopMusic();
            },
            () -> {
                State.setMusicOn(true);
                AudioHandler.playMusic();
            }
        );
        settingsMusicSwitch.setLayoutX(kUI.POS_SETTINGS_SWITCH_MUSIC[0]);
        settingsMusicSwitch.setLayoutY(kUI.POS_SETTINGS_SWITCH_MUSIC[1] - kApp.SCENE_HEIGHT);

        settingsSfxSwitch = new GlassSwitch(
            State::isSfxOn,
            () -> State.setSfxOn(false),
            () -> State.setSfxOn(true)
        );
        settingsSfxSwitch.setLayoutX(kUI.POS_SETTINGS_SWITCH_SFX[0]);
        settingsSfxSwitch.setLayoutY(kUI.POS_SETTINGS_SWITCH_SFX[1] - kApp.SCENE_HEIGHT);

        // home buttons
        GlassButton homePlayButton = new GlassButton( // play
            kUI.WIDTH_BUTTON_LONG, kUI.HEIGHT_BUTTON_LONG,
            "play!", TypeHandler.getFFBold(), kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> ScreenHandler.goTo(ScreenType.SELECTION, fadeOut)
        );
        homePlayButton.setLayoutX(kUI.POS_HOME_BUTTON_PLAY[0]);
        homePlayButton.setLayoutY(kUI.POS_HOME_BUTTON_PLAY[1]);

        GlassButton homeSettingsButton = new GlassButton( // settings
            kUI.WIDTH_BUTTON_LONG, kUI.HEIGHT_BUTTON_LONG,
            "settings", TypeHandler.getFFRegular(), kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> {
                returnHome.stop();
                toSettings.playFromStart();
            }
        );
        homeSettingsButton.setLayoutX(kUI.POS_HOME_BUTTON_SETTINGS[0]);
        homeSettingsButton.setLayoutY(kUI.POS_HOME_BUTTON_SETTINGS[1]);

        GlassButton homeCreditsButton = new GlassButton( // credits
            kUI.WIDTH_BUTTON_LONG, kUI.HEIGHT_BUTTON_LONG,
            "credits", TypeHandler.getFFRegular(), kUI.TEXTSIZE_BUTTON_NORMAL,
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> {
                returnHome.stop();
                toCredits.playFromStart();
            }
        );
        homeCreditsButton.setLayoutX(kUI.POS_HOME_BUTTON_CREDITS[0]);
        homeCreditsButton.setLayoutY(kUI.POS_HOME_BUTTON_CREDITS[1]);

        // credits elements
        Text creditsHero1 = new Text(
            kUI.POS_TEXT_CREDITS_HERO_1[0], kUI.POS_TEXT_CREDITS_HERO_1[1] + kApp.SCENE_HEIGHT,
            "lynzzyr"
        );
        creditsHero1.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsHero1.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_CREDITS_HERO));

        Text creditsBody1 = new Text(
            kUI.POS_TEXT_CREDITS_BODY_1[0], kUI.POS_TEXT_CREDITS_BODY_1[1] + kApp.SCENE_HEIGHT,
            "programming, visuals"
        );
        creditsBody1.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsBody1.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_CREDITS_BODY));

        Text creditsHero2 = new Text(
            kUI.POS_TEXT_CREDITS_HERO_2[0], kUI.POS_TEXT_CREDITS_HERO_2[1] + kApp.SCENE_HEIGHT,
            "bart bonte"
        );
        creditsHero2.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsHero2.setFont(Font.loadFont(TypeHandler.getFFBold(), kUI.TEXTSIZE_CREDITS_HERO));

        Text creditsBody2 = new Text(
            kUI.POS_TEXT_CREDITS_BODY_2[0], kUI.POS_TEXT_CREDITS_BODY_2[1] + kApp.SCENE_HEIGHT,
            "game style, music, sound effects"
        );
        creditsBody2.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsBody2.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_CREDITS_BODY));

        Text creditsBody3 = new Text(
            kUI.POS_TEXT_CREDITS_BODY_3[0], kUI.POS_TEXT_CREDITS_BODY_3[1] + kApp.SCENE_HEIGHT,
            "clock design by\nHans Hilfiker"
        );
        creditsBody3.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsBody3.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_CREDITS_BODY));

        ImageView creditsSBB = new ImageView(new Image(getClass().getResource("/sprites/credits/sbb_wordmark.png").toString()));
        creditsSBB.setX(kUI.POS_CREDITS_SBB[0]);
        creditsSBB.setY(kUI.POS_CREDITS_SBB[1] + kApp.SCENE_HEIGHT);

        Text creditsBody4 = new Text(
            kUI.POS_TEXT_CREDITS_BODY_4[0], kUI.POS_TEXT_CREDITS_BODY_4[1] + kApp.SCENE_HEIGHT,
            "inspired\nUI"
        );
        creditsBody4.setFill(Color.web(kUI.COLOR_LIGHT));
        creditsBody4.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_CREDITS_BODY));

        ImageView creditsLGBadgeGlass = new ImageView(new Image(getClass().getResource("/sprites/credits/lgbadge_glass.png").toString()));
        creditsLGBadgeGlass.setOpacity(0.0);
        creditsLGBadgeGlass.setX(kUI.POS_CREDITS_LGBADGE[0]);
        creditsLGBadgeGlass.setY(kUI.POS_CREDITS_LGBADGE[1] + kApp.SCENE_HEIGHT);

        FadeTransition badgeGlassFade = new FadeTransition(Duration.seconds(kUI.DUR_CREDITS_LGBADGE_GLASS_FADE), creditsLGBadgeGlass);
        badgeGlassFade.setToValue(1.0);
        toCredits.setOnFinished(event -> badgeGlassFade.playFromStart()); // fade in glass after move complete

        ImageView creditsLGBadgeFill = new ImageView(new Image(getClass().getResource("/sprites/credits/lgbadge_fill.png").toString()));
        creditsLGBadgeFill.setX(kUI.POS_CREDITS_LGBADGE[0]);
        creditsLGBadgeFill.setY(kUI.POS_CREDITS_LGBADGE[1] + kApp.SCENE_HEIGHT);

        ImageView creditsLGBadgeText = new ImageView(new Image(getClass().getResource("/sprites/credits/lgbadge_text.png").toString()));
        creditsLGBadgeText.setX(kUI.POS_CREDITS_LGBADGE[0]);
        creditsLGBadgeText.setY(kUI.POS_CREDITS_LGBADGE[1] + kApp.SCENE_HEIGHT);

        // credits buttons
        GlassButton creditsBackButton = new GlassButton(
            kUI.WIDTH_BUTTON_ICON, kUI.HEIGHT_BUTTON_ICON,
            "\uDBC0\uDD87", TypeHandler.getFFIcons(), kUI.TEXTSIZE_BUTTON_ICONS, // chevron.up
            kUI.COLOR_BUTTON_MAIN, kUI.COLOR_LIGHT,
            () -> {
                toCredits.stop();

                creditsLGBadgeGlass.setOpacity(0.0);
                returnHome.playFromStart();
            }
        );
        creditsBackButton.setLayoutX(kUI.POS_CREDITS_BUTTON_BACK[0]);
        creditsBackButton.setLayoutY(kUI.POS_CREDITS_BUTTON_BACK[1] + kApp.SCENE_HEIGHT);

        // add to all-in-one
        elevator.getChildren().addAll(
            // settings
            settingsBackButton, settingsAppearanceText, settingsMusicText, settingsSfxText,
            settingsAppearanceTab, settingsMusicSwitch, settingsSfxSwitch,
            // home
            titleText, homePlayButton, homeSettingsButton, homeCreditsButton,
            // credits
            creditsBackButton,
            creditsHero1, creditsBody1, creditsHero2, creditsBody2,
            creditsBody3, creditsSBB, creditsBody4,
            creditsLGBadgeGlass, creditsLGBadgeFill, creditsLGBadgeText
        );

        // version label
        Label versionLabel = new Label(kApp.VERSION_STRING);
        versionLabel.setPrefSize(kUI.WIDTH_LABEL_HOME_VERSION, kUI.HEIGHT_LABEL_HOME_VERSION);
        versionLabel.setLayoutY(kApp.SCENE_HEIGHT - kUI.HEIGHT_LABEL_HOME_VERSION);
        versionLabel.setFont(Font.loadFont(TypeHandler.getFFRegular(), kUI.TEXTSIZE_HOME_VERSION));
        versionLabel.setTextFill(Color.web(kUI.COLOR_LIGHT));
        versionLabel.setAlignment(Pos.CENTER_RIGHT);

        // add
        getChildren().addAll(bg, clock, elevator, versionLabel);
    }

    @Override
    public void enter() {
        if (!State.hasSplashPlayed()) { // first run
            State.setSplashPlayed(true);
        } else if (State.hasGameEnded()) { // coming from end screen
            State.setGameEnded(false);
        } else {
            setOpacity(0.0); // fade in raises to 1.0
            fadeIn.playFromStart();
        }
    }

    @Override
    public void exit() {
        settingsAppearanceTab.stopUpdater();
        settingsMusicSwitch.stopUpdater();
        settingsSfxSwitch.stopUpdater();

        clock.stopUpdater();
    }
}
