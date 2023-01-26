package gameworld.player.abilities;

import gameworld.Projectile;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

import java.awt.Graphics2D;
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
        this.collisionBox = new Rectangle(30, 30, 40, 30);

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(mainGame.player.worldX + motionHandler.lastMousePosition.x - mg.HALF_WIDTH - 24, mainGame.player.worldY + motionHandler.lastMousePosition.y - mg.HALF_HEIGHT - 24);
        getImages();
    }

    @Override
    public void draw(Graphics2D g2) {

        if (spriteCounter <= 8) {
            g2.drawImage(projectileImage1, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        }
        if (spriteCounter >= 6 && spriteCounter <= 14) {
            g2.drawImage(projectileImage2, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 14 && spriteCounter <= 22) {
            g2.drawImage(projectileImage3, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 22 && spriteCounter <= 30) {
            g2.drawImage(projectileImage4, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 30 && spriteCounter <= 35) {
            g2.drawImage(projectileImage5, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 35 && spriteCounter <= 40) {
            g2.drawImage(projectileImage6, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 40 && spriteCounter <= 56) {
            g2.drawImage(projectileImage7, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 56 && spriteCounter <= 64) {
            g2.drawImage(projectileImage8, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 64 && spriteCounter <= 72) {
            g2.drawImage(projectileImage9, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 72 && spriteCounter <= 80) {
            g2.drawImage(projectileImage10, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight, null);
        } else if (spriteCounter >= 80) {
            this.dead = true;
        }
        spriteCounter++;
    }

    public void update() {

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
