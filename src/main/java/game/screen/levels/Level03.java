package game.screen.levels;

import game.Constants.kGameplay;
import game.screen.ScreenType;
import javafx.scene.input.KeyCode;

import java.time.LocalTime;

/** Starts at 5:55, set to 5:56:30 with up and down arrow keys. */
public class Level03 extends LevelBase {
    public Level03() {
        super(
            3, ScreenType.END,
            LocalTime.of(5, 55), LocalTime.of(5, 56, 30), false,
            "up key\ndown key"
        );

        // key events
        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) getClock().subtractSeconds(kGameplay.LEVEL03_SECONDS_SHIFT);
            else if (event.getCode() == KeyCode.DOWN) getClock().addSeconds(kGameplay.LEVEL03_SECONDS_SHIFT);
        });
    }

    @Override
    public void onUpdate() {}
}
