package main.system.ui;

import gameworld.Entity;
import gameworld.entities.Owly;
import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class GameMap {
    private final MainGame mg;
    public final Rectangle mapMover;
    private final Color lightBackgroundAlpha = new Color(192, 203, 220, 190);
    private final int mapPanelX = 175;
    private final int mapPanelY = 75;
    private int xTile;
    private int yTile;
    private boolean followPlayer = true;
    private Point previousMousePosition;
    private BufferedImage mapImage;

    public GameMap(MainGame mg) {
        this.mg = mg;
        this.mapMover = new Rectangle(mapPanelX, mapPanelY, 1570, 940);
        xTile = (mg.player.worldX + 24) / 48;
        yTile = (mg.player.worldY + 24) / 48;
        hideMapCollision();
        dragMap();
    }

    public void draw(Graphics2D g2) {
        drawGameMapBackground(g2);
        g2.drawImage(mapImage, 175, 85, null);
        drawGameMapTop(g2);
    }

    public void dragMap() {
        if (followPlayer) {
            xTile = (mg.player.worldX + 24) / 48;
            yTile = (mg.player.worldY + 24) / 48;
        }
        if (mapMover.contains(mg.motionH.lastMousePosition) && mg.mouseH.mouse1Pressed) {
            followPlayer = false;
            xTile += Math.max(-3, Math.min(3, previousMousePosition.x - mg.motionH.lastMousePosition.x));
            yTile += Math.max(-3, Math.min(3, previousMousePosition.y - mg.motionH.lastMousePosition.y));
        }
        if (mg.mouseH.mouse2Pressed) {
            followPlayer = true;
        }
        previousMousePosition = mg.motionH.lastMousePosition;
    }

    public void getImage() {
        BufferedImage image = new BufferedImage(1570, 940, BufferedImage.TYPE_INT_ARGB);
        int yTileOffset, xTileOffset, playerX, playerY, entityX, entityY;
        for (int y = 0; y < 186; y++) {
            for (int x = 0; x < 314; x++) {
                yTileOffset = yTile - 93 + y;
                xTileOffset = xTile - 157 + x;
                if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y &&
                        mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD05A6988);
                        }
                    }
                } else {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD063C74D);
                        }
                    }
                }
                playerX = (mg.player.worldX + 24) / 48;
                playerY = (mg.player.worldY + 24) / 48;
                if (xTileOffset == playerX && yTileOffset == playerY) {
                    for (int i = y * 5; i < y * 5 + 5; i++) {
                        for (int b = x * 5; b < x * 5 + 5; b++) {
                            image.setRGB(b, i, 0xD00099DB);
                        }
                    }
                }
                try {
                    for (Entity entity : mg.PROXIMITY_ENTITIES) {
                        if (!(entity instanceof Owly)) {
                            entityX = (entity.worldX + 24) / 48;
                            entityY = (24 + entity.worldY) / 48;
                            if (xTileOffset == entityX && yTileOffset == entityY) {
                                for (int i = y * 5; i < y * 5 + 5; i++) {
                                    for (int b = x * 5; b < x * 5 + 5; b++) {
                                        image.setRGB(b, i, 0xD0FF0044);
                                    }
                                }
                            }
                        }
                    }
                } catch (ConcurrentModificationException ignored) {

                }
                for (Entity entity : mg.npc.NPC_Active) {
                    int projectileX = (entity.worldX + 24) / 48;
                    int projectileY = (24 + entity.worldY) / 48;
                    if (xTileOffset == projectileX && yTileOffset == projectileY) {
                        for (int i = y * 5; i < y * 5 + 5; i++) {
                            for (int b = x * 5; b < x * 5 + 5; b++) {
                                image.setRGB(b, i, 0xD0124E89);
                            }
                        }
                    }
                }
            }
        }
        mapImage = image;
    }

    private void drawGameMapBackground(Graphics2D g2) {
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(175, 75, 1570, 940, 25, 25);
    }

    private void drawGameMapTop(Graphics2D g2) {
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(175, 75, 1570, 35, 25, 25);
    }

    public void hideMapCollision() {
        mapMover.y = -1100;
    }

    public void resetMapCollision() {
        mapMover.y = 75;
    }
}