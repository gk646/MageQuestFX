package gameworld;


import gameworld.projectiles.Ability1;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.MouseHandler;
import main.MainGame;

import java.awt.*;



/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {

    public Point screenPosition, updateVector, mousePosition;
    public final MainGame mainGame;
    public final MouseHandler mouseHandler;

    public Projectile(MainGame mainGame,MouseHandler mouseHandler) {
        super(mainGame);
        this.mainGame = mainGame;
        this.mouseHandler = mouseHandler;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (Projectile projectile : mainGame.PROJECTILES) {
            projectile.draw(g2);
        }
    }

    @Override
    public void update() {
        for (Projectile projectile1 : mainGame.PROJECTILES) {
            for (Entity entity1 : mainGame.ENTITIES) {
                if (mainGame.collisionChecker.checkEntityAgainstEntity(entity1, projectile1)) {
                    if (projectile1.getClass() == PrimaryFire.class) {
                        entity1.health -= 1;
                    } else if (projectile1.getClass() == SecondaryFire.class) {
                        entity1.health -= 5;
                    } else if (projectile1.getClass() == Ability1.class) {
                        entity1.health -= 5;
                    }
                    entity1.hpBarOn = true;
                    projectile1.dead = true;
                }
            }
            projectile1.update();
        }
        mainGame.PROJECTILES.removeIf(projectile -> projectile.dead);
    }
}




