package gameworld.dialogue.quests;

import gameworld.dialogue.Dialog;
import main.MainGame;

public class QST_5 extends Dialog {


    public QST_5(MainGame mg, int type) {
        super(mg, type);
        load_text();
    }

    /**
     * allows the dialog to check for stages and update progress
     */
    @Override
    public void script() {

    }
}
