package projectile;


import handlers.MouseHandler;
import main.Display;
import projectile.projectiles.PlayerAttack;

import java.awt.*;
// import java.awt.image.BufferedImage;


public class Projectile {
    public static Projectile[] projectiles = new Projectile[1000];
    public int xPosition, yPosition;
    MouseHandler mouseHandler;
    public int counter;
    public int projectileSpeed;

    //public BufferedImage pellet;
    public Display display;
    public Point mousePosition,playerPosition;

    public Projectile(Display display, MouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
        this.display = display;
    }

    public void draw(Graphics2D g2) {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.draw(g2);
            }
        }
    }

    public void update() {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.update();
            }
        }
        if (mouseHandler.mousePressed) {
            if (projectiles.length == counter) {
                counter = 0;
            }
            projectiles[counter++] = new PlayerAttack(display, mouseHandler);
        }
    }


}