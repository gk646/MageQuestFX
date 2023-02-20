package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;
import main.system.enums.Zone;

import java.awt.Rectangle;

public class BOS_Slime extends BOSS {

    public BOS_Slime(MainGame mg, int x, int y, int level, int health, Zone zone) {
        super(mg, x, y, level, health, zone);
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        movementSpeed = 2;
        this.enemyImage = Storage.BigSLimewalk1;
        getImages();
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (onPath) {
            switch (spriteCounter % 180 / 30) {
                case 0 -> gc.drawImage(walk1, screenX, screenY);
                case 1 -> gc.drawImage(walk2, screenX, screenY);
                case 2 -> gc.drawImage(walk3, screenX, screenY);
                case 3 -> gc.drawImage(walk4, screenX, screenY);
                case 4 -> gc.drawImage(walk5, screenX, screenY);
                case 5 -> gc.drawImage(walk6, screenX, screenY);
            }
        } else {
            gc.drawImage(enemyImage, screenX, screenY, 48, 48);
        }
        spriteCounter++;
    }


    /**
     *
     */
    @Override
    public void update() {
        super.update();
        onPath = !playerTooFarAbsolute() && (worldX + 24) / 48 != mg.playerX || (worldY + 24) / 48 != mg.playerX;
        searchTicks++;
        if (searchTicks % 120 == 0) {
            slimeCone();
        }
        if (!playerTooFarAbsolute() && searchTicks % 240 == 0) {
            slimeVolley();
        }
        getNearestPlayer();
        searchPathUncapped(goalCol, goalRow, 30);
    }

    private void slimeVolley() {
        for (int i = 0; i < 720; i += 4) {
            mg.PROJECTILES.add(new PRJ_EnemyStandardShot((int) worldX, (int) worldY, level, (int) (worldX + (150 * Math.cos(i % 360 * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin(i % 360 * (Math.PI / 180))))));
        }
    }

    private void slimeCone() {
        int angle = (int) (Math.atan2(Player.worldY - worldY, Player.worldX - worldX) * (180 / Math.PI));
        for (int i = -45; i < 45; i += 4) {
            mg.PROJECTILES.add(new PRJ_EnemyStandardShot((int) worldX, (int) worldY, level, (int) (worldX + (150 * Math.cos((angle + i) * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin((angle + i) * (Math.PI / 180))))));
        }
    }

    private void getImages() {
        this.walk1 = Storage.BigSLimewalk1;
        this.walk2 = Storage.BigSLimewalk2;
        this.walk3 = Storage.BigSLimewalk3;
        this.walk4 = Storage.BigSLimewalk4;
        this.walk5 = Storage.BigSLimewalk5;
        this.walk6 = Storage.BigSLimewalk6;
    }
}
