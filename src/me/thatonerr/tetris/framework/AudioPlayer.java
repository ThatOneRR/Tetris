package me.thatonerr.tetris.framework;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {

    private String filePath;
    private Clip clip;
    private int position;

    public AudioPlayer(String filePath) {
        this.filePath = filePath;
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(AudioPlayer.class.getResourceAsStream(filePath));
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void pause() {
        position = clip.getFramePosition();
        clip.stop();
    }

    public void resume() {
        clip.setFramePosition(position);
        clip.start();
    }

    public void stop() {
        clip.stop();
        clip.close();
    }

    public void reset() {
        stop();
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(AudioPlayer.class.getResourceAsStream(filePath));
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }

}
