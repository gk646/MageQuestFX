package main.system;

import gameworld.Entity;
import main.MainGame;

import java.awt.Rectangle;

public class CollisionChecker {
    private final MainGame mainGame;

    public CollisionChecker(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    public void checkEntityAgainstTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = Math.max(Math.min(entityTopWorldY / 48, 499), 0);
        int entityBottomRow = Math.min(entityBottomWorldY / 48, 499);

        int tileNum1, tileNum2;
        if (entity.direction.contains("right")) {
            entityRightCol = Math.min((entityRightWorldX + entity.movementSpeed) / 48, 499);
            tileNum1 = mainGame.wRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = mainGame.wRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }
        if (entity.direction.contains("left")) {
            entityLeftCol = Math.min((entityLeftWorldX - entity.movementSpeed) / 48, 499);
            tileNum1 = mainGame.wRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = mainGame.wRender.worldData[entityLeftCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }
        entityLeftCol = Math.min(entityLeftWorldX / 48, 499);
        entityRightCol = Math.min(entityRightWorldX / 48, 499);
        if (entity.direction.contains("up")) {

            entityTopRow = Math.min((entityTopWorldY - entity.movementSpeed) / 48, 499);
            tileNum1 = mainGame.wRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = mainGame.wRender.worldData[entityRightCol][entityTopRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionUp = true;
            }

        }
        entityLeftCol = Math.min(entityLeftWorldX / 48, 499);

        if (entity.direction.contains("down")) {
            entityBottomRow = Math.min((entityBottomWorldY + entity.movementSpeed) / 48, 499);
            tileNum1 = mainGame.wRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = mainGame.wRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionDown = true;
            }
        }
    }

    public boolean checkEntityAgainstEntity(Entity checkingForHit, Entity incomingToHit) {
        return new Rectangle(checkingForHit.worldX, checkingForHit.worldY, checkingForHit.collisionBox.width, checkingForHit.collisionBox.height).intersects(new Rectangle(incomingToHit.worldX, incomingToHit.worldY, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }
}


