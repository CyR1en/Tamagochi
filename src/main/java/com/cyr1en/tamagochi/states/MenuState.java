package com.cyr1en.tamagochi.states;

import com.cyr1en.cgdl.Entity.*;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.Handlers.GameStateManager;
import com.cyr1en.cgdl.Handlers.Keys;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.tamagochi.entity.TButton;
import com.cyr1en.tamagochi.enums.SFX;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.util.ArrayList;

public class MenuState extends GameState {

    // entities/objects
    private BackGround bg;
    private Title title;

    // game button variables
    private int currentChoice;
    private TButton[] options;

    // particles
    private ArrayList<Particle> particles;

    // fade variables
    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;

    private int nextState;

    private SoundClip clip;

    // constructor
    public MenuState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    // initialize all the instance variables
    public void init() {
        interpolation = 0;
        currentChoice = -1;
        clip = new SoundClip("/sounds/bg-music.wav", Clip.LOOP_CONTINUOUSLY);
        title = new Title("TOMOGUCCI");
        title.setColor(new Color(40, 50, 55, 245));

        bg = new BackGround("/assets/bg2.png");
        bg.setVector(-1, 0);

        options = new TButton[2];
        options[0] = new TButton(GamePanel.WIDTH / 2, (int) (GamePanel.HEIGHT * 0.6));
        options[0].setText("New Game", new Font("Fira Code Retina Medium", Font.PLAIN, 30));
        options[0].setColor(GameButton.DEFAULT_COLOR);
        options[0].setType(GameButton.CENTER);
        options[1] = new TButton(GamePanel.WIDTH / 2, (int) (GamePanel.HEIGHT * 0.6) + 50);
        options[1].setText("Load Game", new Font("Fira Code Retina Medium", Font.PLAIN, 30));
        options[1].setColor(GameButton.DEFAULT_COLOR);
        options[1].setType(GameButton.CENTER);

        fadeInTimer = 0;
        fadeInDelay = 60;
        fadeOutTimer = -1;
        fadeOutDelay = 60;
        particles = new ArrayList<>();
        ParticleFactory.init(particles);
        clip.start();
        SFX.init();
    }

    public Runnable playEffect(SFX sfx) {
        return sfx::play;
    }
    //update all the variables that's used in this class and other components of it
    public void update() {
        handleInput(); // handle the inputs
        bg.update(); // update the background
        title.update(); // update the title

        // particles update
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).update()) {
                particles.remove(i);
                i--;
            }
        }

        // game button checker. checks if mouse is hovering over the button and update
        for (int i = 0; i < options.length; i++) {
            if (currentChoice == i) {
                options[i].setHover(true);
                options[i].playHover();
            } else {
                options[i].setHover(false);
                options[i].setPlayedHover(false);
            }
        }

        if (fadeInTimer >= 0) {
            fadeInTimer++;
            alpha = (int) (255.0 * fadeInTimer / fadeInDelay);
            if (fadeInTimer == fadeInDelay) {
                fadeInTimer = -1;
            }
        }

        if (fadeOutTimer >= 0) {
            fadeOutTimer++;
            alpha = (int) (255.0 * fadeOutTimer / fadeOutDelay);
            if (fadeOutTimer == fadeOutDelay) {
                gsm.setState(nextState);
            }
        }
        if (alpha < 0)
            alpha = 0;
        if (alpha > 255)
            alpha = 255;
        /*
        if (Main.frame.isShowTitleInfo())
            Main.frame.updateTitleBar();
        else
            Main.frame.clearTitleBar();
            */
    }

    // draws the menu state
    public void draw(Graphics2D g) {
        bg.draw(g, getInterpolation()); //draw the background

        //draw each gameButton
        for (GameButton button : options) {
            button.draw(g);
        }

        //draw the title
        title.draw(g, getInterpolation());

        //draws all the particles
        for (Particle p : particles)
            p.draw(g, getInterpolation());

        if (fadeInTimer >= 0) {
            g.setColor(new Color(255, 255, 255, 255 - alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        if (fadeOutTimer >= 0) {
            g.setColor(new Color(255, 255, 255, alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }

    // handles the selection for the game button
    private void select() {
        if (fadeOutTimer != -1)
            return;
        if (currentChoice == 0) {
            //nextState = StateManager.PLAYING_STATE;
            clip.stop();
            options[currentChoice].playClick();
            fadeInTimer = -1;
            fadeOutTimer = 0;
        }
        if (currentChoice == 1) {
            //nextState = StateManager.CREDIT_STATE;
            clip.stop();
            options[currentChoice].playClick();
            fadeInTimer = -1;
            fadeOutTimer = 0;
        }
    }

    public void handleInput() {
        //when mouse is pressed make a cute little fireworks effect
        if (Mouse.isPressed()) {
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, new Color(40, 40, 40));
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 10, new Color(40, 40, 40));
            select();
        }


        if (Keys.isPressed(Keys.SPACE)) {
            currentChoice = 0;
            select();
        }

        boolean hit = false;
        for (int i = 0; i < options.length; i++) {
            if (options[i].isHovering(Mouse.x, Mouse.y)) {
                currentChoice = i;
                hit = true;
                break;
            }
        }
        if (!hit) {
            currentChoice = -1;
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
