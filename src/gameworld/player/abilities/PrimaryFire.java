package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

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
        this.worldPos = new Point2D.Double(mg.player.worldX + 48 - projectileHeight / 2f, mg.player.worldY + 48 - projectileHeight / 2f);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.updateVector = getTrajectory(mg.motionH.lastMousePosition);
        getPlayerImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(projectileImage1, (int) (worldPos.x - mg.player.worldX + mg.HALF_WIDTH - 24), (int) (worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 24), projectileWidth, projectileHeight, null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    public Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - mg.HALF_HEIGHT - 24, mousePosition.x - mg.HALF_WIDTH - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }


    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}