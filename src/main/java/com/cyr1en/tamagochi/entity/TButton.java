package com.cyr1en.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameButton;
import com.cyr1en.tamagochi.enums.SFX;

public class TButton extends GameButton {

    private boolean played;

    public TButton(int x, int y) {
        super(x, y);
        played = false;
    }

    public TButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        played = false;
    }

    public void playHover() {
        if(!played) {
            SFX.BUTTON_HOVER.play();
            played = true;
        }
    }

    public void setPlayed(boolean b) {
        played = b;
    }
}
