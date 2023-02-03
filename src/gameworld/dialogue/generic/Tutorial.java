package gameworld.dialogue.generic;

import gameworld.dialogue.Dialog;
import gameworld.entities.monsters.ENT_Grunt;
import main.MainGame;

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
    public void script() {
        if (stage >= 7) {
            mg.sqLite.updateQuestFacts(1, 1, 1);
        }
        if (stage == 11) {
            gruntkillcounter = mg.prj_control.GruntKilledCounter;
            MainGame.ENTITIES.add(new ENT_Grunt(mg, mg.player.worldX + 250, mg.player.worldY + 250, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, mg.player.worldX + 250, mg.player.worldY, 1));
            MainGame.ENTITIES.add(new ENT_Grunt(mg, mg.player.worldX + 250, mg.player.worldY - 250, 1));
            next_stage();
            block = true;
        }
        if (stage == 12 && mg.prj_control.GruntKilledCounter == gruntkillcounter + 3) {
            next_stage();
            block = false;
        }
        if (stage >= 15) {
            mg.sqLite.updateQuestFacts(1, 2, 1);
        }
    }
}
