package main.system;

import gameworld.player.Player;
import main.MainGame;
import main.system.rendering.WorldRender;
import main.system.tiles.Tile;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.awt.Point;

class WorldRenderTest {
    MainGame mg;
    int worldRow, worldCol, maxCol, maxRow;
    int[][] worldData1;
    Tile[] tileStorage;

    Point worldSize = new Point(499, 499);


    @Test
    @Order(1)
    void draw() {
        long time = System.nanoTime();
        for (int a = 0; a < 400; a++) {
            worldCol = Math.max(mg.playerX - 21, 0);
            worldRow = Math.max(mg.playerY - 12, 0);
            maxCol = Math.min(worldCol + 42, worldSize.x);
            maxRow = Math.min(worldRow + 24, worldSize.y);
            Player.screenX = mg.HALF_WIDTH - 24;
            Player.screenY = mg.HALF_HEIGHT - 24;
            if (Player.screenX > Player.worldX) {
                Player.screenX = (int) Player.worldX;
            }
            if (Player.screenY > Player.worldY) {
                Player.screenY = (int) Player.worldY;
            }
            for (int i = worldCol; i < maxCol; i++) {
                for (int b = worldRow; b < maxRow; b++) {
                    mg.gc.drawImage(WorldRender.tileStorage[WorldRender.worldData[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY, 48, 48);
                }
            }
        }
        System.out.println("TIME needed instance : " + (System.nanoTime() - time) / 1_000_000);
    }


    @Test
    @Order(2)
    void drawINT() {
        long time = System.nanoTime();
        for (int a = 0; a < 400; a++) {
            int worldCol = Math.max(mg.playerX - 21, 0);
            int worldRow = Math.max(mg.playerY - 12, 0);
            int maxCol = Math.min(worldCol + 42, worldSize.x);
            int maxRow = Math.min(worldRow + 24, worldSize.y);
            Player.screenX = mg.HALF_WIDTH - 24;
            Player.screenY = mg.HALF_HEIGHT - 24;
            if (Player.screenX > Player.worldX) {
                Player.screenX = (int) Player.worldX;
            }
            if (Player.screenY > Player.worldY) {
                Player.screenY = (int) Player.worldY;
            }
            for (int i = worldCol; i < maxCol; i++) {
                for (int b = worldRow; b < maxRow; b++) {
                    mg.gc.drawImage(WorldRender.tileStorage[WorldRender.worldData[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY);
                }
            }
        }
        System.out.println("TIME needed :new " + (System.nanoTime() - time) / 1_000_000);
    }

    @Test
    @Order(3)
    void drawINTNonstatic() {
        long time = System.nanoTime();
        for (int a = 0; a < 400; a++) {
            int worldCol = Math.max(mg.playerX - 21, 0);
            int worldRow = Math.max(mg.playerY - 12, 0);
            int maxCol = Math.min(worldCol + 42, worldSize.x);
            int maxRow = Math.min(worldRow + 24, worldSize.y);
            Player.screenX = mg.HALF_WIDTH - 24;
            Player.screenY = mg.HALF_HEIGHT - 24;
            if (Player.screenX > Player.worldX) {
                Player.screenX = (int) Player.worldX;
            }
            if (Player.screenY > Player.worldY) {
                Player.screenY = (int) Player.worldY;
            }
            for (int i = worldCol; i < maxCol; i++) {
                for (int b = worldRow; b < maxRow; b++) {
                    mg.gc.drawImage(tileStorage[worldData1[i][b]].tileImage, i * 48 - Player.worldX + Player.screenX, b * 48 - Player.worldY + Player.screenY);
                }
            }
        }
        System.out.println("TIME needed :new not static " + (System.nanoTime() - time) / 1_000_000);
    }
}
