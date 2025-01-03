/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package input;


import gameworld.entities.npcs.quests.NPC_Nietzsche;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.MainGame;
import main.Runner;
import main.system.enums.State;

import java.awt.Desktop;
import java.awt.Point;
import java.net.URI;
import java.sql.SQLException;

public class InputHandler {
    public static InputHandler instance;
    public final Point lastMousePosition = new Point(500, 500);
    private final MainGame mg;
    private final Scene scene;
    //Keys
    public boolean upPressed;
    public boolean downPressed;
    public boolean rightPressed;
    public boolean ThreePressed;
    public boolean FourPressed;
    public boolean FivePressed;
    private boolean j_pressed;
    private boolean r_pressed;
    private boolean c_pressed;
    private boolean l_pressed;
    private boolean p_pressed;
    public boolean q_pressed;
    private boolean y_pressed;
    public boolean leftPressed;
    public static boolean q_typed, f_typed;
    public boolean OnePressed;
    public boolean debugFps;
    private boolean p_typed;
    public boolean multiplayer;
    public boolean f_pressed;
    public boolean TwoPressed;
    public boolean e_typed;
    public boolean X_pressed;
    public boolean mouse1Pressed, mouse2Pressed;
    public boolean shift_pressed;
    private String lastTypedString;

    public InputHandler(MainGame mg, Scene scene) {
        this.mg = mg;
        this.scene = scene;
    }

