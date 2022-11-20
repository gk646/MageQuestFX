package entity.entitys;

import main.MainGame;
import handlers.KeyHandler;
import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    MainGame mainGame;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;


    public Player(MainGame mainGame, KeyHandler keyHandler) {
        //Handlers
        this.mainGame = mainGame;
        this.keyHandler = keyHandler;
        screenX = MainGame.SCREEN_WIDTH / 2 - 12;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 12;

        //Initialize default values
        setDefaultValues();
        getPlayerImage();

        //Collision
        this.collisionBox = new Rectangle(8, 4, 32, 40);
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/boy_down_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = 2400;
        worldY = 2400;
        movementSpeed = 4;
        direction = "up";
        this.entityHeight = 48;
        this.entityWidth = 48;
    }

    public void update() {
        direction = "";

        if (keyHandler.upPressed) {
            direction += "up";
        }
        if (keyHandler.downPressed) {
            direction += "down";
        }
        if (keyHandler.leftPressed) {
            direction += "left";
        }
        if (keyHandler.rightPressed) {
            direction += "right";
        }
        //check tile collision
        collision = false;
        mainGame.collisionChecker.checkEntity(this);
        if (!collision) {
            if (direction.contains("up")) {
                worldY -= movementSpeed;
            }
            if (direction.contains("down")) {
                worldY += movementSpeed;
            }
            if (direction.contains("right")) {
                worldX += movementSpeed;
            }
            if (direction.contains("left")) {
                worldX -= movementSpeed;
            }
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage playerSprite;
        playerSprite = up1;
        g2.drawImage(playerSprite, screenX, screenY, 48, 48, null);

    }

}
