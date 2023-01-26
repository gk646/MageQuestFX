package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;

public class EnemyProjectile1 extends Projectile {

    /**
     * Projectile of enemy entities
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
        this.worldPos = new Point2D.Double(x, y);
        this.updateVector = getTrajectory(new Point(mg.player.worldX, mg.player.worldY));
        getPlayerImage();
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(projectileImage1, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT, projectileWidth, projectileHeight, null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x;
        worldPos.y += updateVector.y;
    }

    public Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - mg.HALF_HEIGHT - 24, mousePosition.x - mg.HALF_WIDTH - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}