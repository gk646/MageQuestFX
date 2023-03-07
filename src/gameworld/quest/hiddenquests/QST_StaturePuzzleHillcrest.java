package gameworld.quest.hiddenquests;

import gameworld.quest.HiddenQUEST;
import gameworld.world.WorldController;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;
import java.util.Arrays;

public class QST_StaturePuzzleHillcrest extends HiddenQUEST {
    private final int[] user = new int[4];
    private final int[] correctSolution = new int[]{3, 0, 1, 2};
    private final Point[] activatePoints = new Point[]{new Point(64, 65), new Point(71, 65), new Point(64, 70), new Point(71, 70)};
    private final Point[] inscriptionPoints = new Point[]{new Point(64, 67), new Point(71, 67), new Point(64, 72), new Point(71, 72)};
    private final String[] inscriptions = new String[]{"I am shorter than the statue below", "Iam taller then the statue to the left, but shorter then the tallest", "Iam the oldest", "Iam the least tall statue"};
    int counter = 0;

    public QST_StaturePuzzleHillcrest(MainGame mg, boolean completed) {
        super(mg);
        for (int i = 0; i < 4; i++) {
            user[i] = -1;
        }
        name = "The 4 Statues";
        logicName = QUEST_NAME.HillcrestPuzzle;
        quest_id = logicName.val;
        progressStage = 1;
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
                updateObjective("Solve the puzzle", 0);
                mg.player.dialog.loadNewLine("Looks like theres something underneath. There are small levers built into the back of the statues. Maybe the inscriptions will give me a clue what to do with them.");
                nextStage();
            } else if (progressStage == 3) {
                if (Arrays.equals(user, correctSolution)) {
                    nextStage();
                }
                for (int i = 0; i < 4; i++) {
                    if (new Point(mg.playerX, mg.playerY).equals(inscriptionPoints[i]) && mg.inputH.e_typed) {
                        mg.inputH.e_typed = false;
                        mg.player.dialog.loadNewLine(inscriptions[i]);
                    }
                    if (new Point(mg.playerX, mg.playerY).equals(activatePoints[i]) && mg.inputH.e_typed) {
                        mg.inputH.e_typed = false;
                        user[counter] = i;
                        counter++;
                    }
                    if (user[i] != -1) {
                        if (user[i] == correctSolution[i]) {
                            continue;
                        } else {
                            for (int k = 0; k < 4; k++) {
                                user[i] = -1;
                            }
                            counter = 0;
                        }
                    }
                }
            } else if (progressStage == 4) {
                if (objective1Progress == 0) {
                    WorldRender.worldData1[63][66] = 202;
                    WorldRender.worldData1[64][66] = -1;
                    WorldRender.worldData2[64][64] = -1;
                    WorldRender.worldData2[64][65] = -1;
                    WorldRender.worldData2[63][65] = 189;
                    WorldRender.worldData2[63][64] = 176;

                    objective1Progress = 1;
                }
                updateObjective("Explore the hidden cavern", 0);
            }
        }
    }
}
