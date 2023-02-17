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
    public static int[][] worldData2;
    public Point worldSize;
    private int worldCol;
    private int worldRow;
    private int maxCol;
    private int maxRow;

    public WorldRender(MainGame mg) {
        this.mg = mg;
        tileStorage = new Tile[250];
        getTileImage();
    }

    private void setupTiles(int index, String imagePath, boolean collision) {
        tileStorage[index] = new Tile();
        tileStorage[index].tileImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/" + imagePath))));
        tileStorage[index].collision = collision;
    }


    private void getTileImage() {
        //WOOD
        setupTiles(53, "wooden_fence.png", true);

        //BRICKS
        setupTiles(55, "grey_brick_wall.png", true);
        setupTiles(56, "brick_wall.png", false);
        setupTiles(45, "black.png", true);
        //GRASS

        setupTiles(53, "wooden_fence.png", true);

//BRICKS
        setupTiles(55, "grey_brick_wall.png", true);
        setupTiles(56, "brick_wall.png", false);
        setupTiles(45, "black.png", true);
//GRASS
        setupTiles(0, "grass01.png", false);
        setupTiles(1, "grass02.png", false);
        setupTiles(2, "grass03.png", false);
        setupTiles(3, "grass04.png", false);

//WATER
        setupTiles(4, "water05.png", true);
        setupTiles(5, "water06.png", true);
        setupTiles(6, "water07.png", true);
//GRASS
        setupTiles(13, "grass14.png", false);
        setupTiles(14, "grass15.png", false);
        setupTiles(15, "grass16.png", false);
        setupTiles(16, "grass17.png", false);
//WATER
        setupTiles(17, "water18.png", true);
        setupTiles(18, "water19.png", true);
        setupTiles(19, "water20.png", true);
        setupTiles(20, "water21.png", true);

//GRASS
        setupTiles(26, "grass27.png", false);
        setupTiles(27, "grass27.png", false);
        setupTiles(27, "grass28.png", false);
        setupTiles(28, "grass29.png", false);
        setupTiles(29, "grass30.png", false);

//WATER
        setupTiles(30, "water31.png", true);
        setupTiles(31, "water32.png", true);
        setupTiles(32, "water33.png", true);
        setupTiles(33, "water34.png", false);

        setupTiles(39, "grass40.png", false);
        setupTiles(40, "grass41.png", false);
        setupTiles(41, "grass42.png", false);
        setupTiles(42, "grass43.png", false);
        setupTiles(43, "grass44.png", false);
        setupTiles(44, "grass45.png", false);

        setupTiles(46, "water47.png", false);
        setupTiles(47, "water48.png", false);

        setupTiles(57, "tree01.png", true);
        setupTiles(58, "tree02.png", true);

        setupTiles(79, "79.png", true);
        setupTiles(80, "79.png", true);
        setupTiles(81, "82.png", true);
        setupTiles(82, "83.png", true);
        setupTiles(92, "93.png", true);
        setupTiles(93, "94.png", true);
        setupTiles(94, "95.png", true);
        setupTiles(131, "132.png", false);
        setupTiles(132, "133.png", false);
        setupTiles(169, "170.png", true);
        setupTiles(182, "183.png", true);
        setupTiles(195, "196.png", true);
        setupTiles(199, "200.png", true);
        setupTiles(145, "146.png", false);
        setupTiles(184, "185.png", true);
        setupTiles(107, "108.png", true);
        setupTiles(120, "121.png", true);
        setupTiles(133, "134.png", false);
        setupTiles(226, "227.png", false);
        setupTiles(200, "201.png", true);
        setupTiles(210, "211.png", true);
        setupTiles(171, "171.png", true);
        setupTiles(183, "184.png", true);
        setupTiles(196, "197.png", true);
        setupTiles(186, "186.png", true);
        setupTiles(105, "105.png", true);
        setupTiles(170, "169.png", true);
        setupTiles(187, "187.png", true);
        setupTiles(212, "212.png", true);
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
        if (Player.screenY > Player.worldY) {
            Player.screenY = (int) Player.worldY;
        } else if (Player.worldY + 24 > mg.wRender.worldSize.x * 48 - mg.HALF_HEIGHT) {
            Player.screenY = (int) (MainGame.SCREEN_HEIGHT - (worldSize.x * 48 - Player.worldY));
            worldRow = Math.max(worldRow - 10, 0);
        }
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                if (worldData[i][b] != -1) {
                    g2.drawImage(tileStorage[worldData[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY, 48, 48);
                }
            }
        }
    }

    public void drawSecondLayer(GraphicsContext g2) {
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
        if (Player.screenY > Player.worldY) {
            Player.screenY = (int) Player.worldY;
        } else if (Player.worldY + 24 > mg.wRender.worldSize.x * 48 - mg.HALF_HEIGHT) {
            Player.screenY = (int) (MainGame.SCREEN_HEIGHT - (worldSize.x * 48 - Player.worldY));
            worldRow = Math.max(worldRow - 10, 0);
        }
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                if (worldData2[i][b] != -1) {
                    g2.drawImage(tileStorage[worldData2[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY, 48, 48);
                }
            }
        }
    }
}



