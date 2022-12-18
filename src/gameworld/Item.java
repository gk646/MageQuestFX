package gameworld;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Item {
    public int i_id;
    public int quality;
    public int rarity;
    public int durability;
    public BufferedImage icon;
    public BufferedImage droppedIcon;


    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
    }

    public void draw() {

    }

}
