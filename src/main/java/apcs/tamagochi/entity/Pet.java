package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameObject;

import java.awt.*;

public class Pet extends GameObject {
    //health
    private int health;
    private int maxHealth; //100

    //full
    private int full;
    private int maxFull; //100

    //enjoyment
    private int enjoyment;
    private int maxEnjoyment; //100

    //expperience. amount of exp to level-up increases by *1.5 every level.
    //
    private int exp;
    private int lvl; // peneggdf lv1 ~ lv3, peneggshake lv4, penbabydf lv5 ~ lv9, penadol lv10 ~




    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g, float interpolation) {

    }
}
