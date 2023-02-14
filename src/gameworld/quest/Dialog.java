package gameworld.quest;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.FonT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

abstract public class Dialog {
    protected int stage = 1;
    protected MainGame mg;
    private String text;
    public boolean block;
    public String txtName;
    private NPC npc;
    private int stuckCounter;

    /**
     * The dialog framework
     *
     * @param mg mainGame to access cross-class information
     *           to choose the type for dialog text (also quest id)
     */
    protected Dialog(MainGame mg) {
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
            InputStream inputStream = Dialog.class.getResourceAsStream("/Dialog/" + txtName + ".txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            text = readLine(stage, bufferedReader);
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
    abstract public void script();




}
