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
    }

    /**
     *
     */
    @Override
    public void update() {
        for (NPC npc : mg.npcControl.NPC_Active)
            if (npc instanceof NPC_OldMan) {
                interactWithNpc(npc, DialogStorage.Tutorial);
                if (progressStage == 1) {
                    mg.sqLite.setQuestActive(quest_id);
                }
                if (progressStage == 6) {
                    updateObjective("Follow the old man!");
                    moveToTile(npc, 34, 34, new Point(24, 4), new Point(24, 5), new Point(24, 14), new Point(12, 34));
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
                }
                if (progressStage == 11) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3");
                }
                if (progressStage == 11 && mg.prj_control.GruntKilledCounter == gruntKillCounter + 3) {
                    nextStage();
                    loadDialogStage(npc, DialogStorage.Tutorial, 12);
                    mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                    npc.blockInteraction = false;
                }
                if (progressStage == 14) {
                    moveToTile(npc, 47, 34);
                }
                if (progressStage == 15) {
                    if (WorldController.currentWorld == Zone.Tutorial) {
                        WorldRender.worldData[48][34] = 4;
                    }
                }
                if (progressStage == 16) {
                    moveToTile(npc, 58, 35);
                }
                if (progressStage == 19) {
                    npc.blockInteraction = true;
                    if (npc.show_dialog && playerBagsContainItem("Old Glasses") && playerBagsContainItem("Booze") && playerBagsContainItem("Walking Cane")) {
                        nextStage();
                        mg.sqLite.updateQuestFacts(quest_id, 1, 2);
                    }
                }
                if (progressStage == 20) {
                    npc.blockInteraction = false;
                }
                if (progressStage == 21) {
                    moveToTile(npc, 58, 37);
                }
                if (progressStage == 22) {
                    moveToTile(npc, 58, 47);
                    WorldRender.worldData[58][38] = 15;
                }
                if (progressStage == 24) {
                    npc.blockInteraction = true;
                }
                if (progressStage == 25) {
                    npc.blockInteraction = false;
                }
            }
    }
}
