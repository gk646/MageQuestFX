package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.quest.Dialog;
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
     */
    @Override
    public void script(NPC npc) {

    }


}

