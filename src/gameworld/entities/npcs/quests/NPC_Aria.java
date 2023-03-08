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

public class NPC_Aria extends NPC {
    private boolean attack1;

    public NPC_Aria(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/aria");
        animation.load();
        this.zone = zone;
        this.mg = mainGame;
        goalTile = new Point();
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
        spriteCounter = (int) (Math.random() * 20);
    }

    @Override
    public void update() {
        super.update();
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 800);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
    }

    /**
     *
     */
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (onPath) {
            drawWalk(gc);
        } else {
            drawIdle(gc);
        }

        if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
        spriteCounter++;
        drawNPCName(gc, "Aria");
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX, screenY - 20);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX, screenY - 20);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX, screenY - 20);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX, screenY - 20);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX, screenY - 20);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX, screenY - 20);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX, screenY - 20);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX, screenY - 20);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX, screenY - 20);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX, screenY - 20);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 160) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX, screenY - 20);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX, screenY - 20);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX, screenY - 20);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX, screenY - 20);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX, screenY - 20);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX, screenY - 20);
            case 6 -> attack1 = false;
        }
    }

    private void drawDeath(GraphicsContext gc) {
        switch (spriteCounter % 240 / 35) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX, screenY - 20);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX, screenY - 20);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX, screenY - 20);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX, screenY - 20);
            case 4 -> gc.drawImage(animation.dead.get(4), screenX, screenY - 20);
            case 5 -> gc.drawImage(animation.dead.get(5), screenX, screenY - 20);
        }
    }
}
