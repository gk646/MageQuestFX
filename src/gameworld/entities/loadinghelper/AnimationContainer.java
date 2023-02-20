package gameworld.entities.loadinghelper;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.ArrayList;

public class AnimationContainer {
    public ArrayList<Image> attack1 = new ArrayList<>();
    public ArrayList<Image> attack2 = new ArrayList<>();
    public ArrayList<Image> attack3 = new ArrayList<>();
    public ArrayList<Image> idle = new ArrayList<>();
    public ArrayList<Image> walk = new ArrayList<>();
    public ArrayList<Image> WalkingLeft = new ArrayList<>();
    String name;

    public AnimationContainer(String entityName) {
        this.name = entityName;
    }

    public void loadImages() {
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
}

