package gameworld;


import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.boss.BOSS_Knight;
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
    public int ENEMIES_KILLED;
    private int ShooterKilledCounter;
    public int stoneKnightKilled;
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
            // synchronized (mg.ENTITIES) {
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
                        mg.player.setHealth(mg.player.getHealth() - projectile.weapon_damage_percent);
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
            // }
        }
        for (PROJECTILE projectile : toBeDamageDead) {
            projectile.damageDead = true;
        }
    }

    private void calcProjectileDamage(PROJECTILE projectile, ENTITY entity) {
        if (projectile.projectileType == ProjectileType.OneHitCompletelyDead) {
            projectile.dead = true;
            projectile.damageDead = true;
            entity.getDamageFromPlayer(projectile.weapon_damage_percent, projectile.type, false);
            entity.playGetHitSound();
        } else if (projectile.projectileType == ProjectileType.OneHitNoDMG) {
            entity.getDamageFromPlayer(projectile.weapon_damage_percent, projectile.type, false);
            toBeDamageDead.add(projectile);
            entity.playGetHitSound();
        } else {
            entity.playGetHitSound();
            entity.getDamageFromPlayer(projectile.weapon_damage_percent, projectile.type, false);
        }
        entity.addEffects(projectile.procEffects);
        projectile.playHitSound();
        entity.hpBarOn = true;
        entity.hpBarCounter = 0;
        if (entity.getHealth() <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            entity.spriteCounter = 0;
            if (entity instanceof BOSS) {
                mg.dropManager.bossDieEvent((int) entity.worldX, (int) entity.worldY, entity.zone, entity.level);
            } else {
                mg.dropManager.useDropChance((int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
                if (MainGame.random.nextInt(101) > 20) {
                    mg.WORLD_DROPS.add(new DRP_Coin((int) (entity.worldX + MainGame.random.nextInt(41) - 20), (int) (entity.worldY + MainGame.random.nextInt(41) - 20), entity.level, WorldController.currentWorld));
                }
            }
        }
    }


    public void recordDeath(ENTITY entity) {
        if (entity instanceof ENT_SkeletonWarrior) {
            GruntKilledCounter++;
        } else if (entity instanceof ENT_SkeletonArcher) {
            ShooterKilledCounter++;
        } else if (entity instanceof BOSS_Knight) {
            stoneKnightKilled++;
        }
        ENEMIES_KILLED++;
    }
}







