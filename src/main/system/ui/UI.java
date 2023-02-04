package main.system.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.MainGame;
import main.system.enums.State;

import java.io.InputStream;
import java.util.Objects;

public class UI {

    private final MainGame mg;
    public Font maruMonica;

    public Font maruMonica30;
    public int commandNum = 0;
    public Font pixel_dialog;
    DropShadow dropShadow = new DropShadow();
    InnerShadow innerShadow = new InnerShadow(5, Colors.XPBarBlue);
    private Image playerUI;
    private int loadingProgress = 0;


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        InputStream is = getClass().getResourceAsStream("/Fonts/x12y16pxMaruMonica.ttf");
        assert is != null;
        maruMonica = Font.loadFont(is, 20);
        maruMonica30 = Font.loadFont(is, 30);
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
        //gc.setFont(gc.getFont().deriveFont(Font.BOLD, 96f));
        gc.setFont(mg.ui.maruMonica);
        String text = "Mage Quest_2D";
        int x = getXForCenteredText(text, gc);
        int y = (int) (MainGame.SCREEN_HEIGHT * 0.044f) * 3;
        //FILL BACKGROUND WITH COLOR
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //DRAW TEXT
        gc.setFill(Colors.darkBackground);
        gc.fillText(text, x, y);

        //MENU
        //gc.setFont(gc.getFont().deriveFont(Font.BOLD, 48f));

        text = "START GAME";
        x = getXForCenteredText(text, gc);
        y += 2.5f * (int) (MainGame.SCREEN_HEIGHT * 0.049f);
        gc.fillText(text, x, y);
        if (commandNum == 0) {
            gc.fillText(">", x - 25, y);
        }
        text = "OPTIONS";

        x = getXForCenteredText(text, gc);
        y += (int) (MainGame.SCREEN_HEIGHT * 0.049f);
        gc.fillText(text, x, y);
        if (commandNum == 1) {
            gc.fillText(">", x - 25, y);
        }
        text = "QUIT";
        x = getXForCenteredText(text, gc);
        y += (int) (MainGame.SCREEN_HEIGHT * 0.049f);
        gc.fillText(text, x, y);
        if (commandNum == 2) {
            gc.fillText(">", x - 25, y);
        }
        text = "3.0.0";
        x = (int) (MainGame.SCREEN_HEIGHT * 0.138f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.903f);
        gc.fillText(text, x, y);

        text = "\u00A9 2022 Lukas Gilch";
        x = getXForCenteredText(text, gc);
        gc.fillText(text, x, y);
    }

    private void drawGameUI(GraphicsContext gc) {
        gc.setFill(Colors.Red);
        gc.fillRect(123, 70, (int) ((mg.player.health / (float) mg.player.maxHealth) * 225), 11);
        gc.setFill(Colors.Blue);
        gc.fillRect(123, 90, (int) ((mg.player.mana / mg.player.maxMana) * 162), 11);
        gc.drawImage(playerUI, 40, 40, 330, 200);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.minecraftBoldItalic15);
        gc.fillText((int) mg.player.health + "/" + mg.player.maxHealth, 200, 79);
        gc.fillText((int) mg.player.mana + "/" + mg.player.maxMana, 180, 99);
        gc.setEffect(dropShadow);
        gc.setEffect(innerShadow);
        gc.setFill(Colors.XPBarBlue);
        gc.fillRoundRect(570, 990, 768 + 6 + 6, 13, 5, 5);
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
        gc.setFont(maruMonica30);
        String text = "Loading..." + loadingProgress + "%";
        int x = getXForCenteredText(text, gc);
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

    private int getXForCenteredText(String text, GraphicsContext gc) {
        int length = 150;
        return MainGame.SCREEN_WIDTH / 2 - length / 2;
    }

    public void getPixelFont() {
        InputStream is = getClass().getResourceAsStream("/Fonts/PublicPixel-z84yD.ttf");
        assert is != null;
        pixel_dialog = Font.loadFont(is, 15);
    }


    private void getUIImage() {
        playerUI = setup();
    }

    private Image setup() {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/" + "player_ui.png"))));
    }
}
