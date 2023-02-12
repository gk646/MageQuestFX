package main.system.ui.inventory;

import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;

public class UI_InventorySlot {
    public final Rectangle boundBox;
    private int SLOT_SIZE = 45;
    public boolean grabbed;
    public ITEM item;
    public int toolTipTimer;
    public String type = "+";

    UI_InventorySlot(ITEM item, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, SLOT_SIZE, SLOT_SIZE);
        this.item = item;
    }

    UI_InventorySlot(ITEM item, int xCo, int yCo, int size) {
        this.boundBox = new Rectangle(xCo, yCo, size, size);
        this.item = item;
        this.SLOT_SIZE = size;
    }


    public void drawIcon(GraphicsContext g2, int x, int y, int slotSize) {
        item.drawIcon(g2, x, y, slotSize);
    }


    public void drawSlot(GraphicsContext g2, int startX, int startY) {
        g2.strokeRoundRect(startX, startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
    }
}
