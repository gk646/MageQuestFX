package Projectiles;

import Main.Display;
import Main.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;


public class Projectile implements Runnable {
    public static Projectile[] projectiles = new Projectile[1000];
    public int xPosition, yPosition;
    MouseHandler mouseHandler;
    public int counter;
    public int projectileSpeed;

    public BufferedImage pellet;
    Display display;
    public Vector direction;

    public Projectile(Display display, MouseHandler mouseHandler) {
        this.mouseHandler =mouseHandler;
        this.display = display;
    }

    public void draw(Graphics2D g2) {

    }

    public void update() {
        if (mouseHandler.mousePressed) {

            if (projectiles.length == counter) {
                counter = 0;
            }
            projectiles[counter++] = new FirstBullet(display, mouseHandler);
        }
    }


    @Override
    public void run() {

    }
}