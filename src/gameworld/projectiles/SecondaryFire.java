package gameworld.projectiles;

import gameworld.Projectile;
import gameworld.entitys.Player;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

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
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.mousePosition = mainGame.motionH.mousePosition;
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x,
                MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2;
        this.worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2;
        this.updateVector = getUpdateVector();
        getPlayerImage();
        this.endPositionX = worldX + 650;
        this.endPositionY = worldY + 650;
    }

    @Override
    public void draw(Graphics2D g2) {
        screenX = screenPosition.x - mg.player.worldX + Player.startingPoint.x;
        screenY = screenPosition.y - mg.player.worldY + Player.startingPoint.y;
        if (spriteCounter <= 13) {
            g2.drawImage(projectileImage1, screenX, screenY, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 13) {
            g2.drawImage(projectileImage2, screenX, screenY, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 26) {
            g2.drawImage(projectileImage3, screenX, screenY, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 39) {
            g2.drawImage(projectileImage4, screenX, screenY, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 52) {
            g2.drawImage(projectileImage5, screenX, screenY, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 65) {
            g2.drawImage(projectileImage6, screenX, screenY, projectileWidth, projectileHeight, null);
            spriteCounter = 0;
        }
        spriteCounter++;
    }


    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2 + 24;
        worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2 + 24;
    }

    //Get normalized vector
    private Point getUpdateVector() {
        if (mousePosition == null) {
            mousePosition = mouseHandler.mouse2Position;
        }
        int deltaX = mousePosition.x - MainGame.SCREEN_WIDTH / 2;
        int deltaY = mousePosition.y - MainGame.SCREEN_HEIGHT / 2;
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
