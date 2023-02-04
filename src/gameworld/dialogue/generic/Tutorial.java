package gameworld.dialogue.generic;

import gameworld.dialogue.Dialog;
import gameworld.entities.NPC;
import gameworld.entities.monsters.ENT_Grunt;
import main.MainGame;
import main.system.enums.Map;

import java.awt.Point;

public class Tutorial extends Dialog {
    private int gruntkillcounter = 0;

    /**
     * The tutorial dialog
     */
    public Tutorial(MainGame mg, int type) {
        super(mg, type);
        this.type = type;
        this.mg = mg;
        stage = 1;
        load_text();
    }

    /**
     * Custom quest stages and requirements
     */
    @Override
    public void script(NPC npc) {
        if (stage == 6) {
            npc.onPath = true;
            moveToTile(npc, 34, 34);
        }
        if (stage == 10) {
            gruntkillcounter = mg.prj_control.GruntKilledCounter;
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 30, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 34, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, 48 * 46, 48 * 38, 1));
            next_stage();
            block = true;
            mg.sqLite.updateQuestFacts(1, 1, 1);
        }
        if (stage == 11 && mg.prj_control.GruntKilledCounter == gruntkillcounter + 3) {
            next_stage();
            block = false;
        }
        if (stage == 14) {
            moveToTile(npc, 48, 34);
        }
        if (stage == 15) {
            mg.sqLite.updateQuestFacts(1, 2, 1);
            if (mg.wControl.currentWorld == Map.Tutorial) {
                mg.wRender.worldData[48][34] = 4;
            }
        }
        if (stage == 16) {
            moveToTile(npc, 58, 35);
        }
        if (stage == 20) {
            block = true;
        }
    }

    private void moveToTile(NPC npc, int x, int y) {
        npc.onPath = true;
        npc.goalTile = new Point(x, y);
        if ((npc.worldX + 24) / 48 == npc.goalTile.x && (npc.worldY + 24) / 48 == npc.goalTile.y) {
            next_stage();
            npc.onPath = false;
        }
    }
}
