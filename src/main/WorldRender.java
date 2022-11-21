package main;

import gameworld.World;
import gameworld.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public Tile[] tileStorage;
    public static int[][] worldData;
    public static Point worldSize;
    MainGame mainGame;

    public WorldRender(MainGame mainGame) {
        this.mainGame = mainGame;
        this.tileStorage = new Tile[5];
        getTileImage();
        World.setWorldData();

    }

    private void getTileImage() {
        try {
            tileStorage[1] = new Tile();
            tileStorage[1].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/grass.png")));
            tileStorage[0] = new Tile();
            tileStorage[0].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/wall.png")));
            tileStorage[2] = new Tile();
            tileStorage[2].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/water.png")));
            tileStorage[2].collision = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loading the map data from a .txt-File. Iam using Tiled as a level editor.
     */


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

            if (worldX +48> mainGame.player.worldX - mainGame.player.screenX &&
                    worldX-48 < mainGame.player.worldX + mainGame.player.screenX &&
                    worldY +48> mainGame.player.worldY - mainGame.player.screenY &&
                    worldY -48< mainGame.player.worldY + mainGame.player.screenY) {
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

