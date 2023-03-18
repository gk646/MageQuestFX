package gameworld.entities.props;

import gameworld.entities.ENTITY;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_AttackCone;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;


public class ENT_TargetDummy extends ENTITY {
    private final boolean move;
    private boolean attack2;
    private boolean attack3, attack1;

    /**
     * Standard enemy  / hits you when he's close
     *
     * @param level  the level / also sets hp of the enemy
     * @param worldX coordinates X
     * @param worldY coordinates Y
     */
    public ENT_TargetDummy(MainGame mg, int worldX, int worldY, int health, Zone zone, boolean move) {
        this.mg = mg;
        this.animation = new ResourceLoaderEntity("enemies/skeletonWarrior");
        animation.load();
        this.zone = zone;
        this.move = move;
        //Setting default values
        this.maxHealth = health;
        this.health = maxHealth;
        this.worldX = worldX * 48;
        this.worldY = worldY * 48;
        movementSpeed = 2;
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
        if (health < maxHealth) {
            health += 20;
        }
        if (move && isInsideRectangle(activeTile.x, activeTile.y, new Point(8, 20), new Point(13, 25))) {
            if (collidingWithPlayer && !onPath && !attack2 && !attack3 && !attack1) {
                if (Math.random() < 0.33f) {
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2 * 60));
                    attack1 = true;
                } else if (Math.random() < 0.66f) {
                    attack2 = true;
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2 * 60));
                } else {
                    attack3 = true;
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 75, 48, 48, 0, 0, 2.3f * 60));
                }
                animation.playRandomSoundFromXToIndex(0, 3);
                spriteCounter = 0;
                collidingWithPlayer = false;
            }
            if (!attack2 && !attack3 && !attack1) {
                onPath = true;
                getNearestPlayer();
                searchPath(goalCol, goalRow, 16);
            }
        } else if (move) {
            onPath = false;
            worldX = 10 * 48;
            worldY = 22 * 48;
        }
        hitDelay++;
        searchTicks++;
    }


    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        spriteCounter++;
        drawBuffsAndDeBuffs(gc);

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
    }

    public boolean isInsideRectangle(double x, double y, Point rectPoint1, Point rectPoint2) {
        // Calculate the left-most and top-most point
        double leftX = Math.min(rectPoint1.x, rectPoint2.x);
        double topY = Math.min(rectPoint1.y, rectPoint2.y);

        // Calculate the width and height of the rectangle
        double width = Math.abs(rectPoint1.x - rectPoint2.x);
        double height = Math.abs(rectPoint1.y - rectPoint2.y);

        // Check if the point is inside the rectangle
        return x >= leftX && x < leftX + width && y >= topY && y < topY + height;
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
            case 2 -> gc.drawImage(animation.walk.get(2),
                    screenX - 35, screenY - 6);
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
        switch (spriteCounter % 245 / 35) {
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



