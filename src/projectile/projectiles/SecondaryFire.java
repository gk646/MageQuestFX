package projectile.projectiles;

import handlers.MotionHandler;
import handlers.MouseHandler;
import main.Display;
import projectile.Projectile;

import java.awt.*;

public class SecondaryFire extends Projectile{
    /**
     * What happens when you press secondary mouse button
     * @param display
     * @param motionHandler
     * @param mouseHandler
     */
    private final Point updateVector;
    public SecondaryFire(Display display, MotionHandler motionHandler, MouseHandler mouseHandler) {
        super(display,motionHandler,mouseHandler);
        this.projectileSpeed = 11;
        this.mousePosition = display.motionHandler.mousePosition;
        this.playerPosition = display.player.getPlayerPosition();
        this.updateVector = getUpdateVector();

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.fillOval(playerPosition.x, playerPosition.y, 20, 20);
        g2.setColor(Color.blue);
    }

    @Override
    public void update() {
        playerPosition.x += updateVector.x;
        playerPosition.y += updateVector.y;

    }

    private Point getUpdateVector() {
        if(mousePosition==null){
            mousePosition = mouseHandler.mouse2Position;
        }
        double deltaX = mousePosition.x - playerPosition.x;
        double deltaY = mousePosition.y - playerPosition.y;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * projectileSpeed;
        double normalizedX = (deltaX / length) * projectileSpeed;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}
