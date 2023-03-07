package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_Mushroom;
import gameworld.entities.monsters.ENT_Snake;
import gameworld.entities.monsters.ENT_Wolf;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.maps.MarkerType;

import java.awt.Point;

public class QST_MarlaFakeNecklace extends QUEST {
    private int enemiesKilled;

    public QST_MarlaFakeNecklace(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.TheFakeNecklace;
        quest_id = logicName.val;
        name = "The Fake Necklace";
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
                    updateObjective("Talk to Marla", 0);
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
                } else if (progressStage == 75) {
                    npc.blockInteraction = true;
                } else if (progressStage == 77) {
                    npc.blockInteraction = true;
                } else if (progressStage == 79) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 79);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 82) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 82);
                        objective1Progress = 1;
                    }
                    int num = npc.dialog.drawChoice("*Pay the 50 coins*", "*Do nothing*", null, null);
                    if (num == 10) {
                        if (mg.player.coins >= 50) {
                            progressStage = 100;
                            mg.player.coins -= 50;
                            mg.sound.playEffectSound(5);
                            objective1Progress = 0;
                        } else {

                        }
                    } else if (num == 20) {
                        nextStage();
                        objective1Progress = 0;
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
                        if (objective3Progress == 0) {
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 15);
                            objective3Progress = 1;
                        }
                        int num = npc.dialog.drawChoice("You couldn't have done it without me!", null, null, null);
                        if (num == 10) {
                            nextStage();
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 16);
                        }
                    }
                } else if (progressStage == 16) {
                    int num = npc.dialog.drawChoice("The necklace is fake!", "Ill come along!", null, null);
                    if (num == 10) {
                        progressStage = 75;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 75);
                        mg.sqLite.updateQuestFacts(quest_id, 1, -1);
                    } else if (num == 20) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, progressStage);
                    }
                } else if (progressStage == 17) {
                    moveToTile(npc, 38, 61, new Point(53, 59));
                } else if (progressStage == 18) {
                    //TODO add usable item and lock q slot
                } else if (progressStage == 19) {
                    int num = npc.dialog.drawChoice("Yep, lets go", null, null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 20);
                    }
                } else if (progressStage == 20) {
                    moveToTile(npc, 68, 73, new Point(49, 75));
                } else if (progressStage == 21) {
                    moveToTile(npc, 76, 71);
                    mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                } else if (progressStage == 22) {
                    moveToTile(npc, 93, 59, new Point(86, 62));
                } else if (progressStage == 77) {
                    if (moveToTile(npc, 43, 31, new Point(40, 60), new Point(59, 59), new Point(70, 49), new Point(70, 56), new Point(83, 37), new Point(43, 34)) && npc.show_dialog) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 78);
                    }
                } else if (progressStage == 78) {
                    npc.blockInteraction = true;
                    objective1Progress++;
                    if (objective1Progress >= 360) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 80) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 80);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 81) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 81);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 83) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 83);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 84) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 84);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 500) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 85) {
                    if (moveToTile(npc, 0, 99, new Point(31, 26), new Point(23, 29), new Point(18, 34), new Point(18, 83))) {
                        npc.zone = Zone.Woodland_Edge;
                        mg.sqLite.finishQuest(quest_id);
                    }
                } else if (progressStage == 100) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 100);
                    }
                    if (objective1Progress >= 600) {
                        nextStage();
                        objective1Progress = 0;
                    }
                    objective1Progress++;
                } else if (progressStage == 101) {
                    if (moveToTile(npc, 0, 99, new Point(31, 26), new Point(23, 29), new Point(18, 34), new Point(18, 83))) {
                        npc.zone = Zone.Woodland_Edge;
                        mg.sqLite.finishQuest(quest_id);
                    }
                }
            }
        }
    }
}



