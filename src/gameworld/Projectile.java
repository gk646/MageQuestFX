package gameworld;


import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import handlers.MotionHandler;
import gameworld.entitys.Enemy;
import handlers.MouseHandler;
import main.MainGame;

import java.awt.*;
// import java.awt.image.BufferedImage;

/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public class Projectile extends Entity {
    public final static Projectile[] PROJECTILES = new Projectile[1000];
    public Point screenPosition;
    public int counter;

    //public BufferedImage pellet;
    public MainGame mainGame;
    public Point mousePosition;
    private Entity entity;
    public MotionHandler motionHandler;
    public MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, Entity entityC) {
        super(mainGame);
        this.mainGame = mainGame;
        this.entity = entityC;
        this.motionHandler = motionHandler;
        this.mouseHandler = mouseHandler;

    }

    @Override
    public void draw(Graphics2D g2) {
        for (Projectile projectile : PROJECTILES) {
            if (projectile != null) {
                projectile.draw(g2);
            }
        }
    }

    @Override
    public void update() {
        for (Projectile projectile : PROJECTILES) {
            if (projectile != null && !projectile.dead) {
                projectile.update();
            }
        }
        for (Entity entity1 : entity.entities) {
            if (entity1 != null) {
                for (Projectile projectile1 : PROJECTILES) {
                    if (projectile1 != null) {
                        if (mainGame.collisionChecker.checkEntityAgainstEntity(entity1, projectile1) && !projectile1.dead) {
                            if (projectile1.getClass() == PrimaryFire.class) {
                                entity1.health -= 1;
                                projectile1.dead = true;
                                projectile1 = null;
                            } else if (projectile1.getClass() == SecondaryFire.class) {
                                entity1.health -= 5;
                                projectile1.dead = true;
                                projectile1 = null;
                            }
                            System.out.println(entity.entities[1].health);
                        }
                    }
                }
                if (this.mouseHandler.mouse1Pressed && mainGame.globalLogicTicks % 10 == 0) {
                    if (PROJECTILES.length == counter) {
                        counter = 0;
                    }
                    PROJECTILES[counter++] = new PrimaryFire(mainGame, motionHandler, mouseHandler, entity);
                    motionHandler.mousePressed = false;
                }
                if (this.mouseHandler.mouse2Pressed && mainGame.globalLogicTicks % 40 == 0) {
                    if (PROJECTILES.length == counter) {
                        counter = 0;
                    }
                    PROJECTILES[counter++] = new SecondaryFire(mainGame, motionHandler, mouseHandler, entity);
                    motionHandler.mousePressed = false;
                }

            }
        }

    }
}
