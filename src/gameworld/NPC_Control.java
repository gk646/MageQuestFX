package gameworld;

import gameworld.entities.ENTITY;
import gameworld.entities.npcs.NPC_Man;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Map;

import java.util.ArrayList;

public class NPC_Control {

    private final ArrayList<ENTITY> NPC_GrassLand = new ArrayList<>();
    private final ArrayList<ENTITY> NPC_Tutorial = new ArrayList<>();
    private final MainGame mg;
    public ArrayList<ENTITY> NPC_Active = new ArrayList<>();
    // WORLD CODES
    // 0 = Tutorial // 1 = Grass Lands // 2 = City 1 // 3 =
    //
    private final ArrayList<ENTITY> NPC_City1 = new ArrayList<>();

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
        NPC_City1.add(new NPC_Man(mg, 2, 27 * 48, 26 * 48));
        NPC_Tutorial.add(new NPC_Man(mg, 1, 11 * 48, 4 * 48));
        NPC_GrassLand.add(new NPC_Man(mg, 2, 23_500, 23_500));
        NPC_GrassLand.add(new NPC_Man(mg, 2, 23_400, 23_500));
    }
}
