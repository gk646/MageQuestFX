package main.system;

import gameworld.World;
import gameworld.tiles.Tile;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public static int[][] worldData;
    public static Point worldSize;
    public final Tile[] tileStorage;
    final MainGame mainGame;
    private boolean drawPath = true;

    public WorldRender(MainGame mainGame) {
        this.mainGame = mainGame;
        this.tileStorage = new Tile[50];
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
        //GRASS
        setup(1, "grass01.png", false);
        setup(2, "grass02.png", false);
        setup(3, "grass03.png", false);
        setup(4, "grass04.png", false);

        //WATER
        setup(5, "water05.png", true);
        setup(6, "water06.png", true);
        setup(7, "water07.png", true);
        //GRASS
        setup(14, "grass14.png", false);
        setup(15, "grass15.png", false);
        setup(16, "grass16.png", false);
        setup(17, "grass17.png", false);
        //WATER
        setup(18, "water18.png", true);
        setup(19, "water19.png", true);
        setup(20, "water20.png", true);
        //setup(21, "water.png", false);
        //setup(22, "grass22.png", false);
        //setup(24, "grass24.png", false);

        //GRASS
        setup(27, "grass27.png", false);
        setup(28, "grass27.png", false);
        setup(28, "grass28.png", false);
        setup(29, "grass29.png", false);
        setup(30, "grass30.png", false);

        //WATER
        setup(31, "water31.png", true);
        setup(32, "water32.png", true);
        setup(33, "water33.png", true);

        /*
        setup(14, "grass14.png", false);

        setup(2, "grass02.png", false);
        setup(3, "grass03.png", false);
        setup(4, "grass04.png", false);
        setup(14, "grass14.png", false);
        setup(15, "grass15.png", false);
        setup(17, "grass17.png", false);
        setup(16, "grass16.png", false);
        setup(28, "grass28.png", false);
        setup(29, "grass29.png", false);

         */


    }

    public void draw(Graphics2D g2) {


        int worldCol = (mainGame.player.worldX / 48) - 20;
        int worldRow = (mainGame.player.worldY / 48) - 12;
        int endx = worldCol + 42;
        int endy = worldRow + 25;


        while (worldCol < endx && worldRow < endy) {
            //reading out tile data
            int tileNum = worldData[worldCol][worldRow];
            //making world camera
            int worldX = worldCol * 48;
            int worldY = worldRow * 48;
            //
            int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
            int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;
            //if (worldX + 48 > mainGame.player.worldX - mainGame.player.screenX && worldX - 48 < mainGame.player.worldX + mainGame.player.screenX && worldY + 48 > mainGame.player.worldY - mainGame.player.screenY && worldY - 48 < mainGame.player.worldY + mainGame.player.screenY) {
            g2.drawImage(tileStorage[tileNum].tileImage, screenX, screenY, 48, 48, null);
            // }
            worldCol++;
            if (worldCol == endx) {
                worldCol = (mainGame.player.worldX / 48) - 20;
                worldRow++;
            }
        }
        if (drawPath) {
            g2.setColor((new Color(255, 0, 0, 70)));

            for (int i = 0; i < mainGame.pathFinder.pathList.size(); i++) {
                int worldX = mainGame.pathFinder.pathList.get(i).col * mainGame.tileSize;
                int worldY = mainGame.pathFinder.pathList.get(i).row * mainGame.tileSize;
                int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
                int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;
                g2.fillRect(screenX, screenY, mainGame.tileSize, mainGame.tileSize);
            }
        }
    }


}

