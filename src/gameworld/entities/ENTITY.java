package gameworld.entities;

import gameworld.entities.companion.ENT_Owly;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.dmg_numbers.DamageNumber;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Main inheritable class for all entities
 *
 * @see ENT_Grunt
 * @see ENT_Owly
 * @see ENT_Shooter
 */
abstract public class ENTITY {
    public Dialog dialog;
    protected float health;
    public ArrayList<Effect> effects = new ArrayList<>();
    public Zone zone;
    protected int spriteCounter;
    protected int goalCol;
    protected int goalRow;
    protected float amountedDamageSinceLastDamageNumber;
    protected long timeSinceLastDamageNumber;
    protected long timeSinceLastDamageSound;
    public Image entityImage1;
    protected Image entityImage2;
    protected Image entityImage3;
    protected Image entityImage4;
    protected Image entityImage5;
    protected Image entityImage6;
    protected Image enemyImage;
    public Image walk1, walk2, walk3, walk4, walk5, walk6;

    public int entityWidth;
    public int entityHeight;
    protected int searchTicks;
    public boolean onPath;
    public float worldY;
    public float worldX;
    public int screenX;
    public int screenY;
    public int magicResistance;
    public int maxHealth;
    public float movementSpeed;
    public int level;

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
    public int nextCol1;
    public int nextRow1;
    public int nextCol2;
    public int nextRow2;
    public int nextCol3;
    public int nextRow3;
    public int nextCol4;
    public int nextRow4;

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
    public void searchPathUncapped(int goalCol, int goalRow, int maxDistance) {
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
        }
    }

    /**
     * Tracks the next 4 tiles that have been saved through searchPath() without computing anymore paths
     */
    protected void trackPath() {
        int nextX = nextCol1 * 48;
        int nextY = nextRow1 * 48;
        if ((int) (worldX / 48) == nextCol1 && (int) (worldY / 48) == nextRow1) {
            nextX = nextCol2 * mg.tileSize;
            nextY = nextRow2 * mg.tileSize;
        } else if ((int) (worldX / 48) == nextCol2 && (int) (worldY / 48) == nextRow2) {
            nextX = nextCol3 * mg.tileSize;
            nextY = nextRow3 * mg.tileSize;
        } else if ((int) (worldX / 48) == nextCol3 && (int) (worldY / 48) == nextRow3) {
            nextX = nextCol4 * mg.tileSize + 24;
            nextY = nextRow4 * mg.tileSize + 24;
        }
        decideMovement(nextX, nextY);
    }

    protected void followPlayer(int playerX, int playerY) {
        if ((worldX) / 48 == playerX && (worldY) / 48 == playerY) {
            onPath = false;
        } else {
            searchPathUncapped(playerX, playerY, 100);
        }
    }


    public void playGetHitSound() {

    }

    public Point getTilePosition() {
        return new Point((int) ((worldX + 24) / 48), (int) ((worldY + 24) / 48));
    }

    public void moveToTileSuperVised(int x, int y) {
        searchPathUncapped(x, y, 100);
    }


    protected void getNearestPlayer() {
        /*  if (Math.abs(Player.worldX - this.worldX + Player.worldY - this.worldY) < Math.abs(mg.ENTPlayer2.worldX - this.worldX + mg.ENTPlayer2.worldY - this.worldY)) {

         */
        this.goalCol = (int) ((Player.worldX + 24) / 48);
        this.goalRow = (int) ((Player.worldY + 24) / 48);
     /*
        } else {


            this.goalCol = (int) ((mg.ENTPlayer2.worldX + mg.player.collisionBox.x) / mg.tileSize);
            this.goalRow = (int) ((mg.ENTPlayer2.worldY + mg.player.collisionBox.y) / mg.tileSize);

        }
      */
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

    protected void tickEffects() {
        Iterator<Effect> iter = effects.iterator();
        while (iter.hasNext()) {
            Effect effect = iter.next();
            effect.tick(this);
            if (effect.rest_duration <= 0) {
                iter.remove();
            }
        }
    }


    public float getHealth() {
        return this.health;
    }

    abstract public void draw(GraphicsContext gc);

    public void update() {
        tickEffects();
        if (health <= 0) {
            dead = true;
        }
    }

    public void getDamageFromPlayer(float flat_damage, DamageType type) {
        switch (type) {
            case DarkDMG -> flat_damage += (flat_damage / 100.0f) * Player.effects[2];
            case FireDMG -> flat_damage += (flat_damage / 100.0f) * Player.effects[19];
            case ArcaneDMG -> flat_damage += (flat_damage / 100.0f) * Player.effects[1];
            case PoisonDMG -> flat_damage += (flat_damage / 100.0f) * Player.effects[18];
        }
        amountedDamageSinceLastDamageNumber += flat_damage;
        if (mg.random.nextInt(0, 101) <= Player.effects[21]) {
            flat_damage += (flat_damage / 100.0f) * Player.effects[22];
            mg.damageNumbers.add(new DamageNumber((int) flat_damage, type, this, true));
            timeSinceLastDamageNumber = System.currentTimeMillis();
            amountedDamageSinceLastDamageNumber = 0;
        }
        if (System.currentTimeMillis() - timeSinceLastDamageNumber >= 100 && amountedDamageSinceLastDamageNumber > 0.99) {
            mg.damageNumbers.add(new DamageNumber((int) amountedDamageSinceLastDamageNumber, type, this, false));
            amountedDamageSinceLastDamageNumber = 0;
            timeSinceLastDamageNumber = System.currentTimeMillis();
        }
        this.health -= flat_damage;
    }

    public void getDamage(float flat_damage) {
        this.health -= flat_damage;
    }
}
