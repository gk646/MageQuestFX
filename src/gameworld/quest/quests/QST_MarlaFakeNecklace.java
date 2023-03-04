package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.npcs.NPC_Marla;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.QUEST;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.maps.MarkerType;

import java.awt.Point;

public class QST_MarlaFakeNecklace extends QUEST {

    public QST_MarlaFakeNecklace(MainGame mg, String name) {
        super(mg, name);
        quest_id = 2;
        updateObjective("Talk to Marla", 0);
        mg.sqLite.setQuestActive(quest_id);

    }

    /**
     *
     */
    @Override
    public void update() {
        for (NPC npc : mg.npcControl.NPC_Active) {
            if (npc instanceof NPC_Marla) {
                interactWithNpc(npc, DialogStorage.MarlaNecklace);

                if (progressStage == 1 && objective1Progress == 0) {
                    npc.dialog.loadNewLine(DialogStorage.MarlaNecklace[0]);
                    objective1Progress++;
                } else if (progressStage == 8) {
                    objective1Progress = 0;
                    int num = npc.dialog.drawChoice("Sure, ill help you out", "Maybe later", null, null);
                    if (num == 10) {
                        progressStage = 10;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 10);
                    } else if (num == 20) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 9);
                    }
                } else if (progressStage == 9) {
                    npc.blockInteraction = true;
                    updateObjective("Come back if you change your mind", 0);
                    if (!playerCloseToAbsolute((int) npc.worldX, (int) npc.worldY, 600)) {
                        npc.show_dialog = false;
                        progressStage = 8;
                        npc.blockInteraction = false;
                    }
                } else if (progressStage == 11) {
                    npc.blockInteraction = true;
                    if (objective3Progress == 0) {
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Clearing));
                        mg.ENTITIES.add(new DeadWolf(84, 51, Zone.Clearing));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Clearing));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Clearing));

                        objective3Progress++;
                    }
                    updateObjective("Head east and pickup the mysterious adventurers trail!", 0);
                    mg.wControl.addMapMarker(quest_id + "" + progressStage, 83, 41, MarkerType.Quest);
                    if (playerInsideRectangle(new Point(81, 40), new Point(85, 45))) {
                        nextStage();
                        updateObjective("Look around", 0);
                        mg.wControl.removeMapMarker(quest_id + "" + 11);
                    }
                }
            }
        }
    }
}

