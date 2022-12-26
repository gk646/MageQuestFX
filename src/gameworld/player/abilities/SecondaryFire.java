package gameworld.player.abilities;

import gameworld.Projectile;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;

public class SecondaryFire extends Projectile {

    /**
     * What happens when you press secondary mouse button. Part of
     * {@link Projectile}
     */
    public SecondaryFire(MainGame mainGame, MouseHandler mouseHandler) {
        super(mainGame, mouseHandler);

        //-------VALUES-----------
        this.movementSpeed = 3;
        this.projectileHeight = 32;
        this.projectileWidth = 32;
        this.collisionBox = mg.imageSto.box_secondaryFire;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePos = mainGame.motionH.lastMousePosition;
        this.worldPos = new Point(mg.player.worldX, mg.player.worldY);
        this.endPos = new Point(worldPos.x + 650, worldPos.y + 650);
        screenPos = new Point();
        this.updateVector = getUpdateVector();
        getPlayerImage();
    }

    @Override
    public void draw(Graphics2D g2) {
        screenPos.x = worldPos.x - mg.player.worldX + 960;
        screenPos.y = worldPos.y - mg.player.worldY + 540;
        if (spriteCounter <= 12) {
            g2.drawImage(projectileImage1, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        } else if (spriteCounter <= 24) {
            g2.drawImage(projectileImage2, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        } else if (spriteCounter <= 36) {
            g2.drawImage(projectileImage3, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        } else if (spriteCounter <= 48) {
            g2.drawImage(projectileImage4, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        } else if (spriteCounter <= 60) {
            g2.drawImage(projectileImage5, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        } else if (spriteCounter <= 72) {
            g2.drawImage(projectileImage6, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
            spriteCounter = 0;
        }
        spriteCounter++;
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
        projectileImage1 = mg.imageSto.secondaryFire1;
        projectileImage2 = mg.imageSto.secondaryFire2;
        projectileImage3 = mg.imageSto.secondaryFire3;
        projectileImage4 = mg.imageSto.secondaryFire4;
        projectileImage5 = mg.imageSto.secondaryFire5;
        projectileImage6 = mg.imageSto.secondaryFire6;
    }
}
