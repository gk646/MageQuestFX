package gameworld.entities.npcs.quests;

import gameworld.entities.loadinghelper.EntityPreloader;
import gameworld.entities.npcs.generic.NPC_Generic;
import gameworld.player.Player;
import gameworld.player.PlayerPrompts;
import gameworld.player.abilities.portals.PRJ_EtherPortal;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;


public class ENT_RealmKeeper extends NPC_Generic {
    public static boolean ETHER_ACTIVE, BOSS_KILLED;
    int progressStage = 1, objective1Progress, objective2Progress;
    int portalX, portalY;

    public ENT_RealmKeeper(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = EntityPreloader.realmKeeper;
        this.mg = mainGame;
        this.zone = zone;
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 1.5f;
        dialog.loadNewLine("Should I generate portal to the Ether Realm?");
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
        spriteCounter = (int) (Math.random() * 20);
    }

    @Override
    public void update() {
        super.update();
        scriptRealmKeeper();
        if (collidingWithPlayer()) {
            PlayerPrompts.setETrue();
            if (mg.inputH.e_typed) {
                mg.inputH.e_typed = false;
                playerTalkLocation = new Point(activeTile.x * 48, activeTile.y * 48);
                show_dialog = true;
                dialogHideDelay = 0;
            }
        }
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 500);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
    }


    private void scriptRealmKeeper() {
        if (progressStage == 1) {
            int num = dialog.drawChoice("Pay 100 Coins", "No", null, null);
            if (num == 10) {
                if (!ETHER_ACTIVE) {
                    mg.prj_control.toBeAdded.add(new PRJ_EtherPortal(mg, activeTile.x + 1, activeTile.y));
                    ETHER_ACTIVE = true;
                }
            }
        }
    }

    public void drawDialog(GraphicsContext gc) {
        dialog.drawDialog(gc, this);
    }

    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        drawIdle(gc);
        spriteCounter++;
        drawNPCName(gc, "Realm Keeper");
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX, screenY - 12);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX, screenY - 12);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX, screenY - 12);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX, screenY - 12);
        }
    }
}
