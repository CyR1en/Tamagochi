package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Handlers.Animation;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pet extends GameObject {
    private final int MAX_HEALTH = 100;
    private final int MAX_FULL = 100;
    private final int MAX_ENJOYMENT = 100;
    private final int INITIAL_EXP = 10;

    public static final int DEFAULT = 0;
    public static final int HAPPY = 1;
    public static final int MAD = 2;

    private Animation animation;

    //health
    private int health;
    //full
    private int full;
    //enjoyment
    private int enjoyment;

    //experience. amount of exp to level-up increases by *1.5 every level.
    //
    private int exp;
    private double maxexp;
    private int lvl; // peneggdf lv1 ~ lv3, peneggshake lv4, penbabydf lv5 ~ lv9, penadol lv10 ~

    private boolean follow;


    public Pet(int lvl) {
        this.health = MAX_HEALTH * (lvl / 10);
        this.full = MAX_FULL * (lvl / 10);
        this.enjoyment = MAX_ENJOYMENT * (lvl / 10);
        this.exp = 0;
        this.lvl = lvl;
        maxexp = INITIAL_EXP * Math.pow(1.5, lvl - 1);
        animation = new Animation();
    }

    public void setAnimation(int mood) {
        BufferedImage fullImg;
        int width = 300;
        this.width = width;
        int height = 300;
        this.height = height;
        int row = 1;
        int col = 2;
        BufferedImage[] frames = new BufferedImage[row * col];
        if (mood == DEFAULT) {
            fullImg = ImageUtil.loadBufferedImage("/assets/penbabydf.png");
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        } else if (mood == HAPPY) {
            fullImg = ImageUtil.loadBufferedImage("/assets/penbabyhappy.png");
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        } else if (mood == MAD) {
            fullImg = ImageUtil.loadBufferedImage("/assets/penbabymad.png");
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        }
        animation.setFrames(frames);
        animation.setDelay(700);
    }

    public void feed(Food food) {
        if(food.getQuality() < 20) {
            setAnimation(MAD);
        } else if (food.getQuality() > 20 && food.getQuality() < 70) {
            setAnimation(DEFAULT);
        } else {
            setAnimation(HAPPY);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void update() {
        animation.update();
        if(follow) {
            if (x < Mouse.x) {
                x += 2;
            }
            if (x > Mouse.x) {
                x += -2;
            }
            if (Mouse.y < y) {
                y += -2;
                if (y < (GamePanel.HEIGHT / 2) - height + 180) {
                    y = (GamePanel.HEIGHT / 2) - height + 180;
                }
            }
            if (Mouse.y > y) {
                y += 2;
            }
        }
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }


    @Override
    public void draw(Graphics2D g, float interpolation) {
        BufferedImage img = animation.getImage();
        g.drawImage(img, (int) x - (img.getWidth() / 2), (int) y - (img.getHeight() / 2), img.getWidth(), img.getHeight(), null);
    }
}
