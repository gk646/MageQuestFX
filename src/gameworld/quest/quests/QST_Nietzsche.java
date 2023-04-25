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
import gameworld.entities.npcs.quests.NPC_Nietzsche;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;

public class QST_Nietzsche extends QUEST {
    private int currentKills;

    public QST_Nietzsche(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.Nietzsche;
        quest_id = logicName.val;
        name = "Thus spoke Zarathustra";
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
            if (npc instanceof NPC_Nietzsche) {
                interactWithNpc(npc, DialogStorage.Nietzsche);
                if (progressStage == 1) {
                    updateObjective("Talk to Zarathustra", 0);
                }
                if (progressStage == 4) {
                    int num = npc.dialog.drawChoice("Ahem, sure...?", "Later maybe", null, null);
                    if (num == 10) {
                        nextStage();
                        addQuestMarker("book", 35, 15, Zone.Hillcrest_Hermit_Cave);
                        addQuestMarker("cave", 89, 1, Zone.Hillcrest);
                        updateObjective("Clear the cave", 0);
                        updateObjective("Recover the book", 1);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                        npc.blockInteraction = true;
                    } else if (num == 20) {
                        npc.show_dialog = false;
                    }
                } else if (progressStage == 5) {
                    npc.blockInteraction = true;
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.Nietzsche, 4);
                        objective1Progress++;
                        currentKills = mg.prj_control.ENEMIES_KILLED;
                        mg.WORLD_DROPS.add(new DRP_DroppedItem(35 * 48, 7 * 48, mg.MISC.get(4), Zone.Hillcrest_Hermit_Cave));
                    }
                    if (WorldController.currentWorld == Zone.Hillcrest_Hermit_Cave && objective3Progress == 0) {
                        objective3Progress++;
                        removeQuestMarker("cave");
                    }
                    if (playerBagsContainItem("Antichrist")) {
                        removeObjective(1);
                    }
                    if (currentKills + 20 == mg.prj_control.ENEMIES_KILLED && playerBagsContainItem("Antichrist")) {
                        removeObjective(0);
                        removeObjective(1);
                        removeItemFromBag("Antichrist");
                        updateObjective("Bring Zarathustra the book", 0);
                        removeQuestMarker("book");
                        npc.blockInteraction = false;
                        nextStage();
                    }
                } else if (progressStage == 6) {
                    npc.blockInteraction = false;
                } else if (progressStage == 8) {
                    updateObjective("", 0);
                    mg.player.experience += 15;
                    mg.sqLite.finishQuest(quest_id);
                    completed = true;
                }
            }
        }
    }
}