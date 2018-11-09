package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class SoundManager {
    private HashMap sound_map = new HashMap();
    private HashMap music_map = new HashMap();
    private Clip soundClip;
    private Clip musicClip;
    public void LoadSoundEffect(String id, String filename) {
        sound_map.put(id, filename);
    }
    public void PlaySoundEffect(String id) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        soundClip = AudioSystem.getClip();
        AudioInputStream ais = getAudioInputStream(new File(String.valueOf(sound_map.get(id))));
        soundClip.open(ais);
        soundClip.start();
        Thread.sleep(8);

    }

    public void LoadMusic(String id, String filename) {
        music_map.put(id, filename);
    }

    public void PlayMusic(String id) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        musicClip = AudioSystem.getClip();
        AudioInputStream ais = getAudioInputStream(new File(String.valueOf(music_map.get(id))));
        musicClip.open(ais);
        musicClip.start();
        while(true) {
            Thread.sleep(1000);
            musicClip.loop(-1);
        }
    }

    public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        SoundManager x = new SoundManager();
        x.LoadSoundEffect("game", "resources/hit.wav");
        x.PlaySoundEffect("game");
    }

}