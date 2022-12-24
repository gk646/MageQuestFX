package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Ability1 extends Projectile {

    private final int version;

    /**
     * What happens when you press (1). Part of
     * {@link Projectile}
     */
    public Ability1(MainGame mainGame, MouseHandler mouseHandler, int version) {
        super(mainGame, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 5;
        this.projectileHeight = 25;
        this.projectileWidth = 25;
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.version = version;

        //------POSITION-----------
        this.worldX = mg.player.worldX;
        this.worldY = mg.player.worldY;
        this.direction = "downleftrightup";
        this.updateVector = new Point(1, 1);
        getUpdateVector();
        this.endPositionX = worldX + 650;
        this.endPositionY = worldY + 650;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.drawRect(worldX - mg.player.worldX + mg.HALF_WIDTH, worldY - mg.player.worldY + mg.HALF_HEIGHT, projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldX += updateVector.x;
        worldY += updateVector.y;
    }


    private void getUpdateVector() {
        if (version == 0) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = movementSpeed;
        }
        if (version == 1) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
        }
        if (version == 2) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = movementSpeed;
        }
        if (version == 3) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
        }
        if (version == 4) {
            this.updateVector.x = 0;
            this.updateVector.y = movementSpeed;
        }
        if (version == 5) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = 0;
        }
        if (version == 6) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = 0;
        }
        if (version == 7) {
            this.updateVector.x = 0;
            this.updateVector.y = -1 * movementSpeed;
        }
    }
}