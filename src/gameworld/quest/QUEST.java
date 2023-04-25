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

package gameworld.quest;

import gameworld.entities.NPC;
import gameworld.player.Player;
import gameworld.player.PlayerPrompts;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Point;
import java.util.ArrayList;

abstract public class QUEST {

    public String name;
    public ArrayList<QuestMarker> questMarkers = new ArrayList<>();
    public QUEST_NAME logicName;
    public final String[] objectives = new String[3];
    public String[] questRecap = new String[50];
    public int progressStage = 1;
    public int quest_id;
    protected boolean DontConsumeETyped;
    protected int objective1Progress = 0;
    protected int objective2Progress = 0;
    public int objective3Progress = 0;
    protected final MainGame mg;
    public boolean completed;


    public QUEST(MainGame mg) {
        this.mg = mg;
    }

    abstract public void update();


    protected void removeObjective(int index) {
        objectives[index] = null;
    }

    protected void nextStage() {
        progressStage++;
    }

    /**
     * @param worldX
     * @param worldY
     * @param distance
     * @return if the player is close to coordinates / absolute numbers
     */
    public static boolean playerCloseToAbsolute(int worldX, int worldY, int distance) {
        return new Point((int) Player.worldX, (int) Player.worldY).distance(new Point(worldX, worldY)) < distance;
    }

    protected void cacheDialog(String text) {
        for (int i = 0; i < questRecap.length; i++) {
            if (questRecap[i] == null) {
                questRecap[i] = Dialog.insertNewLine(text, 42);
                break;
            } else if (questRecap[i].equals(Dialog.insertNewLine(text, 42))) {
                break;
            }
        }
    }


