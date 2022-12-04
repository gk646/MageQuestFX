package gameworld;


import gameworld.projectiles.Ability1;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ConcurrentModificationException;
import java.util.Iterator;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {

    public Point screenPosition, updateVector, mousePosition;
    public final MainGame mainGame;
    public final MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MouseHandler mouseHandler) {
        super(mainGame);
        this.mainGame = mainGame;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public synchronized void draw(Graphics2D g2) {
        for (Projectile projectile : mainGame.PROJECTILES) {
            projectile.draw(g2);
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
                        } else if (projectile instanceof SecondaryFire) {
                            entity.health -= 5;
                        } else if (projectile instanceof Ability1) {
                            entity.health -= 5;
                        }
                        entity.hpBarOn = true;
                        projectile.dead = true;
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
}





