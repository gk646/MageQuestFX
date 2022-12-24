package gameworld.player.abilities;

import gameworld.Projectile;
import gameworld.player.Player;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class PrimaryFire extends Projectile {

    /**
     * What happens when you press main mouse button
     *
     * @param mainGame     to access display functions
     * @param mouseHandler to get mouse input
     */
    public PrimaryFire(MainGame mainGame, MouseHandler mouseHandler) {
        super(mainGame, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 7;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = new Rectangle(0, 0, 16, 16);
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = mainGame.motionH.lastMousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2;
        this.worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2;
        this.updateVector = getUpdateVector();
        getPlayerImage();
        this.endPositionX = worldX + 650;
        this.endPositionY = worldY + 650;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(projectileImage1, screenPosition.x - mg.player.worldX + Player.startingPoint.x, screenPosition.y - mg.player.worldY + Player.startingPoint.y, projectileWidth, projectileHeight, null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2 + 24;
        worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2 + 24;
    }

    //Get normalized vector
    private Point getUpdateVector() {
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed * 2;
        double normalizedX = (deltaX / length) * movementSpeed * 2;
        return new Point((int) normalizedX, (int) normalizedY);
    }

    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}