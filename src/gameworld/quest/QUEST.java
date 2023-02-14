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
    protected void moveToTile(NPC npc, int x, int y) {
        npc.onPath = true;
        npc.goalTile = new Point(x, y);
        npc.stuckCounter++;
        if ((npc.worldX + 24) / 48 == npc.goalTile.x && (npc.worldY + 24) / 48 == npc.goalTile.y) {
            npc.onPath = false;
            npc.stuckCounter = 0;
        } else if (npc.stuckCounter > 7000) {
            npc.worldX = npc.goalTile.x * 48;
            npc.worldY = npc.goalTile.y * 48;
            npc.stuckCounter = 0;
        }
    }

    protected void nextStage() {
        progressStage++;
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
            if (!npc.blockInteraction && !npc.onPath && npc.dialog.dialogRenderCounter == 2000 && mg.inputH.e_typed && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
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
        if (mg.inputH.e_typed && mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {

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
}
