package gameworld.quest;

import gameworld.entities.NPC;
import gameworld.player.Player;
import main.MainGame;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Point;

abstract public class QUEST {

    public String name;
    public String objective;
    public int progressStage = 1;
    public int quest_id;
    public String progressStageName;
    protected MainGame mg;

    public QUEST(MainGame mg, String name) {
        this.name = name;
        this.mg = mg;
    }

    abstract public void update();


    /**
     * Move the npc to a tile and proceed when he gets there
     *
     * @param x tile x
     * @param y tile y
     */
    protected void moveToTile(NPC npc, int x, int y, Point... checkpoints) {
        npc.goalTile = new Point(x, y);
        npc.checkPoints = checkpoints;
        npc.onPath = true;
        npc.stuckCounter++;

        if (npc.activeTile.equals(npc.goalTile)) {
            npc.onPath = false;
            npc.stuckCounter = 0;
        }
        if (npc.stuckCounter > 6000) {
            npc.worldX = npc.goalTile.x * 48;
            npc.worldY = npc.goalTile.y * 48;
            npc.stuckCounter = 0;
        }
    }


    protected void nextStage() {
        progressStage++;
    }

    protected void updateObjective(String newText) {
        if (!checkDialogSimilarity(newText)) {
            mg.sound.playFinishObjective();
        }
        objective = newText;
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
            if (!npc.blockInteraction && !npc.onPath && !npc.dialog.drawChoice && npc.dialog.dialogRenderCounter == 2000 && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
                try {
                    nextStage();
                    npc.dialog.loadNewLine(array[progressStage]);
                    npc.dialogHideDelay = 0;
                } catch (ArrayIndexOutOfBoundsException e) {
                    npc.dialog.loadNewLine("...");
                }
            } else if (!npc.blockInteraction && !npc.onPath && mg.inputH.e_typed && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
                npc.dialog.dialogRenderCounter = 2000;
            }
        }
        if (mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
            npc.show_dialog = true;
            npc.playerTalkLocation = new Point((int) Player.worldX + 24, (int) Player.worldY + 24);
        }
        mg.inputH.e_typed = false;
    }

    protected void loadDialogStage(NPC npc, String[] array, int stageNumber) {
        try {
            npc.dialog.loadNewLine(array[stageNumber]);
            npc.dialogHideDelay = 0;
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
    protected boolean playerNearbyRectangle(Point p1, Point p2) {
        int x1 = Math.min(p1.x, p2.x);
        int x2 = Math.max(p1.x, p2.x);
        int y1 = Math.min(p1.y, p2.y);
        int y2 = Math.max(p1.y, p2.y);
        return mg.playerX >= x1 && mg.playerX <= x2 && mg.playerY >= y1 && mg.playerY <= y2;
    }

    protected boolean checkDialogSimilarity(String newObjective) {
        int len1 = objective.length();
        int len2 = newObjective.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (objective.charAt(i - 1) == newObjective.charAt(j - 1)) {
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
}
