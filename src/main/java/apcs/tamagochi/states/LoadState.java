package apcs.tamagochi.states;

import apcs.tamagochi.entity.LoadingIcon;
import apcs.tamagochi.handler.Loader;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Particle;
import com.cyr1en.cgdl.Entity.SoundEffect;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class LoadState extends GameState {

    private BackGround backGround;
    private LoadingIcon loadingIcon;

    private ArrayList<Particle> particles;
    private Loader loader;

    private Transition transition;

    public LoadState(GameStateManager gsm, Loader loader) {
        super(gsm);
        this.loader = loader == null ? new Loader() : loader;
        init();
    }

    @Override
    public void init() {
        transition = new Transition(gsm, -10, 10, -50, 80);
        transition.after(transition1 -> {
            gsm.setState(new PlayingState(gsm, loader.loadData()));
        });
        backGround = new BackGround("/assets/gaming-pattern.png");
        backGround.setVector(-1, 0);
        loadingIcon = new LoadingIcon(GamePanel.WIDTH - 10, GamePanel.HEIGHT - 7);
        particles = new ArrayList<>();
        ParticleFactory.init(particles);
    }


    @Override
    public void update() {
        handleInput();
        backGround.update();
        loadingIcon.update();
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).updateBool()) {
                particles.remove(i);
                i--;
            }
        }
        transition.update();
    }

    @Override
    public void draw(Graphics2D g) {
        backGround.draw(g, getInterpolation());
        loadingIcon.draw(g, getInterpolation());
        for (Particle p : particles)
            p.draw(g, getInterpolation());
        transition.draw(g, interpolation);
    }

    @Override
    public void handleInput() {
        if (Mouse.isPressed()) {
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, new Color(40, 40, 40));
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 10, new Color(40, 40, 40));
            new SoundEffect("/sounds/sfx/pop.wav").play();
        }
    }

    @Override
    public void setInterpolation(float v) {
        interpolation = v;
    }

    @Override
    public float getInterpolation() {
        return interpolation;
    }
}
