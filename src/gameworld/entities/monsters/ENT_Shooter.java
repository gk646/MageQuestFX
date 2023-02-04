package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;

import java.awt.Rectangle;


public class ENT_Shooter extends ENTITY {
    private int shotCooldown;


    /**
     * Main Enemy class
     *
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_Shooter(MainGame mg, int worldX, int worldY, int level) {
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

    @Override
    public void update() {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        onPath = !playerTooFarAbsolute() && (worldX / 48 != (Player.worldX) / 48 || worldY / 48 != (Player.worldY) / 48);
        if (shotCooldown >= 80 && !playerTooFarAbsolute()) {
            mg.PRJControls.add(new PRJ_EnemyStandardShot(mg, worldX, worldY, level));
            shotCooldown = 0;
        }
        getNearestPlayer();
        searchPath(goalCol, goalRow, 16);
        searchTicks++;
        shotCooldown++;
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.drawImage(enemyImage, screenX, screenY, 48, 48);
    }

    private void updatePos() {
        screenX = (int) (worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2 - 24);
        screenY = (int) (worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24);
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
