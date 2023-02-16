package main.system;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.tiles.Tile;

import java.awt.Point;
import java.util.Objects;


public class WorldRender {
    public static Tile[] tileStorage;
    private final MainGame mg;
    public static int[][] worldData;
    public Point worldSize;
    private int worldCol;
    private int worldRow;
    private int maxCol;
    private int maxRow;

    public WorldRender(MainGame mg) {
        this.mg = mg;
        tileStorage = new Tile[75];
        getTileImage();
    }

    private void setupTiles(int index, String imagePath, boolean collision) {
        tileStorage[index] = new Tile();
        tileStorage[index].tileImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/" + imagePath))));
        tileStorage[index].collision = collision;
    }


    private void getTileImage() {
        //WOOD
        setupTiles(54, "wooden_fence.png", true);

        //BRICKS
        setupTiles(56, "grey_brick_wall.png", true);
        setupTiles(57, "brick_wall.png", false);
        setupTiles(46, "black.png", true);
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
        setupTiles(21, "water21.png", true);

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

        setupTiles(58, "tree01.png", true);
        setupTiles(59, "tree02.png", true);
    }


    public void draw(GraphicsContext g2) {
        int worldCol = Math.max(mg.playerX - 21, 0);
        int worldRow = Math.max(mg.playerY - 12, 0);
        int maxCol = Math.min(worldCol + 42, worldSize.x);
        int maxRow = Math.min(worldRow + 24, worldSize.y);
        Player.screenX = mg.HALF_WIDTH - 24;
        Player.screenY = mg.HALF_HEIGHT - 24;
        if (Player.screenX > Player.worldX) {
            Player.screenX = (int) Player.worldX;
        } else if (Player.worldX + 24 > mg.wRender.worldSize.x * 48 - mg.HALF_WIDTH) {
            Player.screenX = (int) (MainGame.SCREEN_WIDTH - (worldSize.x * 48 - Player.worldX));
            worldCol = Math.max(worldCol - 18, 0);
        }
        if (Player.screenY > Player.worldY + 24) {
            Player.screenY = (int) Player.worldY + 24;
        } else if (Player.worldY + 24 > mg.wRender.worldSize.x * 48 - mg.HALF_HEIGHT) {
            Player.screenY = (int) (MainGame.SCREEN_HEIGHT - (worldSize.x * 48 - Player.worldY));
            worldRow = Math.max(worldRow - 10, 0);
        }
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                g2.drawImage(tileStorage[worldData[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY, 48, 48);
            }
        }
    }
}



