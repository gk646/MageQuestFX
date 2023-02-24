package main.system.sound;

import gameworld.entities.ENTITY;
import gameworld.world.WorldController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.MainGame;
import main.system.enums.State;

import java.util.ArrayList;

public class Sound {
    public ArrayList<MediaPlayer> dungeonAmbient = new ArrayList<>(), forestAmbient = new ArrayList<>();
    public static float EFFECTS_VOLUME = 0.5f;
    public MediaPlayer INTRO;
    public MediaPlayer menu_switch;
    public MediaPlayer menu_back;
    private MediaPlayer chestSound;
    public static float AMBIENCE_VOLUME = 0.7f;
    private final double fadeDuration = 2;
    public MediaPlayer spikes;
    public MediaPlayer currentAmbient;
    private double waterVolume = 0.3f;
    private MediaPlayer lava;
    MainGame mg;
    public int currentTrackIndex = 0;
    private boolean fadeOut = false;
    public static Media energySphereBeginning;
    public static Media energySphereHit;
    private MediaPlayer equip, finishObjective;
    private boolean forestPlaying, dungeonPlaying;
    private MediaPlayer waterAmbience;

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
        equip = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/inventory/equip.wav").toString()));
        equip.setVolume(0.25);
        finishObjective = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/quest/finish_objective.wav").toString()));
        finishObjective.setVolume(0.4);
        INTRO = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/intro.wav").toString()));
        INTRO.setVolume(0.8);
        menu_switch = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu_switch.wav").toString()));
        menu_back = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/menu_back.wav").toString()));
        energySphereBeginning = new Media(getClass().getResource("/resources/sound/effects/projectiles/energySphere/fullsound.wav").toString());
        energySphereHit = new Media(getClass().getResource("/resources/sound/effects/projectiles/energySphere/hit.wav").toString());
        chestSound = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/environment/chestOpen.wav").toString()));
        chestSound.setVolume(0.7);
        spikes = new MediaPlayer(new Media(getClass().getResource("/resources/sound/effects/environment/spike.wav").toString()));
        loadAmbience();
    }

    public void playSpike() {
        spikes.seek(Duration.ZERO);
        spikes.play();
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
        addDungeonTrack("0");
        loadForestAmbience("0", 1);
        loadForestAmbience("1", 1);
        waterAmbience = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/waterAmbience/0.wav").toString()));
        waterAmbience.setVolume(waterVolume);
        lava = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/lava.wav").toString()));
        lava.setVolume(waterVolume);
    }

    private void addDungeonTrack(String name) {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/dungeonAmbience/" + name + ".wav").toString()));
        dungeonAmbient.add(ambientTrack1);
    }

    private void loadForestAmbience(String name, float volume) {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/forestAmbience/" + name + ".wav").toString()));
        ambientTrack1.setVolume(volume);
        forestAmbient.add(ambientTrack1);
    }

    public void update() {
        if (mg.gameState == State.PLAY || mg.gameState == State.OPTION) {
            updateZoneAmbience();
            updateProximityAmbience();
        } else {
            if (currentAmbient != null) {
                fadeOut(currentAmbient, AMBIENCE_VOLUME);
            }
            if (waterAmbience != null) {
                fadeOut(waterAmbience, AMBIENCE_VOLUME);
            }
            if (lava != null) {
                fadeOut(lava, AMBIENCE_VOLUME);
            }
        }
    }


    public void fadeIn(MediaPlayer mediaPlayer, double volume) {
        mediaPlayer.setVolume(0.0);
        mediaPlayer.play();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), 0)),
                new KeyFrame(Duration.seconds(fadeDuration), new KeyValue(mediaPlayer.volumeProperty(), volume))
        );
        fadeIn.play();
    }

    public void fadeOut(MediaPlayer mediaPlayer, double volume) {
        mediaPlayer.setVolume(volume);
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), volume)),
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

    private void updateZoneAmbience() {
        if (WorldController.currentWorld.isDungeon()) {
            dungeonPlaying = true;
            if (currentAmbient != null && forestPlaying && !fadeOut) {
                fadeOut(currentAmbient, AMBIENCE_VOLUME);
                forestPlaying = false;
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
                currentAmbient = dungeonAmbient.get(currentTrackIndex);
                fadeIn(currentAmbient, AMBIENCE_VOLUME);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(currentAmbient, AMBIENCE_VOLUME);
                }
            }
        } else if (mg.tileBase.isInOpen()) {
            forestPlaying = true;
            if (currentAmbient != null && dungeonPlaying && !fadeOut) {
                fadeOut(currentAmbient, AMBIENCE_VOLUME);
                dungeonPlaying = false;
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % forestAmbient.size();
                currentAmbient = forestAmbient.get(currentTrackIndex);
                System.out.println("hey");
                fadeIn(currentAmbient, AMBIENCE_VOLUME);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                System.out.println("here");
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(currentAmbient, AMBIENCE_VOLUME);
                }
            }
        } else if (currentAmbient != null) {
            fadeOut(currentAmbient, AMBIENCE_VOLUME);
        }
    }

    private void updateProximityAmbience() {
        if (mg.tileBase.isWaterNearby()) {
            if (waterAmbience.getStatus() != MediaPlayer.Status.PLAYING) {
                fadeIn(waterAmbience, waterVolume);
            } else if (waterAmbience.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                if (waterAmbience.getCurrentTime().toMillis() >= waterAmbience.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(currentAmbient, waterVolume);
                }
            }
        } else if (!fadeOut) {
            fadeOut(waterAmbience, waterVolume);
        }
        if (mg.tileBase.isLavaNearby()) {
            if (lava.getStatus() != MediaPlayer.Status.PLAYING) {
                fadeIn(lava, waterVolume);
            } else if (lava.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
                if (lava.getCurrentTime().toMillis() >= lava.getTotalDuration().toMillis() * 0.93f) {
                    fadeOut(lava, waterVolume);
                }
            }
        } else {
            fadeOut(lava, waterVolume);
        }
    }

    public void setVolumeMusic(float value) {
        INTRO.setVolume(0.8 * (value / 100.0f));
    }

    public void setVolumeEffects(float value) {
        finishObjective.setVolume(0.4 * (value / 100.0f));
        chestSound.setVolume(0.7 * (value / 100.0f));
        EFFECTS_VOLUME = 0.3f * (value / 100.0f);
        for (MediaPlayer player : mg.player.animation.getHitSound) {
            player.setVolume(0.8 * (value / 100.0f));
        }
        for (ENTITY entity : MainGame.ENTITIES) {
            if (entity.animation != null) {
                for (MediaPlayer player : entity.animation.sounds) {
                    player.setVolume(EFFECTS_VOLUME);
                }
            }
        }
    }

    public void setVolumeAmbience(float value) {
        for (MediaPlayer player : forestAmbient) {
            player.setVolume(AMBIENCE_VOLUME * (value / 100.0f));
        }
        for (MediaPlayer player : dungeonAmbient) {
            player.setVolume(AMBIENCE_VOLUME * (value / 100.0f));
        }
        AMBIENCE_VOLUME = 0.7f * (value / 100.0f);
        waterVolume = 0.3f * (value / 100.0f);
        waterAmbience.setVolume(waterVolume);
        lava.setVolume(waterVolume);
    }
}
