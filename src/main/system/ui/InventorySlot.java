package main.system.ui;

import gameworld.Item;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class InventorySlot {
    private final int SLOT_SIZE = 45;
    public boolean grabbed;
    int xCo;
    int yCo;
    Rectangle boundBox;
    Item item;

    InventorySlot(Item item, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, SLOT_SIZE, SLOT_SIZE);
        this.item = item;
        this.xCo = xCo;
        this.yCo = yCo;
    }

    public void setItem(InventorySlot invSlot, Item item) {
        invSlot.item = item;
    }

    public void drawIcon(Graphics2D g2, int x, int y, int slotSize) {
        item.drawIcon(g2, x, y, slotSize);
    }

    public void drawSlot(Graphics2D g2) {
        g2.drawRoundRect(this.xCo, this.yCo, SLOT_SIZE, SLOT_SIZE, 20, 20);

    }
}
