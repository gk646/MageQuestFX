package gameworld.projectiles;

import gameworld.Projectile;
import gameworld.entitys.Player;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class SecondaryFire extends Projectile {

    /**
     * What happens when you press secondary mouse button. Part of
     * {@link Projectile}
     */
    public SecondaryFire(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, KeyHandler keyHandler) {
        super(mainGame, motionHandler, mouseHandler, keyHandler);

        //-------VALUES-----------
        this.movementSpeed = 5;
        this.entityHeight = 25;
        this.entityWidth = 25;
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = mainGame.motionHandler.mousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + 1700;
        this.worldY = screenPosition.y + 1950;
        this.updateVector = getUpdateVector();
        getPlayerImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.blue);
        g2.drawImage(up1, screenPosition.x - mainGame.player.worldX + Player.startingPoint.x, screenPosition.y - mainGame.player.worldY + Player.startingPoint.y, entityWidth, entityHeight, null);

    }

    @Override
    public void update() {
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
        up1 = setup("SecondaryFire01.png");


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
