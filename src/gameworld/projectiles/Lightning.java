package gameworld.projectiles;

import gameworld.Projectile;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Lightning extends Projectile {
    private final MotionHandler motionHandler;

    /**
     * What happens when you press "2". Part of
     * {@link Projectile}
     */
    public Lightning(MainGame mainGame, MouseHandler mouseHandler, MotionHandler motionHandler) {
        super(mainGame, mouseHandler);
        this.motionHandler = motionHandler;

        //-------VALUES-----------
        this.entityHeight = 92;
        this.entityWidth = 70;
        this.collisionBox = new Rectangle(0, 0, 50, 30);

        //------POSITION-----------
        this.mousePosition = motionHandler.lastMousePosition;
        this.screenPosition = mousePosition;
        getPlayerImage();
        worldX = mainGame.player.worldX + screenPosition.x - MainGame.SCREEN_WIDTH / 2 - 24;
        worldY = mainGame.player.worldY + screenPosition.y - MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    @Override
    public void draw(Graphics2D g2) {
        screenX = worldX - mainGame.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mainGame.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24 - 15;
        if (spriteCounter <= 8) {
            g2.drawImage(entityImage1, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 6 && spriteCounter <= 14) {
            g2.drawImage(entityImage2, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 14 && spriteCounter <= 22) {
            g2.drawImage(entityImage3, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 22 && spriteCounter <= 30) {
            g2.drawImage(entityImage4, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 30 && spriteCounter <= 35) {
            g2.drawImage(entityImage5, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 35 && spriteCounter <= 40) {
            g2.drawImage(entityImage6, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 40 && spriteCounter <= 56) {
            g2.drawImage(entityImage7, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 56 && spriteCounter <= 64) {
            g2.drawImage(entityImage8, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 64 && spriteCounter <= 72) {
            g2.drawImage(entityImage9, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 72 && spriteCounter <= 80) {
            g2.drawImage(entityImage10, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 80) {
            this.dead = true;
        }
        spriteCounter++;
    }

    @Override
    public void update() {
    }

    public void getPlayerImage() {
        entityImage1 = setup("lightn01.png");
        entityImage2 = setup("lightn02.png");
        entityImage3 = setup("lightn03.png");
        entityImage4 = setup("lightn04.png");
        entityImage5 = setup("lightn05.png");
        entityImage6 = setup("lightn06.png");
        entityImage7 = setup("lightn07.png");
        entityImage8 = setup("lightn08.png");
        entityImage9 = setup("lightn09.png");
        entityImage10 = setup("lightn10.png");
    }


    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/projectiles/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 70, 92);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}
