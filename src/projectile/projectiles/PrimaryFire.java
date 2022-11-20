package projectile.projectiles;

import handlers.MotionHandler;
import handlers.MouseHandler;
import main.MainGame;
import projectile.Projectile;

import java.awt.*;

public class PrimaryFire extends Projectile {

    private final Point updateVector;

    /**
     * What happens when you press main mouse button
     *
     * @param mainGame      to access display functions
     * @param motionHandler to get mouse input
     * @param mouseHandler  to get mouse input
     */
    public PrimaryFire(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler) {
        super(mainGame, motionHandler, mouseHandler);
        this.projectileSpeed = 11;
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.pPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - 2400, MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - 2400);
        this.updateVector = getUpdateVector();

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.fillOval(pPosition.x - mainGame.player.worldX + 2400, pPosition.y - mainGame.player.worldY + 2400, 20, 20);

    }

    @Override
    public void update() {
        pPosition.x += updateVector.x;
        pPosition.y += updateVector.y;

    }

    private Point getUpdateVector() {
        if (mousePosition == null) {
            mousePosition = mouseHandler.mouse1Position;
        }
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * projectileSpeed;
        double normalizedX = (deltaX / length) * projectileSpeed;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}