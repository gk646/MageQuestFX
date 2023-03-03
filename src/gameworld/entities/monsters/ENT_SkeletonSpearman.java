package gameworld.entities.monsters;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;


public class ENT_SkeletonSpearman extends ENTITY {
    private boolean attack1, attack2, attack3;

    /**
     * Standard enemy  / hits you when he's close
     *
     * @param level  the level / also sets hp of the enemy
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_SkeletonSpearman(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        this.mg = mg;
        this.animation = new ResourceLoaderEntity("enemies/skeletonSpear");
        animation.load();
        this.zone = zone;
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
        this.searchTicks = 60;
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
    }

    @Override
    public void update() {
        super.update();
        if (collidingWithPlayer && !onPath && !attack2 && !attack3 && !attack1) {
            if (Math.random() < 0.33f) {
                attack1 = true;
            } else if (Math.random() < 0.66f) {
                attack2 = true;
            } else {
                attack3 = true;
            }
            //animation.playRandomSoundFromXToIndex(0, 3);
            spriteCounter = 0;
            collidingWithPlayer = false;
        }
        if (!attack2 && !attack3 && !attack1) {
            onPath = true;
            getNearestPlayer();
            searchPath(goalCol, goalRow, 16);
        }
        hitDelay++;
        searchTicks++;
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
        if (attack1) {
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
    }


    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX - 15, screenY - 48);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX - 15, screenY - 48);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX - 15, screenY - 48);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX - 15, screenY - 48);
            case 4 -> gc.drawImage(animation.idle.get(4), screenX - 15, screenY - 48);
            case 5 -> gc.drawImage(animation.idle.get(5), screenX - 15, screenY - 48);
            case 6 -> gc.drawImage(animation.idle.get(6), screenX - 15, screenY - 48);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX - 15, screenY - 48);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX - 15, screenY - 48);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX - 15, screenY - 48);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX - 15, screenY - 48);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX - 15, screenY - 48);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX - 15, screenY - 48);
            case 6 -> gc.drawImage(animation.walk.get(6), screenX - 15, screenY - 48);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX - 15, screenY - 48);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX - 15, screenY - 48);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX - 15, screenY - 48);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX - 15, screenY - 48);
            case 4 -> attack1 = false;
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX - 15, screenY - 48);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX - 15, screenY - 48);
            case 2 -> gc.drawImage(animation.attack1.get(2), screenX - 15, screenY - 48);
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX - 15, screenY - 48);
            case 4 -> attack2 = false;
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.attack3.get(0), screenX - 15, screenY - 48);
            case 1 -> gc.drawImage(animation.attack3.get(1), screenX - 15, screenY - 48);
            case 2 -> gc.drawImage(animation.attack3.get(2), screenX - 15, screenY - 48);
            case 3 -> gc.drawImage(animation.attack3.get(3), screenX - 15, screenY - 48);
            case 4 -> gc.drawImage(animation.attack3.get(4), screenX - 15, screenY - 48);
            case 5 -> attack3 = false;
        }
    }

    @Override
    public void playGetHitSound() {
        if (System.currentTimeMillis() - timeSinceLastDamageSound >= 3_500) {
            timeSinceLastDamageSound = System.currentTimeMillis();
            //animation.playGetHitSound(4);
        }
    }

    private void gruntMovement() {
        if (mg.client && onPath) {
        } else if (onPath) {
            if (searchTicks >= Math.random() * 45) {
                getNearestPlayer();
                searchPath(goalCol, goalRow, 16);
                searchTicks = 0;
            } else {
                trackPath();
            }
        }
    }
}


