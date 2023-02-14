package gameworld.quest;

import gameworld.entities.NPC;
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
        } else if (npc.stuckCounter > 2000) {
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

    protected void interactWithNpc(NPC npc, String txtName, int progressStage) {
        if (mg.collisionChecker.checkEntityAgainstPlayer(npc, 5)) {
            npc.show_dialog = true;
        }
    }
}
