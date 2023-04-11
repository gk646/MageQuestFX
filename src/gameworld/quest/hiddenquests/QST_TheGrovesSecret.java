package gameworld.quest.hiddenquests;

import gameworld.entities.NPC;
import gameworld.entities.npcs.quests.NPC_DyingHermit;
import gameworld.entities.npcs.quests.NPC_GroveManager;
import gameworld.entities.npcs.quests.NPC_GroveReceptionist;
import gameworld.entities.npcs.quests.NPC_IslandLeader;
import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;

public class QST_TheGrovesSecret extends HiddenQUEST {
    private boolean gotQuest;

    public QST_TheGrovesSecret(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.TheGrovesSecret;
        quest_id = logicName.val;
        name = "The Grove's Secret";
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
        if (objective3Progress == 0 && progressStage < 19 && progressStage != 8 && WorldController.currentWorld == Zone.The_Grove) {
            WorldRender.worldData1[61][108] = 1096;
            objective3Progress = 1;
        }
        if (progressStage == 1) {
            if (WorldController.currentWorld == Zone.The_Grove && !activated) {
                this.activated = true;
                mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                updateObjective("Follow the path to \"The Grove\"", 0);
                addQuestMarker("entrance", 57, 108, Zone.The_Grove);
            }
        }
        for (int i = 0; i < mg.npcControl.NPC_Active.size(); i++) {
            NPC npc = mg.npcControl.NPC_Active.get(i);
            if (npc instanceof NPC_GroveReceptionist) {
                interactWithNpc(npc, DialogStorage.TheGroveSecret);
                if (progressStage == 1) {
                    loadDialogStage(npc, DialogStorage.TheGroveSecret, 2);
                } else if (progressStage == 2) {
                    removeQuestMarker("entrance");
                    int num = npc.dialog.drawChoice("What is this place?", null, null, null);
                    if (num == 10) {
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 3);
                        nextStage();
                    }
                } else if (progressStage == 7) {
                    int num = npc.dialog.drawChoice("I want to get in!", null, null, null);
                    if (num == 10) {
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 8);
                        nextStage();
                    }
                } else if (progressStage == 8) {
                    int num = npc.dialog.drawChoice("Pay 150 coins", "I don't have the money!", null, null);
                    if (num == 10 && mg.player.coins >= 150) {
                        mg.player.coins -= 150;
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 9);
                        updateObjective("Step onto the island", 0);
                        addQuestMarker("island", 114, 106, Zone.The_Grove);
                        npc.blockInteraction = true;
                        progressStage = 20;
                        removeQuestMarker("forest");
                        mg.sqLite.updateQuestFacts(quest_id, 1, 3);
                        nextStage();
                    } else if (num == 20) {
                        mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 10);
                        progressStage = 13;
                        npc.blockInteraction = true;
                        mg.npcControl.addToActive.add(new NPC_DyingHermit(mg, 3, 156, Zone.The_Grove));
                        updateObjective("Check if tickets dont grow on trees", 0);
                        addQuestMarker("forest", 7, 139, Zone.The_Grove);
                    }
                } else if (progressStage == 13) {
                    npc.blockInteraction = true;
                    if (objective2Progress == 0) {
                        objective2Progress++;
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 10);
                    }
                } else if (progressStage == 18) {
                    int num = npc.dialog.drawChoice("I have a ticket!", "Pay 150 coins", null, null);
                    if (num == 10 && playerBagsContainItem("\"The Grove\" Ticket")) {
                        nextStage();
                        mg.sqLite.updateQuestFacts(quest_id, 1, 3);
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 19);
                        removeItemFromBag("\"The Grove\" Ticket");
                    } else if (num == 20 && mg.player.coins >= 150) {
                        nextStage();
                        mg.player.coins -= 150;
                        mg.sqLite.updateQuestFacts(quest_id, 1, 3);
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 19);
                    }
                    objective1Progress = 0;
                    objective2Progress = 0;
                    objective3Progress = 0;
                } else if (progressStage == 19) {
                    npc.blockInteraction = true;
                    if (objective2Progress == 0) {
                        objective2Progress++;
                        mg.npcControl.addToActive.add(new NPC_GroveManager(mg, 111, 105, Zone.The_Grove));
                        mg.npcControl.addToActive.add(new NPC_IslandLeader(mg, 72, 51, Zone.The_Grove, "Dr. Flex N. Stretch"));
                        mg.npcControl.addToActive.add(new NPC_IslandLeader(mg, 151, 42, Zone.The_Grove, "Gusto Gourmand"));
                        mg.npcControl.addToActive.add(new NPC_IslandLeader(mg, 66, 148, Zone.The_Grove, "Ms. Snoozy Shores"));
                        mg.npcControl.addToActive.add(new NPC_IslandLeader(mg, 152, 150, Zone.The_Grove, "Captain Buck McSplash"));
                        if (WorldController.currentWorld == Zone.The_Grove) {
                            WorldRender.worldData1[61][108] = 1139;
                        }
                        objective2Progress = 0;
                        nextStage();
                    }
                }
            } else if (npc instanceof NPC_DyingHermit) {
                interactWithNpc(npc, DialogStorage.TheGroveSecret);
                if (progressStage == 17) {
                    npc.blockInteraction = true;
                    //npc.dead = true;
                    //TODO death animation
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(3 * 48 + 20, 156 * 48, mg.MISC.get(1), Zone.The_Grove));
                    nextStage();
                    updateObjective("Go back to the reception", 0);
                    removeQuestMarker("forest");
                    addQuestMarker("entr", 57, 108, Zone.The_Grove);
                }
            } else if (npc instanceof NPC_GroveManager) {
                interactWithNpc(npc, DialogStorage.TheGroveSecret);
                if (progressStage == 22) {
                    if (objective3Progress == 0) {
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 22);
                        objective3Progress = 1;
                    }
                    if (moveToTile(npc, 149, 111, new Point(123, 111))) {
                        nextStage();
                        objective3Progress = 0;
                    }
                } else if (progressStage == 23) {
                    if (objective3Progress == 0) {
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 23);
                        objective3Progress = 1;
                    }
                    if (moveToTile(npc, 113, 107, new Point(149, 103))) {
                        nextStage();
                        objective3Progress = 0;
                    }
                } else if (progressStage == 25) {
                    if (objective3Progress == 0) {
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 25);
                        objective3Progress = 1;
                    }
                    if (moveToTile(npc, 104, 142)) {
                        nextStage();
                        addQuestMarker("mcsplash", 152, 150, Zone.The_Grove);
                        updateObjective("Talk to Captain Buck McSplash", 0);
                        loadDialogStage(npc, DialogStorage.TheGroveSecret, 26);
                        objective3Progress = 0;
                    }
                }
            } else if (npc instanceof NPC_IslandLeader) {
                if (name.equals("Captain Buck McSplash")) {

                } else if (name.equals("Dr. Flex N. Stretch")) {

                }
            }
        }
    }
}
