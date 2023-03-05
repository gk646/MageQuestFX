package main.system.ui;

import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.MainGame;
import main.system.enums.State;

import java.awt.Rectangle;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UI {

    private final MainGame mg;
    public Font maruMonica;

    public Font maruMonica30;
    public int commandNum = 0;
    public final DropShadow shadow = new DropShadow(1, 1, 2, Color.BLACK);
    public final Rectangle discord_button = new Rectangle((int) (MainGame.SCREEN_WIDTH * 0.475), (int) (MainGame.SCREEN_HEIGHT * 0.803f), 42, 42);
    public final Rectangle github_button = new Rectangle((int) (MainGame.SCREEN_WIDTH * 0.505), (int) (MainGame.SCREEN_HEIGHT * 0.803f), 42, 42);

    private Image playerUI;
    private int loadingProgress = 0;
    public int saveMessageStage;
    public float credits_scroll = 0;
    public boolean drawSaveMessage;
    public double codex_scroll = 0.332;
    private final Image discord = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/discord.png")));
    private final Image discord1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/discord2.png")));
    private final Image discord2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/discord3.png")));
    private final Image github = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/github.png")));
    private final Image github1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/github2.png")));
    private final Image github2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/github3.png")));
    private final Image arrows = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/arrows.png")));
    private final Image wa1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/updown1.png")));
    private final Image wa2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/updown2.png")));
    private final Image wa3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/updown3.png")));
    private final Image wa4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/updown4.png")));
    private final Image enter1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/enter1.png")));
    private final Image enter2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/enter2.png")));
    private final Image enter3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/enter3.png")));
    private final Image enter4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/titleScreen/enter4.png")));
    final Light.Distant light = new Light.Distant();
    final Lighting lighting = new Lighting();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Image xpBar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/xpbar.png")));
    private int spriteCounter1 = 0;
    public float musicSlider = 100;
    public float ambientSlider = 75;
    public final Rectangle ambientSliderHitBox = new Rectangle((int) (650 + ambientSlider * 2 - 12), 553, 25, 30);
    public final Rectangle musicSliderHitBox = new Rectangle((int) (650 + musicSlider * 2 - 12), 333, 25, 30);
    public float effectsSlider = 75;
    public final Rectangle effectsSliderHitBox = new Rectangle((int) (650 + effectsSlider * 2 - 12), 443, 25, 30);


    public UI(MainGame mainGame) {
        this.mg = mainGame;
        getUIImage();
        light.setAzimuth(-135.0);
        lighting.setLight(light);
        lighting.setDiffuseConstant(1.9);
        lighting.setSurfaceScale(4);
    }

    public void draw(GraphicsContext gc) {
        if (mg.gameState == State.PLAY) {
            drawGameUI(gc);
        } else if (mg.gameState == State.OPTION || mg.gameState == State.TITLE_OPTION) {
            drawOptions(gc);
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
        } else if (mg.gameState == State.GAME_OVER) {
            drawGameOver(gc);
        } else if (mg.loadingScreen) {
            drawLoadingScreen(gc);
        }
        if (drawSaveMessage) {
            drawSaveMessage(gc);
        }
    }


    private void drawCredits(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold30);
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        gc.setFill(Colors.darkBackground);
        drawCenteredText(gc, "JavaFX ", credits_scroll + MainGame.SCREEN_HEIGHT);
        drawCenteredText(gc, "The next generation client application framework for desktop, mobile and embedded systems.", credits_scroll + MainGame.SCREEN_HEIGHT + 50);
        drawCenteredText(gc, "OpenJDK", credits_scroll + MainGame.SCREEN_HEIGHT + 100);
        drawCenteredText(gc, "The open source implementation of the Java Platform, Standard Edition (Java SE).", credits_scroll + MainGame.SCREEN_HEIGHT + 150);
        drawCenteredText(gc, "Map Editor", credits_scroll + MainGame.SCREEN_HEIGHT + 200);
        drawCenteredText(gc, "Tiled - Copyright © 2008-2021 Thorbjørn Lindeijer", credits_scroll + MainGame.SCREEN_HEIGHT + 250);
        drawCenteredText(gc, "TEXTURES", credits_scroll + MainGame.SCREEN_HEIGHT + 300);
        drawCenteredText(gc, "Character textures: craftpix.net / Full copyright with their Freebie License", credits_scroll + MainGame.SCREEN_HEIGHT + 350);
        drawCenteredText(gc, "Effect textures from: craftpix.net / Full copyright with their Freebie License", credits_scroll + MainGame.SCREEN_HEIGHT + 400);
        drawCenteredText(gc, "Skilltree design inspired by the Minecraft mod: Craft to Exile [Dissonance] / made by mahjerion", credits_scroll + MainGame.SCREEN_HEIGHT + 450);
        drawCenteredText(gc, "Item icons: DALL·E  / Image generation AI by OpenAI", credits_scroll + MainGame.SCREEN_HEIGHT + 500);
        drawCenteredText(gc, "SOUNDS", credits_scroll + MainGame.SCREEN_HEIGHT + 550);
        drawCenteredText(gc, "item equip sound:  https://freesound.org/people/1bob/sounds/651515/", credits_scroll - 600);
        drawCenteredText(gc, "unfa's Laser Weapon Sounds  https://freesound.org/s/187119/", credits_scroll + MainGame.SCREEN_HEIGHT + 650);
        drawCenteredText(gc, "Various soundeffects / public domain from freesound.org", credits_scroll + MainGame.SCREEN_HEIGHT + 650);
        drawCenteredText(gc, "item equip sound:  https://freesound.org/people/1bob/sounds/651515/", credits_scroll + MainGame.SCREEN_HEIGHT + 700);
        drawCenteredText(gc, "DATABASE", credits_scroll + MainGame.SCREEN_HEIGHT + 750);
        drawCenteredText(gc, "SQLite JDBC driver 3.4 - developed by Xerial", credits_scroll + MainGame.SCREEN_HEIGHT + 800);
        drawCenteredText(gc, "SQLite database - developed by D. Richard Hipp", credits_scroll + MainGame.SCREEN_HEIGHT + 850);
        drawCenteredText(gc, "", credits_scroll - 850);
        drawCenteredText(gc, "", credits_scroll - 900);
        drawCenteredText(gc, "", credits_scroll - 950);
        drawCenteredText(gc, "", credits_scroll - 1_000);
        drawCenteredText(gc, "", credits_scroll - 1_050);
        drawCenteredText(gc, "", credits_scroll - 1_100);
        drawCenteredText(gc, "", credits_scroll - 1_150);


        credits_scroll -= 0.25;
        gc.fillText("ESC to back", MainGame.SCREEN_WIDTH * 0.859, MainGame.SCREEN_HEIGHT * 0.925);
    }

    private void drawTitleScreen(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold50);
        String text = "Mage Quest ";
        int x;
        int y = (int) (MainGame.SCREEN_HEIGHT * 0.044f) * 3;
        //FILL BACKGROUND WITH COLOR
        gc.setFill(Colors.LightGrey);
        gc.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        //DRAW TEXT
        gc.setFill(Colors.darkBackground);
        drawCenteredText(gc, text, y);
        //MENU
        gc.setFont(FonT.minecraftBold30);
        text = "START GAME";
        x = (int) (MainGame.SCREEN_WIDTH * 0.448_8f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.355f);
        drawCenteredText(gc, text, y);
        if (commandNum == 0) {
            gc.fillText(">", x - 25, y);
        }
        text = "SETTINGS";
        x = (int) (MainGame.SCREEN_WIDTH * 0.458f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.415f);
        drawCenteredText(gc, text, y);
        if (commandNum == 1) {
            gc.fillText(">", x - 25, y);
        }
        text = "CREDITS";
        x = (int) (MainGame.SCREEN_WIDTH * 0.465_1f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.475f);
        drawCenteredText(gc, text, y);
        if (commandNum == 2) {
            gc.fillText(">", x - 25, y);
        }

        text = "QUIT";
        x = (int) (MainGame.SCREEN_WIDTH * 0.481f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.53f);
        drawCenteredText(gc, text, y);
        if (commandNum == 3) {
            gc.fillText(">", x - 25, y);
        }
        text = "Select";
        x = (int) (MainGame.SCREEN_WIDTH - (MainGame.SCREEN_WIDTH * 0.137f));
        y = (int) (MainGame.SCREEN_HEIGHT * 0.903f);
        gc.fillText(text, x, y);

        text = "v4.0.0";
        x = (int) (MainGame.SCREEN_HEIGHT * 0.138f);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.903f);
        gc.fillText(text, x, y);
        y = (int) (MainGame.SCREEN_HEIGHT * 0.903f);
        x = (int) (MainGame.SCREEN_WIDTH * 0.415f);
        text = "\u00A9 2023 Lukas Gilch";
        gc.fillText(text, x, y);
        switch (spriteCounter1 % 200 / 50) {
            case 0 -> gc.drawImage(discord, (MainGame.SCREEN_WIDTH * 0.475f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 1 -> gc.drawImage(discord1, (MainGame.SCREEN_WIDTH * 0.475f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 2 -> gc.drawImage(discord2, (MainGame.SCREEN_WIDTH * 0.475f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 3 -> gc.drawImage(discord1, (MainGame.SCREEN_WIDTH * 0.475f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
        }
        switch (spriteCounter1 % 200 / 50) {
            case 0 -> gc.drawImage(github, (MainGame.SCREEN_WIDTH * 0.510f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 1 -> gc.drawImage(github1, (MainGame.SCREEN_WIDTH * 0.510f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 2 -> gc.drawImage(github2, (MainGame.SCREEN_WIDTH * 0.510f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
            case 3 -> gc.drawImage(github1, (MainGame.SCREEN_WIDTH * 0.510f), (int) (MainGame.SCREEN_HEIGHT * 0.803f));
        }
        switch (spriteCounter1 % 120 / 30) {
            case 0 -> gc.drawImage(wa1, 1_584, 930);
            case 1 -> gc.drawImage(wa2, 1_584, 930);
            case 2 -> gc.drawImage(wa3, 1_584, 930);
            case 3 -> gc.drawImage(wa4, 1_584, 930);
        }
        switch (spriteCounter1 % 120 / 30) {
            case 0 -> gc.drawImage(enter1, 1_785, 945);
            case 1 -> gc.drawImage(enter2, 1_785, 945);
            case 2 -> gc.drawImage(enter3, 1_785, 945);
            case 3 -> gc.drawImage(enter4, 1_785, 945);
        }
        gc.drawImage(arrows, 1_490, 950);
        spriteCounter1++;
    }

    private void drawGameUI(GraphicsContext gc) {
        gc.setEffect(lighting);
        gc.setFill(Colors.Red);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.063_5f, 68, Math.min((int) ((mg.player.health / mg.player.maxHealth) * 225), 225), 10);
        gc.setFill(Colors.Blue);
        gc.fillRect(MainGame.SCREEN_WIDTH * 0.063_5f, 96, Math.min((int) ((mg.player.getMana() / mg.player.maxMana) * 162), 162), 10);
        gc.setEffect(null);
        gc.drawImage(playerUI, 40, 40, 330, 150);
        gc.setFill(Color.WHITE);
        gc.setFont(FonT.editUndo18);
        gc.setEffect(shadow);
        gc.fillText((int) mg.player.health + "/" + mg.player.maxHealth, 199, 72);
        gc.fillText((int) mg.player.getMana() + "/" + mg.player.maxMana, 173, 97);
        gc.setEffect(null);
        gc.setFill(Colors.lightGreyMiddleAlpha);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.567f, MainGame.SCREEN_HEIGHT * 0.908f, 700, 12);
        gc.setFill(Colors.xpbarOrange);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.567f, MainGame.SCREEN_HEIGHT * 0.908f, (mg.player.experience / (float) mg.player.levelUpExperience) * 700, 12);
        gc.drawImage(xpBar, MainGame.SCREEN_HEIGHT * 0.564f, MainGame.SCREEN_HEIGHT * 0.908f);
        gc.setFill(Colors.white);
        gc.setFont(FonT.varnished16);
        gc.fillText(LocalTime.now().format(formatter), 1860, 23);
        gc.setFont(FonT.antParty20);
        gc.fillText(WorldController.currentWorld.toString(), 1700, 25);
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
        gc.fillText("Back to Main Menu", MainGame.SCREEN_HEIGHT * 0.152f, MainGame.SCREEN_HEIGHT * 0.602f);

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
        gc.fillText("120 FPS - locked", 450, 250);
    }

    private void drawAudioSettings(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold20);
        gc.setFill(Colors.darkBackground);
        gc.fillText("Music volume", 600, 300);
        gc.fillText("FX volume", 600, 410);
        gc.fillText("Ambience Volume", 600, 520);
        gc.fillRoundRect(650, 340, 200, 15, 15, 15);
        gc.fillRoundRect(650, 450, 200, 15, 15, 15);
        gc.fillRoundRect(650, 560, 200, 15, 15, 15);
        gc.fillText(String.valueOf((int) musicSlider), 900, 355);
        gc.fillText(String.valueOf((int) effectsSlider), 900, 465);
        gc.fillText(String.valueOf((int) ambientSlider), 900, 575);
        gc.setFill(Colors.mediumLightGrey);
        gc.fillRoundRect(650 + musicSlider * 2 - 12, 333, 25, 30, 15, 15);
        gc.fillRoundRect(650 + effectsSlider * 2 - 12, 443, 25, 30, 15, 15);
        gc.fillRoundRect(650 + ambientSlider * 2 - 12, 553, 25, 30, 15, 15);
    }

    private void drawKeyBindings(GraphicsContext gc) {
        String text = "C - open character window";
        float y = 0.332f;
        gc.fillText(text, MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * (y + 0.013));
    }

    private void drawGamePlaySettings(GraphicsContext gc) {
        gc.fillText("draw skillbar ", 450, 240);
    }

    private void drawCodex(GraphicsContext gc, double startY) {
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBoldItalic15);
        double y = startY;
        gc.fillText("MOVEMENT: Use the W A S D keyboard keys to move up, left, down and right respectively.", MainGame.SCREEN_HEIGHT * 0.452f, MainGame.SCREEN_HEIGHT * y);
        for (String string : ("""
                WINDOWS:
                "N" - to open the talent panel (explained in TalentPanel).
                "M" - to open the world zone, scroll in and out with scroll wheel, drag it around with holding left mouse and recenter it on the player with "SPACEBAR".
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
        gc.setFont(FonT.minecraftBold30);
        gc.setFill(Colors.darkBackground);
        if (saveMessageStage < 400) {
            int numDots = (saveMessageStage / 40) % 4;
            gc.fillText("Saving Game" + ".".repeat(Math.max(0, numDots)), MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
        }
        if (saveMessageStage >= 400) {
            gc.fillText("Game Saved!", MainGame.SCREEN_HEIGHT * 1.4, MainGame.SCREEN_HEIGHT * 0.8f);
            if (saveMessageStage >= 760) {
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

    private void drawCenteredText(GraphicsContext gc, String text, float y) {
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double x = (gc.getCanvas().getWidth() - textWidth) / 2;
        gc.fillText(text, x, y);
    }

    private Image setup() {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/" + "player_ui3.png"))));
    }
}
