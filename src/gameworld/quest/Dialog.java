package gameworld.quest;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.system.ui.FonT;

public class Dialog {
    protected int dialogRenderCounter = 0;
    public String dialogLine = "...";


    /**
     * The dialog framework
     * <p>
     * mainGame to access cross-class information
     * to choose the type for dialog text (also quest id)
     */
    public Dialog() {
    }

    public static String insertNewLine(String str) {
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

    public void drawDialog(GraphicsContext gc, ENTITY entity) {

        if (dialogRenderCounter == 2_000) {
            gc.setFont(FonT.minecraftBoldItalic15);
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
            gc.setLineWidth(2);
            gc.setStroke(Color.WHITE);
            gc.setFill(Color.WHITE);
            gc.strokeRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
            int stringY = (int) (entity.worldY - Player.worldY + Player.screenY - 24 - 115 + 6);
            for (String string : dialogLine.split("\n")) {
                gc.fillText(string, entity.worldX - Player.worldX + Player.screenX - 24 + 5 - 124, stringY += 16);
            }
        } else {
            gc.setFont(FonT.minecraftBoldItalic15);
            gc.setFill(Color.BLACK);
            gc.fillRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
            gc.setLineWidth(2);
            gc.setStroke(Color.WHITE);
            gc.setFill(Color.WHITE);
            gc.strokeRoundRect(entity.worldX - Player.worldX + Player.screenX - 24 - 124, entity.worldY - Player.worldY + Player.screenY - 24 - 115, 373, 120, 25, 25);
            int stringY = (int) (entity.worldY - Player.worldY + Player.screenY - 24 - 115 + 6);
            for (String string : dialogLine.substring(0, Math.min(dialogLine.length(), dialogRenderCounter / 4)).split("\n")) {
                gc.fillText(string, entity.worldX - Player.worldX + Player.screenX - 24 + 5 - 124, stringY += 16);
            }
            if (dialogRenderCounter / 4 >= dialogLine.length()) {
                dialogRenderCounter = 2000;
            } else {
                dialogRenderCounter++;
            }
        }
    }

    public void loadNewLine(String dialogLine) {
        this.dialogLine = insertNewLine(dialogLine);
        this.dialogRenderCounter = 0;
    }
}
