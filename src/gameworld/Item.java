package gameworld;

import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Item {
    public int i_id;
    public String name;
    public int quality;
    public int rarity;
    public int durability = 100;
    public String stats;
    /*
        INT intellect + max mana / more damage on abilities
        VIT vitality + max health / more health regen
        REG mana regen + mana regeneration
        SPD speed + movement speed

     */
    public int INT;
    public int VIT;
    public int REG;
    public int SPD;

    public String type;

    /* H - Head slot
       C - chest
       P - pants
       B - boots

       A - amulet
       R - ring
       R - ring
       T - relic / talisman

       W - one hand
       2 - two hand
       O - offhand

     */ public String imagePath;
    public String description;
    public BufferedImage icon;

    public Item(int i_id, String name, int rarity, String type, String imagePath, String description) {
        this.i_id = i_id;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.imagePath = imagePath;
        this.description = description;
    }


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(icon, x, y, slotSize, slotSize, null);
    }

    public void draw() {
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
