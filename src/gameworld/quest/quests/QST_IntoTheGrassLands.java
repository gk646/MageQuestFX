package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.npcs.quests.NPC_Grim;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.enums.Zone;

public class QST_IntoTheGrassLands extends QUEST {
    int angryTimer;

    public QST_IntoTheGrassLands(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.IntoTheGrassLands;
        quest_id = logicName.val;
        this.name = "Into the GrassLands";
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
        for (NPC npc : mg.npcControl.NPC_Active) {
            if (npc instanceof NPC_Grim) {
                interactWithNpc(npc, DialogStorage.IntoTheGrassLands);
                if (progressStage == 1) {
                    if (objective1Progress == 0) {
                        objective1Progress = 1;
                        updateObjective("Talk to protector Grim", 0);
                        addQuestMarker("grim", 8, 89, Zone.Hillcrest);
                    }
                    int num = npc.dialog.drawChoice("Travel to GrassLands!", "Make you laugh!", null, null);
                    if (num == 10) {
                        progressStage = 8;
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 8);
                    } else if (num == 20) {
                        nextStage();
                    }
                } else if (progressStage == 2) {
                    npc.blockInteraction = true;
                    npc.show_dialog = false;
                    if (objective1Progress == 1) {
                        mg.player.dialog.loadNewLine("What do you call a bard who can't play music?");
                    }
                    objective1Progress++;
                    if (objective1Progress > 400) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.AuditionMayor, 2);
                    } else if (objective1Progress > 200) {
                        mg.player.dialog.loadNewLine(" A note-worthy failure");
                    }
                } else if (progressStage == 3) {
                    int num = npc.dialog.drawChoice("Ok, iam serious now", "Sure, what do you...", null, null);
                    if (num == 10) {
                        progressStage = 8;
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 8);
                    } else if (num == 20) {
                        nextStage();
                        objective1Progress = 1;
                    }
                } else if (progressStage == 4) {
                    npc.blockInteraction = true;
                    npc.show_dialog = false;
                    if (objective1Progress == 1) {
                        mg.player.dialog.loadNewLine("What do you call a group of orcs playing music together?");
                    }
                    objective1Progress++;
                    if (objective1Progress > 400) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.AuditionMayor, 2);
                    } else if (objective1Progress > 200) {
                        mg.player.dialog.loadNewLine("An orchestra!");
                    }
                } else if (progressStage == 12) {
                    npc.blockInteraction = true;
                    mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                    if (mg.prj_control.stoneKnightKilled >= 1 || mg.player.level >= 5) {
                        nextStage();
                    }
                } else if (progressStage == 13) {
                    mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                }
            }
        }
    }
}

