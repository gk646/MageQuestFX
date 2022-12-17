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

public class SecondaryFire extends Projectile {

    /**
     * What happens when you press secondary mouse button. Part of
     * {@link Projectile}
     */
    public SecondaryFire(MainGame mainGame, MouseHandler mouseHandler) {
        super(mainGame, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 3;
        this.entityHeight = 25;
        this.entityWidth = 25;
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = mainGame.motionHandler.mousePosition;
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
        screenX = screenPosition.x - mainGame.player.worldX + Player.startingPoint.x;
        screenY = screenPosition.y - mainGame.player.worldY + Player.startingPoint.y;
        if (spriteCounter <= 13) {
            g2.drawImage(entityImage1, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 13) {
            g2.drawImage(entityImage2, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 26) {
            g2.drawImage(entityImage3, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 39) {
            g2.drawImage(entityImage4, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 52) {
            g2.drawImage(entityImage5, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 65) {
            g2.drawImage(entityImage6, screenX, screenY, entityWidth, entityHeight, null);
            spriteCounter = 0;
        }
        spriteCounter++;
    }


    @Override
    public void update() {
        outOfBounds(650);
        tileCollision();
        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2 + 24;
        worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2 + 24;
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

    public void getPlayerImage() {
        entityImage1 = setup("SecondaryFire01.png");
        entityImage2 = setup("SecondaryFire02.png");
        entityImage3 = setup("SecondaryFire03.png");
        entityImage4 = setup("SecondaryFire04.png");
        entityImage5 = setup("SecondaryFire05.png");
        entityImage6 = setup("SecondaryFire06.png");
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
