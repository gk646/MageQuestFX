package gameworld.entities.npcs.generic;

import gameworld.entities.NPC;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_GenericVillagerWoman extends NPC {


    public NPC_GenericVillagerWoman(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/woman");
        animation.load();
        this.mg = mainGame;
        this.zone = zone;
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
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
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
        spriteCounter++;
        drawNPCName(gc, "Villager");
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX + 10, screenY - 20);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX + 10, screenY - 20);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX + 10, screenY - 20);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX + 10, screenY - 20);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX + 10, screenY - 20);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX + 10, screenY - 20);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX + 10, screenY - 20);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX + 10, screenY - 20);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX + 10, screenY - 20);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX + 10, screenY - 20);
        }
    }
}
