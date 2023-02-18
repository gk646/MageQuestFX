package main.system.ui.maps;

import gameworld.PRJ_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.companion.ENT_Owly;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.WorldRender;
import main.system.ui.Colors;

import java.util.Objects;

public class MiniMap {
    private final MainGame mg;
    Image miniMapFrame = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/minimap_frame.png")));

    public MiniMap(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {
        int xTile = mg.playerX;
        int yTile = mg.playerY;
        int yTileOffset, xTileOffset, entityX, entityY;
        for (int y = 0; y < 40; y++) {
            for (int x = 0; x < 40; x++) {
                yTileOffset = Math.max(Math.min(yTile - 20 + y, mg.wRender.worldSize.x - 1), 0);
                xTileOffset = Math.max(Math.min(xTile - 20 + x, mg.wRender.worldSize.x - 1), 0);
                if (WorldController.currentMapCover[xTileOffset][yTileOffset] == 1) {
                    if (WorldRender.tileStorage[Math.max(WorldRender.worldData[xTileOffset][yTileOffset], 0)].collision) {
                        gc.setFill(Colors.darkBackground);
                        gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                    } else {
                        gc.setFill(Colors.map_green);
                        gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                    }
                } else {
                    gc.setFill(Colors.black);
                    gc.fillRect(1_700 + x * 5, 25 + y * 5, 5, 5);
                }
            }
        }
        gc.setFill(Colors.Blue);
        gc.fillRect(1_700 + 100, 25 + 100, 5, 5);
        synchronized (mg.PROXIMITY_ENTITIES) {
            gc.setFill(Colors.Red);
            for (gameworld.entities.ENTITY entity : mg.PROXIMITY_ENTITIES) {
                entityX = (int) ((entity.worldX + 24) / 48);
                entityY = (int) ((entity.worldY + 24) / 48);
                if (WorldController.currentMapCover[entityX][entityY] == 1) {
                    if ((entityX - xTile) < 20 && xTile - entityX <= 20 && (entityY - yTile) < 20 && yTile - entityY <= 20 && !(entity instanceof ENT_Owly)) {
                        gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 5, 5);
                    }
                }
            }
        }

        synchronized (mg.PROJECTILES) {
            for (PRJ_Control PRJControl : mg.PROJECTILES) {
                entityX = (int) ((PRJControl.worldPos.x + 24) / 48);
                entityY = (int) ((PRJControl.worldPos.y + 24) / 48);
                if (WorldController.currentMapCover[entityX][entityY] == 1) {
                    if ((entityX - xTile) < 20 && xTile - entityX <= 20 && (entityY - yTile) <= 20 && yTile - entityY < 20) {
                        gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 2, 2);
                    }
                }
            }
        }
        gc.setFill(Colors.blue_npc);
        for (ENTITY entity : mg.npcControl.NPC_Active) {
            entityX = (int) ((entity.worldX + 24) / 48);
            entityY = (int) ((24 + entity.worldY) / 48);
            if (WorldController.currentMapCover[entityX][entityY] == 1) {
                if ((entityX - xTile) < 20 && xTile - entityX <= 20 && (entityY - yTile) <= 20 && yTile - entityY < 20) {
                    gc.fillRect(1_700 + 100 + (entityX - xTile) * 5, 25 + 100 + (entityY - yTile) * 5, 5, 5);
                }
            }
        }
        gc.drawImage(miniMapFrame, 1663, 0);
    }
}


