package gameworld;


import gameworld.entities.Owly;
import gameworld.player.abilities.Ability1;
import gameworld.player.abilities.EnemyProjectile1;
import gameworld.player.abilities.Lightning;
import gameworld.player.abilities.PrimaryFire;
import gameworld.player.abilities.SecondaryFire;
import gameworld.world.DroppedItem;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile {

    protected final MainGame mg;
    protected final MouseHandler mouseHandler;
    public boolean dead, collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public String direction;
    public Rectangle collisionBox;
    public Point updateVector, mousePos, worldPos, endPos, screenPos;
    public int movementSpeed, projectileHeight, projectileWidth, spriteCounter, level;
    public BufferedImage projectileImage1, projectileImage2, projectileImage3, projectileImage4, projectileImage5, projectileImage6, projectileImage7, projectileImage8, projectileImage9, projectileImage10;


    public Projectile(MainGame mg, MouseHandler mouseHandler) {
        this.mg = mg;
        this.mouseHandler = mouseHandler;
    }

    public void draw(Graphics2D g2) {
        try {
            for (Projectile projectile : mg.PROJECTILES) {
                projectile.draw(g2);
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }


    public void update() {
        try {
            for (Projectile projectile : mg.PROJECTILES) {
                if (projectile.dead) {
                    mg.PROJECTILES.remove(projectile);
                    continue;
                }
                for (Entity entity : mg.ENTITIES) {
                    if (entity.dead) {
                        mg.ENTITIES.remove(entity);
                        continue;
                    }
                    if (!entity.playerTooFarAbsolute() && !(projectile instanceof EnemyProjectile1) && !(entity instanceof Owly) && mg.collisionChecker.checkEntityAgainstProjectile(entity, projectile) && !projectile.dead) {
                        calcProjectileDamage(projectile, entity);
                    } else if (!projectile.dead && projectile instanceof EnemyProjectile1 && mg.collisionChecker.checkEntityAgainstProjectile(mg.player, projectile)) {
                        mg.player.health -= entity.level;
                        projectile.dead = true;
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    private void calcProjectileDamage(Projectile projectile, Entity entity) {
        if (projectile instanceof PrimaryFire) {
            entity.health -= 1;
            projectile.dead = true;
        } else if (projectile instanceof SecondaryFire) {
            entity.health -= 1;
        } else if (projectile instanceof Ability1) {
            entity.health -= 5;
        } else if (projectile instanceof Lightning) {
            entity.health -= 1;
        }
        if (entity.health <= 0) {
            mg.player.getExperience(entity);
            entity.dead = true;
            mg.droppedItems.add(new DroppedItem(mg, entity.worldX, entity.worldY));
        } else {
            entity.hpBarOn = true;
        }
    }


    public void updateProjectilePos() {
        try {
            for (Projectile projectile : mg.PROJECTILES) {
                projectile.update();
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
}





