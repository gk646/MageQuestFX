package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.awt.Rectangle;


public class ENT_Grunt extends ENTITY {
    /**
     * Standard enemy  / hits you when he's close
     *
     * @param level  the level / also sets hp of the enemy
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_Grunt(int worldX, int worldY, int level) {
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
        screenX = worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
        onPath = !playerTooFarAbsolute() && (worldX / 48 != Player.worldX / 48 || worldY / 48 != Player.worldY / 48);
        gruntMovement();
        hitDelay++;
        searchTicks++;
    }

    private void updatePos() {
        screenX = worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(enemyImage, screenX, screenY, 48, 48);
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
