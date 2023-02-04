package gameworld.player.abilities;

import gameworld.PRJ_Control;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Effects;

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
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX + 48 - projectileHeight / 2.0f, Player.worldY + 48 - projectileHeight / 2.0f);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.updateVector = getTrajectory(mainGame.inputH.lastMousePosition);
        getPlayerImage();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setEffect(Effects.blueGlow);
        if (spriteCounter <= 12) {
            gc.drawImage(projectileImage1, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 24) {
            gc.drawImage(projectileImage2, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 36) {
            gc.drawImage(projectileImage3, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 48) {
            gc.drawImage(projectileImage4, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 60) {
            gc.drawImage(projectileImage5, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
        } else if (spriteCounter <= 72) {
            gc.drawImage(projectileImage6, (int) worldPos.x - Player.worldX + Player.screenX - 24, (int) worldPos.y - Player.worldY + Player.screenY - 24, projectileWidth, projectileHeight);
            spriteCounter = 0;
        }
        spriteCounter++;
        gc.setEffect(null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 24, mousePosition.x - Player.screenX - 24);
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
