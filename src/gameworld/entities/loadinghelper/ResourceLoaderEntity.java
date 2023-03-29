package gameworld.entities.loadinghelper;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.system.sound.Sound;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class ResourceLoaderEntity {
    public final ArrayList<Image> attack1 = new ArrayList<>();
    public final ArrayList<Image> attack2 = new ArrayList<>();
    public final ArrayList<Image> attack3 = new ArrayList<>();
    public final ArrayList<Image> special = new ArrayList<>();
    public ArrayList<Image> attack4 = new ArrayList<>();
    public final ArrayList<Image> idle = new ArrayList<>();

    public final ArrayList<Image> dead = new ArrayList<>();
    private final ArrayList<Image> hurt = new ArrayList<>();
    public final ArrayList<Image> walk = new ArrayList<>();
    public final ArrayList<Image> run = new ArrayList<>();
    public ArrayList<Image> attack5 = new ArrayList<>();

    public ArrayList<Image> idle2 = new ArrayList<>();
    public ArrayList<Image> runMirror = new ArrayList<>();
    public final ArrayList<MediaPlayer> sounds = new ArrayList<>();
    private final ArrayList<MediaPlayer> sounds2 = new ArrayList<>();
    private final String name;

    public ResourceLoaderEntity(String entityName) {
        this.name = entityName;
        load();
    }

    public void load() {
        loadImages();
        loadSounds();
    }

    private void loadImages() {
        InputStream is;
        String folderName;
        folderName = "attack1";
        for (int i = 0; i < 25; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack1.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack2";
        for (int i = 0; i < 25; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack2.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack3";
        for (int i = 0; i < 25; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack3.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack4";
        for (int i = 0; i < 25; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack4.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "idle";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                idle.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "walk";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                walk.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "death";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                dead.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "hurt";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                hurt.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "run";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                run.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "runMirror";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                runMirror.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "idle2";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                idle2.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "special";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                special.add(new Image(is));
            } else {
                break;
            }
        }
        folderName = "attack5";
        for (int i = 0; i < 15; i++) {
            is = getClass().getResourceAsStream("/resources/Entitys/" + name + "/" + folderName + "/" + i + ".png");
            if (is != null) {
                attack5.add(new Image(is));
            } else {
                break;
            }
        }
    }

    private void loadSounds() {
        String name;
        if (this.name.contains("enemies/")) {
            name = this.name.replace("enemies/", "");
        } else {
            name = this.name;
        }
        for (int i = 0; i < 10; i++) {
            URL url = getClass().getResource("/resources/sound/effects/entities/" + name + "/" + i + ".wav");
            if (url != null) {
                sounds.add(new MediaPlayer(new Media(url.toString())));
                sounds2.add(new MediaPlayer(new Media(url.toString())));
            } else {
                break;
            }
        }
        for (MediaPlayer mediaPlayer : sounds) {
            mediaPlayer.setVolume(Sound.EFFECTS_VOLUME);
        }
    }

    public void playRandomSoundFromXToIndex(int startIndex, int hitSoundLimit) {
        int num = Math.max(startIndex, (int) (Math.random() * hitSoundLimit));
        if (sounds.get(num).getStatus() != MediaPlayer.Status.PLAYING) {
            sounds.get(num).play();
        } else {
            if (sounds2.get(num).getStatus() != MediaPlayer.Status.PLAYING) {
                sounds2.get(num).play();
            } else {
                sounds2.get(num).seek(Duration.ZERO);
                sounds2.get(num).play();
            }
        }
    }
}

