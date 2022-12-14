package gameworld;

import gameworld.entitys.Grunt;
import main.MainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;


/**
 * Only used for handling Enemies atm.
 * Main inheritable class for all game world entity's
 */
public class Entity {
    public int worldY, worldX, entityWidth, entityHeight, screenX, screenY, health, maxHealth, movementSpeed, hpBarCounter, searchTicks;
    public int goalCol, goalRow, nextCol1, nextRow1, nextCol2, nextRow2, nextCol3, nextRow3, nextCol4, nextRow4;
    public MainGame mainGame;
    public BufferedImage up1;
    public String direction;
    public Rectangle collisionBox;
    public boolean initializeEnemies, hpBarOn, onPath, collisionUp, collisionDown, collisionLeft, collisionRight, dead;


    public Entity(MainGame mainGame) {
        this.mainGame = mainGame;
    }

    /**
     * Only really updates enemy position
     */


    public void update() {
        try {
            if (!initializeEnemies) {
                spawnEnemies();
                initializeEnemies = true;
            }

            for (Entity entity : mainGame.ENTITIES) {
                entity.update();
                if(mainGame.collisionChecker.checkEntityAgainstEntity(mainGame.player, entity)){
                    mainGame.player.health-=1;
                    if(mainGame.player.health <= 0){
                        mainGame.gameState = mainGame.gameOver;
                    }
                }
                if (entity.hpBarCounter >= 600) {
                    entity.hpBarOn = false;
                    entity.hpBarCounter = 0;
                }
                if (entity.hpBarOn) {
                    entity.hpBarCounter++;
                }
                if (entity.health <= 0) {
                    mainGame.ENTITIES.remove(entity);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    /**
     * Used for drawing health bars
     */
    public void draw(Graphics2D g2) {
        try {
            for (Entity entity1 : mainGame.ENTITIES) {
                entity1.draw(g2);
                if (entity1.hpBarOn) {
                    g2.setColor(new Color(0xFF0044));
                    g2.fillRect(entity1.screenX, entity1.screenY - 10, (int) (((float) entity1.health / entity1.maxHealth) * 48), 8);
                    g2.setColor(new Color(0xFFFFFF));
                    g2.setFont(mainGame.ui.maruMonica);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
                    g2.drawString(entity1.health + "", entity1.screenX + 14, entity1.screenY);
                }
            }
        } catch (ConcurrentModificationException ignored) {

        }
    }

    public void spawnEnemies() {
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
        mainGame.ENTITIES.add(new Grunt(mainGame, 12000, 12100, 1));
    }

    private void decideMovement(int nextX, int nextY) {
        int enLeftX = worldX + collisionBox.x;
        int enRightX = worldX + collisionBox.x + collisionBox.width;
        int enTopY = worldY + collisionBox.y;
        int enBottomY = worldY + collisionBox.y + collisionBox.height;
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        mainGame.collisionChecker.checkEntityAgainstTile(this);

        if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + mainGame.tileSize) {
            worldY -= movementSpeed;
        } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + mainGame.tileSize) {
            worldY += movementSpeed;
        } else if (enTopY >= nextY && enBottomY < nextY + mainGame.tileSize) {
            if (enLeftX > nextX) {
                worldX -= movementSpeed;
            }
            if (enLeftX < nextX) {
                worldX += movementSpeed;
            }
        } else if (enTopY > nextY && enLeftX > nextX) {
            if (collisionUp) {
                worldX -= movementSpeed;
            } else {
                worldY -= movementSpeed;
            }
        } else if (enTopY > nextY && enLeftX < nextX) {
            if (collisionUp) {
                worldX += movementSpeed;
            } else {
                worldY -= movementSpeed;
            }
        } else if (enTopY < nextY && enLeftX > nextX) {
            if (collisionDown) {
                worldX -= movementSpeed;
            } else {
                worldY += movementSpeed;
            }
        } else if (enTopY < nextY && enLeftX < nextX) {
            if (collisionDown) {
                worldX += movementSpeed;
            } else {
                worldY += movementSpeed;
            }
        }
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + collisionBox.x) / mainGame.tileSize;
        int startRow = (worldY + collisionBox.y) / mainGame.tileSize;
        mainGame.pathFinder.setNodes(startCol, startRow, goalCol, goalRow);
        if (startCol == goalCol && startRow == goalRow) {

        } else if (mainGame.pathFinder.search()) {
            int nextX = mainGame.pathFinder.pathList.get(0).col * mainGame.tileSize;
            int nextY = mainGame.pathFinder.pathList.get(0).row * mainGame.tileSize;

            decideMovement(nextX, nextY);

            nextCol1 = mainGame.pathFinder.pathList.get(0).col;
            nextRow1 = mainGame.pathFinder.pathList.get(0).row;
            if (mainGame.pathFinder.pathList.size() >= 2) {
                nextCol2 = mainGame.pathFinder.pathList.get(1).col;
                nextRow2 = mainGame.pathFinder.pathList.get(1).row;
            }
            if (mainGame.pathFinder.pathList.size() >= 3) {
                nextCol3 = mainGame.pathFinder.pathList.get(2).col;
                nextRow3 = mainGame.pathFinder.pathList.get(2).row;
            }
            if (mainGame.pathFinder.pathList.size() >= 4) {
                nextCol4 = mainGame.pathFinder.pathList.get(3).col;
                nextRow4 = mainGame.pathFinder.pathList.get(3).row;
            }
            if (nextCol1 == goalCol && nextRow1 == goalRow) {
                onPath = false;
            }
        }
    }

    public void trackPath(int goalCol, int goalRow) {
        int nextX = nextCol1 * mainGame.tileSize;
        int nextY = nextRow1 * mainGame.tileSize;
        if ((worldX + collisionBox.x) / mainGame.tileSize == nextCol1 && (worldY + collisionBox.y) / mainGame.tileSize == nextRow1) {
            nextX = nextCol2 * mainGame.tileSize;
            nextY = nextRow2 * mainGame.tileSize;
            if ((worldX + collisionBox.x) / mainGame.tileSize / mainGame.tileSize == nextCol2 * mainGame.tileSize && (worldY + collisionBox.y) / mainGame.tileSize / mainGame.tileSize == nextRow2 * mainGame.tileSize) {
                nextX = nextCol3 * mainGame.tileSize;
                nextY = nextRow3 * mainGame.tileSize;
                if ((worldX + collisionBox.x) / mainGame.tileSize / mainGame.tileSize == nextCol3 * mainGame.tileSize && (worldY + collisionBox.y) / mainGame.tileSize / mainGame.tileSize == nextRow3 * mainGame.tileSize) {
                    nextX = nextCol4 * mainGame.tileSize;
                    nextY = nextRow4 * mainGame.tileSize;
                }
            }
        }
        if (nextCol1 == goalCol && nextRow1 == goalRow) {
            onPath = false;
        } else {
            decideMovement(nextX, nextY);
        }
    }
}
