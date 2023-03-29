package main.system.ui.maps;

import gameworld.entities.ENTITY;
import gameworld.player.PROJECTILE;
import gameworld.quest.QuestMarker;
import gameworld.world.WorldController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.rendering.WorldRender;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GameMap {
    private final Rectangle mapMover;
    private final MainGame mg;
    private final int mapPanelX = 175;
    private final int mapPanelY = 75;
    private final Point previousMousePosition = new Point(500, 500);
    public float zoom = 5;
    public float deltaZoom = 0;
    private int yOffset = 0;
    private int xOffset = 0;
    private boolean followPlayer = true;
    private Image mapImage;
    private BufferedImage image;
    public float xTile;
    public float yTile;

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
        mg.sBar.showNoticeMap = false;
    }

    public void draw(GraphicsContext gc) {
        drawGameMapBackground(gc);
        gc.drawImage(mapImage, 175, 80);
        drawTop(gc);
    }

    public void dragMap() {
        if (followPlayer) {
            xTile = mg.playerX;
            yTile = mg.playerY;
        }
        if (mapMover.contains(mg.inputH.lastMousePosition) && mg.inputH.mouse1Pressed) {
            followPlayer = false;
            xTile += (previousMousePosition.x - mg.inputH.lastMousePosition.x) / zoom;
            yTile += (previousMousePosition.y - mg.inputH.lastMousePosition.y) / zoom;
        }
        if (mg.inputH.mouse2Pressed) {
            followPlayer = true;
        }
        previousMousePosition.x = mg.inputH.lastMousePosition.x;
        previousMousePosition.y = mg.inputH.lastMousePosition.y;
    }

    private void getOffset(int zoom) {

        if (zoom == 1) {
            yOffset = 0;
            xOffset = 0;
        } else if (zoom == 2) {
            yOffset = 0;
            xOffset = 1;
        } else if (zoom == 3) {
            yOffset = 1;
            xOffset = 1;
        } else if (zoom == 4) {
            yOffset = 2;
            xOffset = 3;
        } else if (zoom == 5) {
            yOffset = 0;
            xOffset = 0;
        } else if (zoom == 6) {
            yOffset = 4;
            xOffset = 1;
        } else if (zoom == 7) {
            yOffset = 6;
            xOffset = 6;
        } else if (zoom == 8) {
            yOffset = 2;
            xOffset = 7;
        } else if (zoom == 9) {
            yOffset = 7;
            xOffset = 7;
        } else if (zoom == 10) {
            yOffset = 0;
            xOffset = 5;
        } else if (zoom == 11) {
            yOffset = 3;
            xOffset = 7;
        } else if (zoom == 12) {
            yOffset = 10;
            xOffset = 7;
        } else if (zoom == 13) {
            yOffset = 11;
            xOffset = 8;
        } else if (zoom == 14) {
            yOffset = 6;
            xOffset = 13;
        }
    }

    public void getImage() {
        int yTile_i = (int) yTile;
        int xTile_i = (int) xTile;
        int zoom_i = (int) zoom;
        getOffset(zoom_i);
        image = new BufferedImage(1_570, 935, BufferedImage.TYPE_INT_ARGB);
        int entityX, entityY;
        int tileNum, tileNum1;
        int offsetx = (int) (xTile_i - 1_570.0f / (zoom_i * 2));
        int offsety = (int) (yTile_i - 935.0f / (zoom_i * 2));
        for (int y = 0; y < (935 / zoom_i) + 1; y++) {
            for (int x = 0; x < (1_570 / zoom_i) + 1; x++) {
                if (offsetx + x < mg.wRender.worldSize.x && offsetx + x >= 0 && offsety + y < mg.wRender.worldSize.x && offsety + y >= 0) {
                    if (WorldController.currentMapCover[offsetx + x][offsety + y] == 1) {
                        tileNum = WorldRender.worldData[offsetx + x][offsety + y];
                        tileNum1 = WorldRender.worldData1[offsetx + x][offsety + y];
                        if (mg.wRender.tileStorage[tileNum].collision) {
                            for (float i = y * zoom_i; i < y * zoom_i + zoom_i; i++) {
                                for (float b = x * zoom_i; b < x * zoom_i + zoom_i; b++) {
                                    if (i < 935 && b < 1_570 && i >= 0 && b >= 0) {
                                        image.setRGB((int) b, (int) i, 0xD05A_6988);
                                    }
                                }
                            }
                        } else if (tileNum1 != -1 && mg.wRender.tileStorage[tileNum1].collision) {
                            for (float i = y * zoom_i; i < y * zoom_i + zoom_i; i++) {
                                for (float b = x * zoom_i; b < x * zoom_i + zoom_i; b++) {
                                    if (i < 935 && b < 1_570 && i >= 0 && b >= 0) {
                                        image.setRGB((int) b, (int) i, 0xD05A_6988);
                                    }
                                }
                            }
                        } else {
                            for (float i = y * zoom_i; i < y * zoom_i + zoom_i; i++) {
                                for (float b = x * zoom_i; b < x * zoom_i + zoom_i; b++) {
                                    if (i < 935 && b < 1_570 && i >= 0 && b >= 0) {
                                        image.setRGB((int) b, (int) i, 0xD063_C74D);
                                    }
                                }
                            }
                        }
                    } else {
                        for (float i = y * zoom_i; i < y * zoom_i + zoom_i; i++) {
                            for (float b = x * zoom_i; b < x * zoom_i + zoom_i; b++) {
                                if (i < 935 && b < 1_570 && i >= 0 && b >= 0) {
                                    image.setRGB((int) b, (int) i, 0xD025_2426);
                                }
                            }
                        }
                    }
                }
            }
        }
        int y = 470 + yOffset + (mg.playerY - yTile_i) * zoom_i;
        int x = 785 + xOffset + (mg.playerX - xTile_i) * zoom_i;
        for (int i = y; i < y + zoom_i; i++) {
            for (int b = x; b < x + zoom_i; b++) {
                if (i < 935 && b < 1_570 && i > 0 && b > 0) {
                    image.setRGB(b, i, 0xD000_99DB);
                }
            }
        }
        synchronized (mg.PROXIMITY_ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                entityX = (int) ((entity.worldX + 24) / 48);
                entityY = (int) ((entity.worldY + 24) / 48);
                y = 470 + yOffset + (entityY - yTile_i) * zoom_i;
                x = 785 + xOffset + (entityX - xTile_i) * zoom_i;
                for (float i = y; i < y + zoom_i; i++) {
                    for (float b = x; b < x + zoom_i; b++) {
                        if (i < 935 && b < 1_570 && i > 75 && b > 175) {
                            image.setRGB((int) b, (int) i, 0xD0FF_0044);
                        }
                    }
                }
            }
        }
        synchronized (mg.PROJECTILES) {
            for (PROJECTILE projectile : mg.PROJECTILES) {
                entityX = (int) ((projectile.worldPos.x + 24) / 48);
                entityY = (int) ((projectile.worldPos.y + 24) / 48);
                y = 465 + yOffset + (entityY - yTile_i) * zoom_i;
                x = 785 + xOffset + (entityX - xTile_i) * zoom_i;
                if ((entityX - xTile_i) < 157 && xTile_i - entityX <= 157 && (entityY - yTile_i) <= 93 && yTile_i - entityY < 93) {
                    for (float i = y; i < y + 2; i++) {
                        for (float b = x; b < x + 2; b++) {
                            image.setRGB((int) b, (int) i, 0xD0FF_0044);
                        }
                    }
                }
            }
        }
        synchronized (mg.ENTITIES) {
            for (ENTITY entity : mg.npcControl.NPC_Active) {
                if (entity.zone == WorldController.currentWorld) {
                    entityX = (int) ((entity.worldX + 24) / 48);
                    entityY = (int) ((entity.worldY + 24) / 48);
                    y = 465 + yOffset + (entityY - yTile_i) * zoom_i;
                    x = 785 + xOffset + (entityX - xTile_i) * zoom_i;
                    if ((entityX - xTile_i) < 157 && xTile_i - entityX <= 157 && (entityY - yTile_i) <= 93 && yTile_i - entityY < 93) {
                        for (float i = y; i < y + zoom_i; i++) {
                            for (float b = x; b < x + zoom_i; b++) {
                                if (i < 935 && b < 1_570 && i > 0 && b > 0) {
                                    image.setRGB((int) b, (int) i, 0xD012_4E89);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (mg.qPanel.activeQuest != null) {
            for (QuestMarker questMarker : mg.qPanel.activeQuest.questMarkers) {
                if (questMarker.zone() == WorldController.currentWorld) {
                    y = 465 + yOffset + (questMarker.yTile() - yTile_i) * zoom_i;
                    x = 785 + xOffset + (questMarker.xTile() - xTile_i) * zoom_i;
                    for (float i = y; i < y + zoom_i; i++) {
                        for (float b = x; b < x + zoom_i; b++) {
                            if (i < 935 && b < 1_570 && i > 0 && b > 0) {
                                image.setRGB((int) b, (int) i, 0xA0FE_E761);
                            }
                        }
                    }
                }
            }
        }
        mapImage = SwingFXUtils.toFXImage(image, null);
        image = null;
    }


    private void drawGameMapBackground(GraphicsContext gc) {
        gc.setFill(Colors.mediumLightGreyTransparent);
        gc.fillRoundRect(175, 75, 1_570, 940, 25, 25);
    }

    private void drawTop(GraphicsContext gc) {
        gc.setStroke(Colors.darkBackground);
        gc.setLineWidth(5);
        gc.strokeRoundRect(175, 70, 1_570, 945, 15, 15);
        gc.strokeRoundRect(175, 70, 1_570, 15, 15, 15);
        gc.setLineWidth(1);
        gc.setFill(Colors.mediumVeryLight);
        gc.fillRoundRect(175, 70, 1_570, 15, 10, 10);
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBold13);
        gc.fillText("World Map", 925, 82);
        drawLegend(gc);
    }

    private void drawLegend(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold14);
        gc.setFill(Colors.mediumVeryLight);
        gc.fillRect(1_580, 110, 155, 135);
        gc.setLineWidth(2);
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect(1_580, 110, 155, 135, 3, 3);
        gc.setLineWidth(1);
        gc.setFill(Colors.darkBackground);
        gc.fillText("SPACE : Recenter", 1_585, 125);
        gc.fillText("SCROLL: Zoom +/-", 1_585, 240);
        gc.setFill(Colors.Blue);
        gc.fillRect(1_595, 135, 5, 5);
        gc.fillText("          : Player", 1_585, 140);
        gc.setFill(Colors.Red);
        gc.fillRect(1_595, 155, 5, 5);
        gc.fillText("          : Enemy", 1_585, 160);
        gc.setFill(Colors.blue_npc);
        gc.fillRect(1_595, 175, 5, 5);
        gc.fillText("          : NPC", 1_585, 180);
        gc.setFill(Colors.questMarkerYellow);
        gc.fillRect(1_595, 195, 5, 5);
        gc.fillText("          : Quest POI", 1_585, 200);
        gc.setFill(Colors.darkBackground);
        gc.fillRect(1_595, 215, 5, 5);
        gc.fillText("          : Solid Tile", 1_585, 220);
    }

    public void hideMapCollision() {
        mapMover.y = -1_100;
    }

    public void resetMapCollision() {
        mapMover.y = 75;
        mg.sBar.showNoticeMap = false;
    }
}
