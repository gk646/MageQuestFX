package main.system.sound;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.player.ProjectilePreloader;
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
import java.util.Objects;

public class Sound {
    public final ArrayList<MediaPlayer> dungeonAmbient = new ArrayList<>();
    public final ArrayList<MediaPlayer> forestAmbient = new ArrayList<>();
    public static final Media dialogBeep = new Media(Objects.requireNonNull(Sound.class.getResource("/resources/sound/effects/quest/speak.wav")).toString());

    public static float EFFECTS_VOLUME = 0.5f;
    public final ArrayList<MediaPlayer> effectSounds = new ArrayList<>();
    public MediaPlayer INTRO;

    public static float AMBIENCE_VOLUME = 0.7f;
    private final double fadeDuration = 2;

    public MediaPlayer currentAmbient;
    private double waterVolume = 0.3f;
    private MediaPlayer lava;
    final MainGame mg;
    public int currentTrackIndex = 0;
    private boolean fadeOut = false;
    public static Media energySphereBeginning;
    public static Media energySphereHit;
    private boolean forestPlaying, dungeonPlaying;
    private MediaPlayer waterAmbience;

    public Sound(MainGame mg) {
        this.mg = mg;
    }


    public void loadSounds() {
        energySphereBeginning = new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/projectiles/energySphere/fullsound.wav")).toString());
        energySphereHit = new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/projectiles/energySphere/hit.wav")).toString());

        INTRO = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/intro.wav")).toString()));
        INTRO.setVolume(0.8);

        //EFFECTS
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/equip.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/quest/finish_objective.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/menu_switch.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/menu_back.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/environment/chestOpen.wav")).toString())));
        //5
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/sell.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/environment/spike.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/buy.wav")).toString())));
        //8
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/openInventory.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/pickupGold.wav")).toString())));
        //10
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/readBook.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/zipTabs.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/inventory/closeInventory.wav")).toString())));
        //13
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/environment/epicChestOpen.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/levelup/getTalent.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/quest/interact.wav")).toString())));


        loadAmbience();
    }

    private void loadAmbience() {
        addDungeonTrack("3");
        addDungeonTrack("4");
        addDungeonTrack("0");
        addDungeonTrack("5");
        loadForestAmbience("0");
        loadForestAmbience("1");
        waterAmbience = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/waterAmbience/0.wav")).toString()));
        waterAmbience.setVolume(waterVolume);
        lava = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/lava.mp3")).toString()));
        lava.setVolume(waterVolume);
    }

    private void addDungeonTrack(String name) {
        dungeonAmbient.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/dungeonAmbience/" + name + ".mp3")).toString())));
    }

    private void loadForestAmbience(String name) {
        forestAmbient.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/forestAmbience/" + name + ".wav")).toString())));
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
                fadeIn(currentAmbient, AMBIENCE_VOLUME);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING && !fadeOut) {
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
        EFFECTS_VOLUME = 0.3f * (value / 100.0f);
        for (MediaPlayer player : mg.player.animation.getHitSound) {
            player.setVolume(0.8 * (value / 100.0f));
        }
        for (MediaPlayer player : effectSounds) {
            player.setVolume(1 * (value / 100.0f));
        }
        for (ENTITY entity : mg.ENTITIES) {
            if (entity.animation != null) {
                for (MediaPlayer player : entity.animation.sounds) {
                    player.setVolume(EFFECTS_VOLUME);
                }
            }
        }
        for (GeneralResourceLoader load : ProjectilePreloader.projectileSounds) {
            for (MediaPlayer player : load.sounds) {
                player.setVolume(1 * (value / 100.0f));
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

    public void playEffectSound(int index) {
        effectSounds.get(index).seek(Duration.ZERO);
        effectSounds.get(index).play();
    }

    public void playNewEffectSound(int index) {
        MediaPlayer mediaPlayer = new MediaPlayer(effectSounds.get(index).getMedia());
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }
}
