package ui;

import main.MainGame;
import main.Runner;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    Graphics2D g2;
    MainGame mg;
    Font arial_40, arial_80b,maruMonica;
    public int commandNum=0;
    public UI(MainGame mainGame) {
        this.mg = mainGame;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80b = new Font("Arial", Font.BOLD, 80);
        InputStream is = getClass().getResourceAsStream("/resources/font/x12y16pxMaruMonica.ttf");
        try {
            maruMonica  = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(maruMonica);

        if (mg.gameState == mg.playState) {
            drawGameUI();
        } else if (mg.gameState == mg.pauseState) {
            drawOptions();
        } else if (mg.gameState == mg.titleState) {
            drawTitleScreen();

        }
    }

    private void drawTitleScreen() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250,40));
        textField.setVisible(true);

        Runner.window.add(textField);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Bit Adventure 2d";
        int x = getXforCenteredText(text);
        int y = 48 * 3;

        //FILL BACKGROUND WITH COLOR
        g2.setColor(new Color(108, 170, 229));
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //DRAW TEXTSHADOW

        //DRAW TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "START GAME";
        x = getXforCenteredText(text);
        y += 5 * 48;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">",x-25,y);
        }
        text = "OPTIONS";

        x = getXforCenteredText(text);
        y += 48;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">",x-25,y);
        }
        text = "QUIT";
        x = getXforCenteredText(text);
        y += 48;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">",x-25,y);
        }

    }

    private void drawGameUI() {

    }

    private void drawOptions() {

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return MainGame.SCREEN_WIDTH / 2 - length / 2;
    }
}
