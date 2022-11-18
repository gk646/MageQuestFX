package Projectiles;

import Main.Display;
import Main.MouseHandler;

import java.awt.*;

public class FirstBullet extends Projectile {


    public FirstBullet(Display display, MouseHandler mouseHandler) {
        super(display,mouseHandler);
        this.display = display;
        this.mouseHandler = mouseHandler;
        this.projectileSpeed = 4;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawOval(xPosition, yPosition, 20, 20);
    }

    @Override
    public void update() {

    }
}