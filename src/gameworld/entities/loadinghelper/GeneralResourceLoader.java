package gameworld.entities.loadinghelper;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GeneralResourceLoader {

    public ArrayList<Image> images1 = new ArrayList<>();
    public ArrayList<Image> images2 = new ArrayList<>();
    public ArrayList<Image> images3 = new ArrayList<>();

    public ArrayList<MediaPlayer> getHitSound = new ArrayList<>(), randomNoise = new ArrayList<>(), attackSound = new ArrayList<>();
    String name;

    public GeneralResourceLoader(String path) {
        this.name = path;
    }

    public void loadImages1(String path) {
        InputStream is;
        for (int i = 0; i < 20; i++) {
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

    public void playSound(int index) {
        getHitSound.get(index).seek(Duration.ZERO);
        getHitSound.get(index).play();
    }
}

