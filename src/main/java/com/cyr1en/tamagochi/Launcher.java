package com.cyr1en.tamagochi;

import com.cyr1en.cgdl.Handlers.ImageLoader;
import com.cyr1en.cgdl.Main.GameFrame;
import com.cyr1en.cgdl.Main.GamePanel;
import com.cyr1en.tamagochi.handler.StateManager;

public class Launcher {
    public static void main(String[] args) {
        GamePanel.initPanel(500, 700);
        GameFrame gameFrame = new GameFrame("Tomogucci", new StateManager(), 200, 60);
        gameFrame.setShowTitleInfo(true);
        gameFrame.setIconImage(ImageLoader.load("/assets/cgdl-frame-logo.png"));
    }
}
