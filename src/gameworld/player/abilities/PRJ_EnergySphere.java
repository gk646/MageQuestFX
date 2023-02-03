package gameworld.player.abilities;

import gameworld.PRJ_Control;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PRJ_EnergySphere extends PRJ_Control {

    /**
     * Energy Sphere projectile
     */
    public PRJ_EnergySphere(MainGame mainGame) {
        super(mainGame);

        //-------VALUES-----------
        this.movementSpeed = 3;
        this.projectileHeight = 32;
        this.projectileWidth = 32;
        this.collisionBox = mg.imageSto.box_secondaryFire;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(mg.player.worldX + 48 - projectileHeight / 2.0f, mg.player.worldY + 48 - projectileHeight / 2.0f);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.updateVector = getTrajectory(mainGame.inputH.lastMousePosition);
        getPlayerImage();
    }

    @Override
    public void draw(GraphicsContext g2) {
        if (spriteCounter <= 12) {
            g2.drawImage(projectileImage1, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 24) {
            g2.drawImage(projectileImage2, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 36) {
            g2.drawImage(projectileImage3, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 48) {
            g2.drawImage(projectileImage4, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 60) {
            g2.drawImage(projectileImage5, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 72) {
            g2.drawImage(projectileImage6, (int) worldPos.x - mg.player.worldX + mg.player.screenX, (int) worldPos.y - mg.player.worldY + mg.player.screenY, projectileWidth, projectileHeight);
            spriteCounter = 0;
        }
        spriteCounter++;
    }


    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - mg.HALF_HEIGHT - 24, mousePosition.x - mg.HALF_WIDTH - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    private void getPlayerImage() {
        projectileImage1 = mg.imageSto.secondaryFire1;
        projectileImage2 = mg.imageSto.secondaryFire2;
        projectileImage3 = mg.imageSto.secondaryFire3;
        projectileImage4 = mg.imageSto.secondaryFire4;
        projectileImage5 = mg.imageSto.secondaryFire5;
        projectileImage6 = mg.imageSto.secondaryFire6;
    }
}
