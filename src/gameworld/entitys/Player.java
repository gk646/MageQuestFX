package gameworld.entitys;

import gameworld.Entity;
import input.KeyHandler;
import main.MainGame;
import main.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {
    public final int screenX;
    public final int screenY;
    public static Point startingPoint;
    private final KeyHandler keyHandler;


    public Player(MainGame mainGame, KeyHandler keyHandler) {
        super(mainGame);
        //Setting default values
        worldX = startingPoint.x;
        worldY = startingPoint.y;
        movementSpeed = 4;
        direction = "up";
        this.entityHeight = 48;
        this.entityWidth = 48;
        getPlayerImage();
        this.collisionBox = new Rectangle(8, 16, 31, 31);

        //Handlers
        this.mainGame = mainGame;
        this.keyHandler = keyHandler;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;
    }


    public void update() {
        direction = "";
        if (keyHandler.leftPressed) {
            direction += "left";
        }
        if (keyHandler.upPressed) {
            direction += "up";
        }
        if (keyHandler.downPressed) {
            direction += "down";
        }

        if (keyHandler.rightPressed) {
            direction += "right";
        }
        //check tile collision
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;

        mainGame.collisionChecker.checkEntityAgainstTile(this);

        if (direction.contains("left")) {
            if (!collisionLeft) {
                worldX -= movementSpeed;
            }
        }

        if (direction.contains("up")) {
            if (!collisionUp) {
                worldY -= movementSpeed;
            }
        }

        if (direction.contains("down")) {
            if (!collisionDown) {
                worldY += movementSpeed;
            }
        }

        if (direction.contains("right")) {

            if (!collisionRight) {
                worldX += movementSpeed;
            }
        }
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(up1, screenX, screenY, 48, 48, null);
    }

    public void getPlayerImage() {
        up1 = setup("Mage_down01.png");


    }
    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}