    public void handleMousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            mouse1Pressed = true;
        }
        if (e.getButton() == MouseButton.SECONDARY) {
            mouse2Pressed = true;
        }
    }

    public void handleMouseReleased(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            mouse1Pressed = false;
        }
        if (e.getButton() == MouseButton.SECONDARY) {
            mouse2Pressed = false;
        }
    }

    public void handleKeyType(KeyEvent e) {
        String code = e.getCharacter();
        if (NPC_Nietzsche.typeMode) {
            if (code.equals("\u001B")) {
                NPC_Nietzsche.typeMode = false;
                NPC_Nietzsche.prompt = new StringBuilder();
                return;
            } else if (code.equals("\r")) {
                NPC_Nietzsche.generateResponse = true;
            } else if (code.equals("\b")) {
                NPC_Nietzsche.prompt.deleteCharAt(NPC_Nietzsche.prompt.length() - 1);
                return;
            }
            NPC_Nietzsche.prompt.append(e.getCharacter());
            return;
        }
        lastTypedString = e.getCharacter();
        if (mg.gameState == State.TITLE) {
            if (code.equals(("w"))) {
                mg.sound.playEffectSound(2);
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 3;
                }
            }
            if (code.equals(("s"))) {
                mg.sound.playEffectSound(2);
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 3) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code.equals("\r")) {
                if (mg.drawKeybindings) {
                    mg.gameState = State.PLAY;
                    mg.sound.fadeOut(mg.sound.INTRO, 1, 6);
                    mg.loadGameState.loadSpawnLevel();
                    mg.drawKeybindings = false;
                    mg.ui.sawKeyBindings = true;
                    try {
                        mg.sqLite.savePlayerStats();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    return;
                } else if (mg.ui.commandNum == 0) {
                    mg.drawKeybindings = true;
                    if (mg.ui.sawKeyBindings) {
                        mg.gameState = State.PLAY;
                        mg.sound.fadeOut(mg.sound.INTRO, 1, 6);
                        mg.loadGameState.loadSpawnLevel();
                        mg.drawKeybindings = false;
                        mg.ui.sawKeyBindings = true;
                    }
                    scene.setCursor(Runner.crosshair);
                    return;
                } else if (mg.ui.commandNum == 1) {
                    mg.gameState = State.TITLE_OPTION;
                    mg.ui.commandNum = 0;
                    return;
                } else if (mg.ui.commandNum == 2) {
                    mg.credits = true;
                    return;
                } else if (mg.ui.commandNum == 3) {
                    mg.sqLite.saveGameAndExit();
                }
            }
        } else if (mg.gameState == State.TITLE_OPTION) {
            if (code.equals(("w"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playEffectSound(2);
                    mg.ui.commandNum--;
                    if (mg.ui.commandNum < 0) {
                        mg.ui.commandNum = 6;
                    }
                }
            }
            if (code.equals(("s"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playEffectSound(2);
                    mg.ui.commandNum++;
                    if (mg.ui.commandNum > 6) {
                        mg.ui.commandNum = 0;
                    }
                }
            }
            if (code.equals("\r")) {
                if (mg.ui.commandNum == 0) {
                    mg.drawVideoSettings = true;
                    return;
                } else if (mg.ui.commandNum == 1) {
                    mg.drawAudioSettings = true;
                    return;
                } else if (mg.ui.commandNum == 2) {
                    mg.drawKeybindings = true;
                    return;
                } else if (mg.ui.commandNum == 3) {
                    mg.drawGameplay = true;
                    return;
                } else if (mg.ui.commandNum == 4) {
                    mg.drawCodex = true;
                    return;
                } else if (mg.ui.commandNum == 5) {
                    mg.sqLite.saveGame();
                    return;
                } else if (mg.ui.commandNum == 6) {
                    mg.sqLite.saveGame();
                    Platform.exit();
                    System.exit(0);
                }
            }
            if (code.equals("\u001B")) {
                mg.sound.playEffectSound(3);
                if (mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex) {
                    mg.drawVideoSettings = false;
                    mg.drawAudioSettings = false;
                    mg.drawKeybindings = false;
                    mg.drawGameplay = false;
                    mg.drawCodex = false;
                } else {
                    mg.gameState = State.TITLE;
                    mg.ui.commandNum = 1;
                }
            }
        } else if (mg.gameState == State.OPTION) {
            if (code.equals(("w"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playEffectSound(2);
                    mg.ui.commandNum--;
                    if (mg.ui.commandNum < 0) {
                        mg.ui.commandNum = 6;
                    }
                }
            }
            if (code.equals(("s"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playEffectSound(2);
                    mg.ui.commandNum++;
                    if (mg.ui.commandNum > 6) {
                        mg.ui.commandNum = 0;
                    }
                }
            }
            if (code.equals("\r")) {
                if (mg.ui.commandNum == 0) {
                    mg.drawVideoSettings = true;
                    return;
                } else if (mg.ui.commandNum == 1) {
                    mg.drawAudioSettings = true;
                    return;
                } else if (mg.ui.commandNum == 2) {
                    mg.drawKeybindings = true;
                    return;
                } else if (mg.ui.commandNum == 3) {
                    mg.drawGameplay = true;
                    return;
                } else if (mg.ui.commandNum == 4) {
                    mg.drawCodex = true;
                    return;
                } else if (mg.ui.commandNum == 5) {
                    mg.sqLite.saveGame();
                    return;
                } else if (mg.ui.commandNum == 6) {
                    mg.gameState = State.TITLE;
                    mg.sound.INTRO.seek(Duration.ZERO);
                    mg.sound.INTRO.setCycleCount(MediaPlayer.INDEFINITE);
                    mg.sound.INTRO.setVolume(mg.ui.musicSlider);
                    mg.sound.INTRO.play();
                    mg.ui.commandNum = 0;
                    return;
                }
            }
        }
        if (code.equals("\u001B")) {

            mg.sound.playEffectSound(3);
            if (mg.gameState == State.OPTION) {
                if (mg.drawCodex) {
                    mg.drawCodex = false;
                    return;
                } else if (mg.drawAudioSettings) {
                    mg.drawAudioSettings = false;
                    return;
                } else if (mg.drawKeybindings) {
                    mg.drawKeybindings = false;
                } else if (mg.drawGameplay) {
                    mg.drawGameplay = false;
                } else if (mg.drawVideoSettings) {
                    mg.drawVideoSettings = false;
                } else {
                    scene.setCursor(Runner.crosshair);
                    mg.gameState = State.PLAY;
                }
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (mg.gameState == State.PLAY) {
                scene.setCursor(Runner.selectCrosshair);
                mg.gameState = State.OPTION;
                leftPressed = false;
                upPressed = false;
                rightPressed = false;
                downPressed = false;
                mg.showBag = false;
                mg.showChar = false;

                mg.showMap = false;
                mg.showTalents = false;
                mg.showAbilities = false;
                mg.inventP.hideBagCollision();
                mg.inventP.hideCharCollision();
                mg.skillPanel.hideSkillPanelCollision();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (mg.credits) {
                mg.credits = false;
                mg.ui.credits_scroll = 0;
            }
        }
        //Drawing talent window
        if (code.equals("n")) {
            if (!mg.showTalents) {
                mg.showTalents = true;
                mg.talentP.resetTalentCollision();
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.showTalents = false;
                mg.talentP.hideTalentCollision();
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        } else if (code.equals("p")) {
            if (!mg.showAbilities) {
                mg.showAbilities = true;
                mg.skillPanel.resetSkillPanelCollision();
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.showAbilities = false;
                mg.skillPanel.hideSkillPanelCollision();
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        //Drawing Inventor Panel
        if (code.equals("c")) {
            if (!mg.showChar) {
                mg.inventP.resetCharCollision();
                mg.showChar = true;
                mg.sound.playEffectSound(8);
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.inventP.hideCharCollision();
                mg.showChar = false;
                mg.sound.playEffectSound(12);
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        //Drawing bag panel
        if (code.equals("b")) {
            if (!mg.showBag) {
                mg.inventP.resetBagCollision();
                mg.showBag = true;
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.inventP.hideBagCollision();
                mg.showBag = false;
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        if (code.equals("m")) {
            if (!mg.showMap) {
                mg.gameMap.resetMapCollision();
                mg.showMap = true;
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.gameMap.hideMapCollision();
                mg.showMap = false;
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        if (code.equals("j")) {
            if (!mg.showJournal) {
                mg.qPanel.resetJournalCollision();
                mg.showJournal = true;
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.qPanel.hideJournalCollision();
                mg.showJournal = false;
                if (!inventoryWindowOpen()) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        switch (code) {
            case "e" -> e_typed = true;
            case "q" -> q_typed = true;
            case "f" -> f_typed = true;
        }
        if (code.equals("\s")) {
            if (mg.showTalents) {
                mg.talentP.talentPanelX = 960 - 16;
                mg.talentP.talentPanelY = 540 - 16;
            }
            if (mg.showMap) {
                mg.gameMap.xTile = mg.playerX;
                mg.gameMap.yTile = mg.playerY;
            }
        }
    }

    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if (!NPC_Nietzsche.typeMode) {
            switch (code) {
                case A, LEFT -> leftPressed = true;
                case UP, W -> upPressed = true;
                case DOWN, S -> downPressed = true;
                case RIGHT, D -> rightPressed = true;
                case C -> c_pressed = true;
                case DIGIT1 -> OnePressed = true;
                case DIGIT2 -> TwoPressed = true;
                case DIGIT3 -> ThreePressed = true;
                case DIGIT4 -> FourPressed = true;
                case DIGIT5 -> FivePressed = true;
                case F -> f_pressed = true;
                case H -> debugFps = true;
                case J -> j_pressed = true;
                case L -> l_pressed = true;
                case M -> multiplayer = true;
                case P -> p_pressed = true;
                case Q -> q_pressed = true;
                case R -> r_pressed = true;
                case SHIFT -> shift_pressed = true;
                case X -> X_pressed = true;
                case Y -> y_pressed = true;
                default -> {
                }
            }
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case A, LEFT -> leftPressed = false;
            case UP, W -> upPressed = false;
            case DOWN, S -> downPressed = false;
            case RIGHT, D -> rightPressed = false;
            case C -> c_pressed = false;
            case DIGIT1 -> OnePressed = false;
            case DIGIT2 -> TwoPressed = false;
            case DIGIT3 -> ThreePressed = false;
            case DIGIT4 -> FourPressed = false;
            case DIGIT5 -> FivePressed = false;
            case E -> e_typed = false;
            case F -> {
                f_pressed = false;
                f_typed = false;
            }
            case H -> debugFps = false;
            case J -> j_pressed = false;
            case L -> l_pressed = false;
            case M -> multiplayer = false;
            case P -> {
                p_pressed = false;
                p_typed = false;
            }
            case Q -> {
                q_pressed = false;
                q_typed = false;
            }
            case R -> r_pressed = false;
            case SHIFT -> shift_pressed = false;
            case X -> X_pressed = false;
            case Y -> y_pressed = false;
            default -> {
            }
        }
    }


    public void handleMouseMovement(MouseEvent event) {
        lastMousePosition.x = (int) event.getX();
        lastMousePosition.y = (int) event.getY();
    }

    public void handleMouseClick(MouseEvent event) {
        Point mousePos = lastMousePosition;
        if (event.getButton() == MouseButton.PRIMARY) {
            if (mg.gameState == State.PLAY) {
                if (mg.inventP.combatStatsHitBox.contains(mousePos)) {
                    mg.inventP.showCombatStats = true;
                } else if (mg.inventP.effectsHitBox.contains(mousePos)) {
                    mg.inventP.showCombatStats = false;
                } else if (mg.showBag && mg.inventP.bagEquipSlotsBox.contains(mousePos)) {
                    mg.inventP.showBagEquipSlots = !mg.inventP.showBagEquipSlots;
                    mg.inventP.bagPanelMover.y += mg.inventP.showBagEquipSlots ? -30 : 30;
                } else if (mg.showBag && mg.inventP.bagSortButton.contains(mousePos)) {
                    mg.inventP.sortBagsRarity();
                } else if (mg.showChar && mg.inventP.secondPanelButton.contains(mousePos)) {
                    mg.inventP.activeCharacterPanel = 2;
                } else if (mg.showChar && mg.inventP.firstPanelButton.contains(mousePos)) {
                    mg.inventP.activeCharacterPanel = 1;
                } else if (mg.sBar.characterBox.contains(mousePos)) {
                    if (!mg.showChar) {
                        mg.showChar = true;
                        mg.sound.playEffectSound(8);
                        mg.inventP.resetCharCollision();
                        scene.setCursor(Runner.selectCrosshair);
                    } else {
                        mg.showChar = false;
                        mg.sound.playEffectSound(12);
                        mg.inventP.hideCharCollision();
                        if (!inventoryWindowOpen()) {
                            scene.setCursor(Runner.crosshair);
                        }
                    }
                } else if (mg.sBar.bagBox.contains(mousePos)) {
                    if (!mg.showBag) {
                        mg.showBag = true;
                        mg.inventP.resetBagCollision();
                        scene.setCursor(Runner.selectCrosshair);
                    } else {
                        mg.showBag = false;
                        mg.inventP.hideBagCollision();
                        if (!inventoryWindowOpen()) {
                            scene.setCursor(Runner.crosshair);
                        }
                    }
                } else if (mg.sBar.skilltreeBox.contains(mousePos)) {
                    mg.showTalents = true;
                    scene.setCursor(Runner.selectCrosshair);
                } else if (mg.sBar.abilitiesBox.contains(mousePos)) {
                    if (!mg.showAbilities) {
                        mg.showAbilities = true;
                        mg.skillPanel.resetSkillPanelCollision();
                        scene.setCursor(Runner.selectCrosshair);
                    } else {
                        mg.showAbilities = false;
                        mg.skillPanel.hideSkillPanelCollision();
                        if (!inventoryWindowOpen()) {
                            scene.setCursor(Runner.crosshair);
                        }
                    }
                } else if (mg.sBar.mapBox.contains(mousePos)) {
                    if (!mg.showMap) {
                        mg.gameMap.resetMapCollision();
                        mg.showMap = true;
                        scene.setCursor(Runner.selectCrosshair);
                    } else {
                        mg.gameMap.hideMapCollision();
                        mg.showMap = false;
                        if (!inventoryWindowOpen()) {
                            scene.setCursor(Runner.crosshair);
                        }
                    }
                } else if (mg.sBar.settingsBox.contains(mousePos)) {
                    mg.gameState = State.OPTION;
                    leftPressed = false;
                    upPressed = false;
                    rightPressed = false;
                    downPressed = false;
                    mg.showBag = false;
                    mg.showChar = false;
                    mg.showMap = false;
                    mg.showTalents = false;
                    mg.showAbilities = false;
                    mg.inventP.hideBagCollision();
                    mg.inventP.hideCharCollision();
                    mg.skillPanel.hideSkillPanelCollision();
                } else if (mg.inventP.activeTradingNPC != null && mg.inventP.activeTradingNPC.firstWindow.contains(mousePos)) {
                    mg.inventP.activeTradingNPC.show_trade = true;
                    mg.inventP.activeTradingNPC.show_buyback = false;
                } else if (mg.inventP.activeTradingNPC != null && mg.inventP.activeTradingNPC.secondWindow.contains(mousePos)) {
                    mg.inventP.activeTradingNPC.show_trade = false;
                    mg.inventP.activeTradingNPC.show_buyback = true;
                }
            } else if (mg.gameState == State.TITLE) {
                if (mg.ui.discord_button.contains(mousePos)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/STCdEcBzUv"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (mg.ui.github_button.contains(mousePos)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/gk646/MageQuestFX"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void handleScroll(ScrollEvent event) {
        if (mg.showMap) {
            mg.gameMap.zoom = (float) Math.max(3, Math.min((mg.gameMap.zoom + event.getDeltaY() / 80), 14));
        } else if (mg.drawCodex) {
            mg.ui.codex_scroll = Math.max(-1, Math.min((mg.ui.codex_scroll - event.getDeltaY() / 1_800), 0.322f));
        } else if (mg.showJournal && mg.qPanel.leftSide.contains(lastMousePosition)) {
            mg.qPanel.scroll = Math.min(mg.qPanel.scroll - event.getDeltaY() / 3, 280);
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean inventoryWindowOpen() {
        return mg.showChar || mg.showMap || mg.showTalents || mg.showBag || mg.gameState == State.OPTION || mg.showAbilities || mg.showJournal;
    }
}
