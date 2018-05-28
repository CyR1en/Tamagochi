package apcs.tamagochi.states;

import apcs.tamagochi.entity.Food;
import apcs.tamagochi.entity.Pet;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Button.GameButton;
import com.cyr1en.cgdl.Entity.SoundClip;
import com.cyr1en.cgdl.Entity.SoundEffect;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.Keys;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.SerializationUtil;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PlayingState extends GameState {

    private BackGround background;
    private SoundClip backgroundMusic;

    private Transition transition;
    private Pet pet;
    private ArrayList<Food> foods;

    private GameButton<PlayingState> feedButton;
    private GameButton<PlayingState> saveButton;

    public PlayingState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {
        backgroundMusic = new SoundClip("/sounds/bg-music2.wav", Clip.LOOP_CONTINUOUSLY);
        backgroundMusic.setVolume(0.10f);
        backgroundMusic.start();

        background = new BackGround("/assets/defaultbg.png");
        transition = new Transition(gsm, 0, 30, -1, 30);
        foods = new ArrayList<>();
        foods.add(new Food(10, "Salad"));
        foods.add(new Food(50, "Sushi"));
        foods.add(new Food(100, "Pizza"));

        feedButton = new GameButton<>((int) (GamePanel.WIDTH * .20), GamePanel.HEIGHT - 100);
        feedButton.setText("\uD83C\uDF7D ", new Font("Segoe UI Emoji", Font.PLAIN, 80));
        feedButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        feedButton.setClickSound(new SoundEffect("/sounds/sfx/eat.wav"));
        feedButton.setColor(new Color(224, 128, 151, 255));
        feedButton.setType(GameButton.CENTER);
        feedButton.setObjType(this);
        feedButton.setOnClick(state -> {
            feed();
        });

        saveButton = new GameButton<>(GamePanel.WIDTH - 100, GamePanel.HEIGHT - (GamePanel.HEIGHT - 100));
        saveButton.setText("\uD83D\uDCBE", new Font("Segoe UI Emoji", Font.PLAIN, 40));
        saveButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        saveButton.setClickSound(new SoundEffect("/sounds/sfx/button-click.wav"));
        saveButton.setColor(GameButton.DEFAULT_COLOR);
        saveButton.setType(GameButton.CENTER);
        saveButton.setObjType(this);
        saveButton.setOnClick(state -> {
            File dir = new File("saves/");
            boolean exist;
            if(!dir.exists()) {
                exist = dir.mkdirs();
            } else {
                exist = true;
            }
            System.out.println(exist);
            if(exist) {
                SerializationUtil.serialize(pet, "saves/save.dat");
            }
        });

        pet = new Pet(1);
        pet.setAnimation(Pet.DEFAULT);
        pet.setPosition(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
    }

    public void feed() {
        pet.feed(foods.get((int)(Math.random() * 3)));
    }

    @Override
    public void update() {
        background.update();
        transition.update();
        pet.update();
        feedButton.update();
        saveButton.update();
    }

    @Override
    public void draw(Graphics2D g) {
        handleInput();
        background.draw(g, getInterpolation());
        transition.draw(g, getInterpolation());
        pet.draw(g, getInterpolation());
        feedButton.draw(g, getInterpolation());
        saveButton.draw(g, getInterpolation());
    }

    @Override
    public void handleInput() {
        if(Keys.isPressed(Keys.SPACE)) {
            boolean follow = !pet.isFollow();
            pet.setFollow(follow);
        }
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
