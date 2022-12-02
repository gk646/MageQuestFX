package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Enemy extends Entity {
    private BufferedImage enemyImage;

    /**
     * Main Enemy class
     *
     * @param mainGame  super();
     * @param worldX    coordinates X
     * @param worldY    coordinates Y
     * @param maxHealth max amount of health
     */
    public Enemy(MainGame mainGame, int worldX, int worldY, int maxHealth) {
        super(mainGame);

        //Setting default values
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.worldX = worldX;
        this.worldY = worldY;
        movementSpeed = 4;
        direction = "updownleftright";
        this.entityHeight = 48;
        this.entityWidth = 48;

        this.collisionBox = new Rectangle(6, 6, 42, 42);

        getDisplayImage();
    }

    public void update() {
        screenX = worldX - mainGame.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mainGame.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(enemyImage, screenX, screenY, 48, 48, null);
    }

    public void getDisplayImage() {
        enemyImage = setup("enemy01.png");
    }

    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/enemies/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}
