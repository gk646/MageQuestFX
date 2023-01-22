package main.system.ui;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entities.Owly;
import gameworld.player.abilities.EnemyProjectile1;
import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;

public class MiniMap {
    private final MainGame mg;
    private final int pixelSize = 5;
    private final Color grey = new Color(90, 105, 136);
    private final Color green = new Color(99, 199, 77);
    private final Color blue = new Color(0, 153, 219);
    private final Color red = new Color(255, 0, 68);
    private BufferedImage miniMapImage;

    public MiniMap(MainGame mg) {
        this.mg = mg;
    }

    public void draw(Graphics2D g2) {
        int xTile = (mg.player.worldX + 24) / 48;
        int yTile = (mg.player.worldY + 24) / 48;
        g2.setColor(Color.black);
        g2.drawLine(1699, 24, 1899, 24);
        g2.drawLine(1699, 24, 1699, 225);
        g2.drawLine(1899, 225, 1699, 225);
        g2.drawLine(1900, 225, 1900, 24);
        int yTileOffset, xTileOffset, entityX, entityY;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                yTileOffset = yTile - 20 + y;
                xTileOffset = xTile - 20 + x;
                if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y &&
                        mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    g2.setColor(grey);
                    g2.fillRect(1700 + x * 5, 25 + y * 5, 5, 5);
                } else {
                    g2.setColor(green);
                    g2.fillRect(1700 + x * 5, 25 + y * 5, 5, 5);
                }
                if (xTileOffset == xTile && yTileOffset == yTile) {
                    g2.setColor(blue);
                    g2.fillRect(1700 + x * 5, 25 + y * 5, 5, 5);
                }
                try {
                    for (Entity entity : mg.PROXIMITY_ENTITIES) {
                        if (!(entity instanceof Owly)) {
                            entityX = (entity.worldX + 24) / 48;
                            entityY = (24 + entity.worldY) / 48;
                            if (xTileOffset == entityX && yTileOffset == entityY) {
                                g2.setColor(red);
                                g2.fillRect(1700 + x * 5, 25 + y * 5, 5, 5);
                            }
                        }
                    }
                } catch (ConcurrentModificationException ignored) {

                }
                try {
                    for (Projectile projectile : mg.PROJECTILES) {
                        int projectileX = (projectile.worldPos.x + 24) / 48;
                        int projectileY = (24 + projectile.worldPos.y) / 48;
                        if (xTileOffset == projectileX && yTileOffset == projectileY) {
                            g2.setColor(red);
                            g2.fillRect(1700 + x * 5, 25 + y * 5, 2, 2);
                        }
                    }
                } catch (ConcurrentModificationException ignored) {

                }
            }
        }
    }

    public void drawd(Graphics2D g2) {
        g2.drawLine(1699, 24, 1899, 24);
        g2.drawLine(1699, 24, 1699, 225);
        g2.drawLine(1899, 225, 1699, 225);
        g2.drawLine(1900, 225, 1900, 24);
        g2.drawImage(miniMapImage, 1700, 25, null);
    }

    public void getImageMiniMap() {
        int xTile = (mg.player.worldX + 24) / 48;
        int yTile = (mg.player.worldY + 24) / 48;
        BufferedImage image = new BufferedImage(1570, 940, BufferedImage.TYPE_INT_ARGB);
        int yTileOffset, xTileOffset, playerX, playerY, entityX, entityY;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                yTileOffset = yTile - 20 + y;
                xTileOffset = xTile - 20 + x;
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
                for (Projectile projectile : mg.PROJECTILES) {
                    if (projectile instanceof EnemyProjectile1) {
                        entityX = (projectile.worldPos.x + 24) / 48;
                        entityY = (24 + projectile.worldPos.y) / 48;
                        if (xTileOffset == entityX && yTileOffset == entityY) {
                            for (int i = y * 5; i < y * 5 + 2; i++) {
                                for (int b = x * 5; b < x * 5 + 2; b++) {
                                    image.setRGB(b, i, 0xD0FF0044);
                                }
                            }
                        }
                    }
                }
            }
        }
        miniMapImage = image;
    }
}

