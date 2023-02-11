package gameworld.dialogue;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.FonT;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract public class Dialog {
    //Type
    //0 = tutorial npc
    //1 = trader
    //2 = Generic person
    protected int stage = 1;
    protected MainGame mg;
    protected int type;
    private String text;
    public boolean block;
    private int textCounter;
    private final NPC npc;

    /**
     * The dialog framework
     *
     * @param mg   mainGame to access cross-class information
     * @param type to choose the type for dialog text (also quest id)
     */
    protected Dialog(MainGame mg, int type, NPC npc) {
        this.npc = npc;
        this.type = type;
        this.mg = mg;
    }

    public void draw(GraphicsContext gc, ENTITY entity) {
        gc.setFont(FonT.minecraftBoldItalic15);
        gc.setFill(Color.BLACK);
        gc.fillRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
        gc.setLineWidth(2);
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.strokeRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
        int stringY = (int) (entity.worldY - Player.worldY + Player.screenY - 24 - 115 + 6);
        for (String string : text.split("\n")) {
            gc.fillText(string, entity.worldX - Player.worldX + Player.screenX - 24 + 5 - 124, stringY += 16);
        }
    }

    protected void load_text() {
        try {
            if (type == 1) {
                InputStream inputStream = Dialog.class.getResourceAsStream("/Dialog/tutorial_npc.txt");
                assert inputStream != null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                text = readLine(stage, bufferedReader);
            } else if (type == 2) {
                InputStream inputStream = Dialog.class.getResourceAsStream("/Dialog/traders.txt");
                assert inputStream != null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                text = readLine(stage, bufferedReader);
            }
            if (text == null) {
                text = "...";
            }
            if (text.length() >= 28) {
                text = insertNewLine(text);
            }
            mg.inputH.e_typed = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String insertNewLine(String str) {
        StringBuilder sb = new StringBuilder();
        String[] words = str.split("\\s+");
        int count = 0;
        for (String word : words) {
            if (count + word.length() > 39) {
                sb.append("\n");
                count = 0;
            }
            count += word.length();
            sb.append(word);
            sb.append(" ");
            count++;
        }
        return sb.toString();
    }

    public void next_stage() {
        stage++;
        load_text();
        mg.inputH.e_typed = false;
    }

    private String readLine(int line, BufferedReader breader) throws IOException {
        String line_text;
        int currentLine = 1;
        while ((line_text = breader.readLine()) != null) {
            if (currentLine == line) {
                break;
            }
            currentLine++;
        }
        breader.close();
        return line_text;
    }

    /**
     * allows the dialog to check for stages and update progress
     */
    abstract public void script(NPC npc);

    /**
     * Move the npc to a tile and proceed when he gets there
     *
     * @param x tile x
     * @param y tile y
     */
    protected void moveToTile(int x, int y) {
        npc.onPath = true;
        npc.goalTile = new Point(x, y);
        if ((npc.worldX + 24) / 48 == npc.goalTile.x && (npc.worldY + 24) / 48 == npc.goalTile.y) {
            npc.onPath = false;
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

}
