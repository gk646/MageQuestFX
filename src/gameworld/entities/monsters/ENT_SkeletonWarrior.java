package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.EntityPreloader;
import gameworld.player.Player;
import gameworld.player.abilities.enemies.PRJ_AttackCone;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;


public class ENT_SkeletonWarrior extends ENTITY {
    private boolean attack1, attack2, attack3;

    /**
     * Standard enemy  / hits you when he's close
     *
     * @param level  the level / also sets hp of the enemy
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_SkeletonWarrior(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        this.mg = mg;
        this.animation = EntityPreloader.skeletonWarrior;

        this.zone = zone;
        //Setting default values
        this.maxHealth = (9 + level) * (level + level - 1);
        if (level == 1) {
            maxHealth = 5;
        }
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
        this.searchTicks = 60;
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
    }

    @Override
    public void update() {
        super.update();
        if (collidingWithPlayer && !onPath && !attack2 && !attack3 && !attack1) {
            if (Math.random() < 0.33f) {
                mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2 * level));
                attack1 = true;
            } else if (Math.random() < 0.66f) {
                attack2 = true;
                mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2 * level));
            } else {
                attack3 = true;
                mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2.3f * level));
            }
            animation.playRandomSoundFromXToIndex(0, 2);
            spriteCounter = 0;
            collidingWithPlayer = false;
        }
        onPath = !attack2 && !attack3 && !attack1;
        if (onPath) {
            getNearestPlayer();
            searchPath(goalCol, goalRow, 16);
        }
        hitDelay++;
        searchTicks++;
    }


    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            drawAttack2(gc);
        } else if (attack3) {
            drawAttack3(gc);
        } else {
            if (onPath) {
                drawWalk(gc);
            } else {
                drawIdle(gc);
            }
        }
        spriteCounter++;
        drawBuffsAndDeBuffs(gc);
    }


    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX - 20, screenY - 6);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX - 20, screenY - 6);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX - 20, screenY - 6);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX - 20, screenY - 6);
            case 4 -> gc.drawImage(animation.idle.get(4), screenX - 20, screenY - 6);
            case 5 -> gc.drawImage(animation.idle.get(5), screenX - 20, screenY - 6);
            case 6 -> gc.drawImage(animation.idle.get(6), screenX - 20, screenY - 6);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX - 35, screenY - 6);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX - 35, screenY - 6);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX - 35, screenY - 6);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX - 35, screenY - 6);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX - 35, screenY - 6);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX - 35, screenY - 6);
            case 6 -> gc.drawImage(animation.walk.get(6), screenX - 35, screenY - 6);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX - 20, screenY - 14);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX - 20, screenY - 14);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX - 20, screenY - 14);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX - 20, screenY - 14);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX - 20, screenY - 14);
            case 5 -> attack1 = false;
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.attack2.get(0), screenX - 33, screenY - 23);
            case 1 -> gc.drawImage(animation.attack2.get(1), screenX - 33, screenY - 23);
            case 2 -> gc.drawImage(animation.attack2.get(2), screenX - 33, screenY - 23);
            case 3 -> gc.drawImage(animation.attack2.get(3), screenX - 33, screenY - 23);
            case 4 -> gc.drawImage(animation.attack2.get(4), screenX - 33, screenY - 23);
            case 5 -> gc.drawImage(animation.attack2.get(5), screenX - 33, screenY - 23);
            case 6 -> attack2 = false;
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.attack3.get(0), screenX - 25, screenY - 14);
            case 1 -> gc.drawImage(animation.attack3.get(1), screenX - 25, screenY - 14);
            case 2 -> gc.drawImage(animation.attack3.get(2), screenX - 25, screenY - 14);
            case 3 -> gc.drawImage(animation.attack3.get(3), screenX - 25, screenY - 14);
            case 4 -> attack3 = false;
        }
    }

    private void drawDeath(GraphicsContext gc) {
        switch (spriteCounter % 175 / 35) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX - 32, screenY - 52);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX - 32, screenY - 52);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX - 32, screenY - 52);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX - 32, screenY - 52);
            case 4 -> AfterAnimationDead = true;
        }
    }

    @Override
    public void playGetHitSound() {
        if (System.currentTimeMillis() - timeSinceLastDamageSound >= 3_500) {
            timeSinceLastDamageSound = System.currentTimeMillis();
            //animation.playGetHitSound(4);
        }
    }
}
