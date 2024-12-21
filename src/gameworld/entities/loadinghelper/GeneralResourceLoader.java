/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
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

package gameworld.entities.loadinghelper;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.system.sound.Sound;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GeneralResourceLoader {

    public final ArrayList<Image> images1 = new ArrayList<>();
    private final ArrayList<Image> images2 = new ArrayList<>();
    public ArrayList<Image> images3 = new ArrayList<>();

    public final ArrayList<MediaPlayer> getHitSound = new ArrayList<>();
    public final ArrayList<MediaPlayer> sounds = new ArrayList<>();
    private final String name;
    public ArrayList<MediaPlayer> attackSound = new ArrayList<>();

    public GeneralResourceLoader(String path) {
        this.name = path;
        loadImages1(path);
    }

    private void loadImages1(String path) {
        InputStream is;
        for (int i = 0; i < 50; i++) {
            is = getClass().getResourceAsStream("/resources/" + path + "/" + i + ".png");
            if (is != null) {
                images1.add(new Image(is));
            } else {
                break;
            }
        }
    }

    public void loadImages2(String path) {
        InputStream is;
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/" + path + "/" + i + ".png");
            if (is != null) {
                images2.add(new Image(is));
            } else {
                break;
            }
        }
    }

    public void loadProjectilesSounds() {
        for (int i = 0; i < 10; i++) {
            URL url = getClass().getResource("/resources/sound/effects/" + name + "/" + i + ".wav");
            if (url != null) {
                MediaPlayer mediaPlayer = new MediaPlayer(new Media(url.toString()));
                mediaPlayer.setVolume(Sound.EFFECTS_VOLUME);
                sounds.add(mediaPlayer);
            } else {
                break;
            }
        }
    }

    public void loadSound(String path, String name) {
        for (int i = 0; i < 5; i++) {
            URL url = getClass().getResource("/resources/sound/effects/" + path + "/" + name + ".wav");
            if (url != null) {
                getHitSound.add(new MediaPlayer(new Media(url.toString())));
            } else {
                break;
            }
        }
        for (MediaPlayer mediaPlayer : getHitSound) {
            mediaPlayer.setVolume(0.3);
        }
    }

    public void playSoundFromSounds(int index) {
        sounds.get(index).seek(Duration.ZERO);
        sounds.get(index).play();
    }

    public void playSound(int index) {
        getHitSound.get(index).seek(Duration.ZERO);
        getHitSound.get(index).play();
    }
}