    /**
     * true if the play bags contain item with name
     *
     * @param name the item name as string
     * @return true if item is in player bag
     */
    protected boolean playerBagsContainItem(String name) {
        for (UI_InventorySlot invSlot : mg.inventP.bag_Slots) {
            if (invSlot.item != null) {
                if (invSlot.item.name.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void closeRoundDoor(int x, int y) {
        WorldRender.worldData2[x][y] = 1_301;
        WorldRender.worldData2[x + 1][y] = 1_302;
        WorldRender.worldData1[x][y + 1] = 1_314;
        WorldRender.worldData1[x + 1][y + 1] = 1_315;
    }

    public static void openRoundDoor(int x, int y) {
        WorldRender.worldData2[x][y] = 1_304;
        WorldRender.worldData2[x + 1][y] = 1_305;
        WorldRender.worldData1[x][y] = -1;
        WorldRender.worldData1[x + 1][y] = -1;
        WorldRender.worldData1[x][y + 1] = 1_317;
        WorldRender.worldData1[x + 1][y + 1] = 1_318;
    }

    public static void openSquareDoor(int x, int y) {
        WorldRender.worldData2[x][y] = 1_353;
        WorldRender.worldData1[x][y] = -1;
        WorldRender.worldData1[x][y + 1] = 1_366;
    }

    public static void closeSquareDoor(int x, int y) {
        WorldRender.worldData2[x][y] = 1_327;
        WorldRender.worldData1[x][y + 1] = 1_340;
    }

    private boolean checkDialogSimilarity(String newObjective, int index) {
        if (objectives[index] == null) {
            return false;
        }
        int len1 = objectives[index].length();
        int len2 = newObjective.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (objectives[index].charAt(i - 1) == newObjective.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }

        int distance = dp[len1][len2];
        int maxLen = Math.max(len1, len2);
        double similarity = 1.0 - ((double) distance / (double) maxLen);

        // Check if the similarity is greater than or equal to 80%
        return similarity >= 0.8;
    }

    protected void removeItemFromBag(String name) {
        for (UI_InventorySlot slot : mg.inventP.char_Slots) {
            if (slot.item != null && (slot.item.name.equals(name))) {
                slot.item = null;
                return;
            }
        }
    }

    /**
     * Move the npc to a tile and proceed when he gets there
     *
     * @param x tile x
     * @param y tile y
     */
    protected boolean moveToTile(NPC npc, int x, int y, Point... checkpoints) {
        npc.goalTile = new Point(x, y);
        npc.checkPoints = checkpoints;
        npc.onPath = true;
        npc.stuckCounter++;
        if (npc.activeTile.equals(npc.goalTile)) {
            npc.onPath = false;
            npc.stuckCounter = 0;
            return true;
        } else if (npc.stuckCounter > 6_000) {
            npc.worldX = npc.goalTile.x * 48;
            npc.worldY = npc.goalTile.y * 48;
            npc.stuckCounter = 0;
        }
        return false;
    }

    protected void updateObjective(String newText, int index) {
        if (objectives[index] == null || !objectives[index].equals(newText)) {
            if (!checkDialogSimilarity(newText, index)) {
                mg.sound.playEffectSound(1);
            }
            for (int i = 0; i < 150; i++) {
                if (questRecap[i] == null) {
                    questRecap[i] = Dialog.insertNewLine(newText, 27);
                    break;
                } else if (questRecap[i].equals(Dialog.insertNewLine(newText, 27))) {
                    break;
                }
            }
            objectives[index] = Dialog.insertNewLine(newText, 27);
        }
    }

    protected void loadDialogStage(NPC npc, String[] array, int stageNumber) {
        npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
        try {
            if (npc.dialog.dialogLine.equals(Dialog.insertNewLine(array[stageNumber], 45))) {
                return;
            }
            npc.dialog.loadNewLine(array[stageNumber]);
            npc.dialogHideDelay = 0;
            npc.show_dialog = true;
            npc.playerTalkLocation = new Point(npc.activeTile.x * 48, npc.activeTile.y * 48);
        } catch (ArrayIndexOutOfBoundsException e) {
            npc.dialog.loadNewLine("...");
        }
    }

    /**
     * checks if the player is inside the rectangle of the given points
     *
     * @param p1 point 1
     * @param p2 point2
     * @return if the player is inside the rectangle
     */
    protected boolean playerInsideRectangle(Point p1, Point p2) {
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        return mg.playerX >= x1 && mg.playerX <= x2 && mg.playerY >= y1 && mg.playerY <= y2;
    }


    protected void interactWithNpc(NPC npc, String[] array) {
        if (npc.dialog.dialogLine.equals("...")) {
            try {
                npc.dialog.loadNewLine(array[progressStage]);
                npc.dialogHideDelay = 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                npc.dialog.loadNewLine("...");
            }
        }
        if (npc.show_dialog) {
            Player.interactingWithNPC = true;
            if (mg.inputH.e_typed && !npc.blockInteraction && !npc.onPath && !npc.dialog.drawChoice && npc.dialog.dialogRenderCounter == 2_000 && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
                try {
                    nextStage();
                    npc.dialog.loadNewLine(array[progressStage]);
                    npc.dialogHideDelay = 0;
                    mg.inputH.e_typed = false;
                    cacheDialog(array[progressStage]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    npc.dialog.loadNewLine("...");
                }
            } else if (!npc.blockInteraction && !npc.onPath && mg.inputH.e_typed && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
                npc.dialog.dialogRenderCounter = 2_000;
            }
        }
        if (mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
            if (!npc.show_dialog && !npc.blockInteraction) {
                PlayerPrompts.setETrue();
            }
            if (mg.inputH.e_typed) {
                npc.show_dialog = true;
                Player.interactingWithNPC = true;
                if (!DontConsumeETyped) {
                    mg.inputH.e_typed = false;
                }
                npc.dialogHideDelay = 0;
                npc.playerTalkLocation = new Point((int) Player.worldX + 24, (int) Player.worldY + 24);
            }
        }
    }


    protected void addQuestMarker(String name, int x, int y, Zone zone) {
        for (QuestMarker marker : questMarkers) {
            if (marker.xTile() == x && marker.yTile() == y) {
                return;
            }
        }
        mg.sBar.showNoticeMap = true;
        questMarkers.add(new QuestMarker(name, x, y, zone));
    }

    protected void removeQuestMarker(String name) {
        questMarkers.removeIf(marker -> marker.name().equals(name));
    }
}
