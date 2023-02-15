package gameworld;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.entities.npcs.NPC_OldMan;
import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.util.ArrayList;

public class NPC_Control {

    private final ArrayList<NPC> NPC_GrassLand = new ArrayList<>();
    private final ArrayList<NPC> NPC_Tutorial = new ArrayList<>();
    private final MainGame mg;
    // WORLD CODES
    // 0 = Tutorial // 1 = Grass Lands // 2 = City 1 // 3 =
    //
    private final ArrayList<NPC> NPC_City1 = new ArrayList<>();
    public ArrayList<NPC> NPC_Active = new ArrayList<>();

    public NPC_Control(MainGame mg) {
        this.mg = mg;
        spawnNPC();
    }


    public void update() {
        for (ENTITY entity : NPC_Active) {
            if (entity.zone == WorldController.currentWorld && Math.sqrt(Math.pow(entity.worldX - Player.worldX, 2) + Math.pow(entity.worldY - Player.worldY, 2)) < 4_500) {
                entity.update();
            }
        }
    }

    public void draw(GraphicsContext g2) {
        for (ENTITY entity : NPC_Active) {
            if (entity.zone == WorldController.currentWorld && Math.sqrt(Math.pow(entity.worldX - Player.worldX, 2) + Math.pow(entity.worldY - Player.worldY, 2)) < 1_500) {
                entity.draw(g2);
            }
        }
    }

    public void loadNPC(Zone zone) {
        if (zone == Zone.Tutorial) {
            NPC_Active = NPC_Tutorial;
        } else if (zone == Zone.GrassLands) {
            NPC_Active = NPC_GrassLand;
        } else if (zone == Zone.City1) {
            NPC_Active = NPC_City1;
        }
    }

    private void spawnNPC() {
        NPC_City1.add(new NPC_OldMan(mg, 27 * 48, 26 * 48));
        NPC_Tutorial.add(new NPC_OldMan(mg, 11 * 48, 4 * 48));
        NPC_GrassLand.add(new NPC_OldMan(mg, 23_500, 23_500));
        NPC_GrassLand.add(new NPC_OldMan(mg, 23_400, 23_500));
    }
}
