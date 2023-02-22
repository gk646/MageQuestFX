package main.system.sound;

import gameworld.world.WorldController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.MainGame;
import main.system.ui.inventory.UI_InventoryPanel;

import java.util.ArrayList;

public class Sound {
    private final double initialVolume = 0.6;
    public ArrayList<MediaPlayer> dungeonAmbient = new ArrayList<>(), forestAmbient = new ArrayList<>();
    public MediaPlayer INTRO;
    public MediaPlayer menu_switch;
    public MediaPlayer menu_back;
    private MediaPlayer chestSound;

    private final double fadeDuration = 3.0;
    public MediaPlayer currentDungeonAmbient, currentAmbient;
    MainGame mg;
    public int currentTrackIndex = 0;
    private boolean fadeOut, fadeIn;
    public static Media energySphereBeginning;
    public static Media energySphereHit;
    private MediaPlayer equip, finishObjective;
    private boolean forestPlaying, dungeonPlaying;

    public Sound(MainGame mg) {
        this.mg = mg;
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
        equip = new MediaPlayer(new Media(UI_InventoryPanel.class.getResource("/resources/sound/effects/inventory/equip.wav").toString()));
        equip.setVolume(0.25);
        finishObjective = new MediaPlayer(new Media(UI_InventoryPanel.class.getResource("/resources/sound/effects/quest/finish_objective.wav").toString()));
        finishObjective.setVolume(0.4);
        INTRO = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/intro_new.wav").toString()));
        INTRO.setVolume(0.8);
        menu_switch = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu_switch.wav").toString()));
        menu_back = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu_back.wav").toString()));
        energySphereBeginning = new Media(getClass().getResource("/resources/sound/effects/projectiles/energySphere/fullsound.wav").toString());
        energySphereHit = new Media(getClass().getResource("/resources/sound/effects/projectiles/energySphere/hit.wav").toString());
        chestSound = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/environment/chestOpen.wav").toString()));
        loadAmbience();
    }

    public void playChestOpen() {
        chestSound.seek(Duration.ZERO);
        chestSound.play();
    }

    public void playEquip() {
        equip.seek(Duration.ZERO);
        equip.play();
    }

    public void playFinishObjective() {
        finishObjective.seek(Duration.ZERO);
        finishObjective.play();
    }

    private void loadAmbience() {
        addDungeonTrack("3");
        addDungeonTrack("4");

        loadForestAmbience("0", 0.2f);
        loadForestAmbience("1", 0.1f);
    }

    private void addDungeonTrack(String name) {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/dungeonAmbience/" + name + ".wav").toString()));
        ambientTrack1.setVolume(0.2);
        dungeonAmbient.add(ambientTrack1);
    }

    private void loadForestAmbience(String name, float volume) {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/forestAmbience/" + name + ".wav").toString()));
        ambientTrack1.setVolume(volume);
        ambientTrack1.seek(Duration.seconds(25));
        forestAmbient.add(ambientTrack1);
    }

    public void update() {
        if (WorldController.currentWorld.isDungeon()) {
            dungeonPlaying = true;
            if (currentAmbient != null && forestPlaying && !fadeOut) {
                fadeOut(currentAmbient);
                forestPlaying = false;
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
                currentAmbient = dungeonAmbient.get(currentTrackIndex);
                fadeIn(currentAmbient);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                System.out.println(currentAmbient.getCurrentTime().toMillis() / currentAmbient.getTotalDuration().toMillis());
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(currentAmbient);
                }
            }
        } else if (WorldController.currentWorld.isForest()) {
            forestPlaying = true;
            if (currentAmbient != null && dungeonPlaying && !fadeOut) {
                fadeOut(currentAmbient);
                dungeonPlaying = false;
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % forestAmbient.size();
                currentAmbient = forestAmbient.get(currentTrackIndex);
                fadeIn(currentAmbient);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                System.out.println(currentAmbient.getCurrentTime().toMillis() / currentAmbient.getTotalDuration().toMillis());
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(currentAmbient);
                }
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
        fadeOut.setOnFinished(event -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.stop();
            this.fadeOut = false;
        });
        fadeOut.play();
        this.fadeOut = true;
    }
}
