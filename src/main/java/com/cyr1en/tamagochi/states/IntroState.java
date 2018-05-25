package com.cyr1en.tamagochi.states;

import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Handlers.Keys;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.tamagochi.handler.StateManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IntroState extends GameState {

    private BufferedImage logo;
    private int logoX;
    private int logoY;
    private int logoW;
    private int logoH;

    private int alpha;
    private int ticks;

    public IntroState(StateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        interpolation = 0;
        ticks = 0;
        logo = ImageLoader.load("/assets/cgdl-logo.png");
        logoH = logo.getHeight();
        logoW = logo.getWidth();
        logoX = (GamePanel.WIDTH - logoW) / 2;
        logoY = (GamePanel.HEIGHT - logoH) / 2;
    }

    public void update() {
        handleInput();
        int FADE_IN = 90;
        int LENGTH = 90;
        int FADE_OUT = 90;
        ticks++;
        if (ticks < FADE_IN) {
            alpha = (int) (255 - 255 * 1.0 * ticks / FADE_IN);
            if (alpha < 0) alpha = 0;
        }
        if (ticks > FADE_IN + LENGTH) {
            alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
            if (alpha > 255)
                alpha = 255;
        }
        if (ticks > FADE_IN + LENGTH + FADE_OUT) {
            gsm.setState(StateManager.MENU_STATE);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.drawImage(logo, logoX, logoY, logoW, logoH, null);
        g.setColor(new Color(255, 255, 255, alpha));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            gsm.setState(StateManager.MENU_STATE);
        }
        if (Mouse.isPressed()) {
            gsm.setState(StateManager.MENU_STATE);
        }
    }

    @Override
    public void setInterpolation(float interpolation) {
        this.interpolation = interpolation;
    }

    @Override
    public float getInterpolation() {
        return interpolation;
    }

}

