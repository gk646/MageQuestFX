package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;

import java.awt.Rectangle;


public class ENT_Grunt extends ENTITY {
    /**
     * Standard enemy  / hits you when he's close
     *
     * @param level  the level / also sets hp of the enemy
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_Grunt(MainGame mg, int worldX, int worldY, int level) {
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
        this.collisionBox = new Rectangle(3, 3, 42, 42);
        this.onPath = false;
        getDisplayImage();
        this.searchTicks = 60;
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
    }

    @Override
    public void update() {
        onPath = !playerTooFarAbsolute() && (worldX / 48 != (Player.worldX + 24) / 48 || worldY / 48 != (Player.worldY + 24) / 48);
        getNearestPlayer();
        searchPath(goalCol, goalRow, 16);
        hitDelay++;
        searchTicks++;
    }

    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        gc.drawImage(enemyImage, screenX, screenY);
    }

    private void getDisplayImage() {
        enemyImage = Storage.gruntImage1;
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
