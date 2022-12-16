package gameworld.projectiles;

import gameworld.Projectile;
import gameworld.entitys.Player;
import input.MouseHandler;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
        this.entityHeight = 16;
        this.entityWidth = 16;
        this.collisionBox = new Rectangle(0, 0, 16, 16);
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2;
        this.worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2;
        this.updateVector = getUpdateVector();
        getPlayerImage();
        this.endPositionX = worldX + 1000;
        this.endPositionY = worldY + 1000;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(entityImage1, screenPosition.x - mainGame.player.worldX + Player.startingPoint.x, screenPosition.y - mainGame.player.worldY + Player.startingPoint.y, entityWidth, entityHeight, null);
    }

    @Override
    public void update() {
        if (worldX >= endPositionX || worldY >= endPositionY || worldY <= endPositionY - 2000 || worldX <= endPositionX - 2000) {
            this.dead = true;
        }
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2 + 24;
        worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2 + 24;
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

    public void getPlayerImage() {
        entityImage1 = setup("PrimaryFire01.png");
    }

    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/projectiles/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}