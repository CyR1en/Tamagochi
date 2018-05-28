package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Handlers.Animation;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pet extends GameObject {
    private final int MAX_HEALTH = 100;
    private final int MAX_FULL = 100;
    private final int MAX_ENJOYMENT = 100;
    private final int INITIAL_EXP = 10;

    private final int DEFAULT = 0;
    private final int HAPPY = 1;
    private final int MAD = 2;

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

    public Pet(int health, int full, int enjoyment, int exp, int lvl) {
        this.health = health;
        this.full = full;
        this.enjoyment = enjoyment;
        this.exp = exp;
        this.lvl = lvl;
        maxexp = INITIAL_EXP * Math.pow(1.5, lvl - 1);
    }

    public void setAnimation(int mood) {
        BufferedImage fullImg = ImageUtil.loadBufferedImage("/assets/penbabydf.png");

        int width = 300;
        int height = 300;
        int row = 1;
        int col = 2;
        BufferedImage[] frames = new BufferedImage[row * col];
        if(mood == DEFAULT) {
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    frames[(i * col) + j] = fullImg.getSubimage(i * width, j * height, width, height);
                }
            }
        } else if(mood == HAPPY) {
            for(int i = 1; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    frames[(i * col) + j] = fullImg.getSubimage(i * width, j * height, width, height);
                }
            }
        } else if(mood == MAD) {
            for(int i = 0; i < row; i++) {
                for(int j = 0; j < col; j++) {
                    frames[(i * col) + j] = fullImg.getSubimage(i * width, j * height, width, height);
                }
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g, float interpolation) {

    }
}
