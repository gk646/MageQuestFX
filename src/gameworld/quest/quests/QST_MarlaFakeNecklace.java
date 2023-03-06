package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_Mushroom;
import gameworld.entities.monsters.ENT_Snake;
import gameworld.entities.monsters.ENT_Wolf;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.QUEST;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.maps.MarkerType;

import java.awt.Point;

public class QST_MarlaFakeNecklace extends QUEST {
    private int enemiesKilled;

    public QST_MarlaFakeNecklace(MainGame mg, String name, boolean completed) {
        super(mg, name);
        quest_id = 2;
        name = "The Fake Necklace";
        updateObjective("Talk to Marla", 0);
        mg.sqLite.setQuestActive(quest_id);
        if (!completed) {
            mg.sqLite.setQuestActive(quest_id);
        } else {
            mg.sqLite.finishQuest(quest_id);
        }
    }

    /**
     *
     */
    @Override
    public void update() {
        for (int i = 0; i < mg.npcControl.NPC_Active.size(); i++) {
            NPC npc = mg.npcControl.NPC_Active.get(i);
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
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(84, 51, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 59, 55, Zone.Hillcrest));
                        objective3Progress++;
                    }
                    updateObjective("Head east and pickup the mysterious adventurers trail!", 0);
                    mg.wControl.addMapMarker(quest_id + "" + progressStage, 83, 41, MarkerType.Quest);
                    if (playerInsideRectangle(new Point(81, 40), new Point(85, 45))) {
                        progressStage = 13;
                        updateObjective("Look around", 0);
                        mg.wControl.removeMapMarker(quest_id + "" + 11);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                        objective3Progress = 0;
                    }
                }
            }
            if (npc instanceof NPC_Aria) {
                interactWithNpc(npc, DialogStorage.MarlaNecklace);
                if (progressStage == 13) {
                    if (objective3Progress == 0 && playerCloseToAbsolute((int) npc.worldX, (int) npc.worldY, 500)) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 13);
                        objective3Progress++;
                    }
                    int num = npc.dialog.drawChoice("Beat you to what?", null, null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 14);
                    }
                } else if (progressStage == 14) {
                    objective3Progress = 0;
                    npc.blockInteraction = true;
                    System.out.println(npc.dialog.dialogRenderCounter);
                    if (npc.dialog.dialogRenderCounter == 2000) {
                        nextStage();
                        enemiesKilled = mg.prj_control.ENEMIES_KILLED;
                        mg.ENTITIES.add(new ENT_Wolf(mg, 52, 52, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 49, 58, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 60, 63, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 53, 61, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Wolf(mg, 57, 50, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Snake(mg, 48, 62, 2, Zone.Hillcrest));
                    }
                } else if (progressStage == 15) {
                    if (mg.prj_control.ENEMIES_KILLED >= enemiesKilled + 6) {
                        npc.blockInteraction = false;
                        objective3Progress = 1;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 15);
                        int num = npc.dialog.drawChoice("You couldn't have done it without me!", null, null, null);
                        if (num == 10) {
                            nextStage();
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 16);
                        }
                    }
                }
            }
        }
    }
}



