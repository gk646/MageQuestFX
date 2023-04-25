/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        for (int i = 0; i < mg.npcControl.NPC_Active.size(); i++) {
            NPC npc = mg.npcControl.NPC_Active.get(i);
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
                    if (objective1Progress > 360) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 2);
                    } else if (objective1Progress == 220) {
                        mg.player.dialog.loadNewLine(" A note-worthy failure!");
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
                    if (objective1Progress > 360) {
                        nextStage();
                        objective1Progress = 1;
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 3);
                    } else if (objective1Progress == 220) {
                        mg.player.dialog.loadNewLine("An orchestra!");
                    }
                } else if (progressStage == 5) {
                    int num = npc.dialog.drawChoice("Iam sorry", "I got one more!", null, null);
                    if (num == 10) {
                        progressStage = 8;
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 8);
                    } else if (num == 20) {
                        nextStage();
                        objective1Progress = 1;
                    }
                } else if (progressStage == 6) {
                    npc.blockInteraction = true;
                    npc.show_dialog = false;
                    if (objective1Progress == 1) {
                        mg.player.dialog.loadNewLine("Why do wizard's get jobs as teachers?");
                    }
                    objective1Progress++;
                    if (objective1Progress > 360) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.IntoTheGrassLands, 7);
                        objective1Progress = 0;
                    } else if (objective1Progress == 220) {
                        mg.player.dialog.loadNewLine("Because they are great at spell-checking!");
                    }
                } else if (progressStage == 7) {
                    if (objective1Progress == 0) {
                        updateObjective("Give Grim time to cool off", 0);
                    }
                    objective1Progress++;
                    if (objective1Progress == 18000) {
                        updateObjective("Talk to Grim about the GrassLands", 0);
                        npc.blockInteraction = false;
                    }
                } else if (progressStage == 12) {
                    npc.blockInteraction = true;
                    if (objective2Progress == 0) {
                        updateObjective("Reach level 5", 0);
                        updateObjective("Kill a Stone Knight", 1);
                        objective2Progress = 1;
                        mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                    }

                    if (mg.prj_control.stoneKnightKilled >= 1 || mg.player.level >= 5) {
                        nextStage();
                    }
                } else if (progressStage == 13) {
                    npc.blockInteraction = false;
                    mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                }
            }
        }
    }
}

