package gameworld;

import gameworld.entitys.Owly;
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
    protected int spriteCounter;
    protected int goalCol;
    protected int goalRow;
    protected BufferedImage entityImage2;
    protected BufferedImage entityImage3;
    protected BufferedImage entityImage4;
    protected BufferedImage entityImage5;
    protected BufferedImage entityImage6;
    protected BufferedImage entityImage7;
    protected BufferedImage entityImage8;
    protected BufferedImage entityImage9;
    protected BufferedImage entityImage10;
    protected BufferedImage enemyImage;
    public int worldY;
    public int worldX;
    public int entityWidth;
    public int entityHeight;
    public int screenX;
    public int screenY;
    public int health;
    public int maxHealth;
    public int movementSpeed;
    public int searchTicks;
    public int hitDelay;
    public int level;
    public BufferedImage entityImage1;
    public boolean onPath;
    public boolean collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public boolean dead;
    public MainGame mg;
    public String direction;
    public Rectangle collisionBox;
    boolean hpBarOn;
    private int hpBarCounter;
    private int nextCol1;
    private int nextRow1;
    private int nextCol2;
    private int nextRow2;
    private int nextCol3;
    private int nextRow3;
    private int nextCol4;
    private int nextRow4;
    private boolean initializeEnemies;

    public Entity(MainGame mg) {
        this.mg = mg;
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
            for (Entity entity : mg.ENTITIES) {
                entity.update();
                if (!(entity instanceof Owly)) {
                    if (mg.collisionChecker.checkEntityAgainstEntity(mg.player, entity)) {
                        // mainGame.player.health -= 1;
                    }
                    if (entity.hpBarCounter >= 600) {
                        entity.hpBarOn = false;
                        entity.hpBarCounter = 0;
                    }
                    if (entity.hpBarOn) {
                        entity.hpBarCounter++;
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void updatePos() {
        try {
            for (Entity entity : mg.ENTITIES) {
                entity.updatePos();
                if (mg.collisionChecker.checkEntityAgainstEntity(mg.player, entity)) {
                    mg.player.health -= 1;
                    if (mg.player.health <= 0) {
                        mg.gameState = mg.gameOver;
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
                    mg.ENTITIES.remove(entity);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void updatePosSingleplayer() {
        try {
            for (Entity entity : mg.ENTITIES) {
                entity.updatePos();
            }
        } catch (ConcurrentModificationException ignored) {

        }
    }

    /**
     * Used for drawing health bars
     */
    public void draw(Graphics2D g2) {
        try {
            for (Entity entity1 : mg.ENTITIES) {
                entity1.draw(g2);
                if (!(entity1 instanceof Owly)) {
                    if (entity1.hpBarOn) {
                        g2.setColor(new Color(0xFF0044));
                        g2.fillRect(entity1.screenX, entity1.screenY - 10, (int) (((float) entity1.health / entity1.maxHealth) * 48), 8);
                        g2.setColor(new Color(0xFFFFFF));
                        g2.setFont(mg.ui.maruMonica);
                        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
                        g2.drawString(entity1.health + "", entity1.screenX + 14, entity1.screenY);
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    private void spawnEnemies() {
        mg.ENTITIES.add(new Owly(mg, 23900, 23900, 15));
    }

    protected boolean playerTooFarAbsolute() {
        return Math.abs(worldX - mg.player.worldX) >= 650 || Math.abs(worldY - mg.player.worldY) >= 650;
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
        mg.collisionChecker.checkEntityAgainstTile(this);

        if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + mg.tileSize) {
            worldY -= movementSpeed;
        } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + mg.tileSize) {
            worldY += movementSpeed;
        } else if (enTopY >= nextY && enBottomY < nextY + mg.tileSize) {
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

    protected void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + collisionBox.x) / mg.tileSize;
        int startRow = (worldY + collisionBox.y) / mg.tileSize;
        mg.pathF.setNodes(startCol, startRow, goalCol, goalRow);
        if (startCol == goalCol && startRow == goalRow) {

        } else if (mg.pathF.search()) {
            int nextX = mg.pathF.pathList.get(0).col * mg.tileSize;
            int nextY = mg.pathF.pathList.get(0).row * mg.tileSize;
            decideMovement(nextX, nextY);
            nextCol1 = mg.pathF.pathList.get(0).col;
            nextRow1 = mg.pathF.pathList.get(0).row;
            if (mg.pathF.pathList.size() >= 2) {
                nextCol2 = mg.pathF.pathList.get(1).col;
                nextRow2 = mg.pathF.pathList.get(1).row;
            }
            if (mg.pathF.pathList.size() >= 3) {
                nextCol3 = mg.pathF.pathList.get(2).col;
                nextRow3 = mg.pathF.pathList.get(2).row;
            }
            if (mg.pathF.pathList.size() >= 4) {
                nextCol4 = mg.pathF.pathList.get(3).col;
                nextRow4 = mg.pathF.pathList.get(3).row;
            }
            if (nextCol1 == goalCol && nextRow1 == goalRow) {
                onPath = false;
            }
        }
    }

    protected void trackPath(int goalCol, int goalRow) {
        int nextX = nextCol1 * mg.tileSize;
        int nextY = nextRow1 * mg.tileSize;
        if ((worldX + collisionBox.x) / mg.tileSize == nextCol1 && (worldY + collisionBox.y) / mg.tileSize == nextRow1) {
            nextX = nextCol2 * mg.tileSize;
            nextY = nextRow2 * mg.tileSize;
            if ((worldX + collisionBox.x) / mg.tileSize / mg.tileSize == nextCol2 * mg.tileSize && (worldY + collisionBox.y) / mg.tileSize / mg.tileSize == nextRow2 * mg.tileSize) {
                nextX = nextCol3 * mg.tileSize;
                nextY = nextRow3 * mg.tileSize;
                if ((worldX + collisionBox.x) / mg.tileSize / mg.tileSize == nextCol3 * mg.tileSize && (worldY + collisionBox.y) / mg.tileSize / mg.tileSize == nextRow3 * mg.tileSize) {
                    nextX = nextCol4 * mg.tileSize;
                    nextY = nextRow4 * mg.tileSize;
                }
            }
        }
        if (nextCol1 == goalCol && nextRow1 == goalRow) {
            onPath = false;
        } else {
            decideMovement(nextX, nextY);
        }
    }

    protected void getNearestPlayer() {
        if (Math.abs(mg.player.worldX - this.worldX + mg.player.worldY - this.worldY) < Math.abs(mg.player2.worldX - this.worldX + mg.player2.worldY - this.worldY)) {
            this.goalCol = (mg.player.worldX + mg.player.collisionBox.x) / mg.tileSize;
            this.goalRow = (mg.player.worldY + mg.player.collisionBox.y) / mg.tileSize;
        } else {
            this.goalCol = (mg.player2.worldX + mg.player.collisionBox.x) / mg.tileSize;
            this.goalRow = (mg.player2.worldY + mg.player.collisionBox.y) / mg.tileSize;
        }
    }

    protected void getNearestPlayerMultiplayer() {
        if (Math.abs(mg.player.worldX - this.worldX + mg.player.worldY - this.worldY) < Math.abs(mg.player2.worldX - this.worldX + mg.player2.worldY - this.worldY)) {
            this.goalCol = (mg.player.worldX + mg.player.collisionBox.x) / mg.tileSize;
            this.goalRow = (mg.player.worldY + mg.player.collisionBox.y) / mg.tileSize;
        } else {

            this.goalCol = (mg.player2.worldX + mg.player.collisionBox.x) / mg.tileSize;
            this.goalRow = (mg.player2.worldY + mg.player.collisionBox.y) / mg.tileSize;
        }
    }
}
