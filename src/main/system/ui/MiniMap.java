package main.system.ui;

import gameworld.PRJ_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class MiniMap {
    private final MainGame mg;
    private final Color grey = Color.rgb(90, 105, 136, 1);
    private final Color green = Color.rgb(99, 199, 77, 1);
    private final Color blue = Color.rgb(0, 153, 219, 1);
    private final Color red = Color.rgb(255, 0, 68, 1);
    private final Color blue_npc = Color.rgb(18, 78, 137, 1);
    public MiniMap(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {
        int xTile = (mg.player.worldX + 24) / 48;
        int yTile = (mg.player.worldY + 24) / 48;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(1_700, 25, 1_900, 25);
        gc.strokeLine(1_700, 24, 1_700, 225);
        gc.strokeLine(1_900, 225, 1_700, 225);
        gc.strokeLine(1_900, 225, 1_900, 24);
        int yTileOffset, xTileOffset, entityX, entityY;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                yTileOffset = yTile - 20 + y;
                xTileOffset = xTile - 20 + x;
                if (xTileOffset > 0 && yTileOffset > 0 && xTileOffset < mg.wRender.worldSize.x && yTileOffset < mg.wRender.worldSize.y &&
                        mg.wRender.tileStorage[mg.wRender.worldData[xTileOffset][yTileOffset]].collision) {
                    gc.setFill(grey);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                } else {
                    gc.setFill(green);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                }
                if (xTileOffset == xTile && yTileOffset == yTile) {
                    gc.setFill(blue);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                }
                try {
                    for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                        if (!(entity instanceof ENT_Owly)) {
                            entityX = (entity.worldX + 24) / 48;
                            entityY = (24 + entity.worldY) / 48;
                            if (xTileOffset == entityX && yTileOffset == entityY) {
                                gc.setFill(red);
                                gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                            }
                        }
                    }
                } catch (ConcurrentModificationException | NullPointerException ignored) {

                }
                try {
                    for (PRJ_Control PRJControl : mg.PRJControls) {
                        if (PRJControl != null) {
                            int projectileX = (int) ((PRJControl.worldPos.x + 24) / 48);
                            int projectileY = (int) ((24 + PRJControl.worldPos.y) / 48);
                            if (xTileOffset == projectileX && yTileOffset == projectileY) {
                                gc.setFill(red);
                                gc.fillRect(1_700 + x * 5, 25 + y * 5, 2, 2);
                            }
                        }
                    }
                } catch (ConcurrentModificationException | NoSuchElementException ignored) {
                }
                for (ENTITY entity : mg.npcControl.NPC_Active) {
                    int projectileX = (entity.worldX + 24) / 48;
                    int projectileY = (24 + entity.worldY) / 48;
                    if (xTileOffset == projectileX && yTileOffset == projectileY) {
                        gc.setFill(blue_npc);
                        gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                    }
                }
            }
        }
    }
}

