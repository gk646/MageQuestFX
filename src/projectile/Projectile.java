package projectile;


import handlers.MotionHandler;
import handlers.MouseHandler;
import main.Display;
import projectile.projectiles.PlayerAttack;

import java.awt.*;
// import java.awt.image.BufferedImage;

/**
 * Main inheritable class for all projectiles
 */
public class Projectile {
    public final static Projectile[] PROJECTILES = new Projectile[1000];
    public int xPosition, yPosition;
    public int counter;
    public int projectileSpeed;

    //public BufferedImage pellet;
    public Display display;
    public Point mousePosition, playerPosition;
    public MotionHandler motionHandler;
    public MouseHandler mouseHandler;

    public Projectile(Display display, MotionHandler motionHandler, MouseHandler mouseHandler) {
        this.display = display;
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
        if (this.mouseHandler.mouse1Pressed&&display.globalLogicTicks%10 ==0) {
            if (PROJECTILES.length == counter) {
                counter = 0;
            }
            PROJECTILES[counter++] = new PlayerAttack(display, motionHandler, mouseHandler);
            motionHandler.mousePressed = false;
        }else if(this.mouseHandler.mouse2Pressed&&display.globalLogicTicks%30 ==0)
            if (PROJECTILES.length == counter) {
                counter = 0;
            }
            PROJECTILES[counter++] = new AltFire(display, motionHandler, mouseHandler);
            motionHandler.mousePressed = false;
    }


}