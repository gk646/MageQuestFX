package ui;

import main.MainGame;

import java.awt.*;

public class UI {
    Graphics2D g2;
    MainGame mg;
    Font arial_40, arial_80b;

    public UI(MainGame mainGame) {
        this.mg = mainGame;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80b = new Font("Arial", Font.BOLD, 80);

    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (mg.gameState == mg.playState) {
            drawGameUI();
        } else if (mg.gameState == mg.pauseState) {
            drawMenu();
        } else if (mg.gameState == mg.titleState) {
            drawTitleScreen();


        }
    }

    private void drawTitleScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96f));
        String text = "Bit Adventure 2d";
        int x = getXforCenteredText(text);
        int y = 48*3;
    }

    private void drawGameUI() {

    }

    private void drawMenu() {

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return MainGame.SCREEN_WIDTH/2 -length/2;
    }
}
