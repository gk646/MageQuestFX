package gameworld.entities.npcs.trader;

import gameworld.quest.Dialog;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Rectangle;

public class NPC_Trader extends MERCHANT {


    //TODO when to regenerate trader inventory and make method
    NPC_Trader(MainGame mg, int xTile, int yTile) {
        this.dialog = new Dialog();
        tradeWindowX = 250;
        tradeWindowY = 250;
        for (int i = 0; i < 14; i++) {
            ITEM item = mg.dropI.getFinishedRandomItem(mg.player.level);
            while (item.rarity <= 2) {
                item = mg.dropI.getFinishedRandomItem(mg.player.level);
            }
            buySlots.add(new UI_InventorySlot(item, 123, 123));
        }
        for (int i = 0; i < 28; i++) {
            soldSlots.add(new UI_InventorySlot(null, 123, 123));
        }
        this.zone = Zone.Tutorial;
        this.mg = mg;
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (show_trade) {
            drawMerchantWindow(gc, tradeWindowX, tradeWindowY);
        } else if (show_buyback) {
            drawBuyBack(gc, tradeWindowX, tradeWindowY);
        } else if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
    }

    @Override
    public void update() {
        if (collidingWithPlayer() && mg.inputH.e_typed) {
            dialog.loadNewLine(DialogStorage.Trading[mg.random.nextInt(0, DialogStorage.Trading.length)]);
            if (dialog.dialogRenderCounter == 2000 && mg.inputH.e_typed) {
                show_trade = true;
                mg.showBag = true;
                mg.inventP.activeTradingNPC = this;
            }
        }
    }
}
