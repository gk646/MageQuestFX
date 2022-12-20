package gameworld;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Item {
    public int i_id;
    public String s_id, name;
    public int quality;
    public int rarity;
    public int durability;
    public String type;
    public String stats;
    /* H - Head slot
       C - chest
       P - pants
       B - boots

       A - amulet
       R - ring
       R - ring
       T - relic / talisman

       W - weapon
       O - offhand

     */ String imagePath;
    public BufferedImage icon;

    public BufferedImage droppedIcon;

    public Item(int i_id, String name, int rarity, String type, String imagePath, String stats) {
        this.i_id = i_id;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.imagePath = imagePath;
        this.stats = stats;
    }


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
    }

    public void draw() {

    }

}
