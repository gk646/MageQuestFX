package main;

import gameworld.World;
import gameworld.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public static int[][] worldData;
    public static Point worldSize;
    public Tile[] tileStorage;
    MainGame mainGame;

    public WorldRender(MainGame mainGame) {
        this.mainGame = mainGame;
        this.tileStorage = new Tile[5];
        getTileImage();
        World.setWorldData();

    }

    private void setup(int index, String imagePath, boolean collision) {
        Utilities utilities = new Utilities();
        try {
            tileStorage[index] = new Tile();
            tileStorage[index].tileImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/" + imagePath))));
            tileStorage[index].tileImage = utilities.scaleImage(tileStorage[index].tileImage, 48, 48);
            tileStorage[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTileImage() {
        setup(1, "grass.png", false);
        setup(0, "wall.png", true);
        setup(2, "water.png", true);

    }

    public void draw(Graphics2D g2) {


        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < worldSize.x && worldRow < worldSize.y) {
            //reading out tile data
            int tileNum = worldData[worldCol][worldRow];
            //making world camera
            int worldX = worldCol * 48;
            int worldY = worldRow * 48;
            //
            int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
            int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;

            if (worldX + 48 > mainGame.player.worldX - mainGame.player.screenX && worldX - 48 < mainGame.player.worldX + mainGame.player.screenX && worldY + 48 > mainGame.player.worldY - mainGame.player.screenY && worldY - 48 < mainGame.player.worldY + mainGame.player.screenY) {
                g2.drawImage(tileStorage[tileNum].tileImage, screenX, screenY, 48, 48, null);
            }


            worldCol++;
            if (worldCol == worldSize.x) {
                worldCol = 0;
                worldRow++;
            }


        }


    }

}

