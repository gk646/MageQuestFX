package projectile.projectiles;

import main.Display;
import handlers.MouseHandler;
import projectile.Projectile;


import java.awt.*;

public class PlayerAttack extends Projectile {

    private final Point updateVector;

    public PlayerAttack(Display display, MouseHandler mouseHandler) {
        super(display, mouseHandler);
        this.projectileSpeed = 11;
        this.mousePosition = mouseHandler.getMouseLocation();
        this.playerPosition = display.player.getPlayerPosition();
        this.xPosition = playerPosition.x;
        this.yPosition = playerPosition.y;
        this.updateVector = getUpdateVector();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval(xPosition, yPosition, 20, 20);
        g2.setColor(Color.green);
    }

    @Override
    public void update() {
        xPosition += updateVector.x;
        yPosition += updateVector.y;

    }

    private Point getUpdateVector() {
        double deltax = mousePosition.x - playerPosition.x;
        double deltay = mousePosition.y - playerPosition.y;
        double length = Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
        double normalizedy = (deltay / length) * projectileSpeed;
        double normalizedx = (deltax / length) * projectileSpeed;
        return new Point((int) normalizedx, (int) normalizedy);
    }
}