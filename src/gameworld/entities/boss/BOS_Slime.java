package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;

import java.awt.Rectangle;

public class BOS_Slime extends BOSS {

    public BOS_Slime(MainGame mg, int x, int y, int level, int health) {
        this.mg = mg;
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        this.worldX = x;
        this.worldY = y;
        this.level = level;
        this.maxHealth = health;
        this.enemyImage = Storage.gruntImage1;
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        gc.drawImage(enemyImage, screenX, screenY, 48, 48);
    }

    /**
     *
     */
    @Override
    public void update() {
        searchTicks++;
        if (searchTicks % 120 == 0) {
            slimeCone();
        }
    }

    private void slimeVolley() {
        for (int i = 0; i < 720; i++) {
            mg.PROJECTILES.add(new PRJ_EnemyStandardShot(mg, worldX, worldY, level, (int) (worldX + (150 * Math.cos(i % 360 * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin(i % 360 * (Math.PI / 180))))));
        }
    }

    private void slimeCone() {
        int angle = (int) (Math.atan2(Player.worldY - worldY, Player.worldX - worldX) * (180 / Math.PI));
        for (int i = -45; i < 45; i++) {
            mg.PROJECTILES.add(new PRJ_EnemyStandardShot(mg, worldX, worldY, level, (int) (worldX + (150 * Math.cos((angle + i) * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin((angle + i) * (Math.PI / 180))))));
        }
    }
}
