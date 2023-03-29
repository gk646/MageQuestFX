package gameworld.entities.npcs.generic.zonescripts;

import gameworld.entities.npcs.generic.NPC_GenericVillagerBoy;
import gameworld.entities.npcs.generic.NPC_GenericVillagerBoy1;
import gameworld.entities.npcs.generic.NPC_GenericVillagerWoman;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

abstract public class NPCScript {
    MainGame mg;
    int respawnCounter;
    int amountOfNPCs;
    public Zone zone;
    public Point[] houseEntrances;
    public boolean[] entranceTaken;

    abstract public void update();

    void respawnGeneric() {
        double num = Math.random();
        Point point = houseEntrances[(int) (Math.random() * houseEntrances.length)];
        if (num < 0.15) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerBoy(mg, point.x, point.y, zone));
        } else if (num < 0.3) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerBoy1(mg, point.x, point.y, zone));
        } else if (num < 1) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerWoman(mg, point.x, point.y, zone));
        }
    }
}
