package gameworld.dialogue.quests;

import gameworld.dialogue.Dialog;
import gameworld.entities.NPC;
import main.MainGame;

public class QST_1 extends Dialog {


    public QST_1(MainGame mg, int type, NPC npc) {
        super(mg, type, npc);
        System.out.println(type);

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

