package main.system;

import java.awt.image.BufferedImage;

public interface ImageSetup {
    BufferedImage setup(String packageN, String imagePath);

    void getImages();
}
