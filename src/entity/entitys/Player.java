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
        this.solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = entityWidth - solidArea.x;
        solidArea.height = 32;
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
        direction = "facingUp";
        this.entityHeight = 48;
        this.entityWidth = 48;
    }

    public void update() {
        if (keyHandler.upPressed) {
            worldY -= movementSpeed;
        }
        if (keyHandler.downPressed) {
            worldY += movementSpeed;
        }
        if (keyHandler.leftPressed) {
            worldX -= movementSpeed;
        }
        if (keyHandler.rightPressed) {
            worldX += movementSpeed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage playerSprite;
        playerSprite = up1;
        g2.drawImage(playerSprite, screenX, screenY, 48, 48, null);

    }

}
