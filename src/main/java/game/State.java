package game;

/** Static class holder for all game states such as progress and settings. */
public class State {
    // SETTINGS

    private static Appearance appearance = Appearance.LIGHT; // light by default

    // GAME VALUES

    private static int highestLevelUnlocked = 1; // first only first is playable

    private static boolean splashPlayed = false; // first not true
    private static boolean gameEnded = false;

    // SETTINGS GETTERS

    public static Appearance getAppearance() {
        return appearance;
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
