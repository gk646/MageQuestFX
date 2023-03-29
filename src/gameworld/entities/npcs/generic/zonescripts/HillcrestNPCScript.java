package gameworld.entities.npcs.generic.zonescripts;

import gameworld.entities.NPC;
import gameworld.entities.npcs.generic.NPC_Generic;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

public class HillcrestNPCScript extends NPCScript {

    private int upCounter;

    public HillcrestNPCScript(MainGame mg) {
        this.respawnCounter = 300;
        this.zone = Zone.Hillcrest;
        this.mg = mg;
        this.houseEntrances = new Point[]{new Point(30, 8), new Point(38, 8),
                new Point(45, 9), new Point(27, 14), new Point(33, 14), new Point(48, 26), new Point(53, 26),
                new Point(53, 32), new Point(48, 32), new Point(23, 27), new Point(26, 20), new Point(32, 20), new Point(36, 24)};
        this.entranceTaken = new boolean[houseEntrances.length];
        amountOfNPCs = 6;
    }


    /**
     *
     */
    @Override
    public void update() {
        upCounter++;
        if (upCounter >= respawnCounter) {
            upCounter = 0;
            int genericCounter = 0;
            for (NPC npc : mg.npcControl.NPC_Active) {
                if (npc instanceof NPC_Generic) {
                    genericCounter++;
                }
            }
            if (genericCounter < amountOfNPCs) {
                respawnGeneric();
            }
        }
    }
}
