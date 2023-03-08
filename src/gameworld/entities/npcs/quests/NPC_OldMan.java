package gameworld.entities.npcs.quests;

import gameworld.entities.NPC;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_OldMan extends NPC {


    public NPC_OldMan(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.zone = zone;
        this.mg = mainGame;
        this.animation = new ResourceLoaderEntity("npc/oldman");
        animation.load();
        goalTile = new Point(34, 34);
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
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (onPath) {
            drawWalk(gc);
        } else {
            drawIdle(gc);
        }
        if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
        spriteCounter++;
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX + 17, screenY - 5);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX + 17, screenY - 5);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX + 17, screenY - 5);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX + 17, screenY - 5);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX + 17, screenY - 5);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX + 17, screenY - 5);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX + 17, screenY - 5);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX + 17, screenY - 5);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX + 17, screenY - 5);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX + 17, screenY - 5);
        }
    }

    @Override
    public void update() {
        super.update();
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 500);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
    }
}

