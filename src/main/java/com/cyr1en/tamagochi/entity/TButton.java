package com.cyr1en.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameButton;
import com.cyr1en.tamagochi.enums.SFX;

public class TButton extends GameButton {

    private boolean playedHover;
    private boolean playedClick;

    public TButton(int x, int y) {
        super(x, y);
        playedHover = false;
        playedClick = false;
    }

    public TButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        playedHover = false;
    }

    public void playHover() {
        if(!playedHover) {
            SFX.BUTTON_HOVER.play();
            playedHover = true;
        }
    }

    public void playClick() {
        if(!playedClick) {
            SFX.BUTTON_CLICK.play();
            playedHover = true;
        }
    }

    public void setPlayedClick(boolean b) {
        playedClick = b;
    }

    public void setPlayedHover(boolean b) {
        playedHover = b;
    }
}
