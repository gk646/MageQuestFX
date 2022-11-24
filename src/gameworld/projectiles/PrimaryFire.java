package gameworld.projectiles;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entitys.Player;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

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
    public PrimaryFire(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, Entity entity, KeyHandler keyHandler) {
        super(mainGame, motionHandler, mouseHandler, entity,keyHandler);

        //Setting default values
        this.movementSpeed = 7;
        this.entityHeight = 16;
        this.entityWidth = 16;

        //handlers etc.
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + 1700;
        this.worldY = screenPosition.y + 1950;
        this.updateVector = getUpdateVector();
        this.collisionBox = new Rectangle(0, 0, 16, 16);
        direction = "downleftrightup";
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawRect(screenPosition.x - mainGame.player.worldX + Player.startingPoint.x, screenPosition.y - mainGame.player.worldY + Player.startingPoint.y, entityWidth, entityHeight);

    }

    @Override
    public void update() {
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }

        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + 1700 + 24;
        worldY = screenPosition.y + 1950 + 24;

    }

    //Get normalized vector
    private Point getUpdateVector() {
        if (mousePosition == null) {
            mousePosition = mouseHandler.mouse1Position;
        }
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed * 2;
        double normalizedX = (deltaX / length) * movementSpeed * 2;
        return new Point((int) normalizedX, (int) normalizedY);
    }
}