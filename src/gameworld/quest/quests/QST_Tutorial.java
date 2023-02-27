package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.npcs.NPC_OldMan;
import gameworld.quest.QUEST;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;

public class QST_Tutorial extends QUEST {
    private int gruntKillCounter;


    public QST_Tutorial(MainGame mg, String name) {
        super(mg, name);
        this.objective = "Talk to the old man";
        this.quest_id = 1;
        mg.sqLite.setQuestActive(quest_id);
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
                    updateObjective("Follow the old man!");
                    moveToTile(npc, 34, 34, new Point(24, 4), new Point(24, 14), new Point(12, 14), new Point(12, 33));
                }
                if (progressStage == 10) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3");
                    gruntKillCounter = mg.prj_control.GruntKilledCounter;
                    MainGame.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 31, 1, Zone.Tutorial));
                    MainGame.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 34, 1, Zone.Tutorial));
                    MainGame.ENTITIES.add(new ENT_SkeletonWarrior(mg, 48 * 46, 48 * 38, 1, Zone.Tutorial));
                    nextStage();
                    npc.blockInteraction = true;
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 45, 48 * 21, mg.MISC.get(1), Zone.Tutorial));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 96, 48 * 5, mg.MISC.get(2), Zone.Tutorial));
                    mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 97, 48 * 47, mg.MISC.get(3), Zone.Tutorial));
                } else if (progressStage == 11) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3");
                }
                if (progressStage == 11 && mg.prj_control.GruntKilledCounter == gruntKillCounter + 3) {
                    nextStage();
                    loadDialogStage(npc, DialogStorage.Tutorial, 12);
                    mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                    npc.blockInteraction = false;
                } else if (progressStage == 14) {
                    moveToTile(npc, 47, 34);
                } else if (progressStage == 15) {
                    if (WorldController.currentWorld == Zone.Tutorial) {
                        WorldRender.worldData1[48][34] = 1_214;
                    }
                } else if (progressStage == 16) {
                    moveToTile(npc, 58, 35);
                }
                if (progressStage == 19) {
                    int choice = npc.dialog.drawChoice("Energy Sphere", "Regen Aura", null, null);
                    if (choice == 10) {
                        //TODO add spell book to inventory
                        //TODO make activaitable items with e
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 20);
                    } else if (choice == 20) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 20);
                    }
                } else if (progressStage == 21) {
                    npc.blockInteraction = true;
                    if (npc.show_dialog && playerBagsContainItem("Booze")) {
                        nextStage();
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
                    npc.blockInteraction = true;
                    if (WorldRender.worldData1[74][84] == 1383) {
                        openRoundDoor(35, 67);
                        WorldRender.worldData[75][72] = 131;
                        WorldRender.worldData[75][73] = 131;
                        WorldRender.worldData2[75][72] = -1;
                        WorldRender.worldData2[75][73] = -1;
                        WorldRender.worldData2[76][72] = -1;
                        WorldRender.worldData2[76][73] = -1;
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 27);
                        openRoundDoor(68, 77);
                    } else if (playerInsideRectangle(new Point(68, 79), new Point(69, 79))) {
                        closeRoundDoor(68, 77);
                    }
                } else if (progressStage == 27) {
                    npc.blockInteraction = false;
                } else if (progressStage == 28) {
                    if (moveToTile(npc, 36, 52)) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 29);
                    }
                } else if (progressStage == 29) {
                    if (moveToTile(npc, 68, 75, new Point(36, 71))) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 30);
                    }
                } else if (progressStage == 30) {
                    if (moveToTile(npc, 95, 95, new Point(84, 76), new Point(89, 85))) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.Tutorial, 31);
                    }
                } else if (progressStage == 36) {
                    if (moveToTile(npc, 99, 99)) {
                        npc.zone = Zone.Clearing;
                        npc.setPosition(1, 1);
                        nextStage();
                    }
                }
            }
        }
    }
}



