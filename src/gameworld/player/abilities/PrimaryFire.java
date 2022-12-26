package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;

public class PrimaryFire extends Projectile {

    /**
     * What happens when you press main mouse button
     *
     * @param mg           to access display functions
     * @param mouseHandler to get mouse input
     */
    public PrimaryFire(MainGame mg, MouseHandler mouseHandler) {
        super(mg, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 7;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = mg.imageSto.box_primaryFire;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePos = mg.motionH.lastMousePosition;
        this.worldPos = new Point(mg.player.worldX, mg.player.worldY);
        this.endPos = new Point(worldPos.x + 650, worldPos.y + 650);
        this.updateVector = getUpdateVector();
        getPlayerImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(projectileImage1, worldPos.x - mg.player.worldX + mg.HALF_WIDTH, worldPos.y - mg.player.worldY + mg.HALF_HEIGHT, projectileWidth, projectileHeight, null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x;
        worldPos.y += updateVector.y;
    }

    //Get normalized vector
    private Point getUpdateVector() {
        int deltaX = mousePos.x - mg.HALF_WIDTH;
        int deltaY = mousePos.y - mg.HALF_HEIGHT;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed * 2;
        double normalizedX = (deltaX / length) * movementSpeed * 2;
        return new Point((int) normalizedX, (int) normalizedY);
    }

    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}