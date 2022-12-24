package gameworld.player.abilities;

import gameworld.Projectile;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Lightning extends Projectile {

    /**
     * What happens when you press "2". Part of
     * {@link Projectile}
     */
    public Lightning(MainGame mainGame, MouseHandler mouseHandler, MotionHandler motionHandler) {
        super(mainGame, mouseHandler);

        //-------VALUES-----------
        this.projectileHeight = 92;
        this.projectileWidth = 70;
        this.collisionBox = new Rectangle(30, 30, 30, 30);

        //------POSITION-----------
        this.mousePosition = motionHandler.lastMousePosition;
        this.worldPos = new Point(mg.player.worldX, mg.player.worldY);
        getImages();
    }

    @Override
    public void draw(Graphics2D g2) {
        screenPos.x = worldPos.x - mg.player.worldX + mg.HALF_WIDTH;
        screenPos.y = worldPos.x - mg.player.worldY + mg.HALF_HEIGHT;
        if (spriteCounter <= 8) {
            g2.drawImage(projectileImage1, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 6 && spriteCounter <= 14) {
            g2.drawImage(projectileImage2, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 14 && spriteCounter <= 22) {
            g2.drawImage(projectileImage3, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 22 && spriteCounter <= 30) {
            g2.drawImage(projectileImage4, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 30 && spriteCounter <= 35) {
            g2.drawImage(projectileImage5, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 35 && spriteCounter <= 40) {
            g2.drawImage(projectileImage6, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 40 && spriteCounter <= 56) {
            g2.drawImage(projectileImage7, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 56 && spriteCounter <= 64) {
            g2.drawImage(projectileImage8, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 64 && spriteCounter <= 72) {
            g2.drawImage(projectileImage9, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 72 && spriteCounter <= 80) {
            g2.drawImage(projectileImage10, screenPos.x, screenPos.y, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 80) {
            this.dead = true;
        }
        spriteCounter++;
    }

    private void getImages() {
        projectileImage1 = mg.imageSto.Lightning1;
        projectileImage2 = mg.imageSto.Lightning2;
        projectileImage3 = mg.imageSto.Lightning3;
        projectileImage4 = mg.imageSto.Lightning4;
        projectileImage5 = mg.imageSto.Lightning5;
        projectileImage6 = mg.imageSto.Lightning6;
        projectileImage7 = mg.imageSto.Lightning7;
        projectileImage8 = mg.imageSto.Lightning8;
        projectileImage9 = mg.imageSto.Lightning9;
        projectileImage10 = mg.imageSto.Lightning10;
    }
}
