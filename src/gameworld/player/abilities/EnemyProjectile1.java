package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;

public class EnemyProjectile1 extends Projectile {
    private final int level;

    /**
     * What happens when you press main mouse button
     *
     * @param mg           to access display functions
     * @param mouseHandler to get mouse input
     */
    public EnemyProjectile1(MainGame mg, MouseHandler mouseHandler, int x, int y, int level) {
        super(mg, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 3;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = mg.imageSto.box_primaryFire;
        this.level = level;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = new Point(mg.player.worldX, mg.player.worldY);
        this.worldPos = new Point(x, y);
        this.updateVector = getUpdateVector();
        getPlayerImage();
        this.endPos = new Point(worldPos.x + 650, worldPos.y + 650);
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
        int deltaX = mousePosition.x - worldPos.x;
        int deltaY = mousePosition.y - worldPos.y;
        double length = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double normalizedY = (deltaY / length) * movementSpeed * 2;
        double normalizedX = (deltaX / length) * movementSpeed * 2;
        return new Point((int) normalizedX, (int) normalizedY);
    }

    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}