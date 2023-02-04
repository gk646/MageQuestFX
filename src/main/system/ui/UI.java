package main.system.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.MainGame;
import main.system.enums.State;

import java.util.Objects;

public class UI {

    private final MainGame mg;
    public Font maruMonica;

    public Font maruMonica30;
    public int commandNum = 0;
    DropShadow dropShadow = new DropShadow();
    InnerShadow innerShadow = new InnerShadow(5, Colors.XPBarBlue);
    private Image playerUI;
    private int loadingProgress = 0;


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        getUIImage();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
    }


    public void draw(GraphicsContext gc) {
        gc.setFont(maruMonica);
        if (mg.gameState == State.PLAY) {
            drawGameUI(gc);
        } else if (mg.gameState == State.OPTION || mg.gameState == State.TITLE_OPTION) {
            drawOptions(gc);
        } else if (mg.gameState == State.TITLE) {
            drawTitleScreen(gc);
        } else if (mg.gameState == State.GAMEOVER) {
            drawGameOver(gc);
        } else if (mg.loadingScreen) {
            drawLoadingScreen(gc);
        }
    }

    private void drawTitleScreen(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold50);
        String text = "Mage Quest_2D";
        int x = (int) (MainGame.SCREEN_WIDTH * 0.39f);
        int y = (int) (MainGame.SCREEN_HEIGHT * 0.044f) * 3;
        //FILL BACKGROUND WITH COLOR
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //DRAW TEXT
        gc.setFill(Colors.darkBackground);
        gc.fillText(text, x, y);

        //MENU
        gc.setFont(FonT.minecraftBold30);

        text = "START GAME";
        x = (int) (MainGame.SCREEN_WIDTH * 0.448_8f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.355f);
        gc.fillText(text, x, y);
        if (commandNum == 0) {
            gc.fillText(">", x - 25, y);
        }
        text = "SETTINGS";
        x = (int) (MainGame.SCREEN_WIDTH * 0.458f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.415f);
        gc.fillText(text, x, y);
        if (commandNum == 1) {
            gc.fillText(">", x - 25, y);
        }
        text = "CREDITS";
        x = (int) (MainGame.SCREEN_WIDTH * 0.465_1f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.475f);
        gc.fillText(text, x, y);
        if (commandNum == 2) {
            gc.fillText(">", x - 25, y);
        }

        text = "QUIT";
        x = (int) (MainGame.SCREEN_WIDTH * 0.481f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.53f);
        gc.fillText(text, x, y);
        if (commandNum == 3) {
            gc.fillText(">", x - 25, y);
        }

        text = "v3.2.1";
        x = (int) (MainGame.SCREEN_HEIGHT * 0.138f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.903f);
        gc.fillText(text, x, y);

        text = "\u00A9 2023 Lukas Gilch";
        x = (int) (MainGame.SCREEN_WIDTH * 0.415f);
        gc.fillText(text, x, y);
    }

    private void drawGameUI(GraphicsContext gc) {

        gc.setFill(Colors.Red);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.0640f, 70, (int) ((mg.player.health / (float) mg.player.maxHealth) * 225), 11);
        gc.setFill(Colors.Blue);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.0640f, 90, (int) ((mg.player.mana / mg.player.maxMana) * 162), 11);
        gc.drawImage(playerUI, 40, 40, 330, 200);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.editUndo15);
        gc.fillText((int) mg.player.health + "/" + mg.player.maxHealth, 199, 72);
        gc.fillText((int) mg.player.mana + "/" + mg.player.maxMana, 173, 94);
        gc.setEffect(dropShadow);
        gc.setEffect(innerShadow);
        gc.setFill(Colors.XPBarBlue);
        gc.fillRoundRect(MainGame.SCREEN_WIDTH * 0.296f, MainGame.SCREEN_WIDTH * 0.515, 768 + 6 + 6, 13, 5, 5);
        gc.setEffect(null);
    }


    private void drawOptions(GraphicsContext gc) {
        gc.setStroke(Colors.LightGreyAlpha);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        // gc.setFont(gc.getFont().deriveFont(Font.BOLD, 59f));
        gc.setStroke(Colors.darkBackground);
        gc.fillText("Settings", MainGame.SCREEN_HEIGHT * 0.092f, MainGame.SCREEN_HEIGHT * 0.092f);
        // gc.setFont(gc.getFont().deriveFont(30f));
        gc.fillText("Framerate: ", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.277f);
        gc.fillText("Network Settings: ", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.416f);
        gc.fillText("Quit Game?", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.648f);
        if (commandNum == 0) {
            gc.fillText(">", 450 - 25, 700);
        }
        if (commandNum == 1) {
            gc.fillText(">", 500 - 25, 700);
        }
    }

    private void drawLoadingScreen(GraphicsContext gc) {
        //FILL BACKGROUND WITH COLOR
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //Text
        gc.setFill(Colors.darkBackground);
        //gc.setFont(gc.getFont().deriveFont(Font.BOLD, 96f));
        gc.setFont(FonT.minecraftBold30);
        String text = "Loading..." + loadingProgress + "%";
        int x = 855;
        int y = 600;
        gc.setLineWidth(4);
        gc.fillText(text, x, y);
        gc.setFill(Colors.darkBackground);
        gc.fillRoundRect((int) (MainGame.SCREEN_WIDTH * 0.052f), (int) (MainGame.SCREEN_HEIGHT * 0.83f), (int) ((loadingProgress / 100.0f) * (int) (MainGame.SCREEN_WIDTH * 0.895f)), (int) (MainGame.SCREEN_HEIGHT * 0.069f), (int) (MainGame.SCREEN_HEIGHT * 0.046f), (int) (MainGame.SCREEN_HEIGHT * 0.046f));
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect((int) (MainGame.SCREEN_WIDTH * 0.052f), (int) (MainGame.SCREEN_HEIGHT * 0.83f), (int) (MainGame.SCREEN_WIDTH * 0.895f), (int) (MainGame.SCREEN_HEIGHT * 0.069f), (int) (MainGame.SCREEN_HEIGHT * 0.046f), (int) (MainGame.SCREEN_HEIGHT * 0.046f));
    }

    public void updateLoadingScreen(int x, GraphicsContext gc) {
        loadingProgress += x;
        drawLoadingScreen(gc);
    }

    private void drawGameOver(GraphicsContext gc) {
        gc.fillText("Game Over!", 500, 500);
    }

    private void getUIImage() {
        playerUI = setup();
    }

    private Image setup() {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/" + "player_ui.png"))));
    }
}
