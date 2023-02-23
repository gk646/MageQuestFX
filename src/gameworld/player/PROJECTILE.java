package gameworld.player;


import gameworld.entities.damage.DamageType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import main.system.CollisionChecker;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public abstract class PROJECTILE {

    protected Point endPos;
    public MediaPlayer sound;
    public DamageType type;
    protected Point2D.Double updateVector;
    public float damage;
    public int projectileHeight;
    protected int spriteCounter;
    protected Image projectileImage1;
    protected Image projectileImage2;
    protected Image projectileImage3;
    protected Image projectileImage4;
    protected Image projectileImage5;
    protected Image projectileImage6;
    protected Image projectileImage7;
    protected Image projectileImage8;
    protected Image projectileImage9;
    protected Image projectileImage10;
    public int projectileWidth;
    public boolean dead, collisionUp, collisionDown;
    public boolean collisionLeft;
    public boolean collisionRight;
    public String direction;
    public Rectangle collisionBox;
    public int movementSpeed;
    public Point2D.Double worldPos;
    public long lastHitTime;
    public int level;

    public PROJECTILE() {
    }


    abstract public void draw(GraphicsContext gc);

    abstract public void update();

    abstract public void playHitSound();


    protected void tileCollision() {
        CollisionChecker.checkProjectileAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }
    }

    protected void outOfBounds(int distance) {
        float x = Player.worldX;
        float y = Player.worldY;
        if (worldPos.x >= x + distance || worldPos.y >= y + distance || worldPos.y <= x - distance || worldPos.x <= y - distance) {
            this.dead = true;
        }
    }

    protected void outOfBounds() {
        float x = Player.worldX;
        float y = Player.worldY;
        if (worldPos.x >= x + 650 || worldPos.y >= y + 650 || worldPos.x <= x - 650 || worldPos.y <= y - 650) {
            this.dead = true;
        }
    }

    public void playFlightSound() {
        if (sound != null) {
            sound.setCycleCount(MediaPlayer.INDEFINITE);
            sound.play();
        }
    }

    public void stopFlightSound() {
        if (sound != null) {
            sound.stop();
        }
    }
}





