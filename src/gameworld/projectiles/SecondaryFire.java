package gameworld.projectiles;

import gameworld.Entity;
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
    public SecondaryFire(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, Entity entity) {
        super(mainGame, motionHandler, mouseHandler,entity);

        //Setting default values
        this.movementSpeed = 5;
        this.entityHeight = 25;
        this.entityWidth = 25;

        //handlers etc.
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x+1700;
        this.worldY = screenPosition.y+1950;
        this.updateVector = getUpdateVector();
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        direction = "downleftrightup";
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.drawRect(screenPosition.x - mainGame.player.worldX + Player.startingPoint.x, screenPosition.y - mainGame.player.worldY + Player.startingPoint.y, entityWidth, entityHeight);

    }

    @Override
    public void update() {
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionup||collisiondown||collisionleft||collisionright) {
            this.dead = true;
        }

        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + 1700+24;
        worldY = screenPosition.y + 1950+24;
    }

    //Get normalized vector
    private Point getUpdateVector() {
        if (mousePosition == null) {
            mousePosition = mouseHandler.mouse2Position;
        }
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed * 2;
        double normalizedX = (deltaX / length) * movementSpeed * 2;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}
