package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Owly extends Entity {

    public Owly(MainGame mg, int worldX, int worldY, int maxHealth) {
        super(mg);
        //Setting default values
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.worldX = worldX;
        this.worldY = worldY;
        movementSpeed = mg.player.movementSpeed;
        direction = "updownleftright";
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.collisionBox = new Rectangle(6, 6, 30, 30);
        this.onPath = true;
        getOwlyImage();
        this.searchTicks = 60;
        updatePos();
    }

    public void update() {
        screenX = worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
        if (worldX / mg.tileSize != mg.player.worldX / mg.tileSize || worldY / mg.tileSize != mg.player.worldY / mg.tileSize) {
            onPath = true;
        }
        owlyMovement();
        searchTicks++;

    }

    public void updatePos() {
        screenX = worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    private void getOwlyImage() {
        entityImage1 = setup("owly01.png");
        entityImage2 = setup("owly02.png");
        entityImage3 = setup("owly03.png");
        entityImage4 = setup("owly04.png");
        entityImage5 = setup("owly05.png");
        entityImage6 = setup("owly06.png");
    }

    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/entitys/owly/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 32, 32);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    public void draw(Graphics2D g2) {
        if (spriteCounter <= 8) {
            g2.drawImage(entityImage1, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 9 && spriteCounter <= 17) {
            g2.drawImage(entityImage2, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 18 && spriteCounter <= 24) {
            g2.drawImage(entityImage3, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 25 && spriteCounter <= 33) {
            g2.drawImage(entityImage4, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 34 && spriteCounter <= 42) {
            g2.drawImage(entityImage5, screenX, screenY, entityWidth, entityHeight, null);
        }
        if (spriteCounter >= 43 && spriteCounter <= 51) {
            g2.drawImage(entityImage6, screenX, screenY, entityWidth, entityHeight, null);
            spriteCounter = 0;
        }

        spriteCounter++;
    }

    private void owlyMovement() {
        if (onPath && searchTicks >= Math.random() * 45) {
            getNearestPlayer();
            searchPath(goalCol, goalRow);
            searchTicks = 0;
        } else if (onPath) {
            trackPath(goalCol, goalRow);
        }
    }
}


