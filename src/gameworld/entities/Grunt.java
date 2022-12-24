package gameworld.entities;

import gameworld.Entity;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Grunt extends Entity {
    /**
     * Main Enemy class
     *
     * @param mainGame super();
     * @param worldX   coordinates X
     * @param worldY   coordinates Y
     */
    public Grunt(MainGame mainGame, int worldX, int worldY, int level) {
        super(mainGame);
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
        if (!playerTooFarAbsolute() && (worldX / mg.tileSize != mg.player.worldX / mg.tileSize || worldY / mg.tileSize != mg.player.worldY / mg.tileSize)) {
            onPath = true;
        }
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
        if (mg.client) {
            if (onPath && searchTicks >= Math.random() * 45) {
                getNearestPlayerMultiplayer();
                searchPath(goalCol, goalRow);
                searchTicks = 0;
            } else if (onPath) {
                trackPath(goalCol, goalRow);
            }
        } else {
            if (onPath && searchTicks >= Math.random() * 45) {
                getNearestPlayer();
                searchPath(goalCol, goalRow);
                searchTicks = 0;
            } else if (onPath) {
                trackPath(goalCol, goalRow);
            }
        }
    }
}
