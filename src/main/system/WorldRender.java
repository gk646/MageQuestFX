package main.system;

import gameworld.tiles.Tile;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public int[][] worldData;
    public Point worldSize;
    private int worldCol, worldRow, tileNum;
    public final Tile[] tileStorage;
    private final MainGame mg;

    public WorldRender(MainGame mg) {
        this.mg = mg;
        this.tileStorage = new Tile[50];
        getTileImage();
    }

    private void setupTiles(int index, String imagePath, boolean collision) {
        try {
            tileStorage[index] = new Tile();
            tileStorage[index].tileImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/" + imagePath))));
            tileStorage[index].tileImage = mg.utilities.scaleImage(tileStorage[index].tileImage, 48, 48);
            tileStorage[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getTileImage() {
        //GRASS
        setupTiles(1, "grass01.png", false);
        setupTiles(2, "grass02.png", false);
        setupTiles(3, "grass03.png", false);
        setupTiles(4, "grass04.png", false);

        //WATER
        setupTiles(5, "water05.png", true);
        setupTiles(6, "water06.png", true);
        setupTiles(7, "water07.png", true);
        //GRASS
        setupTiles(14, "grass14.png", false);
        setupTiles(15, "grass15.png", false);
        setupTiles(16, "grass16.png", false);
        setupTiles(17, "grass17.png", false);
        //WATER
        setupTiles(18, "water18.png", true);
        setupTiles(19, "water19.png", true);
        setupTiles(20, "water20.png", true);
        //setup(21, "water.png", false);
        //setup(22, "grass22.png", false);
        //setup(24, "grass24.png", false);

        //GRASS
        setupTiles(27, "grass27.png", false);
        setupTiles(28, "grass27.png", false);
        setupTiles(28, "grass28.png", false);
        setupTiles(29, "grass29.png", false);
        setupTiles(30, "grass30.png", false);

        //WATER
        setupTiles(31, "water31.png", true);
        setupTiles(32, "water32.png", true);
        setupTiles(33, "water33.png", true);
    }


    public void draw(Graphics2D g2) {
        worldCol = Math.max((mg.player.worldX / 48) - 20, 0);
        worldRow = Math.max((mg.player.worldY / 48) - 12, 0);
        for (int i = worldCol; i < Math.min(worldCol + 42, 499); i++) {
            for (int b = worldRow; b < Math.min(worldRow + 25, 499); b++) {
                tileNum = worldData[i][b];
                g2.drawImage(tileStorage[tileNum].tileImage, i * 48 - mg.player.worldX + mg.player.screenX, b * 48 - mg.player.worldY + mg.player.screenY, 48, 48, null);
            }
        }
    }
}

