package AirScape.GameState;

import AirScape.Handlers.BackGround;
import AirScape.Handlers.StateManager;
import AirScape.Main.Main;
import com.cyr1en.cgdl.Entity.GameButton;
import com.cyr1en.cgdl.GameState.GameState;
import com.cyr1en.cgdl.Handlers.GameStateManager;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Handlers.ParticleFactory;
import com.cyr1en.cgdl.Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CreditState extends GameState {

    private int currentChoice = 0;
    private GameButton[] options;

    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;
    private int nextState;

    private BackGround backGround;

    private ArrayList<Particle> particles;

    private long startTime;

    public CreditState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        backGround = new BackGround();
        backGround.setVector(-.3, 0);
        startTime = System.nanoTime();
        particles = new ArrayList<>();
        ParticleFactory.init(particles);
        options = new GameButton[1];
        String s = " B A C K ";
        options[0] = new GameButton((int)( GamePanel.WIDTH * 0.485) , (int)(GamePanel.HEIGHT * 0.95));
        options[0].setText(s, new Font("Fira Code", Font.BOLD, 20));
        options[0].setColor(GameButton.DEFAULT_COLOR);

        fadeInTimer = 0;
        fadeInDelay = 60;
        fadeOutTimer = -1;
        fadeOutDelay = 60;
    }

    public void update() {
        backGround.update();
        handleInput();

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > 200) {
            Random rand = new Random();
            int randX = rand.nextInt(GamePanel.WIDTH);
            int randY = rand.nextInt(GamePanel.HEIGHT);
            int randR = rand.nextInt(255);
            int randG = rand.nextInt(255);
            int randB = rand.nextInt(255);
            Color c = new Color(randR, randG, randB);
            ParticleFactory.createExplosion(randX, randY, c);
            ParticleFactory.createSmallWave(randX, randY, 5, c);
            startTime = System.nanoTime();
        }
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i).update()) {
                particles.remove(i);
                i--;
            }
        }
        for (int i = 0; i < options.length; i++) {
            if (currentChoice == i) {
                options[i].setHover(true);
            } else {
                options[i].setHover(false);
            }
        }

        if (fadeInTimer >= 0) {
            fadeInTimer++;
            alpha = (int) (255.2 * fadeInTimer / fadeInDelay);
            if (fadeInTimer == fadeInDelay) {
                fadeInTimer = -1;
            }
        }

        if (fadeOutTimer >= 0) {
            fadeOutTimer++;
            alpha = (int) (255.02 * fadeOutTimer / fadeOutDelay);
            if(fadeOutTimer == fadeOutDelay) {
                gsm.setState(nextState);
            }
        }
        if (alpha < 0)
            alpha = 0;
        if (alpha > 255)
            alpha = 255;
        if(Main.frame.isShowTitleInfo())
            Main.frame.updateTitleBar();
        else
            Main.frame.clearTitleBar();
    }

    public void draw(Graphics2D g) {

        backGround.draw(g);

        g.setColor(new Color(30, 30, 30, 230));
        g.fillRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);

        for (Particle particle : particles) {
            particle.draw(g);
        }

        g.setColor(new Color(100, 100, 100, 210));
        g.setStroke(new BasicStroke(5));
        g.drawRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);
        g.setStroke(new BasicStroke(1));

        g.setColor(new Color(40, 40, 40, 150));
        g.setFont(new Font("Fira Code", Font.BOLD, 60));
        String s = "C R E D I T S";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), 50);

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(new Color(102, 51, 0));

        s = "Ethan B. (Cyrus) : Programmer / GDL";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int)(GamePanel.HEIGHT * 0.2));

        s = "Darielle C. : Programmer / Concept Originator";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int)(GamePanel.HEIGHT * 0.2) + 50);

        s = "Joy F. : Programmer / Concept Editor";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int)(GamePanel.HEIGHT * 0.2) + 100);

        for (GameButton button : options) {
            button.draw(g);
        }

        if (fadeInTimer >= 0) {
            g.setColor(new Color(255, 255, 255, 255 - alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        if (fadeOutTimer >= 0) {
            g.setColor(new Color(255, 255, 255, alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }

    private void select() {

        if (fadeOutTimer != -1)
            return;

        if (currentChoice == 0) {
            nextState = StateManager.MENU_STATE;
            fadeInTimer = -1;
            fadeOutTimer = 0;
        }
    }

    public void handleInput() {

        if (Mouse.isPressed()) {
            Random rand = new Random();
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 8, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
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
}
