package gameworld.entities.npcs.trader;

import gameworld.entities.damage.DamageType;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.objects.items.ITM_SpellBook;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Rectangle;
import java.util.Objects;


public class NPC_AbilityTrader extends MERCHANT {


    public NPC_AbilityTrader(MainGame mg, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        tradeWindowX = 250;
        tradeWindowY = 250;
        for (int i = 0; i < 14; i++) {
            buySlots.add(new UI_InventorySlot(null, 123, 123));
        }
        buySlots.get(0).item = new ITM_SpellBook("Energy Sphere", 2, null, null, DamageType.Arcane);
        buySlots.get(1).item = new ITM_SpellBook("Frost Nova", 2, null, null, DamageType.Ice);
        buySlots.get(2).item = new ITM_SpellBook("Lightning Strike", 2, null, null, DamageType.Arcane);
        buySlots.get(3).item = new ITM_SpellBook("Ring Salvo", 2, null, null, DamageType.Fire);
        buySlots.get(4).item = new ITM_SpellBook("Regenerative Aura", 2, null, null, DamageType.Arcane);
        buySlots.get(5).item = new ITM_SpellBook("Thunder Splash", 2, null, null, DamageType.Arcane);
        buySlots.get(6).item = new ITM_SpellBook("Thunder Strike", 2, null, null, DamageType.Arcane);
        buySlots.get(7).item = new ITM_SpellBook("Void Eruption", 2, null, null, DamageType.DarkMagic);
        buySlots.get(8).item = new ITM_SpellBook("Void Field", 2, null, null, DamageType.DarkMagic);

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
        gc.fillText("Ability Trader", screenX - 25, screenY + 58);
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

