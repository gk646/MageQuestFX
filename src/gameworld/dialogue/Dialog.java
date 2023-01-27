package gameworld.dialogue;

import gameworld.Entity;
import main.MainGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dialog {
    private final MainGame mg;
    private final int type;
    public String text;
    private int textCounter;
    private final Stroke two_wide = new BasicStroke(2);
    //Type
    //0 = tutorial npc
    //1 = trader
    //2 = Generic person
    public int stage;

    public Dialog(MainGame mg, int type) {
        this.type = type;
        this.mg = mg;
        stage = 1;
        try {
            load_text();
        } catch (IOException e) {
            text = "Seems like i dont have anything to say...";
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void draw(Graphics2D g2, Entity entity) {
        g2.setFont(mg.ui.pixel_dialog.deriveFont(Font.PLAIN, 11f));
        g2.setColor(Color.black);
        g2.fillRoundRect(entity.worldX - mg.player.worldX + mg.player.screenX - 24 - 124, entity.worldY - mg.player.worldY + mg.player.screenY - 24 - 115, 373, 120, 25, 25);
        g2.setStroke(two_wide);
        g2.setColor(Color.white);
        g2.drawRoundRect(entity.worldX - mg.player.worldX + mg.player.screenX - 24 - 124, entity.worldY - mg.player.worldY + mg.player.screenY - 24 - 115, 373, 120, 25, 25);
        int stringY = entity.worldY - mg.player.worldY + mg.player.screenY - 24 - 115 + 6;
        for (String string : text.split("\n")) {
            g2.drawString(string, entity.worldX - mg.player.worldX + mg.player.screenX - 24 + 5 - 124, stringY += g2.getFontMetrics().getHeight() + 5);
        }
    }

    private void load_text() throws IOException {
        if (type == 0) {
            InputStream inputStream = Dialog.class.getResourceAsStream("/Dialog/tutorial_npc.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            text = readLine(stage, bufferedReader);
        }
        if (type == 1) {
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
        mg.keyHandler.e_typed = false;
    }

    public String insertNewLine(String str) {
        StringBuilder sb = new StringBuilder();
        String[] words = str.split("\\s+");
        int count = 0;
        for (String word : words) {
            count += word.length();
            sb.append(word);
            if (count >= 28) {
                sb.append("\n");
                count = 0;
            } else {
                sb.append(" ");
                count++;
            }
        }
        return sb.toString();
    }

    public void next_stage() {
        stage++;
        try {
            load_text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mg.keyHandler.e_typed = false;
    }

    private String readLine(int line, BufferedReader breader) throws IOException {
        String line_text;
        int currentLine = 0;
        while ((line_text = breader.readLine()) != null) {
            if (currentLine == line) {
                break;
            }
            currentLine++;
        }
        breader.close();
        return line_text;
    }
}
