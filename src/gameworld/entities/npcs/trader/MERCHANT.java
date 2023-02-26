package gameworld.entities.npcs.trader;

import gameworld.entities.NPC;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.ui.Colors;
import main.system.ui.inventory.UI_InventorySlot;

import java.util.ArrayList;

abstract public class MERCHANT extends NPC {
    protected Image tradeWindow;
    public boolean show_trade;
    public boolean show_buyback;
    protected int tradeWindowX, tradeWindowY;

    public ArrayList<UI_InventorySlot> buySlots = new ArrayList<>();
    public ArrayList<UI_InventorySlot> soldSlots = new ArrayList<>();

    public boolean sellItem(ITEM item) {
        addItemSold(item);
        mg.player.coins += item.level * item.rarity;
        return true;
    }

    private void addItemSold(ITEM item) {
        soldSlots.remove(27);
        soldSlots.add(new UI_InventorySlot(item, 123, 123));
    }

    protected void drawMerchantWindow(GraphicsContext gc, int startX, int startY) {
        gc.drawImage(tradeWindow, startX - 50, startY - 50);
        int i = 0;
        int y;
        gc.setLineWidth(2);
        for (UI_InventorySlot slot : buySlots) {
            y = 50 * (i / 7);
            slot.boundBox.x = i % 7 * 50 + startX + 10;
            slot.boundBox.y = 60 + y + startY;
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(i % 7 * 50 + 10 + startX, 60 + y + startY, 45, 45, 20, 20);
            mg.inventP.setRarityColor(gc, slot);
            slot.drawSlot(gc, i % 7 * 50 + startX + 10, 60 + y + startY);
            if (slot.item != null && !slot.grabbed) {
                slot.drawIcon(gc, i % 7 * 50 + startX + 10, 60 + y + startY, 45);
            }
            i++;
        }
    }

    protected void drawBuyBack(GraphicsContext gc, int startX, int startY) {
        int i = 0;
        int y;
        gc.setLineWidth(2);
        for (UI_InventorySlot slot : soldSlots) {
            y = 50 * (i / 7);
            slot.boundBox.x = i % 7 * 50 + startX + 10;
            slot.boundBox.y = 60 + y + startY;
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(i % 7 * 50 + 10 + startX, 60 + y + startY, 45, 45, 20, 20);
            mg.inventP.setRarityColor(gc, slot);
            slot.drawSlot(gc, i % 7 * 50 + startX + 10, 60 + y + startY);
            if (slot.item != null && !slot.grabbed) {
                slot.drawIcon(gc, i % 7 * 50 + startX + 10, 60 + y + startY, 45);
            }
            i++;
        }
    }

    public boolean buyItem(ITEM item) {
        if (mg.player.coins >= item.level * 25 + item.rarity * 25) {
            for (UI_InventorySlot slot : mg.inventP.bag_Slots) {
                if (slot.item != null) {
                    slot.item = item;
                    mg.player.coins -= item.level * 25 + item.rarity * 25;
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean buyBackItem(ITEM item) {
        if (mg.player.coins >= item.level + item.rarity) {
            for (UI_InventorySlot slot : mg.inventP.bag_Slots) {
                if (slot.item != null) {
                    slot.item = item;
                    mg.player.coins -= item.level + item.rarity;
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
