package main.system.sound;

import gameworld.world.WorldController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class Sound {
    public static ArrayList<MediaPlayer> ambientTracks = new ArrayList<>();

    public MediaPlayer INTRO;
    public MediaPlayer menu_switch;
    public MediaPlayer menu_back;

    private final double fadeDuration = 3.0;
    private final double initialVolume = 1.0;
    public MediaPlayer currentDungeonAmbient;
    public int currentTrackIndex = 0;
    private boolean fadeOut, fadeIn;


    public Sound() {
    }

    public void playSwitchSound() {
        menu_switch.stop();
        menu_switch.seek(Duration.ZERO);
        menu_switch.play();
    }

    public void playBackSound() {
        menu_back.stop();
        menu_back.seek(Duration.ZERO);
        menu_back.play();
    }

    public void loadSounds() {
        INTRO = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/intro_new.wav").toString()));
        INTRO.setVolume(0.8);
        menu_switch = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu/menu_switch.wav").toString()));
        menu_back = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu/menu_back.wav").toString()));
        loadDungeonAmbience();
    }

    private void loadDungeonAmbience() {
        addDungeonTrack("3");
        addDungeonTrack("4");
    }

    private void addDungeonTrack(String name) {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/dungeonAmbience/" + name + ".wav").toString()));
        ambientTrack1.setVolume(0.7);
        ambientTracks.add(ambientTrack1);
    }

    public void update() {
        if (WorldController.currentWorld.isDungeon()) {
            if (currentDungeonAmbient == null || currentDungeonAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentDungeonAmbient = ambientTracks.get(currentTrackIndex);
                currentDungeonAmbient.setOnEndOfMedia(() -> {
                    currentTrackIndex = (currentTrackIndex + 1) % ambientTracks.size();
                    fadeOut(currentDungeonAmbient);
                    currentDungeonAmbient.setOnStopped(() -> {
                        currentDungeonAmbient = ambientTracks.get(currentTrackIndex);
                        fadeIn(currentDungeonAmbient);
                    });
                });
                fadeIn(currentDungeonAmbient);
            }
        } else if (!fadeOut) {
            if (currentDungeonAmbient != null) {
                fadeOut(currentDungeonAmbient);
                currentDungeonAmbient.setOnStopped(() -> {
                    currentDungeonAmbient = null;
                    currentTrackIndex = (currentTrackIndex + 1) % ambientTracks.size();
                    fadeOut = false;
                });
            }
        }
    }

    public void fadeIn(MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(0.0);
        mediaPlayer.play();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), 0)),
                new KeyFrame(Duration.seconds(fadeDuration), new KeyValue(mediaPlayer.volumeProperty(), initialVolume))
        );
        fadeIn.play();
    }

    public void fadeOut(MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(initialVolume);
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), initialVolume)),
                new KeyFrame(Duration.seconds(fadeDuration), new KeyValue(mediaPlayer.volumeProperty(), 0))
        );
        fadeOut.setOnFinished(event -> mediaPlayer.stop());
        fadeOut.play();
        this.fadeOut = true;
    }
}
