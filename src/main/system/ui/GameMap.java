package main.system.ui;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entities.Owly;
import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class GameMap {
    private final MainGame mg;

    private final Color normalColor = new Color(143, 143, 140, 255), epicColor = new Color(168, 93, 218), legendaryColor = new Color(239, 103, 3);
    private final Color rareColor = new Color(26, 111, 175), lightBackgroundAlpha = new Color(192, 203, 220, 190), darkBackground = new Color(90, 105, 136);
    private final Rectangle mapMover;
    private final int mapPanelX = 175;
    private final int mapPanelY = 75;
    private final Color grey = new Color(124, 121, 121, 219);
    private final Color green = new Color(101, 203, 101, 219);
    private final Color blue = new Color(44, 109, 239, 219);
    private int xTile;
    private int yTile;
    private boolean followPlayer = true;
    private Point previousMousePosition;

    public GameMap(MainGame mg) {
        this.mg = mg;
        this.mapMover = new Rectangle(mapPanelX, mapPanelY, 1570, 940);
        xTile = (mg.player.worldX + 24) / 48;
        yTile = (mg.player.worldY + 24) / 48;
        hideMapCollision();
    }

    public void draw(Graphics2D g2) {
        drawGameMapBackground(mapPanelX, mapPanelY, g2);
        drawGameMap(mapPanelX, mapPanelY, g2);
        drawGameMapTop(mapPanelX, mapPanelY, g2);
    }

    public void dragMap() {
        if (followPlayer) {
            xTile = (mg.player.worldX + 24) / 48;
            yTile = (mg.player.worldY + 24) / 48;
        }
        if (mapMover.contains(mg.motionH.lastMousePosition) && mg.mouseH.mouse1Pressed) {
            followPlayer = false;
            xTile += Math.max(-1.5, Math.min(1.5, previousMousePosition.x - mg.motionH.lastMousePosition.x));
            yTile += Math.max(-1.5, Math.min(1.5, previousMousePosition.y - mg.motionH.lastMousePosition.y));
        }
        if (mg.mouseH.mouse2Pressed) {
            followPlayer = true;
        }
        previousMousePosition = mg.motionH.lastMousePosition;
    }

    private void drawGameMap(int startX, int startY, Graphics2D g2) {
        for (int y = 0; y < 186; y++) {
            for (int x = 0; x < 314; x++) {
                int yTileOffset = yTile - (186 / 2) + y;
                int xTileOffset = xTile - (314 / 2) + x;
                if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y &&
                        mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    g2.setColor(grey);
                    g2.fillRect(startX + x * 5, startY + y * 5 + 10, 5, 5);
                } else {
                    g2.setColor(green);
                    g2.fillRect(startX + x * 5, startY + y * 5 + 10, 5, 5);
                }
                try {
                    for (Entity entity : mg.PROXIMITY_ENTITIES) {
                        if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y
                                && !(entity instanceof Owly) &&
                                yTileOffset == (24 + entity.worldY) / 48 && xTileOffset == (entity.worldX + 24) / 48) {
                            g2.setColor(Color.red);
                            g2.fillRect(startX + x * 5, startY + y * 5 + 10, 5, 5);
                        }
                    }
                } catch (ConcurrentModificationException | NoSuchElementException ignored) {

                }
                for (Projectile projectile : mg.PROJECTILES) {
                    //System.out.println((24 + projectile.worldPos.y) / 48 +" " +  (projectile.worldPos.x + 24) / 48);
                    if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y &&
                            yTileOffset == (24 + projectile.worldPos.y) / 48 && xTileOffset == (projectile.worldPos.x + 24) / 48) {
                        g2.setColor(Color.black);
                        System.out.println("hey");
                        g2.fillRect(startX + x * 5, startY + y * 5 + 10, 1, 1);
                    }
                }
                if (xTileOffset == (mg.player.worldX + 24) / 48 && yTileOffset == (mg.player.worldY + 24) / 48) {
                    g2.setColor(blue);
                    g2.fillRect(startX + x * 5, startY + y * 5 + 10, 5, 5);
                }
            }
        }
    }

    private void drawGameMapBackground(int startX, int startY, Graphics2D g2) {
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(startX, startY, 1570, 940, 25, 25);
    }

    private void drawGameMapTop(int startX, int startY, Graphics2D g2) {
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(startX, startY, 1570, 35, 25, 25);
    }

    public void hideMapCollision() {
        mapMover.y = -1100;
    }

    public void resetMapCollision() {
        mapMover.y = 75;
    }
}
