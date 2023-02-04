package gameworld.dialogue.generic;

import gameworld.dialogue.Dialog;
import gameworld.entities.NPC;
import main.MainGame;

public class Trading extends Dialog {
    /**
     * Trading dialog framework
     *
     * @param mg   maingame instance
     * @param type
     */
    protected Trading(MainGame mg, int type, NPC npc) {
        super(mg, type, npc);
        this.type = type;
        this.mg = mg;
        stage = 1;
        load_text();
    }

    /**
     * allows the dialog to check for stages and update progress
     *
     * @param npc
     */
    @Override
    public void script(NPC npc) {

    }


}
