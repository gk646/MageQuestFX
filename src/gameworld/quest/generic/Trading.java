package gameworld.quest.generic;

import gameworld.quest.Dialog;
import main.MainGame;

public class Trading extends Dialog {
    /**
     * Trading dialog framework
     *
     * @param mg maingame instance
     */
    protected Trading(MainGame mg,) {
        super(mg, type, npc);
        this.type = type;
        this.mg = mg;
        stage = 1;
        load_text();
    }


    /**
     * allows the dialog to check for stages and update progress
     */
    @Override
    public void script() {

    }
}
