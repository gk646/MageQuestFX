package main.system;

import gameworld.World;
import gameworld.tiles.Tile;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.Objects;


public class WorldRender {
    public static int[][] worldData;
    public static Point worldSize;
    public final Tile[] tileStorage;
    final MainGame mainGame;
    private boolean drawPath;

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
    }

    public void draw(Graphics2D g2) {
        int worldCol = (mainGame.player.worldX / 48) - 20;
        int worldRow = (mainGame.player.worldY / 48) - 12;
        for (int i = worldCol; i < worldCol + 42; i++) {
            for (int b = worldRow; b < worldRow + 25; b++) {
                //reading out tile data
                int tileNum = worldData[i][b];
                //making world camera
                int worldX = i * 48;
                int worldY = b * 48;
                //
                int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
                int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;
                //if (worldX + 48 > mainGame.player.worldX - mainGame.player.screenX && worldX - 48 < mainGame.player.worldX + mainGame.player.screenX && worldY + 48 > mainGame.player.worldY - mainGame.player.screenY && worldY - 48 < mainGame.player.worldY + mainGame.player.screenY) {
                g2.drawImage(tileStorage[tileNum].tileImage, screenX, screenY, 48, 48, null);
            }
        }
    }


}

