package apcs.tamagochi.enums;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public enum SFX {
    BUTTON_HOVER("/sounds/sfx/button-hover.wav"),
    BUTTON_CLICK("/sounds/sfx/button-click.wav"),
    POP("/sounds/sfx/pop.wav");

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    SFX(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getResource(soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and loadBufferedImage samples from the audio input stream.
            clip.open(audioInputStream);
            // Value controller for the clip's Gain.
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Reduce gain by 15 decibels
            volume.setValue(-15.f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing
    }

    // Optional static method to pre-loadBufferedImage all the sound files.
    public static void init() {
        values(); // calls the constructor for all the elements
    }
}
