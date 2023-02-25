package input;


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

public class InputHandler {
    public static InputHandler instance;
    public final Point lastMousePosition = new Point(500, 500);
    private final MainGame mg;
    private final Scene scene;
    //Keys
    public boolean upPressed;
    public boolean downPressed;
    public boolean rightPressed, ThreePressed, FourPressed, FivePressed, j_pressed, r_pressed, c_pressed, l_pressed, p_pressed, q_pressed, y_pressed;
    public boolean leftPressed;
    public boolean OnePressed;
    public boolean debugFps;
    public boolean multiplayer;
    public boolean f_pressed;
    public boolean TwoPressed;
    public boolean e_typed;
    public boolean X_pressed;
    public boolean mouse1Pressed, mouse2Pressed;
    public boolean shift_pressed;


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
        if (mg.gameState == State.TITLE) {
            if (code.equals(("w"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 3;
                }
            }
            if (code.equals(("s"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 3) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code.equals("\r")) {
                if (mg.ui.commandNum == 0) {
                    mg.gameState = State.PLAY;
                    mg.sound.INTRO.stop();
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
                    Platform.exit();
                    System.exit(0);
                }
            }
        } else if (mg.gameState == State.TITLE_OPTION) {
            if (code.equals(("w"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playSwitchSound();
                    mg.ui.commandNum--;
                    if (mg.ui.commandNum < 0) {
                        mg.ui.commandNum = 6;
                    }
                }
            }
            if (code.equals(("s"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playSwitchSound();
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
                mg.sound.playBackSound();
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
                    mg.sound.playSwitchSound();
                    mg.ui.commandNum--;
                    if (mg.ui.commandNum < 0) {
                        mg.ui.commandNum = 6;
                    }
                }
            }
            if (code.equals(("s"))) {
                if (!(mg.drawVideoSettings || mg.drawAudioSettings || mg.drawKeybindings || mg.drawGameplay || mg.drawCodex)) {
                    mg.sound.playSwitchSound();
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
                    mg.gameState = State.TITLE;
                    mg.sound.INTRO.seek(Duration.ZERO);
                    mg.sound.INTRO.setCycleCount(MediaPlayer.INDEFINITE);
                    mg.sound.INTRO.play();
                    mg.ui.commandNum = 0;
                }
            }
        }
        if (code.equals("\u001B")) {
            mg.sound.playBackSound();
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
        }
        //Drawing Inventor Panel
        if (code.equals("c")) {
            if (!mg.showChar) {
                mg.inventP.resetCharCollision();
                mg.showChar = true;
                scene.setCursor(Runner.selectCrosshair);
            } else {
                mg.inventP.hideCharCollision();
                mg.showChar = false;
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
        if (code.equals("e")) {
            e_typed = true;
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
        switch (code) {
            case A -> leftPressed = true;
            case C -> c_pressed = true;
            case D -> rightPressed = true;
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
            case S -> downPressed = true;
            case SHIFT -> shift_pressed = true;
            case W -> upPressed = true;
            case X -> X_pressed = true;
            case Y -> y_pressed = true;
            default -> {
            }
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        switch (code) {
            case A -> leftPressed = false;
            case C -> c_pressed = false;
            case D -> rightPressed = false;
            case DIGIT1 -> OnePressed = false;
            case DIGIT2 -> TwoPressed = false;
            case DIGIT3 -> ThreePressed = false;
            case DIGIT4 -> FourPressed = false;
            case DIGIT5 -> FivePressed = false;
            case F -> f_pressed = false;
            case H -> debugFps = false;
            case J -> j_pressed = false;
            case L -> l_pressed = false;
            case M -> multiplayer = false;
            case P -> p_pressed = false;
            case Q -> q_pressed = false;
            case R -> r_pressed = false;
            case S -> downPressed = false;
            case SHIFT -> shift_pressed = false;
            case W -> upPressed = false;
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
            if (mg.qPanel.expandButton.contains(mousePos)) {
                mg.qPanel.expanded = !mg.qPanel.expanded;
                return;
            }
            if (mg.showChar) {
                if (mg.inventP.combatStatsHitBox.contains(mousePos)) {
                    mg.inventP.showCombatStats = true;
                    return;
                }
                if (mg.inventP.effectsHitBox.contains(mousePos)) {
                    mg.inventP.showCombatStats = false;
                    return;
                }
            }
            if (mg.showBag && mg.inventP.bagEquipSlotsBox.contains(mousePos)) {
                mg.inventP.showBagEquipSlots = !mg.inventP.showBagEquipSlots;
                mg.inventP.bagPanelMover.y += mg.inventP.showBagEquipSlots ? -30 : 30;
                return;
            }
            if (mg.showBag && mg.inventP.bagSortButton.contains(mousePos)) {
                mg.inventP.sortBagsRarity();
                return;
            }
            if (mg.showChar && mg.inventP.secondPanelButton.contains(mousePos)) {
                mg.inventP.activeCharacterPanel = 2;
                return;
            }
            if (mg.showChar && mg.inventP.firstPanelButton.contains(mousePos)) {
                mg.inventP.activeCharacterPanel = 1;
                return;
            }
            if (mg.gameState == State.TITLE) {
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
                    return;
                }
            }
        }
        if (mg.sBar.characterBox.contains(mousePos)) {
            mg.showChar = true;
        } else if (mg.sBar.bagBox.contains(mousePos)) {
            mg.showBag = true;
        } else if (mg.sBar.skilltreeBox.contains(mousePos)) {
            mg.showTalents = true;
        } else if (mg.sBar.abilitiesBox.contains(mousePos)) {
            // do nothing
        } else if (mg.sBar.mapBox.contains(mousePos)) {
            mg.showMap = true;
        } else if (mg.sBar.settingsBox.contains(mousePos)) {
            mg.gameState = State.OPTION;
        }
    }


    public void handleScroll(ScrollEvent event) {
        if (mg.showMap) {
            mg.gameMap.zoom = (float) Math.max(3, Math.min((mg.gameMap.zoom + event.getDeltaY() / 80), 14));
        } else if (mg.drawCodex) {
            mg.ui.codex_scroll = Math.max(-1, Math.min((mg.ui.codex_scroll - event.getDeltaY() / 1_800), 0.322f));
        }
    }

    private boolean inventoryWindowOpen() {
        return mg.showChar || mg.showMap || mg.showTalents || mg.showBag || mg.gameState == State.OPTION;
    }
}
