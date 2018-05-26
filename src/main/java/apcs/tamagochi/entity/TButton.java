package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameButton;
import apcs.tamagochi.enums.SFX;

public class TButton extends GameButton {
    //instance fo checking if hover sound has been played to prevent infinite loop
    private boolean playedHover;


    /**
     * Simple TButton constructor
     *
     * @param x horizontal coordinate of the button
     * @param y vertical coordinate of the button
     */
    public TButton(int x, int y) {
        super(x, y);
        playedHover = false;
    }

    /**
     * TButton constructor
     *
     * @param x      horizontal coordinate of the button
     * @param y      vertical coordinate of the button
     * @param width  horizontal length of the button
     * @param height vertical length of the button
     */
    public TButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        playedHover = false;
    }

    /**
     * Play the hover sound
     */
    public void playHover() {
        if (!playedHover) {
            SFX.BUTTON_HOVER.play();
            playedHover = true;
        }
    }

    /**
     * Play the click sound
     */
    public void playClick() {
        SFX.BUTTON_CLICK.play();
    }

    /**
     * playedHover modifier
     *
     * @param b desired value for playedHover
     */
    public void setPlayedHover(boolean b) {
        playedHover = b;
    }
}
