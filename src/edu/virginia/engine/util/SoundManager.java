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
    private Clip myClip;
    public void LoadSoundEffect(String id, String filename) {
        sound_map.put(id, filename);
    }
    public void PlaySoundEffect(String id) {
        Media hit = new Media(new File(String.valueOf(sound_map.get(id))).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

    }

    public void LoadMusic(String id, String filename) {
        music_map.put(id, filename);
    }

    public void PlayMusic(String id) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream mus = getAudioInputStream(new File(String.valueOf(music_map.get(id))));
        myClip.open(mus);
        myClip.start();
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}