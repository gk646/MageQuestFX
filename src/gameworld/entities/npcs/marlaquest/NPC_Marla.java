package gameworld.entities.npcs.marlaquest;

import gameworld.entities.NPC;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_Marla extends NPC {

    public NPC_Marla(MainGame mainGame, int xTile, int yTile) {
        this.dialog = new Dialog();
        this.zone = Zone.Clearing;
        this.mg = mainGame;
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
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {

    }
}
