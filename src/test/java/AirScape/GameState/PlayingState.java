package AirScape.GameState;

import com.APCSFinal.AirScape.Entity.Planes;
import com.APCSFinal.AirScape.Entity.Player;
import com.APCSFinal.AirScape.Entity.Trees;
import com.APCSFinal.AirScape.Handlers.BackGround;
import com.APCSFinal.AirScape.Handlers.ColumnSpawner;
import com.APCSFinal.AirScape.Handlers.StateManager;
import com.APCSFinal.AirScape.Main.Main;
import com.cyrus.CGDL.GameState.GameState;
import com.cyrus.CGDL.Handlers.GameData;
import com.cyrus.CGDL.Handlers.GameStateManager;
import com.cyrus.CGDL.Handlers.Keys;
import com.cyrus.CGDL.Handlers.Mouse;
import com.cyrus.CGDL.Main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

import static com.cyrus.CGDL.Handlers.Mouse.x;

/**
 * this is the state when the user can actually start playing
 *
 * all the states are similar. the only difference is the variables
 */
public class PlayingState extends GameState {

    //entities
    private ArrayList<Trees> trees;
    private ArrayList<Planes> planes;
    public static Player player;

    //background
    private BackGround bg;

    //fades
    private int fadeInTimer;
    private int fadeInDelay;
    private int fadeOutTimer;
    private int fadeOutDelay;
    private int alpha;

    //playing state logic
    private boolean gameOver;
    private boolean initial;

    //next state after the this playing state
    private int nextState;

    //PlayingState constructor
    public PlayingState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    @Override
    public void init() {
        //initialize game data, because we will be saving scores
        GameData.init();

        //initialize entities
        player = new Player();
        trees = new ArrayList<>();
        planes = new ArrayList<>();

        //initialize the column spawner
        ColumnSpawner.init(trees, planes, 2000);

        //initialize the background
        bg = new BackGround();
        bg.setVector(-1, 0);

        //initialize pre and post state fade variables
        fadeInTimer = 0;
        fadeInDelay = 30;
        fadeOutTimer = -1;
        fadeOutDelay = 10;
        gameOver = false;
        initial = true;
    }

    //sets the fade of the state
    private void setFade() {
        if (fadeInTimer >= 0) {
            fadeInTimer++;
            alpha = (int) (255.1 * fadeInTimer / fadeInDelay);
            if (fadeInTimer == fadeInDelay) {
                fadeInTimer = -1;
            }
        }
        if (fadeOutTimer >= 0) {
            fadeOutTimer++;
            alpha = (int) (255.1 * fadeOutTimer / fadeOutDelay);
            if (fadeOutTimer == fadeOutDelay) {
                gsm.setState(nextState);
            }
        }
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
    }

