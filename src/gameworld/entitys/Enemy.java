package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;
import main.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Enemy extends Entity {
    public int screenX;
    public int screenY;
    private BufferedImage enemyImage;
    private final Point startingPoint;

    public Enemy(MainGame mainGame, int worldX, int worldY, int health, Point startingPoint) {
        super(mainGame);

        this.mainGame = mainGame;
        //Setting default values
        this.worldX = worldX;
        this.worldY = worldY;

        movementSpeed = 4;
        direction = "up";
        this.startingPoint = startingPoint;
        this.entityHeight = 48;
        this.entityWidth = 48;
        getPlayerImage();
        this.collisionBox = new Rectangle(6, 6, 42, 42);
        this.health = health;

    }

    public void update() {
        screenX = worldX - mainGame.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = worldY - mainGame.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24;

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(enemyImage, screenX, screenY, 48, 48, null);
    }

    public void getPlayerImage() {
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
