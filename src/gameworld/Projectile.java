package gameworld;


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
    public final MainGame mainGame;
    public final MouseHandler mouseHandler;

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
            // Iterate over the PROJECTILES and ENTITIES collections
            for (Projectile projectile : mainGame.PROJECTILES) {
                for (Entity entity : mainGame.ENTITIES) {
                    // Check for collisions between the projectile and entity
                    if (mainGame.collisionChecker.checkEntityAgainstEntity(entity, projectile)) {
                        // Update the entity's health based on the type of projectile
                        if (projectile instanceof PrimaryFire) {
                            entity.health -= 1;
                            projectile.dead = true;
                        } else if (projectile instanceof SecondaryFire) {
                            entity.health -= 5;
                            projectile.dead = true;
                        } else if (projectile instanceof Ability1) {
                            entity.health -= 5;
                            projectile.dead = true;
                        } else if (projectile instanceof Lightning) {
                            entity.health -= 1;
                        }
                        entity.hpBarOn = true;
                    }
                }
                projectile.update();
                if (projectile.dead) {
                    mainGame.PROJECTILES.remove(projectile);
                }

            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void tileCollision() {
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
    }

    public void outOfBounds(int size) {
        if (worldX >= endPositionX || worldY >= endPositionY || worldY <= endPositionY - size * 2 || worldX <= endPositionX - size * 2) {
            this.dead = true;
        }
    }
}





