package main.system.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
    DropShadow shadow = new DropShadow(1, 1, 2, Color.BLACK);
    Light.Distant light = new Light.Distant();

    Lighting lighting = new Lighting();

    private Image playerUI;
    private int loadingProgress = 0;
    public int saveMessageStage;
    public float credits_scroll = 0;
    public boolean drawSaveMessage;
    public double codex_scroll = 0.332;


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        getUIImage();
        light.setAzimuth(-135.0);
        lighting.setLight(light);
        lighting.setSurfaceScale(3.0);
        lighting.setDiffuseConstant(1);
        lighting.setSpecularConstant(-1);
    }


    public void draw(GraphicsContext gc) {
        if (mg.gameState == State.PLAY) {
            drawGameUI(gc);
        } else if (mg.gameState == State.OPTION || mg.gameState == State.TITLE_OPTION) {
            drawOptions(gc);
            if (drawSaveMessage) {
                drawSaveMessage(gc);
            }
            if (mg.drawVideoSettings) {
                drawVideoSettings(gc);
            } else if (mg.drawAudioSettings) {
                drawAudioSettings(gc);
            } else if (mg.drawKeybindings) {
                drawKeyBindings(gc);
            } else if (mg.drawGameplay) {
                drawGamePlaySettings(gc);
            } else if (mg.drawCodex) {
                drawCodex(gc, codex_scroll);
            }
        } else if (mg.credits) {
            drawCredits(gc);
        } else if (mg.gameState == State.TITLE) {
            drawTitleScreen(gc);
        } else if (mg.gameState == State.GAMEOVER) {
            drawGameOver(gc);
        } else if (mg.loadingScreen) {
            drawLoadingScreen(gc);
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
        gc.setEffect(lighting);
        gc.setFill(Colors.Red);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.063_5f, 69, (int) ((mg.player.health / mg.player.maxHealth) * 225), 10);
        lighting.setSurfaceScale(3.0);
        lighting.setDiffuseConstant(1.7);
        gc.setFill(Colors.Blue);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.063_5f, 95, (int) ((mg.player.mana / mg.player.maxMana) * 162), 10);
        gc.setEffect(null);
        gc.drawImage(playerUI, 40, 40, 330, 150);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.editUndo18);
        gc.setEffect(shadow);
        gc.fillText((int) mg.player.health + "/" + mg.player.maxHealth, 199, 72);
        gc.fillText((int) mg.player.mana + "/" + mg.player.maxMana, 173, 97);
        gc.setEffect(null);
        gc.setFill(Colors.XPBarBlue);
        gc.fillRoundRect(MainGame.SCREEN_WIDTH * 0.296f, MainGame.SCREEN_WIDTH * 0.515, (mg.player.experience / (float) mg.player.levelUpExperience) * 780, 13, 5, 5);
    }


    private void drawOptions(GraphicsContext gc) {
        gc.setFill(Colors.LightGreyAlpha);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        gc.setFont(FonT.minecraftBold50);
        gc.setFill(Colors.darkBackground);
        gc.fillText("Settings", MainGame.SCREEN_HEIGHT * 0.092f, MainGame.SCREEN_HEIGHT * 0.132f);
        gc.setFont(FonT.minecraftBold30);
        gc.fillText("Video Settings", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.302f);
        gc.fillText("Audio Settings", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.352f);
        gc.fillText("Keybindings", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.402f);
        gc.fillText("Gameplay", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.452f);
        gc.fillText("Codex", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.502f);
        gc.fillText("Manual Save", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.552f);
        gc.fillText("Quit Game", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.602f);
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
        } else if (commandNum == 6) {
            gc.fillText(">", MainGame.SCREEN_HEIGHT * 0.122f, MainGame.SCREEN_HEIGHT * 0.602f);
        }
        gc.fillText("ESC to back", MainGame.SCREEN_WIDTH * 0.859, MainGame.SCREEN_HEIGHT * 0.925);
    }

    private void drawVideoSettings(GraphicsContext gc) {

    }

    private void drawAudioSettings(GraphicsContext gc) {

    }

    private void drawKeyBindings(GraphicsContext gc) {

    }

    private void drawGamePlaySettings(GraphicsContext gc) {

    }

    private void drawCodex(GraphicsContext gc, double startY) {
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBoldItalic15);
        double y = startY;
        gc.fillText("MOVEMENT: Use the W A S D keyboard keys to move up, left, down and right respectively.", MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * y);
        for (String string : ("""
                WINDOWS:
                "N" - to open the talent panel (explained in TalentPanel).
                "M" - to open the world map, scroll in and out with scroll wheel, drag it around with holding left mouse and recenter it on the player with "SPACEBAR".
                "C" - to open the character panel. There you see your characters stats and equipped items (explained in character panel).
                "B" - to open the bag panel. This hold all the items in your bag including miscellaneous items and equippable bags and consumables.""").split("\n"))
            gc.fillText(string, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y += 0.013));

        y += 0.02;
        for (String string : ("""
                MOUSE and ABILITIES:
                Click the mouse on the screen with either mouse button to activate the ability in the respective slot.
                The button to activate slots are indicated in the hot bar and but are from left to right: 1 numerical key, 2, 3,4,5 , left mouse button , right mouse button""").split("\n"))
            gc.fillText(string, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y += 0.013));

        y += 0.02;
        for (String string : ("""
                INVENTORY:
                Each inventory slot can only hold a specific piece of equipment as indicated by the letter.
                The letter always corresponds to the first letter of the item type e.g "H" for headslot (or Helm).
                "2" - is for twohanded, "O" for offhand and "W" for onehand weapons.
                On the bottom you can click between the different tabs inside the  character panel:
                  """).split("\n")) {
            gc.fillText(string, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y += 0.013));
        }

        y += 0.02;
        for (String string : ("""
                BAGS:
                Holds all your valuable items.
                There is a quick equip feature when you press shift and click on a item it automatically searches for the first possible slot.
                Also when you hold shift and hover over a item in your bag you get a comparison with the actively equipped item (should there be one)
                While looking at this comparison you can still shift click to equip the item get the new comparison in reverse order.
                Left is the equipped item on the right your bag""").split("\n")) {
            gc.fillText(string, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y += 0.013));
        }
        y += 0.02;
        for (String string : ("""
                STATS:
                    INT: Intelligence. Has the biggest impact on your maximum mana and a slight impact on mana regeneration.
                    WIS: Wisdom. Has the biggest impact on your mana regeneration and a slight impact on maximum mana.
                    VIT: vitality. Only base stat that increases your health.
                    AGI: Agility. Determines how fast you can move. Has a small impact on resist chance.
                    LUC: Luck. The only base stat that increases your chance for a critical hit. Has a small impact on getting better loot.
                    CHA: Charisma. Changes how NPC's like you and for how much you can sell and buy items from merchants.
                    STR: Strength. Impacts how much you can carry.""").split("\n")) {
            gc.fillText(string, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y += 0.013));
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
