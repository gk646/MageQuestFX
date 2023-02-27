package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.npcs.marlaquest.NPC_Marla;
import gameworld.quest.QUEST;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.ui.maps.MarkerType;

import java.awt.Point;

public class QST_MarlaFakeNecklace extends QUEST {

    public QST_MarlaFakeNecklace(MainGame mg, String name) {
        super(mg, name);
        quest_id = 2;
        updateObjective("Head east and pickup the mysterious adventurers trail!");
        mg.sqLite.setQuestActive(quest_id);
    }

    /**
     *
     */
    @Override
    public void update() {
        if (mg.inputH.e_typed) {
            for (NPC npc : mg.npcControl.NPC_Active) {
                if (npc instanceof NPC_Marla) {
                    interactWithNpc(npc, DialogStorage.MarlaNecklace);
                    if (progressStage == 6) {
                        mg.wControl.addMapMarker(quest_id + "" + progressStage, 55, 50, MarkerType.Quest);
                    }
                    if (playerInsideRectangle(new Point(45, 50), new Point(55, 55))) {
                        nextStage();
                        mg.wControl.removeMapMarker(quest_id + "" + 6);
                    }
                }
            }
        }
    }
}
