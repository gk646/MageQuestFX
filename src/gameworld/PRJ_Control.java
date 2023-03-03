package gameworld;


import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.player.CollisionProjectiles;
import gameworld.player.EnemyProjectile;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_Coin;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class PRJ_Control {


    protected final MainGame mg;

    public int GruntKilledCounter;
    public long lastHitTime;
    private int ShooterKilledCounter;
    private final ArrayList<PROJECTILE> toBeDamageDead = new ArrayList<>();

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
        toBeDamageDead.clear();
        synchronized (mg.PROJECTILES) {
            synchronized (mg.ENTITIES) {
                Iterator<PROJECTILE> iterator = mg.PROJECTILES.iterator();
                while (iterator.hasNext()) {
                    PROJECTILE projectile = iterator.next();
                    if (projectile instanceof CollisionProjectiles || projectile instanceof EnemyProjectile) {
                        mg.collisionChecker.checkProjectileAgainstTile(projectile);
                        if (projectile.collisionUp || projectile.collisionDown || projectile.collisionLeft || projectile.collisionRight) {
                            projectile.dead = true;
                        }
                    }
                    if (projectile instanceof EnemyProjectile && mg.collisionChecker.checkPlayerAgainstProjectile(projectile)) {
                        mg.player.health -= projectile.damage;
                        projectile.dead = true;
                    }
                    projectile.update();
                    if (projectile.dead) {
                        iterator.remove();
                        continue;
                    }
                    Iterator<ENTITY> entityIterator = mg.ENTITIES.iterator();
                    while (entityIterator.hasNext()) {
                        ENTITY entity = entityIterator.next();
                        if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                            if (entity.AfterAnimationDead) {
                                recordDeath(entity);
                                entityIterator.remove();
                                continue;
                            }
                            if (!(projectile instanceof EnemyProjectile) && !projectile.damageDead && !entity.dead && mg.collisionChecker.checkEntityAgainstProjectile(entity, projectile)) {
                                calcProjectileDamage(projectile, entity);
                            }
                        }
                    }
                }
            }
        }
        for (PROJECTILE projectile : toBeDamageDead) {
            projectile.damageDead = true;
        }
    }

    private void calcProjectileDamage(PROJECTILE projectile, ENTITY entity) {
        if (projectile.projectileType == ProjectileType.OneHitCompletelyDead) {
            projectile.dead = true;
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
            entity.playGetHitSound();
            //entity.BuffsDebuffEffects.add(new DamageEffect(360, 1, true, DamageType.FireDMG, 60));
        } else if (projectile.projectileType == ProjectileType.OneHitNoDMG) {
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
            toBeDamageDead.add(projectile);
            entity.playGetHitSound();
        } else {
            entity.playGetHitSound();
            entity.getDamageFromPlayer(projectile.damage, projectile.type);
        }
        projectile.playHitSound();
        entity.hpBarOn = true;
        if (entity.getHealth() <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            if (entity instanceof BOSS) {
                mg.dropI.dropRareItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
            } else {
                mg.dropI.dropItem(mg, (int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
                mg.WORLD_DROPS.add(new DRP_Coin((int) (entity.worldX + mg.random.nextInt(41) - 20), (int) (entity.worldY + mg.random.nextInt(41) - 20), entity.level, WorldController.currentWorld));
            }
        }
    }


    public void recordDeath(ENTITY entity) {
        if (entity instanceof ENT_SkeletonWarrior) {
            GruntKilledCounter++;
        } else if (entity instanceof ENT_SkeletonArcher) {
            ShooterKilledCounter++;
        }
    }
}







