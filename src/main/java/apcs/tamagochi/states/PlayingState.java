package apcs.tamagochi.states;

import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;

import java.awt.*;

public class PlayingState extends GameState {

    private  BackGround background;
    private Transition transition;

    public PlayingState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {
        background = new BackGround("/assets/defaultbg.png");
        transition = new Transition(gsm, 0, 30, -1, 30);
    }

    @Override
    public void update() {
        background.update();
        transition.update();
    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g, getInterpolation());
        transition.draw(g, getInterpolation());
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void setInterpolation(float v) {
        this.interpolation = v;
    }

    @Override
    public float getInterpolation() {
        return interpolation;
    }
}
