package main.system;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.tiles.Tile;

import java.awt.Point;
import java.util.Objects;


public class ObjectRenderer {
    public final Tile[] tileStorage;
    private final MainGame mg;
    public int[][] worldData;
    public Point worldSize;
    private int worldCol, worldRow;

    public ObjectRenderer(MainGame mg) {
        this.mg = mg;
        this.tileStorage = new Tile[75];
        getTileImage();
    }

    private void setupTiles(int index, String imagePath, boolean collision) {
        tileStorage[index] = new Tile();
        tileStorage[index].tileImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/tiles/" + imagePath))));
        tileStorage[index].collision = collision;
    }


    private void getTileImage() {


    }


    public void draw(GraphicsContext g2) {
        int screenX, screenY;
        worldCol = Math.max(mg.playerX - 21, 0);
        worldRow = Math.max(mg.playerY - 12, 0);
        int maxCol = Math.min(worldCol + 42, mg.wRender.worldSize.x);
        int maxRow = Math.min(worldRow + 24, mg.wRender.worldSize.y);
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                mg.player.screenX = mg.HALF_WIDTH;
                mg.player.screenY = mg.HALF_HEIGHT;
                if (mg.player.screenX > mg.player.worldX) {
                    mg.player.screenX = mg.player.worldX;
                }
                if (mg.player.screenY > mg.player.worldY) {
                    mg.player.screenY = mg.player.worldY;
                }
                screenX = i * 48 - mg.player.worldX + mg.player.screenX;
                screenY = b * 48 - mg.player.worldY + mg.player.screenY;
                g2.drawImage(tileStorage[worldData[i][b]].tileImage, screenX, screenY, 48, 48);
            }
        }
    }
}


