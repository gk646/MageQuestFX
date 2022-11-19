package entity.entitys;

import main.Display;
import handlers.KeyHandler;
import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    Display display;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;


    public Player(Display display, KeyHandler keyHandler) {
        //Handlers
        this.display = display;
        this.keyHandler = keyHandler;
        screenX = Display.SCREEN_WIDTH / 2;
        screenY = Display.SCREEN_HEIGHT / 2;

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
        worldX = 1000;
        worldY = 1000;
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

    public Point getPlayerPosition() {
        return new Point(screenX + entityWidth / 4, screenY + entityHeight / 4);
    }

}
