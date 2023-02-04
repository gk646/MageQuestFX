package main.system.ui.maps;

import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.player.Player;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class GameMap {
    public final Rectangle mapMover;
    private final MainGame mg;
    private final int mapPanelX = 175;
    private final int mapPanelY = 75;
    private final Point previousMousePosition = new Point(500, 500);
    private int xTile;
    private int yTile;
    private boolean followPlayer = true;
    private Image mapImage;

    /**
     * The big ingame map when you press "M"
     *
     * @param mg Main-game reference
     */
    public GameMap(MainGame mg) {
        this.mg = mg;
        this.mapMover = new Rectangle(mapPanelX, mapPanelY, 1_570, 940);
        xTile = (int) ((Player.worldX + 24) / 48);
        yTile = (int) ((Player.worldY + 24) / 48);
        hideMapCollision();
        dragMap();
    }

    public void draw(GraphicsContext gc) {
        drawGameMapBackground(gc);
        gc.drawImage(mapImage, 175, 85);
        drawGameMapTop(gc);
    }

    public void dragMap() {
        if (followPlayer) {
            xTile = (int) ((Player.worldX + 24) / 48);
            yTile = (int) ((Player.worldY + 24) / 48);
        }
        if (mapMover.contains(mg.inputH.lastMousePosition) && mg.inputH.mouse1Pressed) {
            followPlayer = false;
            xTile += Math.max(-3, Math.min(3, previousMousePosition.x - mg.inputH.lastMousePosition.x));
            yTile += Math.max(-3, Math.min(3, previousMousePosition.y - mg.inputH.lastMousePosition.y));
        }
        if (mg.inputH.mouse2Pressed) {
            followPlayer = true;
        }
        previousMousePosition.x = mg.inputH.lastMousePosition.x;
        previousMousePosition.y = mg.inputH.lastMousePosition.y;
    }

    public void getImage() {
        BufferedImage image = new BufferedImage(1_570, 940, BufferedImage.TYPE_INT_ARGB);
        int yTileOffset, xTileOffset, playerX, playerY, entityX, entityY;
        for (int y = 0; y < 186; y++) {
            for (int x = 0; x < 314; x++) {
                yTileOffset = yTile - 93 + y;
                xTileOffset = xTile - 157 + x;
                if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y && mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD05A_6988);
                        }
                    }
                } else {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD063_C74D);
                        }
                    }
                }
                playerX = (int) ((Player.worldX + 24) / 48);
                playerY = (int) ((Player.worldY + 24) / 48);
                if (xTileOffset == playerX && yTileOffset == playerY) {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD000_99DB);
                        }
                    }
                }
                try {
                    for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                        if (!(entity instanceof ENT_Owly)) {
                            entityX = (entity.worldX + 24) / 48;
                            entityY = (24 + entity.worldY) / 48;
                            if (xTileOffset == entityX && yTileOffset == entityY) {
                                for (int i = y * 5; i < y * 5 + 5; i++) {
                                    for (int b = x * 5; b < x * 5 + 5; b++) {
                                        image.setRGB(b, i, 0xD0FF_0044);
                                    }
                                }
                            }
                        }
                    }
                } catch (ConcurrentModificationException ignored) {

                }
                for (ENTITY entity : mg.npcControl.NPC_Active) {
                    int projectileX = (entity.worldX + 24) / 48;
                    int projectileY = (24 + entity.worldY) / 48;
                    if (xTileOffset == projectileX && yTileOffset == projectileY) {
                        for (int i = y * 5; i < y * 5 + 5; i++) {
                            for (int b = x * 5; b < x * 5 + 5; b++) {
                                image.setRGB(b, i, 0xD012_4E89);
                            }
                        }
                    }
                }
            }
        }

        mapImage = SwingFXUtils.toFXImage(image, null);
    }

    private void drawGameMapBackground(GraphicsContext g2) {
        g2.setStroke(Colors.LightGreyAlpha);
        g2.fillRoundRect(175, 75, 1_570, 940, 25, 25);
    }

    private void drawGameMapTop(GraphicsContext g2) {
        g2.setStroke(Colors.LightGreyAlpha);
        g2.fillRoundRect(175, 75, 1_570, 35, 25, 25);
    }

    public void hideMapCollision() {
        mapMover.y = -1_100;
    }

    public void resetMapCollision() {
        mapMover.y = 75;
    }
}
