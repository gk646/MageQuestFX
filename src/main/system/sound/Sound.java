/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.sound;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.world.WorldController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.MainGame;
import main.system.enums.State;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

public class Sound {
    private final ArrayList<MediaPlayer> dungeonAmbient = new ArrayList<>();
    private final ArrayList<MediaPlayer> forestAmbient = new ArrayList<>();
    public static final Media dialogBeep = new Media(Objects.requireNonNull(Sound.class.getResource("/resources/sound/effects/quest/speak.wav")).toString());

    public static float EFFECTS_VOLUME = 0.5f;
    private final ArrayList<MediaPlayer> effectSounds = new ArrayList<>();
    public MediaPlayer INTRO;
    public MediaPlayer Sonata;
    private MediaPlayer HillCrest;
    private static float AMBIENCE_VOLUME = 0.7f;
    private final double fadeDuration = 2;

    private MediaPlayer currentAmbient;
    private double waterVolume = 0.3f;
    private MediaPlayer lava;
    private MediaPlayer firePlace;
    private final MainGame mg;
    private int currentTrackIndex = 0;
    public static Media energySphereBeginning;
    public static Media energySphereHit;
    private boolean forestPlaying, dungeonPlaying;
    private MediaPlayer waterAmbience;
    public MediaPlayer BossMusic1;

    private final boolean[] dumbMediaPplayer = new boolean[10];

    public Sound(MainGame mg) {
        this.mg = mg;
    }


