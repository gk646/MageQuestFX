package gameworld;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.entities.npcs.NPC_OldMan;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Map;

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
            entity.update();
        }
    }

    public void draw(GraphicsContext g2) {
        for (ENTITY entity : NPC_Active) {
            entity.draw(g2);
        }
    }

    public void loadNPC(Map map) {
        if (map == Map.Tutorial) {
            NPC_Active = NPC_Tutorial;
        } else if (map == Map.GrassLands) {
            NPC_Active = NPC_GrassLand;
        } else if (map == Map.City1) {
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
