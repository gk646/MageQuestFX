package gameworld.entities;

import gameworld.entities.companion.ENT_Owly;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Rectangle;

/**
 * Main inheritable class for all entities
 *
 * @see ENT_Grunt
 * @see ENT_Owly
 * @see ENT_Shooter
 */
abstract public class ENTITY {
    protected int spriteCounter;
    protected int goalCol;
    protected int goalRow;
    protected Image entityImage2;
    protected Image entityImage3;
    protected Image entityImage4;
    protected Image entityImage5;
    protected Image entityImage6;
    protected Image enemyImage;
    public int entityWidth;
    public int entityHeight;
    protected int searchTicks;
    public boolean onPath;
    public float worldY;
    public float worldX;
    public int screenX;
    public int screenY;
    public int health;
    public int maxHealth;
    public float movementSpeed;
    public int level;
    public Image entityImage1;
    public int hitDelay;
    public boolean collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public boolean dead;
    public MainGame mg;
    public String direction;
    public Rectangle collisionBox;
    public boolean hpBarOn;
    protected int hpBarCounter;
    private int nextCol1;
    private int nextRow1;
    private int nextCol2;
    private int nextRow2;
    private int nextCol3;
    private int nextRow3;
    private int nextCol4;
    private int nextRow4;

    /**
     * Returns boolean value of: Is the player further than 650 worldPixels away
     *
     * @return the player being away more than 650 worldPixels
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean playerTooFarAbsolute() {
        return Math.abs(worldX - Player.worldX) >= 650 || Math.abs(worldY - Player.worldY) >= 650;
    }


    private void decideMovement(int nextX, int nextY) {
        float enLeftX = worldX;
        float enRightX = (worldX + entityWidth);
        float enTopY = worldY;
        float enBottomY = (worldY + entityHeight);
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        mg.collisionChecker.checkEntityAgainstTile(this);
        //mg.ob_control.checkCollisionEntity(this);
        if (enLeftX < nextX && !collisionRight) {
            worldX += movementSpeed;
        } else if (enLeftX > nextX && !collisionLeft) {
            worldX -= movementSpeed;
        } else if (enTopY < nextY && !collisionDown) {
            worldY += movementSpeed;
        } else if (enTopY > nextY && !collisionUp) {
            worldY -= movementSpeed;
        } else if (enRightX > nextX) {
            worldX -= movementSpeed;
        } else if (enRightX < nextX) {
            worldX += movementSpeed;
        } else if (enBottomY > nextY) {
            worldX -= movementSpeed;
        } else if (enBottomY < nextX) {
            worldX += movementSpeed;
        }
    }

    /**
     * Searches a path from active tile to goalTile.
     * Limited to a distance of 16 in both directions
     *
     * @param goalCol     the column of the goal tile
     * @param goalRow     the row of the goal tile
     * @param maxDistance max distance to set nodes in both directions
     */
    protected void searchPath(int goalCol, int goalRow, int maxDistance) {
        int startCol = (int) ((worldX + 24) / 48);
        int startRow = (int) ((worldY + 24) / 48);
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

    /**
     * Same as searchPath(int, int, int) but not limited in distance
     *
     * @param goalCol     the column of the goal tile
     * @param goalRow     the row of the goal tile
     * @param maxDistance the maximum distance that is searched in every direction
     * @see ENTITY#searchPath(int, int, int)
     */
    private void searchPathUncapped(int goalCol, int goalRow, int maxDistance) {
        int startCol = (int) ((worldX + 24) / 48);
        int startRow = (int) ((worldY + 24) / 48);
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

    /**
     * Tracks the next 4 tiles that have been saved through searchPath() without computing anymore paths
     *
     * @param goalCol goal tile x
     * @param goalRow goal tile y
     */
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

    protected void followPlayer(int playerX, int playerY) {
        if (!((worldX) / 48 == playerX && (worldY) / 48 == playerY)) {
            searchPathUncapped(playerX, playerY, 150);
        } else {
            onPath = false;
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
        if (Math.abs(Player.worldX - this.worldX + Player.worldY - this.worldY) < Math.abs(mg.ENTPlayer2.worldX - this.worldX + mg.ENTPlayer2.worldY - this.worldY)) {
            this.goalCol = (int) ((Player.worldX + 24) / 48);
            this.goalRow = (int) ((Player.worldY + 24) / 48);
        } else {
            this.goalCol = (int) ((mg.ENTPlayer2.worldX + mg.player.collisionBox.x) / mg.tileSize);
            this.goalRow = (int) ((mg.ENTPlayer2.worldY + mg.player.collisionBox.y) / mg.tileSize);
        }
    }

    protected void getNearestPlayerMultiplayer() {
        if (Math.abs(Player.worldX - this.worldX + Player.worldY - this.worldY) < Math.abs(mg.ENTPlayer2.worldX - this.worldX + mg.ENTPlayer2.worldY - this.worldY)) {
            this.goalCol = (int) ((Player.worldX + 24) / mg.tileSize);
            this.goalRow = (int) ((Player.worldY + 24) / mg.tileSize);
        } else {

            this.goalCol = (int) ((mg.ENTPlayer2.worldX + mg.player.collisionBox.x) / mg.tileSize);
            this.goalRow = (int) ((mg.ENTPlayer2.worldY + mg.player.collisionBox.y) / mg.tileSize);
        }
    }


    abstract public void draw(GraphicsContext gc);

    abstract public void update();
}

/*


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
 */
