package Entitys;

import Main.Display;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    Display display;
    KeyHandler keyHandler;

    public Player(Display display, KeyHandler keyHandler) {
        this.display = display;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
        direction = "facingUp";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/boy_down_1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        xPosition = 100;
        yPosition = 100;
        movementSpeed = 4;
    }

    public void update() {
        if (keyHandler.upPressed) {
            yPosition -= movementSpeed;
        }
        if (keyHandler.downPressed) {
            yPosition += movementSpeed;
        }
        if (keyHandler.leftPressed) {
            xPosition -= movementSpeed;
        }
        if (keyHandler.rightPressed) {
            xPosition += movementSpeed;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage playerSprite = null;
        switch (direction){
            case "up":
                playerSprite  = up1;
            case default:
                playerSprite = up1;

        }
        g2.drawImage(playerSprite,xPosition,yPosition,48,48,null);

    }

}
