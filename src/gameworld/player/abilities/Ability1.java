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
        this.worldPos = new java.awt.geom.Point2D.Double(mg.player.worldX, mg.player.worldY);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.direction = "downleftrightup";
        this.updateVector = new java.awt.geom.Point2D.Double(1, 1);
        getUpdateVector();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.drawRect((int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT, projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x;
        worldPos.y += updateVector.y;
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