package game;

import com.jthemedetecor.OsThemeDetector;

/** Static class holder for all settings and progress. */
public class State {
    // SETTINGS

    private static Appearance appearance = Appearance.SYSTEM; // system by default
    private static final Appearance systemAppearance = OsThemeDetector.getDetector().isDark()
        ? Appearance.DARK
        : Appearance.LIGHT;

    private static boolean sfxOn;
    private static boolean musicOn;

    // GAME VALUES

    private static int highestLevelUnlocked = 1; // first only first is playable

    private static boolean splashPlayed = false; // first not true
    private static boolean gameEnded = false;

    // SETTINGS GETTERS

    public static Appearance getRawAppearance() {
        return State.appearance;
    }

    public static Appearance getEffectiveAppearance() {
        if (State.appearance == Appearance.SYSTEM) return State.systemAppearance;
        else return State.appearance;
    }

    public static boolean isSfxOn() {
        return sfxOn;
    }
    public static boolean isMusicOn() {
        return musicOn;
    }

    // GAME VALUE GETTERS

    public static int getHighestLevelUnlocked() {
        return highestLevelUnlocked;
    }

    public static boolean hasSplashPlayed() {
        return splashPlayed;
    }
    public static boolean hasGameEnded() {
        return gameEnded;
    }

    // SETTINGS SETTERS

    public static void setAppearance(Appearance appearance) {
        State.appearance = appearance;
    }

    public static void setSfxOn(boolean sfxOn) {
        State.sfxOn = sfxOn;
    }
    public static void setMusicOn(boolean musicOn) {
        State.musicOn = musicOn;
    }

    // GAME VALUE SETTERS

    public static void setHighestLevelUnlocked(int highestLevelUnlocked) {
        State.highestLevelUnlocked = highestLevelUnlocked;
    }

    public static void setSplashPlayed(boolean splashPlayed) {
        State.splashPlayed = splashPlayed;
    }
    public static void setGameEnded(boolean gameEnded) {
        State.gameEnded = gameEnded;
    }
}
