package gameworld.quest.quests;

import gameworld.entities.NPC;
import gameworld.quest.QUEST;
import main.MainGame;

public class QST_IntoTheGrassLands extends QUEST {
    public QST_IntoTheGrassLands(MainGame mg, String name, boolean completed) {
        super(mg, name);
        this.objectives[0] = "Pay the road tax";
        this.quest_id = 1;
        if (!completed) {
            mg.sqLite.setQuestActive(quest_id);
        } else {
            mg.sqLite.finishQuest(quest_id);
        }
    }

    /**
     *
     */
    @Override
    public void update() {
        for (NPC npc : mg.npcControl.NPC_Active) {
        }
    }
}
