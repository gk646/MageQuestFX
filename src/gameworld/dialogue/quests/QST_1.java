package gameworld.dialogue.quests;

import gameworld.dialogue.Dialog;
import main.MainGame;

public class QST_1 extends Dialog {


    public QST_1(MainGame mg, int type) {
        super(mg, type);
        System.out.println(type);

        load_text();
    }

    /**
     * allows the dialog to check for stages and update progress
     */
    @Override
    public void script() {

    }
}

