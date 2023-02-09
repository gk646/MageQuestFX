package main.system.ui.maps;

import gameworld.PRJ_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.player.Player;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.WorldRender;
import main.system.ui.Colors;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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
        xTile = mg.playerX;
        yTile = mg.playerY;
        hideMapCollision();
        dragMap();
    }

    public void draw(GraphicsContext gc) {
        drawGameMapBackground(gc);
        gc.drawImage(mapImage, 175, 75);
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
        int yTileOffset, xTileOffset, entityX, entityY;
        for (int y = 0; y < 186; y++) {
            for (int x = 0; x < 314; x++) {
                yTileOffset = Math.max(Math.min(yTile - 93 + y, mg.wRender.worldSize.x - 1), 0);
                xTileOffset = Math.max(Math.min(xTile - 157 + x, mg.wRender.worldSize.x - 1), 0);
                if (WorldRender.tileStorage[WorldRender.worldData[xTileOffset][yTileOffset]].collision) {

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
            }
        }
        int y = 465 + (mg.playerY - yTile) * 5;
        int x = 785 + (mg.playerX - xTile) * 5;
        for (int i = y; i < y + 5; i++) {
            for (int b = x; b < x + 5; b++) {
                image.setRGB(b, i, 0xD000_99DB);
            }
        }

        synchronized (mg.PROXIMITY_ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                entityX = (entity.worldX + 24) / 48;
                entityY = (entity.worldY + 24) / 48;
                if ((entityX - xTile) < 157 && xTile - entityX <= 157 && (entityY - yTile) < 93 && yTile - entityY <= 93 && !(entity instanceof ENT_Owly)) {
                    y = 465 + (entityY - yTile) * 5;
                    x = 785 + (entityX - xTile) * 5;
                    for (int i = y; i < y + 5; i++) {
                        for (int b = x; b < x + 5; b++) {
                            image.setRGB(b, i, 0xD0FF_0044);
                        }
                    }
                }
            }
        }

        synchronized (mg.PROJECTILES) {
            for (PRJ_Control PRJControl : mg.PROJECTILES) {
                entityX = (int) ((PRJControl.worldPos.x + 24) / 48);
                entityY = (int) ((PRJControl.worldPos.y + 24) / 48);
                if ((entityX - xTile) < 157 && xTile - entityX <= 157 && (entityY - yTile) <= 93 && yTile - entityY < 93) {
                    y = 465 + (entityY - yTile) * 5;
                    x = 785 + (entityX - xTile) * 5;
                    for (int i = y; i < y + 2; i++) {
                        for (int b = x; b < x + 2; b++) {
                            image.setRGB(b, i, 0xD0FF_0044);
                        }
                    }
                }
            }
        }

        for (ENTITY entity : mg.npcControl.NPC_Active) {
            entityX = (entity.worldX + 24) / 48;
            entityY = (entity.worldY + 24) / 48;
            y = 465 + (entityY - yTile) * 5;
            x = 785 + (entityX - xTile) * 5;
            if ((entityX - xTile) < 157 && xTile - entityX <= 157 && (entityY - yTile) <= 93 && yTile - entityY < 93) {
                for (int i = y; i < y + 5; i++) {
                    for (int b = x; b < x + 5; b++) {
                        image.setRGB(b, i, 0xD012_4E89);
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
