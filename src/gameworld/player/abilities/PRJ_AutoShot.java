package gameworld.player.abilities;

import gameworld.PRJ_Control;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PRJ_AutoShot extends PRJ_Control {

    /**
     * What happens when you press main mouse button
     *
     * @param mg to access display functions
     */
    public PRJ_AutoShot(MainGame mg) {
        super(mg);

        //-------VALUES-----------
        this.movementSpeed = 7;
        this.projectileHeight = 16;
        this.projectileWidth = 16;
        this.collisionBox = mg.imageSto.box_primaryFire;
        this.direction = "downleftrightup";
        //------POSITION-----------
        this.worldPos = new Point2D.Double(mg.player.worldX + 48 - projectileHeight / 2.0f, mg.player.worldY + 48 - projectileHeight / 2.0f);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.updateVector = getTrajectory(mg.inputH.lastMousePosition);
        getPlayerImage();
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.drawImage(projectileImage1, (int) (worldPos.x - mg.player.worldX + mg.player.screenX), (int) (worldPos.y - mg.player.worldY + mg.player.screenY), projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - mg.player.screenY, mousePosition.x - mg.player.screenX);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }


    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.primaryFire1;
    }
}