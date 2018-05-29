package apcs.tamagochi.entity;

import com.cyr1en.cgdl.Entity.GameObject;
import com.cyr1en.cgdl.Handlers.Animation;
import com.cyr1en.cgdl.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LoadingIcon extends GameObject {

    private Animation animation;

    public LoadingIcon(int x, int y) {
        this.x = x;
        this.y = y;
        animation = new Animation();
        setAnimation();
        animation.setDelay(30);
    }

    public void setAnimation() {
        width = height = 64;
        int row = 1;
        int col = 96;
        BufferedImage fullImg = ImageUtil.loadBufferedImage("/assets/sprites/loading3.png");
        BufferedImage[] frames = new BufferedImage[row * col];
        for (int j = 0; j < col; j++) {
            frames[j] = fullImg.getSubimage(j * width, 0, width, height);
        }
        animation.setFrames(frames);
    }

    @Override
    public void update() {
        animation.update();
    }

    @Override
    public void draw(Graphics2D graphics2D, float v) {
        BufferedImage frame = animation.getImage();
        graphics2D.drawImage(frame, (int)(x - (width / 2)),(int)(y - (height / 2)), width - 150, height - 150, null);
    }
}
