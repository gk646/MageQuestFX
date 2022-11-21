package gameworld.projectiles;

import gameworld.Projectile;
import gameworld.entitys.Player;
import handlers.MotionHandler;
import handlers.MouseHandler;
import main.MainGame;

import java.awt.*;

public class SecondaryFire extends Projectile {

    private final Point updateVector;

    /**
     * What happens when you press secondary mouse button. Part of
     * {@link Projectile}
     */
    public SecondaryFire(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler) {
        super(mainGame, motionHandler, mouseHandler);
        //Setting default values
        this.movementSpeed = 6;
        this.entityHeight = 30;
        this.entityWidth= 30;

        //handlers etc.
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.pPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.updateVector = getUpdateVector();
        this.collisionBox= new Rectangle(0,0,20,20);

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.fillRect(pPosition.x - mainGame.player.worldX + 2400, pPosition.y - mainGame.player.worldY + 2400, 30, 30);

    }

    @Override
    public void update() {
        pPosition.x += updateVector.x;
        pPosition.y += updateVector.y;

    }

    private Point getUpdateVector() {
        if (mousePosition == null) {
            mousePosition = mouseHandler.mouse2Position;
        }
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed;
        double normalizedX = (deltaX / length) * movementSpeed;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}
