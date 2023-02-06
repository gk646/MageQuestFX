package main.system.ui.maps;

import gameworld.PRJ_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.WorldRender;
import main.system.ui.Colors;

public class MiniMap {
    private final MainGame mg;

    public MiniMap(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {
        int xTile = mg.playerX;
        int yTile = mg.playerY;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(1_700, 25, 200, 200);
        int yTileOffset, xTileOffset, entityX, entityY;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                yTileOffset = Math.max(Math.min(yTile - 20 + y, mg.wRender.worldSize.x - 1), 0);
                xTileOffset = Math.max(Math.min(xTile - 20 + x, mg.wRender.worldSize.x - 1), 0);
                if (WorldRender.tileStorage[WorldRender.worldData[xTileOffset][yTileOffset]].collision) {
                    gc.setFill(Colors.darkBackground);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                } else {
                    gc.setFill(Colors.map_green);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                }
            }
        }
        synchronized (mg.PROXIMITY_ENTITIES) {
            for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                entityX = (entity.worldX + 24) / 48;
                entityY = (entity.worldY + 24) / 48;
                if ((entityX - xTile) < 20 && xTile - entityX <= 20 && (entityY - yTile) < 20 && yTile - entityY <= 20 && !(entity instanceof ENT_Owly)) {
                    gc.setFill(Colors.Red);
                    gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 5, 5);
                }
            }
        }
        gc.setFill(Colors.Blue);
        gc.fillRect(1_700 + 100, 25 + 100, 5, 5);
        synchronized (mg.PROJECTILES) {
            for (PRJ_Control PRJControl : mg.PROJECTILES) {
                entityX = (int) ((PRJControl.worldPos.x + 24) / 48);
                entityY = (int) ((PRJControl.worldPos.y + 24) / 48);
                if ((entityX - xTile) < 20 && xTile - entityX <= 20 && (entityY - yTile) <= 20 && yTile - entityY < 20) {
                    gc.setFill(Colors.Red);
                    gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 2, 2);
                }
            }
        }
        for (ENTITY entity : mg.npcControl.NPC_Active) {
            entityX = (entity.worldX + 24) / 48;
            entityY = (24 + entity.worldY) / 48;
            gc.setFill(Colors.blue_npc);
            gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 5, 5);
        }
    }
}