    //draw the fade
    private void drawFade(Graphics2D g) {
        if (fadeInTimer >= 0) {
            g.setColor(new Color(255, 255, 255, 255 - alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
        if (fadeOutTimer >= 0) {
            g.setColor(new Color(255, 255, 255, alpha));
            g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        }
    }

    //update all the variables that;s used in this class
    @Override
    public void update() {
        handleInput(); // handles the input

        /**
         * if the playing state is on the initial state
         * only update the back ground and the player
         */
        if (initial) {
            player.setVector(0, 0); // set vector of the player to be 0,0 so that it wouldn't move
            player.update(); // update the player
            bg.update(); // update the back ground
        } else {
            player.setInitial(false); // set the player initial state to false

            //if the game is not over, update all the object that's used in the playing state
            if (!gameOver) {
                bg.update(); //update the background
                player.update(); //update the player

                //if the player dies, handle the game over
                if (player.isCrashed())
                    handleGameOver();
                if (player.isOutOfBounds())
                    handleGameOver();

                // spawn column a column
                ColumnSpawner.spawnColumn();

                // loop that updates all the trees in the screen
                for (int i = 0; i < trees.size(); i++) {
                    boolean remove = trees.get(i).update(); // if tree is outside the frame, remove = true
                    if (trees.get(i).collided(player)) //if any of the trees collided with the player, then handle the game over
                        handleGameOver();
                    if (trees.get(i).getX() <= player.getX() - 58 && !trees.get(i).isPassed()) //if you pass a tree add score
                        player.addScore();
                    if (remove) { // if remove is true then remove the tree at the current index
                        trees.remove(i);
                        i--;
                    }
                }

                // this loop is the same as the ones for the trees
                for (int i = 0; i < planes.size(); i++) {
                    boolean remove = planes.get(i).update();
                    if (planes.get(i).collided(player)) {
                        player.Crashed(true);
                        handleGameOver();
                    } else
                        player.Crashed(false);
                    if (remove) {
                        planes.remove(i);
                        i--;
                    }
                }
            }
        }
        //update the title bar if the showTitleInfo is enabled
        if (Main.frame.isShowTitleInfo())
            Main.frame.updateTitleBar();
        else
            Main.frame.clearTitleBar();
        //update the fade for the current state
        setFade();
    }

    //draw the updated properties of each object inside this State
    @Override
    public void draw(Graphics2D g) {

        //the first layer of back ground
        g.setColor(new Color(104, 244, 255, 170));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        //the visible background
        bg.draw(g);

        //draws the player
        player.draw(g);

        //draws all the trees in the Array List
        for (Trees t : trees)
            t.draw(g);

        //draws all the planes in the Array List
        for (Planes p : planes)
            p.draw(g);

        //draws the score
        g.setFont(new Font("Comic Sans", Font.BOLD, 60)); //set the graphics to a new font
        g.setColor(new Color(40, 40, 40, 140)); //set the graphics to a new color
        String s = String.format("%d", player.getScore()); // get the string from the player score
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth(); //get the length of the string with the current font
        g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), GamePanel.HEIGHT / 8); //draw the string in the center

        //if it's the initial state then. print "Press or Hold 'Space'to Start"
        if (initial) {
            g.setFont(new Font("Fira Code", Font.BOLD, 20));
            g.setColor(new Color(40, 40, 40, 255));
            s = "Press or Hold \"Space\" to Start";
            length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, GamePanel.WIDTH / 2 - (length / 2), (int) (GamePanel.HEIGHT * 0.85));
            g.drawLine(GamePanel.WIDTH / 2 - (length / 2), (int) (GamePanel.HEIGHT * 0.85) + 10,
                    GamePanel.WIDTH / 2 + (length / 2), (int) (GamePanel.HEIGHT * 0.85) + 10);
        }


        //debugs
        g.setColor(Color.BLACK);
        g.setFont(new Font("Source Code Pro", Font.PLAIN, 15));
        g.drawString("gameRes: " + GamePanel.WIDTH + "x" + GamePanel.HEIGHT, 0, 15);
        g.drawString("collision: " + player.isCrashed(), 0, 30);
        g.drawString("TreeList: " + trees.size(), 0, 45);
        g.drawString("PlaneList: " + planes.size(), 0, 60);
        g.drawString("Game Over: " + gameOver, 0, 75);
        g.drawString("FPS: " + GamePanel.currentFPS, 0, 90);
        drawFade(g);
    }

    //handles the game when the player crash
    private void handleGameOver() {
        if (!gameOver) {
            GameData.setSaveInfo(0, player.getScore()); //set the data to be whatever the score of the player is
            GameData.save(); //then save it
            gameOver = true;
            nextState = StateManager.GAME_OVER_STATE;
            fadeInTimer = -1;
            fadeOutTimer = 0;
            System.out.println("=+ GAME OVER +=");
        }
    }

    //handles the input
    @Override
    public void handleInput() {

        player.setUp(Keys.keyState[Keys.SPACE] || Mouse.isDown());
        if (Keys.isPressed(Keys.SPACE) || Mouse.isPressed())
            initial = false;

    }

}
