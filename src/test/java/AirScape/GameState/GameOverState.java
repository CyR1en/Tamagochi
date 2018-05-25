package AirScape.GameState;

import com.APCSFinal.AirScape.Handlers.BackGround;
import com.APCSFinal.AirScape.Handlers.StateManager;
import com.APCSFinal.AirScape.Main.Main;
import com.cyrus.CGDL.Entity.GameButton;
import com.cyrus.CGDL.GameState.GameState;
import com.cyrus.CGDL.Handlers.GameData;
import com.cyrus.CGDL.Handlers.GameStateManager;
import com.cyrus.CGDL.Handlers.Keys;
import com.cyrus.CGDL.Handlers.Mouse;
import com.cyrus.CGDL.Handlers.ParticleFactory;
import com.cyrus.CGDL.Main.GamePanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameOverState extends GameState {

    private int currentChoice = 0;
    private GameButton[] option;

    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;
    private int nextState;

    private BackGround bg;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        GameData.load();
        bg = new BackGround();
        bg.setVector(-.2, 0);
        option = new GameButton[2];
        option[0] = new GameButton(100, GamePanel.HEIGHT - 50);
        option[0].setText(" R e p l a y ", new Font("Fira Code Retina Medium", Font.BOLD, 20));
        option[0].setColor(GameButton.DEFAULT_COLOR);
        option[1] = new GameButton(GamePanel.WIDTH - 100, GamePanel.HEIGHT - 50);
        option[1].setText(" M e n u ", new Font("Fira Code Retina Medium", Font.BOLD, 20));
        option[1].setColor(GameButton.DEFAULT_COLOR);

        fadeInTimer = 0;
        fadeInDelay = 10;
        fadeOutTimer = -1;
        fadeOutDelay = 30;
    }

    public void update() {
        handleInput();
        bg.update();
        for (int i = 0; i < option.length; i++) {
            if (currentChoice == i)
                option[i].setHover(true);
            else
                option[i].setHover(false);
        }

        if (fadeInTimer >= 0) {
            fadeInTimer++;
            alpha = (int) (255.01 * fadeInTimer / fadeInDelay);
            if (fadeInTimer == fadeInDelay) {
                fadeInTimer = -1;
            }
        }

        if (fadeOutTimer >= 0) {
            fadeOutTimer++;
            alpha = (int) (255.01 * fadeOutTimer / fadeOutDelay);
            if (fadeOutTimer == fadeOutDelay) {
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
        bg.draw(g);
        g.setColor(new Color(30, 30, 30, 230));
        g.fillRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);

        g.setColor(new Color(30, 30, 30, 180));
        g.setFont(new Font("Fira Code", Font.BOLD, 50));
        String s = "G A M E O V E R";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 50);

        g.setColor(new Color(100, 100, 100, 210));
        g.setStroke(new BasicStroke(5));
        g.drawRect(GamePanel.WIDTH / 8, GamePanel.HEIGHT / 8, GamePanel.WIDTH * 3 / 4, GamePanel.HEIGHT * 3 / 4);
        g.setStroke(new BasicStroke(1));

        g.setFont(new Font("Tahoma", Font.PLAIN, 20));
        g.setColor(new Color(102, 51, 0));

        s = "= S C O R E =";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 200);

        s = Integer.toString(PlayingState.player.getScore());
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, 250);

        s = "= H I G H  S C O R E =";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, GamePanel.HEIGHT / 2);

        s = Integer.toString(GameData.getDataAt(0));
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - length / 2, GamePanel.HEIGHT / 2 + 50);

        g.setFont(new Font("Fira Code", Font.BOLD, 20));
        g.setColor(new Color(0, 100, 100));

        for (GameButton button : option) {
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
            nextState = StateManager.PLAYING_STATE;
            fadeInTimer = -1;
            fadeOutTimer = 0;
        } else if (currentChoice == 1) {
            nextState = StateManager.MENU_STATE;
            fadeInTimer = -1;
            fadeOutTimer = 0;
        }
    }

    public void handleInput() {

        if (Mouse.isPressed()) {
            ParticleFactory.createExplosion(Mouse.x, Mouse.y, Color.white);
            ParticleFactory.createSmallWave(Mouse.x, Mouse.y, 50);
            select();
        }

        if(Keys.isPressed(Keys.SPACE)) {
            currentChoice = 0;
            select();
        }

        boolean hit = false;
        for (int i = 0; i < option.length; i++) {
            if (option[i].isHovering(Mouse.x, Mouse.y)) {
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


