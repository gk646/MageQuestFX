package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int xPosition, yPosition, entityWidth, entityHeight;
    public int movementSpeed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public Rectangle solidArea;
    public boolean collision = false;

}
