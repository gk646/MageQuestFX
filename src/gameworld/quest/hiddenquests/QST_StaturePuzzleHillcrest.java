/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld.quest.hiddenquests;

import gameworld.player.PlayerPrompts;
import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST_NAME;
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
    private int counter = 0;

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
                String text = "Looks like theres something underneath. There are small levers built into the back of the statues. Maybe the inscriptions will give me a clue what to do with them.";
                mg.player.dialog.loadNewLine(text);
                cacheDialog(text);
                addQuestMarker("1", 68, 69, Zone.Hillcrest);
                nextStage();
            } else if (progressStage == 3) {
                if (Arrays.equals(user, correctSolution)) {
                    nextStage();
                }
                for (int i = 0; i < 4; i++) {
                    if (new Point(mg.playerX, mg.playerY).equals(inscriptionPoints[i])) {
                        PlayerPrompts.setETrue();
                        if (mg.inputH.e_typed) {
                            mg.inputH.e_typed = false;
                            mg.player.dialog.loadNewLine(inscriptions[i]);
                            cacheDialog(inscriptions[i]);
                        }
                    }
                    if (new Point(mg.playerX, mg.playerY).equals(activatePoints[i])) {
                        PlayerPrompts.setETrue();
                        if (mg.inputH.e_typed) {
                            mg.inputH.e_typed = false;
                            user[counter] = i;
                            counter++;
                            mg.sound.playEffectSound(16);
                        }
                    }
                    if (user[i] != -1) {
                        if (user[i] == correctSolution[i]) {
                            continue;
                        } else {
                            mg.sound.playEffectSound(17);
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
                if (WorldController.currentWorld == Zone.Treasure_Cave) {
                    objective3Progress = 1;
                }
                if (objective3Progress == 1 && WorldController.currentWorld == Zone.Hillcrest) {
                    updateObjective("", 0);
                    mg.sqLite.finishQuest(quest_id);
                    completed = true;
                }
                updateObjective("Explore the hidden cavern", 0);
            }
        }
    }
}
