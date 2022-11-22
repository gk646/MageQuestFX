package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;
import main.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player2 extends Entity {


    public int screenX;
    public int screenY;
    public static Point startingPoint;
    private final MainGame mainGame;
    private BufferedImage player2;

    public Player2(MainGame mainGame) {

        //Setting default values
        worldX = startingPoint.x;
        worldY = startingPoint.y;
        screenX = worldX ;
        screenY = worldY ;
        getPlayerImage();

        this.entityHeight = 48;
        this.entityWidth = 48;


        //Handlers
        this.mainGame = mainGame;
    }
    private void getPlayerImage() {
        player2 = setup("player_2.png");
    }
    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
    public void draw(Graphics2D g2) {

        g2.drawImage(player2,screenX+startingPoint.x-mainGame.player.worldX, screenY+startingPoint.y-mainGame.player.worldY, 48, 48,null);
    }

}

