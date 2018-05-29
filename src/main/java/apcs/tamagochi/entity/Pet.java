package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Handlers.Animation;
import com.cyr1en.cgdl.Handlers.Mouse;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Pet extends GameObject implements Serializable {
    //Max variables
    private final int MAX_HEALTH = 100;
    private final int MAX_FULL = 100;
    private final int MAX_ENJOYMENT = 100;
    private final int INITIAL_EXP = 10;

    //Moods
    public static final int DEFAULT = 0;
    public static final int HAPPY = 1;
    public static final int MAD = 2;

    //Make animation transient because Animation class is not serializable
    private transient Animation animation;
    private transient ScheduledExecutorService scheduler;

    //health
    private double health;
    //full
    private int full;
    //enjoyment
    private int enjoyment;

    //experience. amount of exp to level-up increases by *1.5 every level.
    private int exp;
    private double maxexp;

    private int lvl; // peneggdf lv1 ~ lv3, peneggshake lv4, penbabydf lv5 ~ lv9, penadol lv10 ~

    //if pet is following the mouse
    private boolean follow;

    private boolean hatched;

    private boolean isDead;

    //initialize the pet based on level
    public Pet(int lvl) {
        this.health = 100;
        this.full = 10;
        this.enjoyment = 10;
        this.exp = 0;
        this.lvl = lvl;
        maxexp = INITIAL_EXP * Math.pow(1.5, lvl - 1);
        animation = new Animation();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            fullDecay();
            enjoymentDecay();
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void setAnimation(int mood) {
        BufferedImage fullImg;
        int row;
        int col;
        BufferedImage[] frames = new BufferedImage[0];
        if (lvl > 0 && lvl < 4) {
            width = 320;
            height = 300;
            row = 1;
            col = 2;
            fullImg = ImageUtil.loadBufferedImage("/assets/sprites/peneggdf.png");
            frames = new BufferedImage[row * col];
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        } else if (lvl == 4) {
            width = 320;
            height = 300;
            row = 1;
            col = 4;
            fullImg = ImageUtil.loadBufferedImage("/assets/sprites/peneggshake.png");
            frames = new BufferedImage[row * col];
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        } else if (lvl == 5 && !hatched) {
            width = 320;
            height = 400;
            row = 1;
            col = 13;
            frames = new BufferedImage[row * col];
            fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penhatch.png");
            for (int j = 0; j < col; j++) {
                frames[j] = fullImg.getSubimage(j * width, 0, width, height);
            }
        } else if (lvl > 4 && lvl < 10) {
            width = height = 300;
            row = 1;
            col = 2;
            frames = new BufferedImage[row * col];
            if (mood == DEFAULT) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabydf.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            } else if (mood == HAPPY) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabyhappy.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            } else if (mood == MAD) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabymad.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            }
        } else if (lvl > 9) {
            width = height = 300;
            row = 1;
            col = 2;
            frames = new BufferedImage[row * col];
            if (mood == DEFAULT) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabydf.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            } else if (mood == HAPPY) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabyhappy.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            } else if (mood == MAD) {
                fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabymad.png");
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
            }
        }
        animation.setFrames(frames);
        animation.setDelay(700);
    }

    public void feed(Food food) {
        System.out.println("feeding");
        if (!(full >= MAX_FULL)) {
            System.out.println("feeding 2");
            if (food.getQuality() < 20) {
                full += 10;
                exp += ((int) (Math.random() * 3) + 1) * Math.pow(1.5, lvl - 1);
                setAnimation(MAD);
            } else if (food.getQuality() > 20 && food.getQuality() < 70) {
                full += 20;
                exp += ((int) (Math.random() * 3) + 1) * Math.pow(1.5, lvl - 1);
                setAnimation(DEFAULT);
            } else {
                full += 30;
                exp += ((int) (Math.random() * 3) + 1) * Math.pow(1.5, lvl - 1);
                setAnimation(HAPPY);
            }
            if (exp >= maxexp) {
                exp = 0;
                lvl++;
                maxexp = INITIAL_EXP * Math.pow(1.5, lvl - 1);
            }
            if (full < MAX_FULL) {
                health++;
                if (health > MAX_HEALTH) {
                    health = MAX_HEALTH;
                }
            } else {
                full = MAX_FULL;
            }
        }
    }

    public void play() {
        System.out.println("feeding");
        if (!(enjoyment >= MAX_ENJOYMENT)) {
            enjoyment += ((int) (Math.random() * 20) + 1) * Math.pow(1.5, lvl - 1);
            exp += ((int) (Math.random() * 3) + 1) * Math.pow(1.5, lvl - 1);
            if (enjoyment < MAX_ENJOYMENT) {
                health++;
                if (health > MAX_HEALTH) {
                    health = MAX_HEALTH;
                }
            } else {
                enjoyment = MAX_ENJOYMENT;
            }
            if (exp >= maxexp) {
                exp = 0;
                lvl++;
                maxexp = INITIAL_EXP * Math.pow(1.5, lvl - 1);
            }
        }
    }


    public void fullDecay() {
        if (full < 1) {
            health = health - (int) (Math.random() * 25 + 1);
            if (health < 1) {
                health = 0;
                if (!isDead) {
                    BufferedImage fullImg = ImageUtil.loadBufferedImage("/assets/sprites/pendead.png");
                    width = height = 300;
                    int row = 1;
                    int col = 2;
                    BufferedImage[] frames = new BufferedImage[row * col];
                    for (int j = 0; j < col; j++) {
                        frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                    }
                    animation.setFrames(frames);
                    isDead = true;
                }
            }
        } else {
            full = full - (int) (Math.random() * 25 + 1);
            if (full < 0)
                full = 0;
        }
    }

    public void enjoymentDecay() {
        if (enjoyment < 1) {
            health = health - (int) (Math.random() * 25 + 1);
            if (health < 1) {
                health = 0;
                if (!isDead) {
                    BufferedImage fullImg = ImageUtil.loadBufferedImage("/assets/sprites/pendead.png");
                    width = height = 300;
                    int row = 1;
                    int col = 2;
                    BufferedImage[] frames = new BufferedImage[row * col];
                    for (int j = 0; j < col; j++) {
                        frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                    }
                    animation.setFrames(frames);
                    isDead = true;
                }
            }
        } else {
            enjoyment = enjoyment - (int) (Math.random() * 25 + 1);
            if (enjoyment < 0)
                enjoyment = 0;
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public void update() {
        animation.update();
        if (lvl == 5 && !hatched) {
            if (animation.getFrame() >= 12) {
                System.out.println("switching");
                BufferedImage fullImg = ImageUtil.loadBufferedImage("/assets/sprites/penbabydf.png");
                width = height = 300;
                int row = 1;
                int col = 2;
                BufferedImage[] frames = new BufferedImage[row * col];
                for (int j = 0; j < col; j++) {
                    frames[j] = fullImg.getSubimage(j * width, 0, width, height);
                }
                animation.setFrames(frames);
                hatched = true;
            }
        }
        lastX = x;
        lastY = y;
        if (follow && !isDead) {
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


    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public int getEnjoyment() {
        return enjoyment;
    }

    public void setEnjoyment(int enjoyment) {
        this.enjoyment = enjoyment;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public double getMaxexp() {
        return maxexp;
    }

    public void setMaxexp(double maxexp) {
        this.maxexp = maxexp;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public boolean isHatched() {
        return hatched;
    }

    public void setHatched(boolean hatched) {
        this.hatched = hatched;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    @Override
    public void draw(Graphics2D g, float interpolation) {
        BufferedImage img = animation.getImage();
        int interpolatedY = (int) ((this.y - lastY) * interpolation + this.lastY - height / 2);
        int interpolatedX = (int) ((this.x - lastX) * interpolation + this.lastX - width / 2);
        g.drawImage(img, interpolatedX, interpolatedY, img.getWidth(), img.getHeight(), null);
    }

    /**
     * Make our own readObject when de-serializing the save file because the Animation class is transient
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        animation = new Animation();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            fullDecay();
            enjoymentDecay();
        }, 0, 10, TimeUnit.SECONDS);
    }
}
