package gameworld;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.entities.npcs.generic.NPC_GenericVillagerWoman;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.npcs.trader.NPC_AbilityTrader;
import gameworld.entities.npcs.trader.NPC_AccessoriesTrader;
import gameworld.entities.npcs.trader.NPC_ArmourTrader;
import gameworld.entities.npcs.trader.NPC_WeaponTrader;
import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.util.ArrayList;

public class NPC_Control {


    private final MainGame mg;

    public ArrayList<NPC> NPC_Active = new ArrayList<>();

    public NPC_Control(MainGame mg) {
        this.mg = mg;
        NPC_Active.add(new NPC_ArmourTrader(mg, 27, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_WeaponTrader(mg, 33, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_AbilityTrader(mg, 30, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_AccessoriesTrader(mg, 36, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_GenericVillagerWoman(mg, 26, 20, Zone.Hillcrest));
        NPC_Active.add(new NPC_Marla(mg, 41, 28));
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
            if (entity.zone == WorldController.currentWorld && Math.sqrt(Math.pow(entity.worldX - Player.worldX, 2) + Math.pow(entity.worldY - Player.worldY, 2)) < 1_800) {
                entity.draw(g2);
            }
        }
    }
}
