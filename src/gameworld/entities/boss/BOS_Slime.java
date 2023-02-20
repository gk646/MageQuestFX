package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.entities.loadinghelper.AnimationContainer;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;
import main.system.enums.Zone;

import java.awt.Rectangle;

public class BOS_Slime extends BOSS {
    private final AnimationContainer anim = new AnimationContainer("BossSlime");


    public BOS_Slime(MainGame mg, int x, int y, int level, int health, Zone zone) {
        super(mg, x, y, level, health, zone);
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        movementSpeed = 2;
        this.enemyImage = Storage.BigSLimewalk1;
        anim.loadImages();
    }


    /**
     * aasdfsdf
     */

    @Override
    public void update() {
        super.update();
        //onPath = !playerTooFarAbsoluteBoss(1000) && (worldX + 24) / 48 != mg.playerX || (worldY + 24) / 48 != mg.playerX;
        searchTicks++;
        if (health < 0.7 * maxHealth) {
            if (searchTicks % 120 == 0) {
                slimeCone();
            }
            if (!playerTooFarAbsolute() && searchTicks % 240 == 0) {
                slimeVolley();
            }
        }
        getNearestPlayer();
        //searchPathUncapped(goalCol, goalRow, 40);
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX - 49);
        screenY = (int) (worldY - Player.worldY + Player.screenY - 45 - 48);
        if (onPath) {
            switch (spriteCounter % 180 / 30) {
                case 0 -> gc.drawImage(anim.walk.get(0), screenX, screenY);
                case 1 -> gc.drawImage(anim.walk.get(1), screenX, screenY);
                case 2 -> gc.drawImage(anim.walk.get(2), screenX, screenY);
                case 3 -> gc.drawImage(anim.walk.get(3), screenX, screenY);
                case 4 -> gc.drawImage(anim.walk.get(4), screenX, screenY);
                case 5 -> gc.drawImage(anim.walk.get(5), screenX, screenY);
            }
        } else if (isOnPlayer()) {
            drawAttack1(gc);
        } else {
            gc.drawImage(anim.walk.get(0), screenX, screenY);
        }

        spriteCounter++;
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(anim.attack1.get(0), screenX, screenY);
            case 1 -> gc.drawImage(anim.attack1.get(1), screenX, screenY);
            case 2 -> gc.drawImage(anim.attack1.get(2), screenX, screenY);
            case 3 -> gc.drawImage(anim.attack1.get(3), screenX, screenY);
            case 4 -> gc.drawImage(anim.attack1.get(4), screenX, screenY);
            case 5 -> gc.drawImage(anim.attack1.get(5), screenX, screenY);
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(anim.attack2.get(0), screenX, screenY);
            case 1 -> gc.drawImage(anim.attack2.get(1), screenX, screenY);
            case 2 -> gc.drawImage(anim.attack2.get(2), screenX, screenY);
            case 3 -> gc.drawImage(anim.attack2.get(3), screenX, screenY);
            case 4 -> gc.drawImage(anim.attack2.get(4), screenX, screenY);
            case 5 -> gc.drawImage(anim.attack2.get(5), screenX, screenY);
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(anim.attack3.get(0), screenX, screenY);
            case 1 -> gc.drawImage(anim.attack3.get(1), screenX, screenY);
            case 2 -> gc.drawImage(anim.attack3.get(2), screenX, screenY);
            case 3 -> gc.drawImage(anim.attack3.get(3), screenX, screenY);
        }
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
}
