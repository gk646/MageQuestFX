package main.system;

import gameworld.entities.BOSS;
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

    public void checkProjectileAgainstTile(PROJECTILE projectile) {
        int tileNum1, tileNum2, tileNumBG1, tileNumBG1_2;

        int leftWorldX = (int) (projectile.worldPos.x + projectile.collisionBox.x);
        int rightWorldX = (int) (projectile.worldPos.x + projectile.collisionBox.x + projectile.collisionBox.width);
        int topWorldY = (int) (projectile.worldPos.y + projectile.collisionBox.y);
        int bottomWorldY = (int) (projectile.worldPos.y + projectile.collisionBox.y + projectile.collisionBox.height);

        int leftCol = leftWorldX / 48;
        if (leftCol < 0 || leftCol > WorldRender.worldData.length - 1) {
            projectile.collisionUp = true;
            return;
        }
        int rightCol = rightWorldX / 48;
        if (rightCol < 0 || rightCol > WorldRender.worldData.length - 1) {
            projectile.collisionUp = true;
            return;
        }
        int topRow = topWorldY / 48;
        if (topRow < 0 || topRow > WorldRender.worldData.length - 1) {
            projectile.collisionUp = true;
            return;
        }
        int bottomRow = bottomWorldY / 48;
        if (bottomRow < 0 || bottomRow > WorldRender.worldData.length - 1) {
            projectile.collisionUp = true;
            return;
        }

        // Check right collision
        tileNum1 = WorldRender.worldData[rightCol][topRow];
        tileNum2 = WorldRender.worldData[rightCol][bottomRow];
        tileNumBG1 = WorldRender.worldData1[rightCol][topRow];
        tileNumBG1_2 = WorldRender.worldData1[rightCol][bottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision || tileNumBG1 != -1 && tileNumBG1_2 != -1 && mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision) {
            projectile.collisionRight = true;
            return;
        }

        // Check left collision
        tileNum1 = WorldRender.worldData[leftCol][topRow];
        tileNum2 = WorldRender.worldData[leftCol][bottomRow];
        tileNumBG1 = WorldRender.worldData1[leftCol][topRow];
        tileNumBG1_2 = WorldRender.worldData1[leftCol][bottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision || tileNumBG1 != -1 && tileNumBG1_2 != -1 && mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision) {
            projectile.collisionLeft = true;
            return;
        }

        // Check up collision
        tileNum1 = WorldRender.worldData[leftCol][topRow];
        tileNum2 = WorldRender.worldData[rightCol][topRow];
        tileNumBG1 = WorldRender.worldData1[leftCol][topRow];
        tileNumBG1_2 = WorldRender.worldData1[rightCol][topRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision || tileNumBG1 != -1 && tileNumBG1_2 != -1 && mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision) {
            projectile.collisionUp = true;
            return;
        }
        // Check down collision
        tileNum1 = WorldRender.worldData[leftCol][bottomRow];
        tileNum2 = WorldRender.worldData[rightCol][bottomRow];
        tileNumBG1 = WorldRender.worldData1[leftCol][bottomRow];
        tileNumBG1_2 = WorldRender.worldData1[rightCol][bottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision || tileNumBG1 != -1 && tileNumBG1_2 != -1 && mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision) {
            projectile.collisionDown = true;
        }
    }


    public boolean checkEntityAgainstEntity(ENTITY checkingForHit, ENTITY incomingToHit) {
        return new Rectangle((int) checkingForHit.worldX, (int) checkingForHit.worldY, checkingForHit.collisionBox.width, checkingForHit.collisionBox.height).intersects(new Rectangle((int) incomingToHit.worldX, (int) incomingToHit.worldY, incomingToHit.collisionBox.width, incomingToHit.collisionBox.height));
    }

    public void checkBossAgainstTile(BOSS entity) {
        float entityLeftWorldX = (entity.worldX + 3);
        float entityRightWorldX = (entity.worldX + 3 + 42);
        float entityTopWorldY = (entity.worldY + 3);
        float entityBottomWorldY = (entity.worldY + 3 + 42);

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = (int) Math.max(Math.min(entityTopWorldY / 48, mg.wRender.worldSize.x), 0);
        int entityBottomRow = (int) Math.min(entityBottomWorldY / 48, mg.wRender.worldSize.x);

        int tileNum1, tileNum2, tileNumBG1, tileNumBG1_2;

        entityRightCol = (int) Math.min((entityRightWorldX + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityRightCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];

        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionRight = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionRight = true;
        }


        entityLeftCol = (int) Math.min((entityLeftWorldX - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionLeft = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionLeft = true;
        }

        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);
        entityRightCol = (int) Math.min(entityRightWorldX / 48, mg.wRender.worldSize.x);

        entityTopRow = (int) Math.min((entityTopWorldY - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityTopRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionUp = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionUp = true;
        }

        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);


        entityBottomRow = (int) Math.min((entityBottomWorldY + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionDown = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionDown = true;
        }
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

        int tileNum1, tileNum2, tileNumBG1, tileNumBG1_2;

        entityRightCol = (int) Math.min((entityRightWorldX + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityRightCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityRightCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];

        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionRight = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionRight = true;
        }


        entityLeftCol = (int) Math.min((entityLeftWorldX - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityLeftCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionLeft = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionLeft = true;
        }

        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);
        entityRightCol = (int) Math.min(entityRightWorldX / 48, mg.wRender.worldSize.x);

        entityTopRow = (int) Math.min((entityTopWorldY - entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityTopRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityTopRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityTopRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityTopRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionUp = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionUp = true;
        }

        entityLeftCol = (int) Math.min(entityLeftWorldX / 48, mg.wRender.worldSize.x);


        entityBottomRow = (int) Math.min((entityBottomWorldY + entity.movementSpeed) / 48, mg.wRender.worldSize.x);
        tileNum1 = WorldRender.worldData[entityLeftCol][entityBottomRow];
        tileNum2 = WorldRender.worldData[entityRightCol][entityBottomRow];
        tileNumBG1 = WorldRender.worldData1[entityLeftCol][entityBottomRow];
        tileNumBG1_2 = WorldRender.worldData1[entityRightCol][entityBottomRow];
        if (mg.wRender.tileStorage[tileNum1].collision || mg.wRender.tileStorage[tileNum2].collision) {
            entity.collisionDown = true;
        } else if (tileNumBG1 != -1 && tileNumBG1_2 != -1 && (mg.wRender.tileStorage[tileNumBG1].collision && mg.wRender.tileStorage[tileNumBG1_2].collision)) {
            entity.collisionDown = true;
        }
    }


    public boolean checkEntityAgainstPlayer(ENTITY checkingForHit, int extension) {
        return new Rectangle((int) (checkingForHit.worldX + checkingForHit.collisionBox.x - extension), (int) (checkingForHit.worldY + checkingForHit.collisionBox.y - extension), checkingForHit.collisionBox.width + extension * 2, checkingForHit.collisionBox.height + extension * 2).intersects(new Rectangle((int) Player.worldX + mg.player.collisionBox.x, (int) Player.worldY + mg.player.collisionBox.y, mg.player.collisionBox.width, mg.player.collisionBox.height));
    }


    public boolean checkPlayerRight() {
        int right = (int) ((Player.worldX + 32 + Player.playerEffects[45]) / 48);
        int num1 = WorldRender.worldData1[right][mg.playerY];
        if (mg.wRender.tileStorage[WorldRender.worldData[right][mg.playerY]].collision) {
            return true;
        } else return num1 != -1 && mg.wRender.tileStorage[num1].collision;
    }

    public boolean checkPlayerLeft() {
        int left = (int) ((Player.worldX - Player.playerEffects[45]) / 48);
        int num1 = WorldRender.worldData1[left][mg.playerY];
        if (mg.wRender.tileStorage[WorldRender.worldData[left][mg.playerY]].collision) {
            return true;
        } else return num1 != -1 && mg.wRender.tileStorage[num1].collision;
    }

    public boolean checkPlayerUp() {
        float y = Player.worldY;
        int bottom = (int) ((y - Player.playerEffects[45]) / 48);
        int num = WorldRender.worldData[mg.playerX][bottom];
        int num1 = WorldRender.worldData1[mg.playerX][bottom];
        if (mg.wRender.tileStorage[num].collision) {
            return true;
        } else return num1 != -1 && mg.wRender.tileStorage[num1].collision;
    }

    public boolean checkPlayerDown() {
        float y = Player.worldY;
        int bottom = (int) ((y + 32 + Player.playerEffects[45]) / 48);
        int num = WorldRender.worldData[mg.playerX][bottom];
        int num1 = WorldRender.worldData1[mg.playerX][bottom];
        if (mg.wRender.tileStorage[num].collision) {
            return true;
        } else return num1 != -1 && mg.wRender.tileStorage[num1].collision;
    }
}


