package gameworld.player;


import gameworld.entities.damage.DamageType;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


/**
 * Inherits Entity
 * Main inheritable class for all projectiles
 */
public abstract class PROJECTILE {

    public Effect[] procEffects = new Effect[3];
    protected GeneralResourceLoader resource;
    protected Point endPos;
    /**
     * for Projectile sounds <p>
     * 0: flight sound/ normal sound /<p>
     * 1: hit sound / dead sound
     */
    public final MediaPlayer[] sounds = new MediaPlayer[3];

    public DamageType type;
    protected Point2D.Double updateVector;
    public ProjectileType projectileType;
    public float weapon_damage_percent;
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
    public boolean damageDead;
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
    public int duration;

    public PROJECTILE() {
    }


    abstract public void draw(GraphicsContext gc);

    abstract public void update();

    public void playHitSound() {
        if (System.currentTimeMillis() - lastHitTime >= 100) {
            sounds[1].seek(Duration.ZERO);
            sounds[1].play();
            lastHitTime = System.currentTimeMillis();
        }
    }

    public void playStartSound() {
        if (sounds[0] != null) {
            sounds[0].seek(Duration.ZERO);
            sounds[0].play();
        }
    }


    protected void outOfBoundsEnemy() {
        if (worldPos.x >= endPos.x || worldPos.y >= endPos.y || worldPos.y <= endPos.y - 850 * 2 || worldPos.x <= endPos.x - 850 * 2) {
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
}





