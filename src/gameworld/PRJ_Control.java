package gameworld;


import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.debuffs.EFT_Burning_I;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import gameworld.player.abilities.PRJ_EnergySphere;
import gameworld.player.abilities.PRJ_Lightning;
import gameworld.player.abilities.PRJ_RingSalvo;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_Coin;
import gameworld.world.objects.drops.DRP_DroppedItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import main.MainGame;
import main.system.CollisionChecker;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Iterator;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class PRJ_Control {

    public MediaPlayer sound;
    public DamageType type;
    protected Point endPos;
    protected Point2D.Double updateVector;
    public float damage;
    public int projectileHeight;
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
    public int projectileWidth;
    protected MainGame mg;
    public boolean dead, collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public String direction;
    public Rectangle collisionBox;
    public int movementSpeed;
    public Point2D.Double worldPos;
    public int GruntKilledCounter;
    public long lastHitTime;
    private int ShooterKilledCounter;
    /*

     */
    public int level;

    public PRJ_Control(MainGame mg) {
        this.mg = mg;
    }

    protected PRJ_Control() {

    }

    public void draw(GraphicsContext gc) {
        synchronized (mg.PROJECTILES) {
            for (PRJ_Control projectile : mg.PROJECTILES) {
                projectile.draw(gc);
            }
        }
    }

    public void update() {
        synchronized (mg.PROJECTILES) {
            synchronized (MainGame.ENTITIES) {
                Iterator<PRJ_Control> iterator = mg.PROJECTILES.iterator();
                while (iterator.hasNext()) {
                    PRJ_Control projectile = iterator.next();
                    if (projectile.dead) {
                        iterator.remove();
                        continue;
                    }
                    projectile.update();
                    if (projectile instanceof PRJ_EnemyStandardShot && mg.collisionChecker.checkPlayerAgainstProjectile(projectile)) {
                        mg.player.health -= projectile.level;
                        projectile.dead = true;
                    }
                    Iterator<ENTITY> entityIterator = MainGame.ENTITIES.iterator();
                    while (entityIterator.hasNext()) {
                        ENTITY entity = entityIterator.next();
                        if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                            if (entity.dead) {
                                recordDeath(entity);
                                entityIterator.remove();
                                continue;
                            }
                            if (!(projectile instanceof PRJ_EnemyStandardShot) && !(entity instanceof ENT_Owly) && mg.collisionChecker.checkEntityAgainstProjectile(entity, projectile)) {
                                calcProjectileDamage(projectile, entity);
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcProjectileDamage(PRJ_Control projectile, ENTITY entity) {
        if (projectile instanceof PRJ_EnergySphere) {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
        } else if (projectile instanceof PRJ_RingSalvo) {
            entity.getDamage(5);
        } else if (projectile instanceof PRJ_Lightning) {
            entity.getDamage(1);
        } else {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
            entity.effects.add(new EFT_Burning_I(360, 1, true, 60));
            projectile.dead = true;

            projectile.playHitSound();
        }
        entity.hpBarOn = true;
        if (entity.getHealth() <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            if (entity instanceof BOSS) {
                mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, 2, entity.zone));
            } else {
                mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, entity.zone));
                mg.WORLD_DROPS.add(new DRP_Coin((int) (entity.worldX + mg.random.nextInt(41) - 20), (int) (entity.worldY + mg.random.nextInt(41) - 20), entity.level));
            }
        }
    }

    public void updateProjectilePos() {
        synchronized (mg.PROJECTILES) {
            for (PRJ_Control PRJControl : mg.PROJECTILES) {
                PRJControl.update();
            }
        }
    }

    protected void tileCollision() {
        CollisionChecker.checkProjectileAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
    }

    protected void outOfBounds() {
        if (worldPos.x >= endPos.x || worldPos.y >= endPos.y || worldPos.y <= endPos.y - 650 * 2 || worldPos.x <= endPos.x - 650 * 2) {
            this.dead = true;
        }
    }

    protected void outOfBounds(int x) {
        if (worldPos.x >= endPos.x || worldPos.y >= endPos.y || worldPos.y <= endPos.y - x * 2 || worldPos.x <= endPos.x - x * 2) {
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


    public void playHitSound() {

    }


    public void playFlightSound() {
        if (sound != null) {
            sound.setCycleCount(MediaPlayer.INDEFINITE);
            sound.play();
        }
    }

    public void stopFlightSound() {
        if (sound != null) {
            sound.stop();
        }
    }
}







