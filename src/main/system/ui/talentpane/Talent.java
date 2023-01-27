package main.system.ui.talentpane;

import main.system.ImageSetup;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class Talent {

    private final int i_id;
    private final String name;
    private final String description;

    public String imagePath;
    private final BufferedImage icon;

    public Talent(int i_id, String name, String imagePath, String description, int sizeX, int sizeY) {
        this.i_id = i_id;
        this.name = name;
        this.icon = setup(imagePath, sizeX, sizeY);
        this.description = description;
    }


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(icon, x, y, slotSize, slotSize, null);
    }


    private BufferedImage setup(String imagePath, int sizeX, int sizeY) {
        ImageSetup imageSetup = new ImageSetup();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/TalentIcons/" + imagePath))));
            scaledImage = imageSetup.scaleImage(scaledImage, sizeX, sizeY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}

