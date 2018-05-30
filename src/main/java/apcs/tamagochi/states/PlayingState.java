package apcs.tamagochi.states;

import apcs.tamagochi.entity.Food;
import apcs.tamagochi.entity.Pet;
import apcs.tamagochi.handler.StateManager;
import com.cyr1en.cgdl.Entity.BackGround;
import com.cyr1en.cgdl.Entity.Button.GameButton;
import com.cyr1en.cgdl.Entity.SoundClip;
import com.cyr1en.cgdl.Entity.SoundEffect;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.GameState.GameStateManager;
import com.cyr1en.cgdl.GameState.Transition;
import com.cyr1en.cgdl.Handlers.Keys;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.FontUtil;
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
    private GameButton<PlayingState> playButton;

    private GameButton<PlayingState> saveButton;
    private GameButton<PlayingState> homeButton;


    public PlayingState(GameStateManager gsm, Pet pet) {
        super(gsm);
        this.pet = pet;
        init();
    }

    @Override
    public void init() {
        backgroundMusic = new SoundClip("/sounds/bg-music2.wav", Clip.LOOP_CONTINUOUSLY);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.start();

        background = new BackGround("/assets/defaultbg.png");
        transition = new Transition(gsm, 0, 30, -1, 60);
        foods = new ArrayList<>();
        foods.add(new Food(10, "Salad"));
        foods.add(new Food(50, "Sushi"));
        foods.add(new Food(100, "Pizza"));

        feedButton = new GameButton<>((int) (GamePanel.WIDTH * .25), GamePanel.HEIGHT - 70);
        feedButton.setText("\uD83C\uDF7D", new Font("Segoe UI Emoji", Font.PLAIN, 70));
        feedButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        feedButton.setClickSound(new SoundEffect("/sounds/sfx/eat.wav"));
        feedButton.setColor(new Color(224, 1, 107, 255));
        feedButton.setType(GameButton.CENTER);
        feedButton.setObjType(this);
        feedButton.setOnClick(state -> pet.feed(foods.get((int) (Math.random() * 3))));

        playButton = new GameButton<>((int) (GamePanel.WIDTH * .75), GamePanel.HEIGHT - 70);
        playButton.setText("\uD83C\uDFAE", new Font("Segoe UI Emoji", Font.PLAIN, 70));
        playButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        playButton.setClickSound(new SoundEffect("/sounds/sfx/eat.wav"));
        playButton.setColor(new Color(224, 1, 107, 255));
        playButton.setType(GameButton.CENTER);
        playButton.setObjType(this);
        playButton.setOnClick(state -> pet.play());

        saveButton = new GameButton<>(GamePanel.WIDTH - 20, GamePanel.HEIGHT - (GamePanel.HEIGHT - 15));
        saveButton.setText("\uD83D\uDCBE", new Font("Segoe UI Emoji", Font.PLAIN, 20));
        saveButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        saveButton.setClickSound(new SoundEffect("/sounds/sfx/button-click.wav"));
        saveButton.setType(GameButton.CENTER);
        saveButton.setObjType(this);
        saveButton.setOnClick(state -> {
            File dir = new File("saves/");
            boolean exist;
            if (!dir.exists()) {
                exist = dir.mkdirs();
            } else {
                exist = true;
            }
            if (exist) {
                SerializationUtil.serialize(pet, "saves/save.dat");
            }
        });

        homeButton = new GameButton<>(15, 15);
        homeButton.setText("\uD83C\uDFE0", new Font("Segoe UI Emoji", Font.PLAIN, 20));
        homeButton.setHoverSound(new SoundEffect("/sounds/sfx/button-hover.wav"));
        homeButton.setClickSound(new SoundEffect("/sounds/sfx/button-click.wav"));
        homeButton.setType(GameButton.CENTER);
        homeButton.setObjType(this);
        homeButton.setOnClick(state -> {
            backgroundMusic.stop();
            transition.nextState(StateManager.MENU_STATE);
        });

        pet.setAnimation(Pet.DEFAULT);
        pet.setPosition(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2);
    }

    @Override
    public void update() {
        handleInput();
        background.update();
        pet.update();
        feedButton.update();
        if (pet.isDead()) {
            feedButton.setActive(false);
            saveButton.setActive(false);
            playButton.setActive(false);
        }
        saveButton.update();
        homeButton.update();
        playButton.update();
        transition.update();
    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g, getInterpolation());
        feedButton.draw(g, getInterpolation());
        saveButton.draw(g, getInterpolation());
        homeButton.draw(g, getInterpolation());
        playButton.draw(g, getInterpolation());
        pet.draw(g, getInterpolation());
        int x = 10;
        int y = 60;
        int offset = 32;

        FontUtil.registerFont("/fonts/Summer's Victory Over Spring - TTF.ttf");
        g.setFont(new Font("Summers Victory Over Spring", Font.BOLD, 30));
        g.setColor(new Color(0, 0, 0));
        g.drawString("Health: " + pet.getHealth(), x, y);
        g.drawString("Full: " + pet.getFull(), x, y + offset);
        g.drawString("Enjoyment: " + pet.getEnjoyment(), x, y + (offset * 2));
        g.drawString("Exp: " + pet.getExp() + "/" + pet.getMaxexp(), x, y + (offset * 3));
        g.drawString("Lvl: " + pet.getLvl(), x, y + (offset * 4));
        if (pet.isDead()) {
            FontUtil.registerFont("/fonts/youmurdererbb_reg.ttf");
            g.setFont(new Font("YouMurderer BB", Font.BOLD, 80));
            g.setColor(new Color(176, 0, 3));
            int length = (int) g.getFontMetrics().getStringBounds("You Ded!", g).getWidth();
            g.drawString("You Ded!", (GamePanel.WIDTH / 2) - length / 2, GamePanel.HEIGHT / 2);
        }
        transition.draw(g, getInterpolation());
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.SPACE)) {
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
