package gameworld;

import gameworld.entities.Owly;
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
    private final Color red = new Color(0xFF0044);
    private final Color white = new Color(0xFFFFFF);
    protected BufferedImage entityImage2;
    protected BufferedImage entityImage3;
    protected BufferedImage entityImage4;
    protected BufferedImage entityImage5;
    protected BufferedImage entityImage6;
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

    public Entity() {

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
                    if (entity.hitDelay >= 30 && mg.collisionChecker.checkEntityAgainstEntity(mg.player, entity)) {
                        mg.player.health -= entity.level;
                        mg.player.getDurabilityDamageArmour();
                        entity.hitDelay = 0;
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


    /**
     * Used for drawing health bars
     */
    public void draw(Graphics2D g2) {
        try {
            for (Entity entity1 : mg.ENTITIES) {
                entity1.draw(g2);
                if (!(entity1 instanceof Owly)) {
                    if (entity1.hpBarOn) {
                        g2.setColor(red);
                        g2.fillRect(entity1.screenX, entity1.screenY - 10, (int) (((float) entity1.health / entity1.maxHealth) * 48), 8);
                        g2.setColor(white);
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
        mg.ENTITIES.add(new Owly(mg, mg.player.worldX + 50, mg.player.worldY + 50, 10));
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
        if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + 48) {
            worldY -= movementSpeed;
        } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + 48) {
            worldY += movementSpeed;
        } else if (enTopY >= nextY && enBottomY < nextY + 48) {
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

    protected void searchPath(int goalCol, int goalRow, int maxDistance) {
        int startCol = (worldX + 24) / 48;
        int startRow = (worldY + 24) / 48;
        mg.pathF.setNodes(startCol, startRow, goalCol, goalRow, maxDistance);
        if (startCol == goalCol && startRow == goalRow) {
            onPath = false;
        } else if (mg.pathF.search()) {
            int nextX = mg.pathF.pathList.get(0).col * 48;
            int nextY = mg.pathF.pathList.get(0).row * 48;
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

    protected void searchPathUncapped(int goalCol, int goalRow, int maxDistance) {
        int startCol = (worldX + 24) / 48;
        int startRow = (worldY + 24) / 48;
        mg.pathF.setNodes(startCol, startRow, goalCol, goalRow, maxDistance);
        if (startCol == goalCol && startRow == goalRow) {
            onPath = false;
        } else if (mg.pathF.searchUncapped()) {
            int nextX = mg.pathF.pathList.get(0).col * 48;
            int nextY = mg.pathF.pathList.get(0).row * 48;
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
        int nextX = nextCol1 * 48;
        int nextY = nextRow1 * 48;
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

    protected void followPlayer() {
        int playerX = (mg.player.worldX + 24) / 48;
        int playerY = (mg.player.worldY + 24) / 48;
        if (!((worldX) / 48 == playerX && (worldY) / 48 == playerY)) {
            searchPathUncapped(playerX, playerY, 150);
        }
    }

    public void moveToTile(int x, int y) {
        if (onPath) {
            if (!((worldX) / 48 == x && (worldY) / 48 == y)) {
                searchPathUncapped(x, y, 100);
            } else {
                onPath = false;
            }
        }
    }

    protected void getNearestPlayer() {
        if (Math.abs(mg.player.worldX - this.worldX + mg.player.worldY - this.worldY) < Math.abs(mg.player2.worldX - this.worldX + mg.player2.worldY - this.worldY)) {
            this.goalCol = (mg.player.worldX + mg.player.entityWidth / 2) / mg.tileSize;
            this.goalRow = (mg.player.worldY + mg.player.entityHeight / 2) / mg.tileSize;
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
