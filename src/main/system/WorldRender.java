package main.system;

import gameworld.tiles.Tile;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public final Tile[] tileStorage;
    private final MainGame mg;
    public int[][] worldData;
    public Point worldSize, teleportPoint1, teleportPoint2;
    private int worldCol, worldRow;

    public WorldRender(MainGame mg) {
        this.mg = mg;
        this.tileStorage = new Tile[75];
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

        //BRICKS
        setupTiles(56, "grey_brick_wall.png", true);
        setupTiles(57, "brick_wall.png", true);

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
        setupTiles(34, "water34.png", false);


        setupTiles(40, "grass40.png", false);
        setupTiles(41, "grass41.png", false);
        setupTiles(42, "grass42.png", false);
        setupTiles(43, "grass43.png", false);
        setupTiles(44, "grass44.png", false);
        setupTiles(45, "grass45.png", false);

        setupTiles(47, "water47.png", false);
        setupTiles(48, "water48.png", false);
    }


    public void draw(Graphics2D g2) {
        worldCol = Math.max((mg.player.worldX / 48) - 20, 0);
        worldRow = Math.max((mg.player.worldY / 48) - 12, 0);
        int maxCol = Math.min(worldCol + 42, mg.wRender.worldSize.x);
        int maxRow = Math.min(worldRow + 25, mg.wRender.worldSize.y);
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                g2.drawImage(tileStorage[worldData[i][b]].tileImage, i * 48 - mg.player.worldX + mg.player.screenX, b * 48 - mg.player.worldY + mg.player.screenY, 48, 48, null);
            }
        }
    }
}


