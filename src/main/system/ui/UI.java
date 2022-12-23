package main.system.ui;

import main.MainGame;
import main.Runner;
import main.system.Multiplayer;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI implements ActionListener, ChangeListener {
    public final Color lightBackgroundAlpha = new Color(0xCAC0CBDC, true);
    private final MainGame mg;
    public Font maruMonica;
    public int commandNum = 0;
    private boolean once = false;
    private BufferedImage playerUI;
    private final DragListener dragListener;
    public int loadingProgress = 0;

    private final Color lightBackground = new Color(192, 203, 220);
    private final Color darkBackground = new Color(90, 105, 136);


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        InputStream is = getClass().getResourceAsStream("/Fonts/x12y16pxMaruMonica.ttf");
        try {
            assert is != null;
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        getUIImage();
        this.dragListener = new DragListener();
    }


    public void draw(Graphics2D g2) {
        g2.setFont(maruMonica);
        if (mg.gameState == mg.playState) {
            drawGameUI(g2);
        } else if (mg.gameState == mg.optionState || mg.gameState == mg.titleOption) {
            drawOptions(g2);
        } else if (mg.gameState == mg.titleState) {
            drawTitleScreen(g2);
        } else if (mg.gameState == mg.gameOver) {
            drawGameOver(g2);
        } else if (mg.loadingScreen) {
            drawLoadingScreen(g2);
        }
    }

    private void drawTitleScreen(Graphics2D g2) {
        Runner.slider.setVisible(false);
        Runner.textField.setVisible(false);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Mage Quest_2D";
        int x = getXForCenteredText(text, g2);
        int y = 48 * 3;
        //FILL BACKGROUND WITH COLOR
        g2.setColor(lightBackground);
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //DRAW TEXT
        g2.setColor(darkBackground);
        g2.drawString(text, x, y);
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "START GAME";
        x = getXForCenteredText(text, g2);
        y += 5 * 48;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 25, y);
        }
        text = "OPTIONS";

        x = getXForCenteredText(text, g2);
        y += 48;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 25, y);
        }
        text = "QUIT";
        x = getXForCenteredText(text, g2);
        y += 48;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 25, y);
        }
        text = "1.2.4";
        x = 150;
        y = 900;
        g2.drawString(text, x, y);

        text = "\u00A9 2022 Lukas Gilch";
        x = getXForCenteredText(text, g2);
        g2.drawString(text, x, y);
    }

    private void drawGameUI(Graphics2D g2) {
        Runner.slider.setVisible(false);
        Runner.textField.setVisible(false);
        g2.setColor(new Color(0xFF0044));
        g2.fillRect(123, 70, (int) ((mg.player.health / (float) mg.player.maxHealth) * 225), 11);
        g2.setColor(new Color(0x0099DB));
        g2.fillRect(123, 90, (int) ((mg.player.mana / mg.player.maxMana) * 162), 11);
        g2.drawImage(playerUI, 40, 40, 330, 200, null);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
        g2.drawString((int) mg.player.health + "/" + mg.player.maxHealth, 200, 79);
        g2.drawString((int) mg.player.mana + "/" + mg.player.maxMana, 180, 99);
    }

    private void drawOptions(Graphics2D g2) {
        g2.setColor(lightBackgroundAlpha);
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 59f));
        g2.setColor(darkBackground);
        g2.drawString("Settings", 100, 100);
        g2.setFont(g2.getFont().deriveFont(30f));
        g2.drawString("Framerate: ", 500, 300);
        g2.drawString("Network Settings: ", 500, 450);
        g2.drawString("Quit Game?", 500, 700);
        if (commandNum == 0) {
            g2.drawString(">", 450 - 25, 700);
        }
        if (commandNum == 1) {
            g2.drawString(">", 500 - 25, 700);
        }
        if (!once) {
            Runner.textField.setFocusable(true);
            Runner.textField.setBackground(lightBackgroundAlpha);
            Runner.textField.setForeground(darkBackground);
            Runner.textField.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            Runner.textField.addActionListener(this);
            Runner.textField.setLocation(500, 460);
            Runner.slider.addChangeListener(this);
            Runner.slider.setMajorTickSpacing(30);
            Runner.slider.setPaintLabels(true);
            Runner.slider.setPaintTicks(true);
            Runner.slider.setBackground(lightBackgroundAlpha);
            Runner.slider.setForeground(darkBackground);
            Runner.slider.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
            Runner.slider.setLocation(500, 310);
            once = true;
        }
        Runner.slider.setVisible(true);
        Runner.textField.setVisible(true);
    }

    private void drawLoadingScreen(Graphics2D g2) {
        //FILL BACKGROUND WITH COLOR
        g2.setColor(lightBackground);
        g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Loading..." + loadingProgress + "%";
        int x = getXForCenteredText(text, g2);
        int y = 600;
        g2.setStroke(new BasicStroke(4));
        g2.drawString(text, x, y);
        g2.setColor(darkBackground);
        g2.fillRoundRect((int) (MainGame.SCREEN_WIDTH * 0.052f), (int) (MainGame.SCREEN_HEIGHT * 0.83f), (int) ((loadingProgress / 100f) * (int) (MainGame.SCREEN_WIDTH * 0.895f)), (int) (MainGame.SCREEN_HEIGHT * 0.069f), (int) (MainGame.SCREEN_HEIGHT * 0.046f), (int) (MainGame.SCREEN_HEIGHT * 0.046f));
        g2.setColor(darkBackground);
        g2.drawRoundRect((int) (MainGame.SCREEN_WIDTH * 0.052f), (int) (MainGame.SCREEN_HEIGHT * 0.83f), (int) (MainGame.SCREEN_WIDTH * 0.895f), (int) (MainGame.SCREEN_HEIGHT * 0.069f), (int) (MainGame.SCREEN_HEIGHT * 0.046f), (int) (MainGame.SCREEN_HEIGHT * 0.046f));
    }

    public void updateLoadingScreen(int x) {
        loadingProgress += x;
        mg.repaint();
    }

    private void drawGameOver(Graphics2D g2) {
        g2.drawString("Game Over!", 500, 500);
    }

    private int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return MainGame.SCREEN_WIDTH / 2 - length / 2;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Runner.textField) {
            Multiplayer.ipAddress = Runner.textField.getText();
            mg.requestFocus();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        MainGame.FRAMES_PER_SECOND = Runner.slider.getValue();
        mg.requestFocus();
    }

    private void getUIImage() {
        playerUI = setup();
    }

    private BufferedImage setup() {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/" + "player_ui.png"))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 330, 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}
