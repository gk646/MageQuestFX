package gameworld;


import gameworld.projectiles.Ability1;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {
    public final static ArrayList<Projectile> PROJECTILES = new ArrayList<>();
    public Point screenPosition, updateVector, mousePosition;
    public final MainGame mainGame;
    private final Entity entity;
    public final MotionHandler motionHandler;
    public final KeyHandler keyHandler;
    public final MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, Entity entityC, KeyHandler keyHandler) {
        super(mainGame);
        this.mainGame = mainGame;
        this.entity = entityC;
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
            for (Entity entity1 : entity.entities) {
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
        if (this.mouseHandler.mouse1Pressed && mainGame.globalLogicTicks % 10 == 0) {
            PROJECTILES.add(new PrimaryFire(mainGame, motionHandler, mouseHandler, entity, keyHandler));
        }
        if (this.mouseHandler.mouse2Pressed && mainGame.globalLogicTicks % 40 == 0) {
            PROJECTILES.add(new SecondaryFire(mainGame, motionHandler, mouseHandler, entity, keyHandler));
        }
        if (this.keyHandler.OnePressed && mainGame.globalLogicTicks % 40 == 0) {
            for (int i = 0; i <= 7; i++) {
                PROJECTILES.add(new Ability1(mainGame, motionHandler, mouseHandler, entity, keyHandler, i));
            }
        }
        PROJECTILES.removeIf(projectile -> projectile.dead);
    }
}




