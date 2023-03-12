package gameworld.quest.hiddenquests;

import gameworld.entities.NPC;
import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.world.WorldController;
import main.MainGame;
import main.system.enums.Zone;

public class QST_TheGrovesSecret extends HiddenQUEST {
    private boolean gotQuest;

    public QST_TheGrovesSecret(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.TheGrovesSecret;
        quest_id = logicName.val;
        name = "The Grove's Secret";
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
        if (progressStage == 1) {
            if (WorldController.currentWorld == Zone.TheGrove && !gotQuest) {
                this.activated = true;
                mg.sqLite.updateQuestFacts(quest_id, 1, 1);
            }
        }
    }
}
