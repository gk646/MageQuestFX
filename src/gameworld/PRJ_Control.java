package gameworld;


import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.DamageEffect;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.abilities.PRJ_EnemyStandardShot;
import gameworld.player.abilities.PRJ_EnergySphere;
import gameworld.player.abilities.PRJ_Lightning;
import gameworld.player.abilities.PRJ_RingSalvo;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_Coin;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.util.Iterator;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class PRJ_Control {


    protected MainGame mg;

    public int GruntKilledCounter;
    public long lastHitTime;
    private int ShooterKilledCounter;

    /**
     * Used for handling projectiles
     * main source of collision detection and damage provision
     *
     * @param mg maingame
     */
    public PRJ_Control(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {
        synchronized (mg.PROJECTILES) {
            for (PROJECTILE projectile : mg.PROJECTILES) {
                projectile.draw(gc);
            }
        }
    }

    public void update() {
        synchronized (mg.PROJECTILES) {
            synchronized (MainGame.ENTITIES) {
                Iterator<PROJECTILE> iterator = mg.PROJECTILES.iterator();
                while (iterator.hasNext()) {
                    PROJECTILE projectile = iterator.next();
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
                                entity.playGetHitSound();
                            }
                        }
                    }
                }
            }
        }
    }

    private void calcProjectileDamage(PROJECTILE projectile, ENTITY entity) {
        if (projectile instanceof PRJ_EnergySphere) {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
        } else if (projectile instanceof PRJ_RingSalvo) {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
        } else if (projectile instanceof PRJ_Lightning) {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
        } else {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
            entity.effects.add(new DamageEffect(360, 1, true, DamageType.FireDMG, 60));
            projectile.dead = true;
            projectile.playHitSound();
        }
        entity.hpBarOn = true;
        if (entity.getHealth() <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            if (entity instanceof BOSS) {
                mg.dropI.dropRareItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
            } else {
                mg.dropI.dropItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
                mg.WORLD_DROPS.add(new DRP_Coin((int) (entity.worldX + mg.random.nextInt(41) - 20), (int) (entity.worldY + mg.random.nextInt(41) - 20), entity.level));
            }
        }
    }


    public void recordDeath(ENTITY entity) {
        if (entity instanceof ENT_Grunt) {
            GruntKilledCounter++;
        } else if (entity instanceof ENT_Shooter) {
            ShooterKilledCounter++;
        }
    }
}







