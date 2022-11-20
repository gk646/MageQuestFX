package main;

import entity.Entity;

public class CollisionChecker {
    MainGame mainGame;

    CollisionChecker(MainGame mainGame) {
        this.mainGame = mainGame;

    }

    public void checkEntity(Entity entity) {
        int entityLeftWorldX = entity.worldX - entity.collisionBox.x-48;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x -entity.collisionBox.width+7;
        int entityTopWorldY = entity.worldY - entity.collisionBox.y-23;
        int entityBottomWorldY = entity.worldY +entity.collisionBox.y -entity.collisionBox.height+12;

        int entityLeftCol = entityLeftWorldX / 48;
        int entityRightCol = entityRightWorldX / 48;
        int entityTopRow = entityTopWorldY / 48;
        int entityBottomRow = entityBottomWorldY / 48;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY + entity.movementSpeed) / 48;
                tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
                tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
                if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.movementSpeed) / 48;
                tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
                tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
                if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX + entity.movementSpeed) / 48;
                tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
                tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
                if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.movementSpeed) / 48;
                tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
                tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
                if (mainGame.wRender.tileStorage[tileNum1].collision || mainGame.wRender.tileStorage[tileNum2].collision) {
                    entity.collision = true;
                }
                break;

        }
    }


}
