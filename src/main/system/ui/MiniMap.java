package main.system.ui;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entities.Owly;
import main.MainGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class MiniMap {
    private final MainGame mg;
    private final Color grey = new Color(90, 105, 136);
    private final Color green = new Color(99, 199, 77);
    private final Color blue = new Color(0, 153, 219);
    private final Color red = new Color(255, 0, 68);
    private final Color blue_npc = new Color(18, 78, 137);


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
                        if (projectile != null) {
                            int projectileX = (projectile.worldPos.x + 24) / 48;
                            int projectileY = (24 + projectile.worldPos.y) / 48;
                            if (xTileOffset == projectileX && yTileOffset == projectileY) {
                                g2.setColor(red);
                                g2.fillRect(1700 + x * 5, 25 + y * 5, 2, 2);
                            }
                        }
                    }
                } catch (ConcurrentModificationException | NoSuchElementException ignored) {
                }
                for (Entity entity : mg.npc.NPC_Active) {
                    int projectileX = (entity.worldX + 24) / 48;
                    int projectileY = (24 + entity.worldY) / 48;
                    if (xTileOffset == projectileX && yTileOffset == projectileY) {
                        g2.setColor(blue_npc);
                        g2.fillRect(1700 + x * 5, 25 + y * 5, 5, 5);
                    }
                }
            }
        }
    }
}

