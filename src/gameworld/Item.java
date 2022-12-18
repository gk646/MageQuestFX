package gameworld;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Item {
    public int i_id;
    public int quality;
    public int rarity;
    public int durability;
    public char type;
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

     */
    public BufferedImage icon;
    public BufferedImage droppedIcon;


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
    }

    public void draw() {

    }

}
