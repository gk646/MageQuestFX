package gameworld;

import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("GrazieInspection")
public class Item {
    public int i_id;
    public String name;
    public int quality;
    public int rarity;
    public int durability = 100;
    private final String stats;
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

    public Item(int i_id, String name, int rarity, String type, String imagePath, String description, String stats) {
        this.i_id = i_id;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.imagePath = imagePath;
        this.description = description;
        this.stats = stats;
        getStats();
    }


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        g2.drawImage(icon, x, y, slotSize, slotSize, null);
    }


    private void getStats() {
        Pattern p;
        Matcher m;
        if (stats.contains("INT")) {
            p = Pattern.compile("INT([0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                INT = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("VIT")) {
            p = Pattern.compile("VIT([0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                VIT = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("REG")) {
            p = Pattern.compile("REG([0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                REG = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("SPD")) {
            p = Pattern.compile("SPD([0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                SPD = Integer.parseInt(m.group(1));
            }
        }
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
