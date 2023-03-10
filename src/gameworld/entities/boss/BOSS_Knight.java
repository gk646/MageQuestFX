package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_AttackCone;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;

public class BOSS_Knight extends BOSS {
    public boolean active;
    private int healthPackCounter = 0;
    public boolean activate;
    private boolean attack1, attack2, attack3, attack4, attack5;

    public BOSS_Knight(MainGame mg, int x, int y, int level, int health, Zone zone) {
        super(mg, x, y, level, health, zone);
        this.animation = new ResourceLoaderEntity("enemies/BOSSKnight");
        animation.load();
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        movementSpeed = 3;
        name = "Stone Knight";
        //animation.playRandomSoundFromXToIndex(7, 7);
    }

    @Override
    public void update() {
        super.update();
        hpBarOn = false;
        if (active) {
            if (health < 0.5 * maxHealth) {
                healthPackCounter = 0;
                if (searchTicks % 240 == 0) {
                }
                if (searchTicks % 480 == 0) {

                }
                standardAttackScript();
            } else {
                standardAttackScript();
            }
            if (!attack2 && !attack3 && !attack1 && !attack4 && !attack5) {
                onPath = true;
                getNearestPlayer();
                searchPathBigEnemies(goalCol, goalRow, 30);
            }
            hitDelay++;
            searchTicks++;
            healthPackCounter++;
        }
    }

    private void standardAttackScript() {
        if (closeToPlayer(40) && !attack2 && !attack3 && !attack1 && !attack4 && !attack5) {
            if (Math.random() < 0.33f) {
                attack1 = true;
                animation.playRandomSoundFromXToIndex(1, 1);
            } else {
                attack4 = true;
                animation.playRandomSoundFromXToIndex(0, 0);
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
        screenX = (int) (worldX - Player.worldX + Player.screenX - 28);
        screenY = (int) (worldY - Player.worldY + Player.screenY - 75);
        if (!active) {
            drawPray(gc);
        } else if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            drawAttack2(gc);
        } else if (attack3) {
            drawAttack3(gc);
        } else if (attack4) {
            drawAttack4(gc);
        } else if (attack5) {
            drawAttack5(gc);
        } else if (onPath) {
            drawRun(gc);
        } else {
            drawIdle(gc);
        }
        if (active) {
            drawBossHealthBar(gc);
        }
        spriteCounter++;
    }

    private void drawRun(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.run.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.run.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.run.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.run.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.run.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.run.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.run.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.run.get(7), screenX, screenY);
        }
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 200 / 25) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack1.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.attack1.get(7), screenX, screenY);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack1.get(6), screenX, screenY);
            case 7 -> attack1 = false;
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.attack2.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack2.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack2.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack2.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack2.get(4), screenX, screenY);
            case 5 -> attack2 = false;
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

    private void drawAttack4(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.attack4.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack4.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack4.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack4.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack4.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack4.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack4.get(6), screenX, screenY);
            case 7 -> attack4 = false;
        }
    }

    private void drawAttack5(GraphicsContext gc) {
        switch (spriteCounter % 140 / 20) {
            case 0 -> gc.drawImage(animation.attack5.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack5.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack5.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack5.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack5.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack5.get(5), screenX, screenY);
            case 6 -> attack5 = false;
        }
    }

    private void drawDeath(GraphicsContext gc) {
        mg.sound.fadeOut(mg.sound.BossMusic1, 1, 5);
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX, screenY);
            case 4 -> AfterAnimationDead = true;
        }
    }

    private void drawPray(GraphicsContext gc) {
        if (activate) {
            switch (spriteCounter % 120 / 30) {
                case 0 -> {
                    gc.drawImage(animation.idle2.get(9), screenX, screenY);
                    return;
                }
                case 1 -> {
                    gc.drawImage(animation.idle2.get(10), screenX, screenY);
                    return;
                }
                case 2 -> {
                    gc.drawImage(animation.idle2.get(11), screenX, screenY);
                    return;
                }
                case 3 -> {
                    mg.sound.BossMusic1.seek(Duration.ZERO);
                    mg.sound.BossMusic1.play();
                    active = true;
                    return;
                }
            }
        }
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.idle2.get(2), screenX, screenY);
            case 1 -> gc.drawImage(animation.idle2.get(3), screenX, screenY);
            case 2 -> gc.drawImage(animation.idle2.get(4), screenX, screenY);
            case 3 -> gc.drawImage(animation.idle2.get(5), screenX, screenY);
            case 4 -> gc.drawImage(animation.idle2.get(6), screenX, screenY);
            case 5 -> gc.drawImage(animation.idle2.get(7), screenX, screenY);
            case 6 -> gc.drawImage(animation.idle2.get(8), screenX, screenY);
        }
    }

    private void drawSpecial(GraphicsContext gc) {
        switch (spriteCounter % 180 / 20) {
            case 0 -> gc.drawImage(animation.attack5.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack5.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack5.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack5.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack5.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack5.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack5.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.attack5.get(7), screenX, screenY);
            case 8 -> healthPackCounter = 0;
        }
    }
}
