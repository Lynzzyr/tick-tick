package game.screen.levels;

import game.Constants.kGameplay;
import game.Constants.kUI;
import game.element.Crown;
import game.screen.ScreenType;

import java.time.LocalTime;

/** Starts at 10:09:30, set to 10:20 with crown. */
public class Level01 extends LevelBase {
    // elements
    private final Crown crown;

    public Level01() {
        super(
            1, ScreenType.LEVEL02,
            LocalTime.of(10, 9, 30), LocalTime.of(10, 20), false,
            "try turning the\ndial..."
        );

        // crown
        crown = new Crown();
        crown.setX(kUI.POS_LEVEL01_CROWN[0]);
        crown.setY(kUI.POS_LEVEL01_CROWN[1]);

        getChildren().add(crown);
    }

    @Override
    public void onUpdate() {
        // get dy of crown at frame
        double currDy = crown.getDy();

        // clock logic
        if (currDy > 0) getClock().addSeconds(currDy * kGameplay.FACTOR_CROWN_DRAG);
        else if (currDy < 0) getClock().subtractSeconds(Math.abs(currDy) * kGameplay.FACTOR_CROWN_DRAG);
    }
}
