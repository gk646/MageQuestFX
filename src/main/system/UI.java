package main.system;

import main.MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class UI implements ActionListener {
    Graphics2D g2;
    MainGame mg;
    public Font arial_40, arial_80b, maruMonica;
    public int titleState = 0, commandNum = 0;
    private boolean once = false;
    JTextField textField;

    public UI(MainGame mainGame) {
        this.mg = mainGame;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80b = new Font("Arial", Font.BOLD, 80);
        InputStream is = getClass().getResourceAsStream("/resources/font/x12y16pxMaruMonica.ttf");
        try {
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
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
        } else if (mg.gameState == mg.titleState && titleState == 0) {
            drawTitleScreen();
        } else if (mg.gameState == mg.titleState && titleState == 1) {
            drawOptions();
        }

    }

    private void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Bit Adventure 2d";
        int x = getXforCenteredText(text);
        int y = 48 * 3;

        //FILL BACKGROUND WITH COLOR
        g2.setColor(new Color(192, 203, 220));
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);

        //DRAW TEXT
        g2.setColor(new Color(90, 105, 136));
        g2.drawString(text, x, y);
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "START GAME";
        x = getXforCenteredText(text);
        y += 5 * 48;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 25, y);

        }
        text = "OPTIONS";

        x = getXforCenteredText(text);
        y += 48;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 25, y);

        }
        text = "QUIT";
        x = getXforCenteredText(text);
        y += 48;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 25, y);
        }

    }

    private void drawGameUI() {

    }

    public void drawOptions() {
        g2.setColor(new Color(192, 203, 220));
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 59f));
        g2.setColor(new Color(90, 105, 136));
        g2.drawString("Option screen ", 100, 100);
        if (!once) {
            textField = new JTextField("IP-Address:Port");
            textField.setPreferredSize(new Dimension(250, 60));
            textField.setVisible(true);
            textField.setFocusable(true);
            textField.setBackground(new Color(192, 203, 220));
            textField.setForeground(new Color(90, 105, 136));
            textField.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            textField.addActionListener(this);
            mg.add(textField);
            mg.revalidate();
            once = true;
        }
        textField.setLocation(600, 600);
        textField.repaint();
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return MainGame.SCREEN_WIDTH / 2 - length / 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textField) {
            Multiplayer.portNumber = Integer.parseInt(textField.getText());
            textField.transferFocus();
        }
    }
}
