package gameworld;

import gameworld.maps.OverWorld;
import gameworld.tiles.Tile;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.*;

import java.io.IOException;
import java.util.Objects;

public class WorldRender {
    MainGame mainGame;
    Tile[] tileStorage;
    int[][] worldData;

    public WorldRender(MainGame mainGame) {
        this.mainGame = mainGame;
        this.tileStorage = new Tile[5];
        getTileImage();
        loadMap();

    }

    private void getTileImage() {
        try {
            tileStorage[1] = new Tile(48);
            tileStorage[1].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/grass.png")));
            tileStorage[0] = new Tile(48);
            tileStorage[0].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/wall.png")));
            tileStorage[2] = new Tile(48);
            tileStorage[2].tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/water.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loading the map data from a .txt-File. Iam using Tiled as a level editor.
     */
    public void loadMap() {
        this.worldData = OverWorld.loadMap();
    }

    public void draw(Graphics2D g2) {

        int worldCol = Math.max(mainGame.player.worldX / 48 - 16,0);
        int worldRow = Math.max(mainGame.player.worldY / 48 - 11,0);

        while ( worldCol <= mainGame.player.worldX / 48 + 15 && worldRow <= mainGame.player.worldY / 48 + 8) {
            //reading out tile data
            int tileNum = worldData[worldCol][worldRow];
            //making world camera
            int worldX = worldCol * 48 + mainGame.player.worldX / 48;
            int worldY = worldRow * 48 + mainGame.player.worldY / 48;
            //
            int screenX = worldX - mainGame.player.worldX + mainGame.player.screenX;
            int screenY = worldY - mainGame.player.worldY + mainGame.player.screenY;

            g2.drawImage(tileStorage[tileNum].tileImage, screenX, screenY, tileStorage[0].tileSize, tileStorage[0].tileSize, null);

            worldCol++;
            if (worldCol == mainGame.player.worldX / 48 + 15) {
                worldCol = Math.max(mainGame.player.worldX / 48 - 16,0);
                worldRow++;
            }

        }


    }

}
