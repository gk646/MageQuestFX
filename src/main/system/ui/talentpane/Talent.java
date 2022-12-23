package main.system.ui.talentpane;

import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Talent {

    public int i_id;
    public String name, description;

    public String imagePath;
    public BufferedImage icon;

    public Talent(int i_id, String name, String type, String imagePath, String description) {
        this.i_id = i_id;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
    }


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(icon, x, y, slotSize, slotSize, null);
    }


    public BufferedImage setup(Utilities utilities, String imagePath) {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}

