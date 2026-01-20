package game.screen.levels;

import game.screen.ScreenType;

import java.time.LocalTime;

/** Starts at 2:44:30, set to 2:45 by just waiting. */
public class Level02 extends LevelBase {
    public Level02() {
        super(
            2, ScreenType.LEVEL03,
            LocalTime.of(2, 44, 45), LocalTime.of(2, 45), true,
            "patience is a\nvirtue..."
        );
    }

    @Override
    public void onUpdate() {}
}
