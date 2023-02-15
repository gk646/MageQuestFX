package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;
import main.system.enums.Zone;

import java.awt.Rectangle;


public class ENT_Shooter extends ENTITY {
    private int shotCooldown;


    /**
     * Main Enemy class
     *
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_Shooter(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        this.zone = zone;
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
        this.collisionBox = new Rectangle(8, 10, 30, 30);
        this.onPath = false;
        getDisplayImage();
        this.searchTicks = 60;
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
    }

    @Override
    public void update() {
        onPath = !playerTooFarAbsolute() && (worldX + 24) / 48 != mg.playerX || (worldY + 24) / 48 != mg.playerX;
        if (shotCooldown >= 80 && !playerTooFarAbsolute()) {
            mg.PROJECTILES.add(new PRJ_EnemyStandardShot(mg, (int) worldX, (int) worldY, level, (int) Player.worldX, (int) Player.worldY));
            shotCooldown = 0;
        }
        getNearestPlayer();
        searchPath(goalCol, goalRow, 16);
        searchTicks++;
        shotCooldown++;
        if (hpBarCounter >= 600) {
            hpBarOn = false;
            hpBarCounter = 0;
        } else if (hpBarOn) {
            hpBarCounter++;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        gc.drawImage(enemyImage, screenX, screenY, 48, 48);
    }


    private void getDisplayImage() {
        enemyImage = Storage.shooterImage1;
    }


    private void shooterMovement() {
        if (mg.client) {
            if (onPath && searchTicks >= Math.random() * 55) {
                getNearestPlayerMultiplayer();
                searchPath(goalCol, goalRow, 16);
                searchTicks = 0;
            } else if (onPath) {
                trackPath(goalCol, goalRow);
            }
        } else {
            if (onPath && searchTicks >= Math.random() * 55) {
                getNearestPlayer();
                searchPath(goalCol, goalRow, 16);
                searchTicks = 0;
            } else if (onPath) {
                trackPath(goalCol, goalRow);
            }
        }
    }

    private void getNearestCircularTile() {

    }
}
