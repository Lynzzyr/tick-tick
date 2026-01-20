package game;

/** Static class holding all game constants. */
public class Constants {
    /** Values for the application including window size and name. */
    public static final class kApp {
        // starting dimensions of the scene, strict aspect ratio
        public static final double SCENE_WIDTH = 960;
        public static final double SCENE_HEIGHT = 600;

        public static final String NAME = "Tick, Tick";

        public static final String VERSION_STRING = "v1.0-ALPHA";
    }

    /** General values for UI sizes and positioning. */
    public static final class kUI {
        // Liquid Glass element sizes
        public static final double WIDTH_BUTTON_LONG = 300;
        public static final double HEIGHT_BUTTON_LONG = 50;

        public static final double WIDTH_BUTTON_ICON = 50;
        public static final double HEIGHT_BUTTON_ICON = 50;

        public static final double WIDTH_BUTTON_SELECTION_LEVEL = 75;
        public static final double HEIGHT_BUTTON_SELECTION_LEVEL = 75;

        public static final double WIDTH_TAB_SETTINGS_APPEARANCE = 396;
        public static final double HEIGHT_TAB_SETTINGS_APPEARANCE = 40;

        // other sizes
        public static final double WIDTH_LABEL_HOME_VERSION = 940;
        public static final double HEIGHT_LABEL_HOME_VERSION = 25;

        public static final int TEXTSIZE_BUTTON_NORMAL = 36; // pt
        public static final int TEXTSIZE_BUTTON_ICONS = 28;

        public static final double TEXTSIZE_HOME_TITLE = 96;
        public static final double TEXTSIZE_HOME_VERSION = 12;

        public static final double TEXTSIZE_SETTINGS = 60;

        public static final int TEXTSIZE_LEVEL = 48;
        public static final int TEXTSIZE_LEVEL_BODY = 36;

        public static final int TEXTSIZE_END_HERO = 60;
        public static final int TEXTSIZE_END_BODY = 36;

        public static final double RADIUS_HOME_CLOCK = 112.5;
        public static final double RADIUS_LEVELS_CLOCK = 257.5;

        // positions
        public static final double[] POS_HOME_TITLE = {100, 180};
        public static final double[] POS_HOME_BUTTON_PLAY = {100, 300};
        public static final double[] POS_HOME_BUTTON_SETTINGS = {100, 380};
        public static final double[] POS_HOME_BUTTON_CREDITS = {100, 460};

        public static final double[] POS_SETTINGS_BUTTON_BACK = {100, 70};

        public static final double[] POS_CREDITS_BUTTON_BACK = {100, 70};

        public static final double[] POS_SELECTION_BUTTON_BACK = {100, 70};
        public static final double[] POS_SELECTION_BUTTON_LEVEL01 = {100, 170};
        public static final double[] POS_SELECTION_BUTTON_LEVEL02 = {185, 170};
        public static final double[] POS_SELECTION_BUTTON_LEVEL03 = {270, 170};
        public static final double[] POS_SELECTION_BUTTON_LEVEL04 = {355, 170};
        public static final double[] POS_SELECTION_BUTTON_LEVEL05 = {440, 170};
        public static final double[] POS_SELECTION_BUTTON_LEVEL06 = {100, 255};
        public static final double[] POS_SELECTION_BUTTON_LEVEL07 = {185, 255};
        public static final double[] POS_SELECTION_BUTTON_LEVEL08 = {270, 255};
        public static final double[] POS_SELECTION_BUTTON_LEVEL09 = {355, 255};
        public static final double[] POS_SELECTION_BUTTON_LEVEL10 = {440, 255};

        public static final double[] POS_LEVELS_BUTTON_BACK = {30, 30};

        public static final double[] POS_SETTINGS_TAB_APPEARANCE = {100, 230};

        public static final double[] POS_CLOCK_HOME = {552, 187};
        public static final double[] POS_CLOCK_LEVEL = {232, 9}; // yes it is off-center womp womp

        public static final double[] POS_END_BUTTON_RETURN = {340, 350};

        public static final double[] POS_TEXT_SETTINGS_APPEARANCE = {100 ,200};
        public static final double[] POS_TEXT_SETTINGS_MUSIC = {100, 360};
        public static final double[] POS_TEXT_SETTINGS_SFX = {100, 440};

        public static final double[] POS_TEXT_SELECTION = {180, 110};

        public static final double[] POS_TEXT_LEVEL_NUM = {880, 60};
        public static final double[] POS_TEXT_LEVEL_TARGET = {30, 140};
        public static final double[] POS_TEXT_LEVEL_TOOLTIP = {640, 540};

        public static final double[] POS_END_TEXT_HERO = {340, 180};
        public static final double[] POS_END_TEXT_BODY = {310, 230};

        public static final double[] POS_LEVEL01_CROWN = {740, 180};

