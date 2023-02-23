package main.system;

import gameworld.entities.ENTITY;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import main.MainGame;
import main.system.rendering.WorldRender;

import java.awt.Rectangle;

public class CollisionChecker {

    private final MainGame mg;

    public CollisionChecker(MainGame mainGame) {
        this.mg = mainGame;
    }

    @SuppressWarnings("DuplicateExpressions")
    public static void checkProjectileAgainstTile(PROJECTILE projectile) {
        int entityLeftWorldX = (int) (projectile.worldPos.x + projectile.collisionBox.x);
        int entityRightWorldX = (int) (projectile.worldPos.x + projectile.collisionBox.x + projectile.collisionBox.width);
        int entityTopWorldY = (int) (projectile.worldPos.y + projectile.collisionBox.y);
        int entityBottomWorldY = (int) (projectile.worldPos.y + projectile.collisionBox.y + projectile.collisionBox.height);

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = Math.max(Math.min(entityTopWorldY / 48, 499), 0);
        int entityBottomRow = Math.max(0, Math.min(entityBottomWorldY / 48, 499));

        int tileNum1, tileNum2;
        if (projectile.direction.contains("right")) {
            entityRightCol = Math.max(0, Math.min((entityRightWorldX + projectile.movementSpeed) / 48, 499));
            tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                projectile.collisionRight = true;
            }
        }
        if (projectile.direction.contains("left")) {
            entityLeftCol = Math.max(0, Math.min((entityLeftWorldX - projectile.movementSpeed) / 48, 499));
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                projectile.collisionLeft = true;
            }
        }
        entityLeftCol = Math.max(0, Math.min(entityLeftWorldX / 48, 499));
        entityRightCol = Math.max(0, Math.min(entityRightWorldX / 48, 499));
        if (projectile.direction.contains("up")) {
            entityTopRow = Math.max(0, Math.min((entityTopWorldY - projectile.movementSpeed) / 48, 499));
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                projectile.collisionUp = true;
            }
        }
        entityLeftCol = Math.max(0, Math.min(entityLeftWorldX / 48, 499));

        if (projectile.direction.contains("down")) {
            entityBottomRow = Math.max(0, Math.min((entityBottomWorldY + projectile.movementSpeed) / 48, 499));
            tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                projectile.collisionDown = true;
            }
        }
    }

    public boolean checkEntityAgainstEntity(ENTITY checkingForHit, ENTITY incomingToHit) {
        return new Rectangle((int) checkingForHit.worldX, (int) checkingForHit.worldY, checkingForHit.collisionBox.width, checkingForHit.collisionBox.height).intersects(new Rectangle((int) incomingToHit.worldX, (int) incomingToHit.worldY, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }

    public boolean checkEntityAgainstProjectile(ENTITY checkingForHit, PROJECTILE incomingToHit) {
        return new Rectangle((int) (checkingForHit.worldX + checkingForHit.collisionBox.x), (int) (checkingForHit.worldY + checkingForHit.collisionBox.y), checkingForHit.collisionBox.width, checkingForHit.collisionBox.height).intersects(new Rectangle((int) incomingToHit.worldPos.x + incomingToHit.collisionBox.x, (int) incomingToHit.worldPos.y + incomingToHit.collisionBox.y, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }

    public boolean checkPlayerAgainstProjectile(PROJECTILE incomingToHit) {
        return new Rectangle((int) (Player.worldX + 8), (int) (Player.worldY + 8), 37, 37).intersects(new Rectangle((int) incomingToHit.worldPos.x + incomingToHit.collisionBox.x, (int) incomingToHit.worldPos.y + incomingToHit.collisionBox.y, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }

    public void checkEntityAgainstTile(ENTITY entity) {
        float entityLeftWorldX = (entity.worldX + entity.collisionBox.x);
        float entityRightWorldX = (entity.worldX + entity.collisionBox.x + entity.collisionBox.width);
        float entityTopWorldY = (entity.worldY + entity.collisionBox.y);
        float entityBottomWorldY = (entity.worldY + entity.collisionBox.y + entity.collisionBox.height);

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = (int) Math.max(Math.min(entityTopWorldY / 48, mg.wRender.worldSize.x), 0);
        int entityBottomRow = (int) Math.min(entityBottomWorldY / 48, mg.wRender.worldSize.x);

        int tileNum1, tileNum2;
        if (entity.direction.contains("right")) {
            entityRightCol = (int) Math.min((entityRightWorldX + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }
        if (entity.direction.contains("left")) {
            entityLeftCol = (int) Math.min((entityLeftWorldX - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }
        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);
        entityRightCol = (int) Math.min(entityRightWorldX / 48, mg.wRender.worldSize.x);
        if (entity.direction.contains("up")) {

            entityTopRow = (int) Math.min((entityTopWorldY - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                entity.collisionUp = true;
            }
        }
        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);

        if (entity.direction.contains("down")) {
            entityBottomRow = (int) Math.min((entityBottomWorldY + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                entity.collisionDown = true;
            }
        }
    }

    public void checkPlayerAgainstTile(Player player) {
        int entityLeftWorldX = (int) (Player.worldX + 18);
        int entityRightWorldX = (int) (Player.worldX + 28);
        int entityTopWorldY = (int) (Player.worldY + 8);
        int entityBottomWorldY = (int) (Player.worldY + 40);

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = Math.max(Math.min(entityTopWorldY / 48, mg.wRender.worldSize.x), 0);
        int entityBottomRow = Math.min(entityBottomWorldY / 48, mg.wRender.worldSize.x);

        int tileNum1, tileNum2, tileNumBG1, tileNumBG1_2;
        if (player.direction.contains("right")) {
            entityRightCol = (int) Math.min((entityRightWorldX + player.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            tileNumBG1 = WorldRender.worldData1[entityRightCol][entityTopRow];
            tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];

            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                player.collisionRight = true;
            } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (WorldRender.tileStorage[tileNumBG1].collision && WorldRender.tileStorage[tileNumBG1_2].collision)) {
                player.collisionRight = true;
            }
        }
        if (player.direction.contains("left")) {
            entityLeftCol = (int) Math.min((entityLeftWorldX - player.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
            tileNumBG1_2 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                player.collisionLeft = true;
            } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (WorldRender.tileStorage[tileNumBG1].collision && WorldRender.tileStorage[tileNumBG1_2].collision)) {
                player.collisionLeft = true;
            }
        }
        entityLeftCol = Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);
        entityRightCol = Math.min(entityRightWorldX / 48, mg.wRender.worldSize.x);
        if (player.direction.contains("up")) {
            entityTopRow = (int) Math.min((entityTopWorldY - player.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
            tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
            tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityTopRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                player.collisionUp = true;
            } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (WorldRender.tileStorage[tileNumBG1].collision && WorldRender.tileStorage[tileNumBG1_2].collision)) {
                player.collisionUp = true;
            }
        }
        entityLeftCol = Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);

        if (player.direction.contains("down")) {
            entityBottomRow = (int) Math.min((entityBottomWorldY + player.movementSpeed) / 48, mg.wRender.worldSize.x);
            tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
            tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
            tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
            tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];
            if (WorldRender.tileStorage[tileNum1].collision || WorldRender.tileStorage[tileNum2].collision) {
                player.collisionDown = true;
            } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (WorldRender.tileStorage[tileNumBG1].collision && WorldRender.tileStorage[tileNumBG1_2].collision)) {
                player.collisionDown = true;
            }
        }
    }

    public boolean checkEntityAgainstPlayer(ENTITY checkingForHit, int extension) {
        return new Rectangle((int) (checkingForHit.worldX + checkingForHit.collisionBox.x - extension), (int) (checkingForHit.worldY + checkingForHit.collisionBox.y - extension), checkingForHit.collisionBox.width + extension * 2, checkingForHit.collisionBox.height + extension * 2).intersects(new Rectangle((int) Player.worldX + mg.player.collisionBox.x, (int) Player.worldY + mg.player.collisionBox.y, mg.player.collisionBox.width, mg.player.collisionBox.height));
    }
}


