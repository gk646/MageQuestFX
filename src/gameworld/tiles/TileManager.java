package gameworld.tiles;

import main.Display;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    Display display;
    Tile[] tileArray;
    int[][] mapData;

    public TileManager(Display display) {
        this.display = display;
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

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/resources/maps/map1.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int row = 0;
            int col = 0;
            while (row < 100 && row < 100) {

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
        int col = 0;
        int row = 0;
        int xPositionTile = 0;
        int yPositionTile = 0;

        while (col <= 100 && row <= 100) {
            int tileNum = mapData[col][row];

            g2.drawImage(tileArray[tileNum].tileImage, xPositionTile, yPositionTile, tileArray[0].tileSize, tileArray[0].tileSize, null);
            xPositionTile+= tileArray[tileNum].tileSize;
            col++;
            if (col == 100) {
                col = 0;
                xPositionTile =0;
                row++;
                yPositionTile+= tileArray[tileNum].tileSize;

            }
        }
    }

}
