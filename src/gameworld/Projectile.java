package gameworld;


import gameworld.entitys.Owly;
import gameworld.projectiles.Ability1;
import gameworld.projectiles.Lightning;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ConcurrentModificationException;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {

    public Point screenPosition, updateVector, mousePosition;
    public int endPositionX, endPositionY;
    protected final MainGame mainGame;
    protected final MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MouseHandler mouseHandler) {
        super(mainGame);
        this.mainGame = mainGame;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void draw(Graphics2D g2) {
        try {
            for (Projectile projectile : mainGame.PROJECTILES) {
                projectile.draw(g2);
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    @Override
    public void update() {
        try {
            for (Projectile projectile : mainGame.PROJECTILES) {
                if (projectile.dead) {
                    mg.ENTITIES.remove(projectile);
                    continue;
                }
                for (Entity entity : mainGame.ENTITIES) {
                    if (entity.dead) {
                        mg.ENTITIES.remove(entity);
                        continue;
                    }
                    if (!(entity instanceof Owly) && entityIsClose(entity) && mainGame.collisionChecker.checkEntityAgainstEntity(entity, projectile)) {
                        calcProjectileDamage(projectile, entity);
                    }
                    if (projectile.dead && projectile instanceof PrimaryFire) {
                        mainGame.PROJECTILES.remove(projectile);
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    private boolean entityIsClose(Entity entity) {
        return entity.playerTooFarAbsolute();
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
        } else {
            entity.hpBarOn = true;
        }
    }


    public void updateProjectilePos() {
        try {
            for (Projectile projectile : mainGame.PROJECTILES) {
                projectile.update();
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    protected void tileCollision() {
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
    }

    protected void outOfBounds() {
        if (worldX >= endPositionX || worldY >= endPositionY || worldY <= endPositionY - 650 * 2 || worldX <= endPositionX - 650 * 2) {
            this.dead = true;
        }
    }
}





