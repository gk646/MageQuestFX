package gameworld.entities.NPC;

import gameworld.Entity;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC_Man extends Entity {
    private BufferedImage player2;
    private boolean dialouge;

    public NPC_Man(MainGame mainGame, int x, int y) {
        this.mg = mainGame;
        //Setting default values
        getPlayerImage();
        worldX = x;
        worldY = y;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 4;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(player2, worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24, worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24, 48, 48, null);
        if (dialouge) {
            g2.setColor(Color.RED);
            g2.draw(new Rectangle(worldX - mg.player.worldX + MainGame.SCREEN_WIDTH / 2 - 24 - 50, worldY - mg.player.worldY + MainGame.SCREEN_HEIGHT / 2 - 24 - 100, 200, 25));
        }
    }

    public void update() {
        if (mg.collisionChecker.checkEntityAgainstEntity(this, mg.player) && mg.keyHandler.Epressed) {
            dialouge = true;
            onPath = true;
        }
        moveToTile(35, 50);
        searchTicks++;
    }

    private void getPlayerImage() {
        player2 = setup("man01.png");
    }

    private void owlyMovement() {
        if (onPath && searchTicks >= Math.random() * 45) {
            getNearestPlayer();
            searchPath(goalCol, goalRow, 100);
            searchTicks = 0;
        } else if (onPath) {
            trackPath(goalCol, goalRow);
        }
    }

    private BufferedImage setup(String imagePath) {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/npc/" + imagePath))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}

