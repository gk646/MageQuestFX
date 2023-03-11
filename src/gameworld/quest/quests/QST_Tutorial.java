package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.damage.DamageType;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.npcs.quests.NPC_OldMan;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITM_SpellBook;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;

public class QST_Tutorial extends QUEST {
    private int gruntKillCounter;
    private boolean once;

    public QST_Tutorial(MainGame mg, boolean completed) {
        super(mg);
        this.name = "Tutorial";
        this.logicName = QUEST_NAME.Tutorial;
        this.quest_id = logicName.val;
        this.objectives[0] = "Talk to the old man";
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
            if (npc instanceof NPC_OldMan) {
                interactWithNpc(npc, DialogStorage.Tutorial);

                if (progressStage == 6) {
                    updateObjective("Follow the old man!", 0);
                    moveToTile(npc, 34, 34, new Point(24, 4), new Point(24, 14), new Point(12, 14), new Point(12, 33));
                }
                if (progressStage == 10) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3", 0);
                    gruntKillCounter = mg.prj_control.GruntKilledCounter;
                    mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 31, 1, Zone.Woodland_Edge));
                    mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 34, 1, Zone.Woodland_Edge));
                    mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 38, 1, Zone.Woodland_Edge));
                    nextStage();
                    npc.blockInteraction = true;
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 94, 48 * 44, mg.MISC.get(2), Zone.Woodland_Edge));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(48 * 96, 48 * 13, mg.MISC.get(4), Zone.Woodland_Edge));
                } else if (progressStage == 11) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3", 0);
                }
                if (progressStage == 11 && mg.prj_control.GruntKilledCounter == gruntKillCounter + 3) {
                    nextStage();
                    loadDialogStage(npc, DialogStorage.Tutorial, 12);
                    mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                    npc.blockInteraction = false;
                } else if (progressStage == 14) {
                    moveToTile(npc, 47, 34);
                } else if (progressStage == 15) {
                    if (WorldController.currentWorld == Zone.Woodland_Edge) {
                        WorldRender.worldData1[48][34] = 1_214;
                    }
                } else if (progressStage == 16) {
                    moveToTile(npc, 58, 35);
                }
                if (progressStage == 19) {
                    int choice = npc.dialog.drawChoice("Energy Sphere", "Regen Aura", null, null);
                    if (choice == 10) {
                        mg.inventP.addItemToBag(new ITM_SpellBook("Energy Sphere", 2, null, null, DamageType.Arcane));
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 20);
                    } else if (choice == 20) {
                        mg.inventP.addItemToBag(new ITM_SpellBook("Regenerative Aura", 2, null, null, DamageType.Arcane));
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 20);
                    }
                } else if (progressStage == 21) {
                    if (objective1Progress == 0) {
                        updateObjective("Look around to find something useful", 0);
                        addQuestMarker("path1", 47, 4, Zone.Woodland_Edge);
                        addQuestMarker("path2", 83, 5, Zone.Woodland_Edge);
                        addQuestMarker("path3", 80, 37, Zone.Woodland_Edge);
                    }
                    if (playerInsideRectangle(new Point(57, 18), new Point(59, 21)) && objective1Progress == 0) {
                        updateObjective("Follow the path", 0);
                        updateObjective("Follow the path", 1);
                        updateObjective("Find another path", 2);
                        objective1Progress = 1;
                        objective2Progress = 1;
                    }

                    if (playerInsideRectangle(new Point(41, 7), new Point(43, 9)) && objective1Progress == 1) {
                        updateObjective("Look around", 0);
                        objective1Progress = 2;
                    }

                    if (playerInsideRectangle(new Point(42, 19), new Point(47, 21)) && objective1Progress < 3) {
                        mg.player.dialog.loadNewLine("Hmm, nothing to find here; i gotta keep looking...");
                        removeObjective(0);
                        removeQuestMarker("path1");
                        objective1Progress = 3;
                    }
                    if (playerInsideRectangle(new Point(83, 2), new Point(85, 7)) && objective2Progress < 2) {
                        mg.player.dialog.loadNewLine("I should look around here");
                        objective2Progress = 2;
                    }
                    if (playerBagsContainItem("Silver Key")) {
                        removeObjective(1);
                        objective2Progress = 3;
                        removeQuestMarker("path2");
                    }

                    if (playerInsideRectangle(new Point(78, 36), new Point(81, 42)) && objective3Progress == 0) {
                        objective3Progress = 1;
                        updateObjective("Look around", 2);
                    }
                    if (playerBagsContainItem("Booze")) {
                        removeObjective(2);
                        objective3Progress = 3;
                        removeQuestMarker("path3");
                    }
                    if (objective1Progress == 3 && objective2Progress == 3 && objective3Progress == 3) {
                        updateObjective("Return the stuff you found", 0);
                    }
                    npc.blockInteraction = true;
                    if (npc.show_dialog && playerBagsContainItem("Booze") && playerBagsContainItem("Silver Key")) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 22);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                    }
                } else if (progressStage == 22) {
                    npc.blockInteraction = false;
                } else if (progressStage == 23) {
                    moveToTile(npc, 58, 37);
                } else if (progressStage == 24) {
                    moveToTile(npc, 58, 47);
                    openSquareDoor(58, 37);
                } else if (progressStage == 26 || progressStage == 25) {
                    addQuestMarker("path1", 68, 77, Zone.Woodland_Edge);
                    updateObjective("Search the ruins for a way out", 0);
                    npc.blockInteraction = true;
                    if (WorldController.currentWorld == Zone.Woodland_Edge && WorldRender.worldData1[74][84] == 1_383) {
                        updateObjective("Get back to the old man and escape", 0);
                        removeQuestMarker("path1");
                        openRoundDoor(35, 67);
                        WorldRender.worldData[75][72] = 131;
                        WorldRender.worldData[75][73] = 131;
                        WorldRender.worldData2[75][72] = -1;
                        WorldRender.worldData2[75][73] = -1;
                        WorldRender.worldData2[76][72] = -1;
                        WorldRender.worldData2[76][73] = -1;
                        progressStage = 27;
                        loadDialogStage(npc, DialogStorage.Tutorial, 27);
                        openRoundDoor(68, 77);
                    } else if (playerInsideRectangle(new Point(68, 79), new Point(69, 79)) && WorldController.currentWorld == Zone.Woodland_Edge) {
                        closeRoundDoor(68, 77);
                    }
                } else if (progressStage == 27) {
                    npc.blockInteraction = false;
                } else if (progressStage == 28) {
                    updateObjective("Follow the old man... again", 0);
                    addQuestMarker("path1", 95, 95, Zone.Woodland_Edge);
                    if (moveToTile(npc, 36, 52)) {
                        nextStage();
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                        loadDialogStage(npc, DialogStorage.Tutorial, 29);
                    }
                } else if (progressStage == 29) {
                    if (moveToTile(npc, 68, 75, new Point(36, 71))) {
                        nextStage();
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                        loadDialogStage(npc, DialogStorage.Tutorial, 30);
                    }
                } else if (progressStage == 30) {
                    if (moveToTile(npc, 95, 95, new Point(84, 76), new Point(89, 85))) {
                        nextStage();
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                        loadDialogStage(npc, DialogStorage.Tutorial, 31);
                    }
                } else if (progressStage == 36) {
                    if (moveToTile(npc, 99, 99)) {
                        npc.zone = Zone.Hillcrest;
                        npc.setPosition(1, 1);
                        removeQuestMarker("path1");
                        mg.sqLite.updateQuestFacts(quest_id, 1, 3);
                        nextStage();
                        objective3Progress = 0;
                    }
                } else if (progressStage == 37) {
                    addQuestMarker("path1", 20, 20, Zone.Hillcrest);
                    if (objective3Progress == 0) {
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                        loadDialogStage(npc, DialogStorage.Tutorial, 37);
                        objective3Progress = 1;
                    }
                    if (moveToTile(npc, 20, 20, new Point(4, 4), new Point(8, 8), new Point(12, 10))) {
                        updateObjective("Speak with the old man", 0);
                        mg.player.spawnLevel = 1;
                    }
                } else if (progressStage == 40) {
                    objective3Progress = 0;
                    removeQuestMarker("path1");
                    int num = npc.dialog.drawChoice("Iam in a hurry!", "Tell me everything...", null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 41);
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                    } else if (num == 20) {
                        progressStage = 50;
                        loadDialogStage(npc, DialogStorage.Tutorial, 50);
                        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
                    }
                } else if (progressStage == 45 || progressStage == 58) {
                    if (!once) {
                        mg.player.coins += 25;
                        mg.sound.playEffectSound(9);
                        once = true;
                        if (!mg.qPanel.PlayerHasQuests(QUEST_NAME.TheAudition)) {
                            mg.qPanel.quests.add(new QST_TheAudition(mg, false));
                        }
                    }
                } else if (progressStage == 60 || progressStage == 47) {
                    moveToTile(npc, 44, 21, new Point(25, 23));
                    updateObjective("Quest Finished", 0);
                    mg.sqLite.updateQuestFacts(quest_id, 1, 4);
                    mg.sqLite.finishQuest(quest_id);
                    npc.show_dialog = false;
                    completed = true;
                }
            }
        }
    }
}



