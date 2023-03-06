package gameworld.quest.hiddenquests;

import gameworld.quest.HiddenQUEST;
import gameworld.world.WorldController;
import main.MainGame;
import main.system.enums.Zone;

public class QST_StaturePuzzleHillcrest extends HiddenQUEST {
    public QST_StaturePuzzleHillcrest(MainGame mg, String name, boolean completed) {
        super(mg, name);
        quest_id = 4;
        name = "The 4 Statures";
        progressStage = 1;
        mg.sqLite.setQuestActive(quest_id);
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
        if (WorldController.currentWorld == Zone.Hillcrest && progressStage == 1) {
            if (mg.playerX == 64 && mg.playerY == 67 && mg.inputH.e_typed) {
                mg.inputH.e_typed = false;
                this.activated = true;
                nextStage();
            }
        } else if (progressStage > 1) {
            if (progressStage == 2) {
                updateObjective("Solver the puzzle", 0);
            }
        }
    }
}
