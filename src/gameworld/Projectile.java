package gameworld;


import gameworld.projectile.projectiles.PrimaryFire;
import gameworld.projectile.projectiles.SecondaryFire;
import handlers.MotionHandler;
import handlers.MouseHandler;
import main.MainGame;

import java.awt.*;
// import java.awt.image.BufferedImage;

/**
 * Main inheritable class for all projectiles
 */
public class Projectile {
    public final static Projectile[] PROJECTILES = new Projectile[1000];
    public Point pPosition;
    public int counter;
    public int projectileSpeed;

    //public BufferedImage pellet;
    public MainGame mainGame;
    public Point mousePosition;
    public MotionHandler motionHandler;
    public MouseHandler mouseHandler;

    public Projectile(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler) {
        this.mainGame = mainGame;
        this.motionHandler = motionHandler;
        this.mouseHandler = mouseHandler;

    }

    public void draw(Graphics2D g2) {
        for (Projectile projectile : PROJECTILES) {
            if (projectile != null) {
                projectile.draw(g2);
            }
        }
    }

    public void update() {
        for (Projectile projectile : PROJECTILES) {
            if (projectile != null) {
                projectile.update();
            }
        }
        if (this.mouseHandler.mouse1Pressed && mainGame.globalLogicTicks % 10 == 0) {
            if (PROJECTILES.length == counter) {
                counter = 0;
            }
            PROJECTILES[counter++] = new PrimaryFire(mainGame, motionHandler, mouseHandler);
            motionHandler.mousePressed = false;
        }
        if (this.mouseHandler.mouse2Pressed && mainGame.globalLogicTicks % 30 == 0) {
            if (PROJECTILES.length == counter) {
                counter = 0;
            }
            PROJECTILES[counter++] = new SecondaryFire(mainGame, motionHandler, mouseHandler);
            motionHandler.mousePressed = false;
        }
    }


}