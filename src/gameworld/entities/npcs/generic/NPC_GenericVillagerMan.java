package gameworld.entities.npcs.generic;

import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_GenericVillagerMan extends NPC_Generic {
    public NPC_GenericVillagerMan(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/man");
        this.mg = mainGame;
        this.zone = zone;
        goalTile = new Point();
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }

    /**
     *
     */
    @Override
    public void draw(GraphicsContext gc) {
        drawNPCName(gc, "Villager");
    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     * @param gc
     */
    @Override
    public void drawDialog(GraphicsContext gc) {
        dialog.drawDialog(gc, this);
    }
}
