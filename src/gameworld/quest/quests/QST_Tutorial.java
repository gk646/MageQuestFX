package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.npcs.NPC_OldMan;
import gameworld.quest.QUEST;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.WorldRender;
import main.system.enums.Zone;

public class QST_Tutorial extends QUEST {
    private int gruntKillCounter;


    public QST_Tutorial(MainGame mg, String name) {
        super(mg, name);
        this.objective = "Talk to the old man";
    }

    /**
     *
     */
    @Override
    public void update() {
        for (NPC npc : mg.npcControl.NPC_Active)
            if (npc instanceof NPC_OldMan) {
                interactWithNpc(npc, DialogStorage.Tutorial);
                if (progressStage == 6) {
                    updateObjective("Follow the old man!");
                    npc.onPath = true;
                    moveToTile(npc, 34, 34);
                }
                if (progressStage == 10) {
                    updateObjective("Kill enemies: " + (mg.prj_control.GruntKilledCounter - gruntKillCounter) + "/3");
                    gruntKillCounter = mg.prj_control.GruntKilledCounter;
                    MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 31, 1, Zone.Tutorial));
                    MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 34, 1, Zone.Tutorial));
                    MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 38, 1, Zone.Tutorial));
                    nextStage();
                    npc.blockInteraction = true;
                    mg.sqLite.updateQuestFacts(1, 1, 1);
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
                    npc.blockInteraction = false;
                }
                if (progressStage == 14) {
                    moveToTile(npc, 47, 34);
                }
                if (progressStage == 15) {
                    mg.sqLite.updateQuestFacts(1, 2, 1);
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