        // colors
        public static final String COLOR_LIGHT = "#ffffff"; // white
        public static final String COLOR_DARK = "#120e0e"; // very dark red
        public static final String COLOR_ACCENT = "#eb0000"; // bright red

        public static final String COLOR_BUTTON_MAIN = "#ff8b00"; // orange 50% opacity
        public static final String COLOR_BUTTON_MAIN_ALT = "#ffa914"; // lighter orange-yellow 50% opacity
        public static final String COLOR_BUTTON_SECONDARY = "#3cd3fe"; // cyan 50% opacity

        // animations
        public static final double DUR_SCREEN_FADE = 0.25;

        public static final double DUR_SPLASH_FADE = 0.5;
        public static final double DUR_SPLASH_STALL = 0.5;
        public static final double DUR_SPLASH_STAY = 2;

        public static final double DUR_HOME_ELEVATOR_MOVE = 0.6;

        public static final double DUR_LEVELS_COMPLETE_STALL = 2;
    }

    /** Values for Liquid Glass. */
    public static final class kLiquidGlass {
        // sizing
        public static final double WIDTH_OUTLINE = 1.0;

        public static final double MARGIN_KNOB = 3; // px round border to container

        public static final double FACTOR_OUTLINE_BRIGHTNESS = 2;

        public static final double FACTOR_RADIUS_HALO = 0.5; // multiply by width
        public static final double FACTOR_DRAG_HALO = 0.05; // multiply by center coordinates

        public static final double FACTOR_TEXTSIZE_TAB = 0.375; // multiply by height

        // colors
        public static final String COLOR_GLASS = "#ffffff1a"; // white 10% opacity
        public static final String COLOR_HALO = "#ffffff40"; // white 25% opacity

        public static final String COLOR_TAB_KNOB = "#676975"; // light slightly blueish gray
        public static final String COLOR_TAB_CONTAINER = "#313037"; // darkish faint blue

        public static final String COLOR_SWITCH_KNOB = "#ffffff"; // white
        public static final String COLOR_SWITCH_CONTAINER = "#3f403f"; // half gray
        public static final String COLOR_SWITCH_CONTAINER_YES = "#2ed057"; // green

        // animations
        public static final double SCALE_BOUNCEOUT_EXPAND = 1.4;
        public static final double SCALE_BOUNCEOUT_SETTLE = 1.37;
        public static final double SCALE_CANCEL_SHRINK = 0.9;
        public static final double SCALE_CANCEL_COUNTERACT = 1.02;

        public static final double DUR_BOUNCEOUT_EXPAND = 0.167; // 10 frames in 60 fps
        public static final double DUR_BOUNCEOUT_SETTLE = 0.167;
        public static final double DUR_CANCEL_SHRINK = 0.217; // 13 frames in 60 fps
        public static final double DUR_CANCEL_COUNTERACT = 0.217;
        public static final double DUR_CANCEL_NORMALIZE = 0.217;

        public static final double DUR_SOLID_FADE = 0.2;

        public static final double DUR_HALO_FADEIN = 0.1; // 6 frames in 60 fps
        public static final double DUR_HALO_FADEOUT = 0.416; // 25 frames in 60 fps
    }

    /** Values for clocks. */
    public static final class kClock {
        // dimension and position proportions to multiply by radius; derived from official blueprint
        // N.B. blueprint uses unit alpha = diameter / 100
        // for any value on blueprint, derive by diving by 50
        public static final double PADDING = 0.03;

        public static final double WIDTH_BIGTICK = 0.07;
        public static final double HEIGHT_BIGTICK = 0.24;

        public static final double WIDTH_SMALLTICK = 0.028;
        public static final double HEIGHT_SMALLTICK = 0.07;

        public static final double WIDTH_HOUR_TIP = 0.104;
        public static final double WIDTH_HOUR_BASE = 0.128;
        public static final double HEIGHT_HOUR = 0.88; // delta y NOT length of slant
        public static final double BASE_OFFSET_HOUR = 0.24; // this much up from bottom is the pivot

        public static final double WIDTH_MINUTE_TIP = 0.072;
        public static final double WIDTH_MINUTE_BASE = 0.104;
        public static final double HEIGHT_MINUTE = 1.16;
        public static final double BASE_OFFSET_MINUTE = 0.24;

        public static final double WIDTH_SECOND = 0.028;
        public static final double HEIGHT_SECOND = 0.954;
        public static final double BASE_OFFSET_SECOND = 0.33;

        public static final double RADIUS_SECOND_DOT = 0.105;
    }

    /** Values for gameplay. */
    public static final class kGameplay {
        public static final double FACTOR_CROWN_DRAG = 0.25; // seconds per pixel

        public static final int TOLERANCE_TARGET = 5; // target + x seconds

        public static final int LEVEL03_SECONDS_SHIFT = 5;
    }
}
