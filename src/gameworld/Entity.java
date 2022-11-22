package gameworld;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main inheritable class for all gameworld.entity's
 */
public class Entity {
    public int worldY, worldX, entityWidth, entityHeight;
    public int movementSpeed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,player2;
    public String direction;
    public Rectangle collisionBox;
    public boolean collisionup, collisiondown, collisionleft, collisionright,dead;

}
