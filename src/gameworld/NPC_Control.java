package gameworld;

import gameworld.entities.NPC.NPC_Man;
import main.MainGame;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class NPC_Control {

    // WORLD CODES
    // 0 = Tutorial // 1 = Grass Lands // 2 = City 1 // 3 =
    //
    public final ArrayList<Entity> NPC_City1 = new ArrayList<>();
    public final ArrayList<Entity> NPC_GrassLand = new ArrayList<>();
    public final ArrayList<Entity> NPC_Tutorial = new ArrayList<>();
    private final MainGame mg;
    public ArrayList<Entity> NPC_Active = new ArrayList<>();

    public NPC_Control(MainGame mg) {
        this.mg = mg;
        spawnNPC();
    }


    public void update() {
        for (Entity entity : NPC_Active) {
            entity.update();
        }
    }

    public void draw(Graphics2D g2) {
        for (Entity entity : NPC_Active) {
            entity.draw(g2);
        }
    }

    public void loadNPC(int mapNumber) {
        if (mapNumber == 0) {
            NPC_Active = NPC_Tutorial;
        } else if (mapNumber == 1) {
            NPC_Active = NPC_GrassLand;
        } else if (mapNumber == 2) {
            NPC_Active = NPC_City1;
        }
    }

    private void spawnNPC() {
        NPC_City1.add(new NPC_Man(mg, 0, 27 * 48, 26 * 48));
        NPC_Tutorial.add(new NPC_Man(mg, 0, 11 * 48, 4 * 48));
        NPC_GrassLand.add(new NPC_Man(mg, 1, 23500, 23500));
        NPC_GrassLand.add(new NPC_Man(mg, 2, 23400, 23500));
    }
}
