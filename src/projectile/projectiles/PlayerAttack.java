package projectile.projectiles;

import handlers.MotionHandler;
import main.Display;
import handlers.MouseHandler;
import projectile.Projectile;


import java.awt.*;

public class PlayerAttack extends Projectile {

    private final Point updateVector;

    public PlayerAttack(Display display,MotionHandler motionHandler, MouseHandler mouseHandler) {
        super(display,motionHandler,mouseHandler);
        this.projectileSpeed = 11;

        this.mousePosition = display.motionHandler.mousePosition;
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
        if(mousePosition==null){
            mousePosition = mouseHandler.mousePosition;
        }
        double deltaX = mousePosition.x - playerPosition.x;
        double deltaY = mousePosition.y - playerPosition.y;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * projectileSpeed;
        double normalizedX = (deltaX / length) * projectileSpeed;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}