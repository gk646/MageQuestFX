/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
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
import gameworld.entities.npcs.quests.NPC_HillcrestMayor;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

public class QST_TheAudition extends QUEST {
    public QST_TheAudition(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.TheAudition;
        quest_id = logicName.val;
        name = "The Audition";
        progressStage = 1;
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
            if (npc instanceof NPC_HillcrestMayor) {
                interactWithNpc(npc, DialogStorage.AuditionMayor);
                if (progressStage == 1) {
                    mg.sqLite.setQuestActive(quest_id);
                    updateObjective("Talk to the mayor", 0);
                    addQuestMarker("1", 8, 37, Zone.Hillcrest);
                    if (playerInsideRectangle(new Point(10, 36), new Point(14, 37))) {
                        removeQuestMarker("1");
                    }
                } else if (progressStage == 2) {
                    int num = npc.dialog.drawChoice("Iam here for information!", "Sure, 25 coin donation", null, null);
                    if (num == 10) {
                        progressStage = 4;
                        loadDialogStage(npc, DialogStorage.AuditionMayor, 4);
                    } else if (num == 20) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.AuditionMayor, 3);
                        progressStage = 4;
                    }
                } else if (progressStage == 6) {
                    if (!mg.qPanel.PlayerHasQuests(QUEST_NAME.IntoTheGrassLands)) {
                        mg.qPanel.quests.add(new QST_IntoTheGrassLands(mg, false));
                        mg.npcControl.NPC_Active.add(new NPC_Grim(mg, 4, 90, Zone.Hillcrest));
                    }
                } else if (progressStage == 7) {
                    int num = npc.dialog.drawChoice("Didn't ask!", null, null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.AuditionMayor, 8);
                    }
                } else if (progressStage == 9) {

                } else if (progressStage == 10) {
                    updateObjective("", 0);
                    mg.sqLite.finishQuest(quest_id);
                    this.completed = true;
                }
            }
        }
    }
}
