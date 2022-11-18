package projectile.projectiles;

import main.Display;
import handlers.MouseHandler;
import projectile.Projectile;


import java.awt.*;

public class PlayerAttack extends Projectile {



    public PlayerAttack(Display display, MouseHandler mouseHandler) {
        super(display, mouseHandler);
        this.projectileSpeed = 6;
        this.mousePosition = mouseHandler.getMouseLocation();
        this.xPosition = display.player.getPlayerPosition().x;
        this.yPosition = display.player.getPlayerPosition().y;

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawOval(xPosition, yPosition, 20, 20);
    }

    @Override
    public void update() {
        xPosition += projectileSpeed;
    }
}