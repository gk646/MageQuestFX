package gameworld.tiles;

import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    MainGame mainGame;
    Tile[] tileArray;
    int[][] mapData;
    public int counter = 0;
    public TileManager(MainGame mainGame) {
        this.mainGame = mainGame;
        this.tileArray = new Tile[5];
        this.mapData = new int[101][101];
        getTileImage();
        loadMap();

    }

    private void getTileImage() {
        try {
            tileArray[1] = new Tile(48);
            tileArray[1].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/grass.png")));
            tileArray[0] = new Tile(48);
            tileArray[0].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/wall.png")));
            tileArray[2] = new Tile(48);
            tileArray[2].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/water.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loading the map data from a .txt-File. Iam using Tiled as a level editor.
     */
    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/resources/maps/map1.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int row = 0;
            int col = 0;
            while (row < 100 && col < 100) {
                String line = bufferedReader.readLine();
                while (col < 100) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapData[col][row] = num;
                    col++;
                }
                if (col == 100) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics2D g2) {
        counter=0;
        int worldCol = mainGame.player.worldX / 48-16 ;
        int worldRow = mainGame.player.worldY / 48 -11;

        while (worldCol  <= mainGame.player.worldX / 48 +15&& worldRow  <= mainGame.player.worldY / 48 + 7) {
            //reading out tile data
            int tileNum = mapData[worldCol][worldRow];
            //making world camera
            int worldX = worldCol * 48 + mainGame.player.worldX / 48;
            int worldY = worldRow * 48 + mainGame.player.worldY / 48;
            //
            int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
            int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;

            g2.drawImage(tileArray[tileNum].tileImage, screenX, screenY, tileArray[0].tileSize, tileArray[0].tileSize, null);
            counter++;
            worldCol++;
            if (worldCol == mainGame.player.worldX / 48 +15) {
                worldCol = mainGame.player.worldX / 48-16;
                worldRow++;
            }

        }


    }

}
