package game.handlers;

import game.State;
import javafx.scene.media.AudioClip;

import java.util.ArrayList;

/** Global handler managing all audio. */
public class AudioHandler {
    // sound holder
    private static final ArrayList<AudioClip> allAudio;

    // play on splash
    private static final AudioClip sfxSplash;

    // play on success
    private static final AudioClip sfxOk;

    // play on fail
    private static final AudioClip sfxFail;

    // music loop
    private static final AudioClip music;

    // CONSTRUCTOR

    static {
        // sfx
        allAudio = new ArrayList<>();

        sfxSplash = new AudioClip(
            AudioHandler.class.getResource("/sounds/splash.wav").toString()
        );
        allAudio.add(sfxSplash);

        sfxOk = new AudioClip(
            AudioHandler.class.getResource("/sounds/ok.wav").toString()
        );
        allAudio.add(sfxOk);

        sfxFail = new AudioClip(
            AudioHandler.class.getResource("/sounds/fail.wav").toString()
        );
        allAudio.add(sfxFail);

        // music
        music = new AudioClip(
            AudioHandler.class.getResource("/sounds/loop.wav").toString()
        );
        music.setCycleCount(AudioClip.INDEFINITE);
        allAudio.add(music);
    }

    // METHODS

    /** Load all sound effects into memory to avoid stall on first play. */
    public static void preloadAudio() {
        for (AudioClip sfx : allAudio) {
            sfx.play(0.0);
            sfx.stop();
        }
    }

    // sfx

    /** Plays the splash SFX. */
    public static void playSfxSplash() {
        if (State.isSfxOn()) sfxSplash.play();
    }

    /** Plays the OK SFX. */
    public static void playSfxOk() {
        if (State.isSfxOn()) sfxOk.play();
    }

    /** Plays the fail SFX. */
    public static void playSfxFail() {
        if (State.isSfxOn()) sfxFail.play();
    }

    // music

    /** Plays and loops the music. */
    public static void playMusic() {
        if (State.isMusicOn() && !music.isPlaying()) music.play();
    }

    /** Stops the music. */
    public static void stopMusic() {
        music.stop();
    }
}
