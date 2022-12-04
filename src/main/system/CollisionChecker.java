package main.system;

import gameworld.Entity;
import main.MainGame;

import java.awt.Rectangle;

public class CollisionChecker {
    final MainGame mainGame;

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
        int entityTopRow = Math.max(entityTopWorldY / 48, 0);
        int entityBottomRow = Math.max(entityBottomWorldY / 48, 0);

        int tileNum1, tileNum2;
        if (entity.direction.contains("right")) {
            entityRightCol = Math.max((entityRightWorldX + entity.movementSpeed) / 48, 0);
            tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }
        if (entity.direction.contains("left")) {
            entityLeftCol = Math.max((entityLeftWorldX - entity.movementSpeed) / 48, 0);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }
        entityLeftCol = Math.max(entityLeftWorldX / 48, 0);
        entityRightCol = Math.max(entityRightWorldX / 48, 0);
        if (entity.direction.contains("up")) {

            entityTopRow = Math.max((entityTopWorldY - entity.movementSpeed) / 48, 0);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionUp = true;
            }

        }
        entityLeftCol = Math.max(entityLeftWorldX / 48, 0);

        if (entity.direction.contains("down")) {
            entityBottomRow = Math.max((entityBottomWorldY + entity.movementSpeed) / 48, 0);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionDown = true;
            }
        }


    }

    public boolean checkEntityAgainstEntity(Entity checkingForHit, Entity incomingToHit) {

        return new Rectangle(checkingForHit.worldX, checkingForHit.worldY, checkingForHit.collisionBox.width, checkingForHit.collisionBox.height).intersects(new Rectangle(
                incomingToHit.worldX, incomingToHit.worldY, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }
}


