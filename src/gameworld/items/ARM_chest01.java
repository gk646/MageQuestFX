package gameworld.items;

import gameworld.Item;
import main.system.ImageSetup;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ARM_chest01 extends Item implements ImageSetup {


    public ARM_chest01() {
        this.i_id = 1;
        this.durability = 100;
        this.quality = 70 + (int) (Math.random() * 30);
        this.rarity = 1;
        this.type = 'C';
        getImages();
    }

    @Override
    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(icon, x, y, slotSize, slotSize, null);
    }

    @Override
    public void draw() {

    }

    @Override
    public BufferedImage setup(String packageN, String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/" + packageN + "/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    @Override
    public void getImages() {
        icon = setup("items", "ARM_chest01.png");
        droppedIcon = setup("items", "ARM_chest01_icon.png");
    }
}
