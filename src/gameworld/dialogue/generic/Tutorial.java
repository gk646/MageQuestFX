package gameworld.dialogue.generic;

import gameworld.dialogue.Dialog;
import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.world.objects.drops.DRP_DroppedItem;
import main.MainGame;
import main.system.WorldRender;
import main.system.enums.Map;

public class Tutorial extends Dialog {
    private int gruntkillcounter = 0;

    /**
     * The tutorial dialog
     */
    public Tutorial(MainGame mg, int type, NPC npc) {
        super(mg, type, npc);
        load_text();
    }

    /**
     * Custom quest stages and requirements
     */
    @Override
    public void script(NPC npc) {
        if (stage == 6) {
            npc.onPath = true;
            moveToTile(34, 34);
        }
        if (stage == 10) {
            gruntkillcounter = mg.prj_control.GruntKilledCounter;
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 31, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 34, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 38, 1));
            next_stage();
            block = true;
            mg.sqLite.updateQuestFacts(1, 1, 1);
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 45, 48 * 21, mg.MISC.get(1)));
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 96, 48 * 5, mg.MISC.get(2)));
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, 48 * 97, 48 * 47, mg.MISC.get(3)));
        }
        if (stage == 11 && mg.prj_control.GruntKilledCounter == gruntkillcounter + 3) {
            next_stage();
            block = false;
        }
        if (stage == 14) {
            moveToTile(47, 34);
        }
        if (stage == 15) {
            mg.sqLite.updateQuestFacts(1, 2, 1);
            if (mg.wControl.currentWorld == Map.Tutorial) {
                WorldRender.worldData[48][34] = 4;
            }
        }
        if (stage == 16) {
            moveToTile(58, 35);
        }
        if (stage == 19) {
            block = true;
            if (npc.show_dialog && playerBagsContainItem("Old Glasses") && playerBagsContainItem("Booze") && playerBagsContainItem("Walking Cane")) {
                next_stage();
            }
        }
        if (stage == 20) {
            block = false;
        }
        if (stage == 21) {
            moveToTile(58, 37);
            WorldRender.worldData[58][38] = 15;
        }

        if (stage == 22) {
            moveToTile(58, 47);
        }
        if (stage == 24) {
            block = true;
        }
        if (stage == 25) {
            block = false;
        }
    }
}
