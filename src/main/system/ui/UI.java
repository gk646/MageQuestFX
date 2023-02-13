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
    private final DropShadow dropShadow = new DropShadow();
    InnerShadow innerShadow = new InnerShadow(5, Colors.XPBarBlue);
    private Image playerUI;
    private int loadingProgress = 0;
    public int saveMessageStage;
    public float credits_scroll = 0;
    public boolean drawSaveMessage;


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        getUIImage();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
    }


    public void draw(GraphicsContext gc) {
        if (mg.gameState == State.PLAY) {
            drawGameUI(gc);
        } else if (mg.gameState == State.OPTION || mg.gameState == State.TITLE_OPTION) {
            drawOptions(gc);
            if (drawSaveMessage) {
                drawSaveMessage(gc);
            }
        } else if (mg.gameState == State.TITLE && !mg.credits) {
            drawTitleScreen(gc);
        } else if (mg.gameState == State.GAMEOVER) {
            drawGameOver(gc);
        } else if (mg.loadingScreen) {
            drawLoadingScreen(gc);
        } else if (mg.credits) {
            drawCredits(gc);
        }
    }

    private void drawCredits(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold30);
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        gc.setFill(Colors.darkBackground);
        gc.fillText("Map Editor: Tiled / Version: 1.9.2 / made by Thorbjørn Lindeijer", 200, credits_scroll);
        gc.fillText("Character textures: craftpix.net / Full copyright with their Freebie License", 200, credits_scroll - 35);
        gc.fillText("Effect textures from: craftpix.net / Full copyright with their Freebie License", 200, credits_scroll - 70);
        gc.fillText("Skilltree design inspired by the Minecraft mod: Craft to Exile [Dissonance] / made by mahjerion", 200, credits_scroll - 105);
        gc.fillText("Item icons: DALL·E  / Image generation AI by OpenAI", 200, credits_scroll - 140);
        credits_scroll += 0.25;
        gc.fillText("ESC to back", MainGame.SCREEN_WIDTH * 0.859, MainGame.SCREEN_HEIGHT * 0.925);
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
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.064_0f, 70, (int) ((mg.player.health / mg.player.maxHealth) * 225), 11);
        gc.setFill(Colors.Blue);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.064_0f, 90, (int) ((mg.player.mana / mg.player.maxMana) * 162), 11);
        gc.drawImage(playerUI, 40, 40, 330, 150);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.editUndo18);
        gc.fillText((int) mg.player.health + "/" + mg.player.maxHealth, 199, 72);
        gc.fillText((int) mg.player.mana + "/" + mg.player.maxMana, 173, 94);
        gc.setFill(Colors.XPBarBlue);
        gc.fillRoundRect(MainGame.SCREEN_WIDTH * 0.296f, MainGame.SCREEN_WIDTH * 0.515, (mg.player.experience / (float) mg.player.levelUpExperience) * 780, 13, 5, 5);
        //gc.fillRoundRect(MainGame.SCREEN_WIDTH * 0.296f, MainGame.SCREEN_WIDTH * 0.515, 780, 13, 5, 5);

    }


    private void drawOptions(GraphicsContext gc) {
        gc.setFill(Colors.LightGreyAlpha);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        gc.setFont(FonT.minecraftBold50);
        gc.setFill(Colors.darkBackground);
        gc.fillText("Settings", MainGame.SCREEN_HEIGHT * 0.092f, MainGame.SCREEN_HEIGHT * 0.132f);
        gc.setFont(FonT.minecraftBold30);
        gc.fillText("Gameplay", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.302f);
        gc.fillText("Video Settings", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.352f);
        gc.fillText("Sound Settings", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.402f);
        gc.fillText("Codex", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.452f);
        gc.fillText("Manual Save", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.502f);
        gc.fillText("Quit Game", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.552f);
        /*
        gc.fillText("Framerate: 120FPS locked", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.277f);
        gc.fillText("Network Settings: ", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.416f);
        gc.fillText("Quit Game?", MainGame.SCREEN_HEIGHT * 0.462f, MainGame.SCREEN_HEIGHT * 0.648f);

         */
        if (commandNum == 0) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.302f);
        } else if (commandNum == 1) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.352f);
        } else if (commandNum == 2) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.402f);
        } else if (commandNum == 3) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.452f);
        } else if (commandNum == 4) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.502f);
        } else if (commandNum == 5) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.552f);
        }
    }

    private void drawSaveMessage(GraphicsContext gc) {

        if (saveMessageStage < 40) {
            gc.fillText("Saving Game", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
        } else if (saveMessageStage < 80) {
            gc.fillText("Saving Game.", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
        } else if (saveMessageStage < 120) {
            gc.fillText("Saving Game..", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
        } else if (saveMessageStage < 150) {
            gc.fillText("Saving Game...", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
        } else if (saveMessageStage > 150) {
            gc.fillText("Game Saved!", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
            if (saveMessageStage >= 360) {
                drawSaveMessage = false;
                saveMessageStage = 0;
            }
        }
        saveMessageStage++;
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
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/" + "player_ui3.png"))));
    }
}
