package gameworld;


import gameworld.projectiles.Ability1;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

import java.awt.*;

import static main.MainGame.ENTITIES;
import static main.MainGame.PROJECTILES;

/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {

    public Point screenPosition, updateVector, mousePosition;
    public final MainGame mainGame;
    public final MotionHandler motionHandler;
    public final KeyHandler keyHandler;
    public final MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, KeyHandler keyHandler) {
        super(mainGame);
        this.mainGame = mainGame;
        this.motionHandler = motionHandler;
        this.mouseHandler = mouseHandler;
        this.keyHandler = keyHandler;
    }

    @Override
    public void draw(Graphics2D g2) {
        PROJECTILES.forEach((n) -> n.draw(g2));
    }

    @Override
    public void update() {
        for (Projectile projectile1 : PROJECTILES) {
            for (Entity entity1 : ENTITIES) {
                if (mainGame.collisionChecker.checkEntityAgainstEntity(entity1, projectile1)) {
                    if (projectile1.getClass() == PrimaryFire.class) {
                        entity1.health -= 1;
                    } else if (projectile1.getClass() == SecondaryFire.class) {
                        entity1.health -= 5;
                    } else if (projectile1.getClass() == Ability1.class) {
                        entity1.health -= 5;
                    }
                    projectile1.dead = true;
                }
            }
            projectile1.update();
        }
        PROJECTILES.removeIf(projectile -> projectile.dead);
    }
}




