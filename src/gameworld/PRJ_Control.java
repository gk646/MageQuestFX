package gameworld;


import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_AutoShot;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import gameworld.player.abilities.PRJ_EnergySphere;
import gameworld.player.abilities.PRJ_Lightning;
import gameworld.player.abilities.PRJ_RingSalvo;
import gameworld.world.objects.drops.DRP_DroppedItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class PRJ_Control {

    protected Point endPos;
    protected Point2D.Double updateVector;
    protected int projectileHeight;
    protected int spriteCounter;
    protected Image projectileImage1;
    protected Image projectileImage2;
    protected Image projectileImage3;
    protected Image projectileImage4;
    protected Image projectileImage5;
    protected Image projectileImage6;
    protected Image projectileImage7;
    protected Image projectileImage8;
    protected Image projectileImage9;
    protected Image projectileImage10;
    protected int projectileWidth;
    protected final MainGame mg;
    public boolean dead, collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public String direction;
    public Rectangle collisionBox;
    public int movementSpeed;
    public Point2D.Double worldPos;
    public int GruntKilledCounter, ShooterKilledCounter;
    /*

     */
    public int level;

    public PRJ_Control(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext g2) {
        try {
            for (PRJ_Control PRJControl : mg.PRJControls) {
                PRJControl.draw(g2);
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void update() {
        try {
            for (PRJ_Control projectile : mg.PRJControls) {
                if (projectile.dead) {
                    mg.PRJControls.remove(projectile);
                    continue;
                }
                for (ENTITY entity : MainGame.ENTITIES) {
                    if (entity.dead) {
                        recordDeath(entity);
                        MainGame.ENTITIES.remove(entity);
                        continue;
                    }
                    if (!entity.playerTooFarAbsolute() && !(projectile instanceof PRJ_EnemyStandardShot) && !(entity instanceof ENT_Owly) && mg.collisionChecker.checkEntityAgainstProjectile(entity, projectile) && !projectile.dead) {
                        calcProjectileDamage(projectile, entity);
                    } else if (!projectile.dead && projectile instanceof PRJ_EnemyStandardShot && mg.collisionChecker.checkEntityAgainstProjectile(mg.player, projectile)) {
                        mg.player.health -= entity.level;
                        projectile.dead = true;
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    private void calcProjectileDamage(PRJ_Control PRJControl, ENTITY entity) {
        if (PRJControl instanceof PRJ_AutoShot) {
            entity.health -= 1;
            PRJControl.dead = true;
        } else if (PRJControl instanceof PRJ_EnergySphere) {
            entity.health -= 1;
        } else if (PRJControl instanceof PRJ_RingSalvo) {
            entity.health -= 5;
        } else if (PRJControl instanceof PRJ_Lightning) {
            entity.health -= 1;
        }
        if (entity.health <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, entity.worldX, entity.worldY, entity.level));
        } else {
            entity.hpBarOn = true;
        }
    }


    public void updateProjectilePos() {
        try {
            for (PRJ_Control PRJControl : mg.PRJControls) {
                PRJControl.update();
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    protected void tileCollision() {
        mg.collisionChecker.checkProjectileAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
    }

    protected void outOfBounds() {
        if (worldPos.x >= endPos.x || worldPos.y >= endPos.y || worldPos.y <= endPos.y - 650 * 2 || worldPos.x <= endPos.x - 650 * 2) {
            this.dead = true;
        }
    }

    private void recordDeath(ENTITY entity) {
        if (entity instanceof ENT_Grunt) {
            GruntKilledCounter++;
        } else if (entity instanceof ENT_Shooter) {
            ShooterKilledCounter++;
        }
    }

    private boolean playerTooFarAbsolute() {
        return Math.abs(worldPos.x - Player.worldX) >= 650 || Math.abs(worldPos.y - Player.worldY) >= 650;
    }
}





