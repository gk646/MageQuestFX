package gameworld.entities.loadinghelper;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ResourceLoaderEntity {
    public ArrayList<Image> attack1 = new ArrayList<>();
    public ArrayList<Image> attack2 = new ArrayList<>();
    public ArrayList<Image> attack3 = new ArrayList<>();
    public ArrayList<Image> idle = new ArrayList<>();
    public ArrayList<Image> walk = new ArrayList<>();
    public ArrayList<Image> WalkingLeft = new ArrayList<>();

    public ArrayList<MediaPlayer> getHitSound = new ArrayList<>(), randomNoise = new ArrayList<>(), attackSound = new ArrayList<>();
    String name;

    public ResourceLoaderEntity(String entityName) {
        this.name = entityName;
    }

    public void load() {
        loadImages();
        loadSounds();
    }

    private void loadImages() {
        InputStream is;
        String folderName;
        folderName = "attack1";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/enemies/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack1.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack2";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/enemies/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack2.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack3";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/enemies/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack3.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "idle";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/enemies/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                idle.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "walk";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/enemies/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                walk.add(new Image(is));
            } else {
                break;
            }
        }
    }

    private void loadSounds() {
        String dataName;
        dataName = "getHit";
        for (int i = 0; i < 5; i++) {
            URL url = getClass().getResource("/resources/sound/effects/entities/" + name + "/" + dataName + i + ".wav");
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

    public void playGetHitSound() {
        int num = (int) (Math.random() * getHitSound.size());
        getHitSound.get(num).seek(Duration.ZERO);
        getHitSound.get(num).play();
    }
}

