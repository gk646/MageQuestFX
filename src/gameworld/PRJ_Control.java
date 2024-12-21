/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld;


import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.boss.BOSS_Knight;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.npcs.quests.ENT_RealmKeeper;
import gameworld.player.CollisionProjectiles;
import gameworld.player.EnemyProjectile;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectileType;
import gameworld.player.abilities.portals.PRJ_EtherPortal;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_Coin;
import gameworld.world.objects.drops.DRP_EtherMark;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class PRJ_Control {
    private final MainGame mg;
    public int GruntKilledCounter;
    public int ENEMIES_KILLED;
    private int ShooterKilledCounter;
    public int stoneKnightKilled;
    private final ArrayList<PROJECTILE> toBeDamageDead = new ArrayList<>();
    public final ArrayList<PROJECTILE> toBeAdded = new ArrayList<>();

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
            for (var projectile : mg.PROJECTILES) {
                projectile.draw(gc);
            }
        }
    }

    public void update() {
        toBeDamageDead.clear();
        synchronized (mg.PROJECTILES) {
            if (toBeAdded.size() > 0) {
                mg.PROJECTILES.addAll(toBeAdded);
                toBeAdded.clear();
            }
            // synchronized (mg.ENTITIES) {
            var iterator = mg.PROJECTILES.iterator();
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
        for (var projectile : toBeDamageDead) {
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
                if (WorldController.currentWorld == Zone.EtherRealm) {
                    toBeAdded.add(new PRJ_EtherPortal(mg, (int) (entity.worldX / 48), (int) (entity.worldY / 48)));
                    ENT_RealmKeeper.ETHER_ACTIVE = false;
                } else {
                    mg.dropManager.bossDieEvent((int) entity.worldX, (int) entity.worldY, entity.zone, entity.level);
                }
            } else {
                mg.dropManager.useDropChance((int) entity.worldX, (int) entity.worldY, entity.level, entity.zone);
                if (MainGame.random.nextInt(101) > 20) {
                    mg.WORLD_DROPS.add(new DRP_Coin((int) (entity.worldX + MainGame.random.nextInt(21) - 10), (int) (entity.worldY + MainGame.random.nextInt(21) - 10), entity.level, WorldController.currentWorld));
                }
            }
            if (WorldController.currentWorld == Zone.EtherRealm) {
                if (MainGame.random.nextInt(101) > 10) {
                    mg.WORLD_DROPS.add(new DRP_EtherMark((int) (entity.worldX + MainGame.random.nextInt(21) - 10), (int) (entity.worldY + MainGame.random.nextInt(21) - 10), 5, WorldController.currentWorld));
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
        mg.gameStatistics.updateMonstersKilled();
    }
}







