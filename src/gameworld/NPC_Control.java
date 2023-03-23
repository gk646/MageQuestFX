package gameworld;

import gameworld.entities.NPC;
import gameworld.entities.npcs.generic.NPC_Generic;
import gameworld.entities.npcs.generic.zonescripts.HillcrestNPCScript;
import gameworld.entities.npcs.generic.zonescripts.NPCScript;
import gameworld.entities.npcs.quests.ENT_GroveReceptionist;
import gameworld.entities.npcs.quests.ENT_RealmKeeper;
import gameworld.entities.npcs.quests.NPC_HillcrestMayor;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.npcs.quests.NPC_Nietzsche;
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
import java.util.Iterator;

public class NPC_Control {
    MainGame mg;
    ArrayList<NPCScript> zoneScripts = new ArrayList<>();
    public final ArrayList<NPC> NPC_Active = new ArrayList<>();

    public NPC_Control(MainGame mg) {
        this.mg = mg;
        zoneScripts.add(new HillcrestNPCScript(mg));
        NPC_Active.add(new NPC_ArmourTrader(mg, 27, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_WeaponTrader(mg, 33, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_AbilityTrader(mg, 30, 31, Zone.Hillcrest));
        NPC_Active.add(new NPC_AccessoriesTrader(mg, 36, 31, Zone.Hillcrest));

        NPC_Active.add(new ENT_GroveReceptionist(mg, 55, 108, Zone.The_Grove));
        NPC_Active.add(new NPC_Nietzsche(mg, 88, 4, Zone.Hillcrest));
        NPC_Active.add(new NPC_Marla(mg, 41, 28));
        NPC_Active.add(new NPC_HillcrestMayor(mg, 4, 36));

        NPC_Active.add(new ENT_RealmKeeper(mg, 29, 21, Zone.Hillcrest));
    }


    public void update() {
        synchronized (NPC_Active) {
            Iterator<NPC> entityIterator = NPC_Active.iterator();
            while (entityIterator.hasNext()) {
                NPC npc = entityIterator.next();
                if (npc.zone == WorldController.currentWorld && Math.sqrt(Math.pow(npc.worldX - Player.worldX, 2) + Math.pow(npc.worldY - Player.worldY, 2)) < 4_500) {
                    if (!npc.dead) {
                        npc.update();
                    } else {
                        npc.dialogHideDelay++;
                        if (npc.dialogHideDelay > 600) {
                            npc.show_dialog = false;
                        }
                    }
                    if (npc instanceof NPC_Generic) {
                        if (((NPC_Generic) npc).despawn) {
                            entityIterator.remove();
                        }
                    }
                }
            }
            for (NPCScript script : zoneScripts) {
                script.update();
            }
        }
    }

    public void draw(GraphicsContext gc) {
        synchronized (NPC_Active) {
            for (NPC npc : NPC_Active) {
                if (npc.zone == WorldController.currentWorld && Math.sqrt(Math.pow(npc.worldX - Player.worldX, 2) + Math.pow(npc.worldY - Player.worldY, 2)) < 1_800) {
                    npc.draw(gc);
                }
            }
        }
    }

    public void drawDialogs(GraphicsContext gc) {
        synchronized (NPC_Active) {
            for (NPC npc : NPC_Active) {
                if (npc.zone == WorldController.currentWorld && npc.show_dialog && Math.sqrt(Math.pow(npc.worldX - Player.worldX, 2) + Math.pow(npc.worldY - Player.worldY, 2)) < 1_800) {
                    npc.drawDialog(gc);
                }
            }
        }
    }

    public void loadGenerics(Zone zone) {
        for (NPCScript script : zoneScripts) {
            if (script.zone == zone) {
                WorldController.currentScript = script;
            }
        }
    }
}
