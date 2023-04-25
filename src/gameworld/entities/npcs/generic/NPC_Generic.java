/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
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

package gameworld.entities.npcs.generic;

import gameworld.entities.NPC;
import gameworld.player.PlayerPrompts;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import main.MainGame;
import main.system.rendering.WorldRender;

import java.awt.Point;

abstract public class NPC_Generic extends NPC {
    public boolean despawn;
    private boolean finished;
    private final Point previousTile = new Point();
    private int counter1;
    private int counter2;
    private final String dialogLine = DialogStorage.NPCComments[MainGame.random.nextInt(0, DialogStorage.NPCComments.length - 1)];

    void scriptMovement() {
        if (collidingWithPlayer()) {
            if (!show_dialog) {
                PlayerPrompts.setETrue();
            }
            if (mg.inputH.e_typed) {
                mg.inputH.e_typed = false;
                dialog.loadNewLine(dialogLine);
                show_dialog = true;
            }
        }
        if (show_dialog) {
            dialogHideDelay++;
            if (dialogHideDelay >= 500) {
                dialogHideDelay = 0;
                show_dialog = false;
            }
        }
        if (WorldController.currentScript != null) {
            if (goalTile == null) {
                for (int i = 0; i < WorldController.currentScript.entranceTaken.length; i++) {
                    if (!WorldController.currentScript.entranceTaken[i]) {
                        WorldController.currentScript.entranceTaken[i] = true;
                        goalTile = WorldController.currentScript.houseEntrances[i];
                        return;
                    }
                }
            } else if (!goalTile.equals(activeTile)) {
                if (!show_dialog) {
                    moveTo(goalTile.x, goalTile.y, checkPoints);
                }
                onPath = !show_dialog && (worldX != previousTile.x || worldY != previousTile.y);
                if (checkpointIndex == 0) {
                    counter1++;
                }

                if (counter1 >= 350) {
                    int num = (int) (Math.random() * 35 * 30);
                    for (int i = 0; i < 4; i++) {
                        if (getRandomCheckpoint(num)) {
                            break;
                        }
                        num = (int) (Math.random() * 35 * 30);
                    }
                    counter1 = 0;
                }
                previousTile.x = (int) worldX;
                previousTile.y = (int) worldY;
            } else {
                onPath = false;
                if (!finished) {
                    for (int i = 0; i < WorldController.currentScript.houseEntrances.length; i++) {
                        if (goalTile.equals(WorldController.currentScript.houseEntrances[i])) {
                            WorldController.currentScript.entranceTaken[i] = false;
                            finished = true;
                            break;
                        }
                    }
                }
                if (!show_dialog) {
                    counter2++;
                }
                if (counter2 >= 500) {
                    despawn = true;
                }
            }
        }
    }

    private boolean getRandomCheckpoint(int randomNum) {
        int numX = randomNum / 35;
        int numY = randomNum % 35;
        if (!checkCollisionOnTileNum(21 + numX, 4 + numY)) {
            checkPoints = new Point[]{new Point(21 + numX, 4 + numY)};
            return true;
        }
        return false;
    }


    private boolean checkCollisionOnTileNum(int tileNumX, int tilenumY) {
        int num1 = WorldRender.worldData1[tileNumX][tilenumY];
        return !mg.wRender.tileStorage[WorldRender.worldData[tileNumX][tilenumY]].collision
                && num1 != -1 &&
                !mg.wRender.tileStorage[num1].collision;
    }
}