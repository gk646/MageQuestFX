package gameworld.entities.monsters;

import gameworld.Entity;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Grunt extends Entity {
    /**
     * Main Enemy class
     *
     * @param level  super();
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public Grunt(MainGame mg, int worldX, int worldY, int level) {
        this.mg = mg;
        //Setting default values
        this.maxHealth = (9 + level) * (level + level - 1);
        this.health = maxHealth;
        this.worldX = worldX;
        this.worldY = worldY;
        movementSpeed = 2;
        this.level = level;
        direction = "updownleftright";
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        this.onPath = false;
        getDisplayImage();
        this.searchTicks = 60;
        updatePos();
    }


    public void update() {
        screenX = worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
        onPath = !playerTooFarAbsolute() && (worldX / 48 != mg.player.worldX / 48 || worldY / 48 != mg.player.worldY / 48);
        gruntMovement();
        hitDelay++;
        searchTicks++;
    }

    public void updatePos() {
        screenX = worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(enemyImage, screenX, screenY, 48, 48, null);
    }

    private void getDisplayImage() {
        enemyImage = mg.imageSto.gruntImage1;
    }

    private void gruntMovement() {
        if (mg.client && onPath) {
            if (searchTicks >= Math.random() * 45) {
                getNearestPlayerMultiplayer();
                searchPath(goalCol, goalRow, 16);
                searchTicks = 0;
            } else {
                trackPath(goalCol, goalRow);
            }
        } else if (onPath) {
            if (searchTicks >= Math.random() * 45) {
                getNearestPlayer();
                searchPath(goalCol, goalRow, 16);
                searchTicks = 0;
            } else {
                trackPath(goalCol, goalRow);
            }
        }
    }
}
