package gameworld.entities.npcs.trader;

import gameworld.player.Player;
import gameworld.quest.Dialog;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Rectangle;
import java.util.Objects;

public class NPC_ArmourTrader extends MERCHANT {


    //TODO when to regenerate trader inventory and make method
    public NPC_ArmourTrader(MainGame mg, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        tradeWindowX = 250;
        tradeWindowY = 250;
        for (int i = 0; i < 14; i++) {
            ITEM item = mg.dropI.getFinishedRandomItem(mg.player.level);
            while (item.rarity > 10 || !(item.type == 'H' || item.type == 'C' || item.type == 'P' || item.type == 'B')) {
                item = mg.dropI.getFinishedRandomItem(mg.player.level);
            }
            buySlots.add(new UI_InventorySlot(item, 123, 123));
        }
        for (int i = 0; i < 28; i++) {
            soldSlots.add(new UI_InventorySlot(null, 123, 123));
        }
        this.zone = zone;
        this.mg = mg;
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        entityImage1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Entitys/npc/man01.png")));
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }

    //TODO make misc items and display value / make quest item type
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        gc.drawImage(entityImage1, screenX, screenY);
        if (show_trade) {
            drawMerchantWindow(gc, tradeWindowX, tradeWindowY);
            if (!collidingWithPlayer()) {
                show_trade = false;
                mg.inventP.activeTradingNPC = null;
                dialog.dialogRenderCounter = 0;
            }
            drawMerchantTooltip(gc);
        } else if (show_buyback) {
            drawBuyBack(gc, tradeWindowX, tradeWindowY);
            if (!collidingWithPlayer()) {
                show_buyback = false;
                mg.inventP.activeTradingNPC = null;
                dialog.dialogRenderCounter = 0;
            }
            drawMerchantTooltip(gc);
        } else if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
        drawNPCName(gc, "Armour Trader");
    }

    @Override
    public void update() {
        super.update();
        if (mg.inputH.e_typed && collidingWithPlayer()) {
            if (dialog.dialogRenderCounter == 0) {
                dialog.loadNewLine(DialogStorage.Trading[mg.random.nextInt(0, DialogStorage.Trading.length)]);
                show_dialog = true;
            } else if (dialog.dialogRenderCounter < 2_000) {
                dialog.dialogRenderCounter = 2_000;
            } else if (dialog.dialogRenderCounter == 2_000 && mg.inputH.e_typed) {
                show_trade = true;
                show_dialog = false;
                mg.showBag = true;
                mg.inventP.activeTradingNPC = this;
            }
            mg.inputH.e_typed = false;
        }
    }
}
