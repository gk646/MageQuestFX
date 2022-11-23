package main;

import gameworld.Entity;

import java.awt.*;

public class CollisionChecker {
    MainGame mainGame;

    CollisionChecker(MainGame mainGame) {
        this.mainGame = mainGame;

    }

    public void checkEntityAgainstTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = entityTopWorldY / 48;
        int entityBottomRow = entityBottomWorldY / 48;

        int tileNum1, tileNum2;
        if (entity.direction.contains("right")) {
            entityRightCol = (entityRightWorldX + entity.movementSpeed) / 48;
            tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionright = true;
            }
        }
        if (entity.direction.contains("left")) {
            entityLeftCol = (entityLeftWorldX - entity.movementSpeed) / 48;
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionleft = true;
            }
        }
        entityLeftCol = entityLeftWorldX / 48;
        entityRightCol = entityRightWorldX / 48;
        if (entity.direction.contains("up")) {

            entityTopRow = (entityTopWorldY - entity.movementSpeed) / 48;
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisionup = true;
            }

        }
        entityLeftCol = entityLeftWorldX / 48;

        if (entity.direction.contains("down")) {
            entityBottomRow = (entityBottomWorldY + entity.movementSpeed) / 48;
            tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                entity.collisiondown = true;
            }
        }


    }

    public boolean checkEntityAgainstEntity(Entity checkingForHit, Entity incomingToHit){

        if(new Rectangle(checkingForHit.worldX,checkingForHit.worldY,checkingForHit.collisionBox.width,checkingForHit.collisionBox.height).intersects(new Rectangle(
                incomingToHit.worldX,incomingToHit.worldY,incomingToHit.collisionBox.width,incomingToHit.collisionBox.height))){
            return true;
        }
        return false;
    }
}