    public void loadSounds() {
        energySphereBeginning = new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/projectiles/energySphere/fullsound.wav")).toString());
        energySphereHit = new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/projectiles/energySphere/hit.wav")).toString());
        HillCrest = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/townAmbience/town1.mp3")).toString()));
        INTRO = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/intro.wav")).toString()));
        INTRO.setVolume(0.8);
        BossMusic1 = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/bossMusic/0.mp3")).toString()));
        Sonata = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/moonlight_sonata.wav")).toString()));
        Sonata.setVolume(0.3);
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
        //16
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/environment/lever.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/environment/puzzle_error.wav")).toString())));
        effectSounds.add(new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/effects/quest/complete.wav")).toString())));
        loadAmbience();
    }

    private void loadAmbience() {
        addDungeonTrack("3");
        addDungeonTrack("4");
        addDungeonTrack("0");
        addDungeonTrack("5");
        loadForestAmbience("0");
        loadForestAmbience("1");
        firePlace = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/resources/sound/music/fireplace.wav")).toString()));
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
                fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
            }
            if (waterAmbience != null) {
                fadeOut(waterAmbience, AMBIENCE_VOLUME, 1);
            }
            if (lava != null) {
                fadeOut(lava, AMBIENCE_VOLUME, 2);
            }
            if (firePlace != null) {
                fadeOut(firePlace, AMBIENCE_VOLUME, 3);
            }
            if (HillCrest.getStatus() != MediaPlayer.Status.STOPPED) {
                fadeOut(HillCrest, 0.7f, 4);
            }
        }
    }


    private void fadeIn(MediaPlayer mediaPlayer, double volume) {
        mediaPlayer.seek(Duration.ZERO);
        mediaPlayer.setVolume(0.0);
        mediaPlayer.play();
        Timeline fadeIn = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), 0)),
                new KeyFrame(Duration.seconds(fadeDuration), new KeyValue(mediaPlayer.volumeProperty(), volume))
        );
        fadeIn.play();
    }

    public void fadeOut(MediaPlayer mediaPlayer, double volume, int index) {
        if (!dumbMediaPplayer[index]) {
            dumbMediaPplayer[index] = true;
            Timeline fadeOut = new Timeline(
                    new KeyFrame(Duration.seconds(0), new KeyValue(mediaPlayer.volumeProperty(), volume)),
                    new KeyFrame(Duration.seconds(fadeDuration), new KeyValue(mediaPlayer.volumeProperty(), 0))
            );
            fadeOut.setOnFinished(event -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.stop();
                dumbMediaPplayer[index] = false;
            });
            fadeOut.play();
        }
    }

    private void updateZoneAmbience() {
        if (WorldController.currentWorld.isDungeon()) {
            dungeonPlaying = true;
            if (currentAmbient != null && forestPlaying) {
                fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
                forestPlaying = false;
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % dungeonAmbient.size();
                currentAmbient = dungeonAmbient.get(currentTrackIndex);
                fadeIn(currentAmbient, AMBIENCE_VOLUME);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING) {
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.95f) {
                    fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
                }
            }
        } else if (mg.tileBase.isInOpen()) {
            forestPlaying = true;
            if (currentAmbient != null && dungeonPlaying) {
                fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
                dungeonPlaying = false;
            } else if (currentAmbient == null || currentAmbient.getStatus() != MediaPlayer.Status.PLAYING) {
                currentTrackIndex = (currentTrackIndex + 1) % forestAmbient.size();
                currentAmbient = forestAmbient.get(currentTrackIndex);
                fadeIn(currentAmbient, AMBIENCE_VOLUME);
            } else if (currentAmbient != null && currentAmbient.getStatus() == MediaPlayer.Status.PLAYING) {
                if (currentAmbient.getCurrentTime().toMillis() >= currentAmbient.getTotalDuration().toMillis() * 0.95f) {
                    fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
                }
            }
        } else if (currentAmbient != null) {
            fadeOut(currentAmbient, AMBIENCE_VOLUME, 0);
        }
    }

    private void updateProximityAmbience() {
        if (mg.tileBase.isWaterNearby()) {
            if (waterAmbience.getStatus() != MediaPlayer.Status.PLAYING) {
                fadeIn(waterAmbience, waterVolume);
            } else if (waterAmbience.getStatus() == MediaPlayer.Status.PLAYING) {
                if (waterAmbience.getCurrentTime().toMillis() >= waterAmbience.getTotalDuration().toMillis() * 0.95f) {
                    fadeOut(currentAmbient, waterVolume, 1);
                }
            }
        } else if (waterAmbience.getStatus() == MediaPlayer.Status.PLAYING) {
            fadeOut(waterAmbience, waterVolume, 1);
        }
        if (mg.tileBase.isLavaNearby()) {
            if (lava.getStatus() != MediaPlayer.Status.PLAYING) {
                fadeIn(lava, waterVolume);
            } else if (lava.getStatus() == MediaPlayer.Status.PLAYING) {
                if (lava.getCurrentTime().toMillis() >= lava.getTotalDuration().toMillis() * 0.95f) {
                    fadeOut(lava, waterVolume, 2);
                }
            }
        } else if (lava.getStatus() == MediaPlayer.Status.PLAYING) {
            fadeOut(lava, waterVolume, 2);
        }
        if (mg.tileBase.isFireNearby()) {
            if (firePlace.getStatus() != MediaPlayer.Status.PLAYING) {
                fadeIn(firePlace, waterVolume + 0.2);
            } else if (firePlace.getStatus() == MediaPlayer.Status.PLAYING && firePlace.getCurrentTime().toMillis() >= firePlace.getTotalDuration().toMillis() * 0.95f) {
                fadeOut(firePlace, waterVolume + 0.2, 3);
            }
        } else {
            fadeOut(firePlace, waterVolume + 0.2, 3);
        }

        if (WorldController.currentWorld == Zone.Hillcrest) {
            if (playerInsideRectangle(new Point(21, 4), new Point(56, 34))) {
                if (HillCrest.getStatus() != MediaPlayer.Status.PLAYING) {
                    fadeIn(HillCrest, 0.7);
                } else if (HillCrest.getStatus() == MediaPlayer.Status.PLAYING && HillCrest.getCurrentTime().toMillis() >= HillCrest.getTotalDuration().toMillis() * 0.95f) {
                    fadeOut(HillCrest, 0.7, 4);
                }
            }
        } else if (HillCrest.getStatus() == MediaPlayer.Status.PLAYING) {
            fadeOut(HillCrest, 0.7, 4);
        }
    }


    public void setVolumeMusic(float value) {
        INTRO.setVolume(0.8 * (value / 100.0f));
        HillCrest.setVolume(0.8 * (value / 100.0f));
        BossMusic1.setVolume(1 * (value / 100.0f));
        Sonata.setVolume(1 * (value / 100.0f));
    }

    public void setVolumeEffects(float value) {
        EFFECTS_VOLUME = 0.3f * (value / 100.0f);
        for (MediaPlayer player : mg.statusMessage.animation.getHitSound) {
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
        firePlace.setVolume(waterVolume);
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

    private boolean playerInsideRectangle(Point p1, Point p2) {
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        return mg.playerX >= x1 && mg.playerX <= x2 && mg.playerY >= y1 && mg.playerY <= y2;
    }
}
