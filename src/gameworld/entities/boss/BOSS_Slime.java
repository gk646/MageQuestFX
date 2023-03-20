package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.player.abilities.enemies.PRJ_AcidBreath;
import gameworld.player.abilities.enemies.PRJ_AttackCone;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.Storage;
import main.system.enums.Zone;

import java.awt.Rectangle;

public class BOSS_Slime extends BOSS {

    private boolean attack1, attack2, attack3, spitting;


    public BOSS_Slime(MainGame mg, int x, int y, int level, int health, Zone zone) {
        super(mg, x, y, level, health, zone);
        this.animation = new ResourceLoaderEntity("enemies/BossSlime");
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        movementSpeed = 2;
        name = "Big Bloated";
        this.enemyImage = Storage.BigSLimewalk1;
        animation.load();
        animation.playRandomSoundFromXToIndex(7, 7);
    }


    /**
     * aasdfsdf
     */

    @Override
    public void update() {
        super.update();
        hpBarOn = false;
        spitting = false;
        if (health < 0.5 * maxHealth) {
            if (searchTicks % 240 == 0) {
                slimeCone();
                spitting = true;
                //TODO play acid breath sound
            }
            if (searchTicks % 480 == 0) {
                slimeVolley();
                spitting = true;
            }
            standardAttackScript();
        } else {
            standardAttackScript();
        }
        if (!attack2 && !attack3 && !attack1 && !spitting) {
            onPath = true;
            getNearestPlayer();
            searchPathBigEnemies(goalCol, goalRow, 30);
        }
        hitDelay++;
        searchTicks++;
    }

    private void standardAttackScript() {
        if (collidingWithPlayer && !onPath && !attack2 && !attack3 && !attack1) {
            if (Math.random() < 0.33f) {
                attack1 = true;
            } else if (Math.random() < 0.66f) {
                attack2 = true;
            } else {
                attack3 = true;
            }
            mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 70, 64, 64, -8, -8, 3 * level));
            //animation.playGetHitSound(3);
            spriteCounter = 0;
            collidingWithPlayer = false;
        }
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX - 49);
        screenY = (int) (worldY - Player.worldY + Player.screenY - 45 - 48);
        if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            drawAttack2(gc);
        } else if (attack3) {
            drawAttack3(gc);
        } else if (onPath) {
            drawWalk(gc);
        } else {
            drawIdle(gc);
        }

        drawBossHealthBar(gc);
        spriteCounter++;
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX, screenY);
        }
        if (spriteCounter % 90 / 15 == 0) {
            animation.playRandomSoundFromXToIndex(6, 6);
        }
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX, screenY);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX, screenY);
            case 6 -> attack1 = false;
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.attack2.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack2.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack2.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack2.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack2.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack2.get(5), screenX, screenY);
            case 6 -> attack2 = false;
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.attack3.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack3.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack3.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack3.get(3), screenX, screenY);
            case 4 -> attack3 = false;
        }
    }

    private void drawDeath(GraphicsContext gc) {
        switch (spriteCounter % 245 / 35) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX, screenY);
            case 4 -> AfterAnimationDead = true;
        }
    }

    private void slimeVolley() {
        for (int i = 0; i < 720; i += 4) {
            mg.PROJECTILES.add(new PRJ_AcidBreath((int) worldX, (int) worldY, level, (int) (worldX + (150 * Math.cos(i % 360 * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin(i % 360 * (Math.PI / 180))))));
        }
        animation.playRandomSoundFromXToIndex(4, 5);
    }

    private void slimeCone() {
        int angle = (int) (Math.atan2(Player.worldY - worldY, Player.worldX - worldX) * (180 / Math.PI));
        for (int i = -45; i < 45; i += 4) {
            mg.PROJECTILES.add(new PRJ_AcidBreath((int) worldX, (int) worldY, level, (int) (worldX + (150 * Math.cos((angle + i) * (Math.PI / 180)))), (int) (worldY + (150 * Math.sin((angle + i) * (Math.PI / 180))))));
        }
        animation.playRandomSoundFromXToIndex(4, 5);
    }

    @Override
    public void playGetHitSound() {
        if (System.currentTimeMillis() - timeSinceLastDamageSound >= 3_500) {
            timeSinceLastDamageSound = System.currentTimeMillis();
            animation.playRandomSoundFromXToIndex(0, 4);
        }
    }
}
