/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    public UI_InventorySlot(ITEM item, int xCo, int yCo) {
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
