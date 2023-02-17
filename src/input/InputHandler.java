package input;


import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import main.MainGame;
import main.Runner;
import main.system.enums.State;
import main.system.enums.Zone;

import java.awt.Point;

public class InputHandler {
    public static InputHandler instance;
    public final Point lastMousePosition = new Point(500, 500);
    private final MainGame mg;
    private final Scene scene;
    //Keys
    public boolean upPressed;
    public boolean downPressed;
    public boolean rightPressed;
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
                    scene.setCursor(Cursor.CROSSHAIR);
                } else if (mg.ui.commandNum == 1) {
                    mg.gameState = State.TITLE_OPTION;
                    mg.ui.commandNum = 0;
                } else if (mg.ui.commandNum == 2) {
                    mg.credits = true;
                } else if (mg.ui.commandNum == 3) {
                    Platform.exit();
                    System.exit(0);
                }
            }
        }
        if (mg.gameState == State.TITLE_OPTION) {
            if (code.equals(("w"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 6;
                }
            }
            if (code.equals(("s"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 6) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code.equals("\r")) {
                if (mg.ui.commandNum == 0) {
                } else if (mg.ui.commandNum == 4) {
                    mg.drawCodex = true;
                } else if (mg.ui.commandNum == 5) {
                    mg.sqLite.saveGame();
                } else if (mg.ui.commandNum == 6) {
                    mg.sqLite.saveGame();
                    Platform.exit();
                    System.exit(0);
                }
            }
            if (code.equals("\u001B")) {
                mg.sound.playBackSound();
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
                    mg.gameState = State.TITLE;
                    mg.ui.commandNum = 1;
                }
            }
        }
        if (mg.gameState == State.OPTION) {
            if (code.equals(("w"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 6;
                }
            }
            if (code.equals(("s"))) {
                mg.sound.playSwitchSound();
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 6) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code.equals("\r")) {
                if (mg.ui.commandNum == 0) {
                } else if (mg.ui.commandNum == 3) {
                    mg.wControl.loadMap(Zone.GrassLands, 495, 495);
                } else if (mg.ui.commandNum == 4) {
                    mg.drawCodex = true;
                } else if (mg.ui.commandNum == 5) {
                    mg.sqLite.saveGame();
                } else if (mg.ui.commandNum == 6) {
                    mg.sqLite.saveGameAndExit();
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
                scene.setCursor(Cursor.HAND);
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
                scene.setCursor(Runner.crosshair3);
            } else {
                mg.showTalents = false;
                mg.talentP.hideTalentCollision();
                if (!mg.showBag || !mg.showTalents) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        //Drawing Inventor Panel
        if (code.equals("c")) {
            if (!mg.showChar) {
                mg.inventP.resetCharCollision();
                mg.showChar = true;
                scene.setCursor(Cursor.HAND);
            } else {
                mg.inventP.hideCharCollision();
                mg.showChar = false;
                if (!mg.showBag || !mg.showTalents) {
                    scene.setCursor(Runner.crosshair);
                }
            }
        }
        //Drawing bag panel
        if (code.equals("b")) {
            if (!mg.showBag) {
                mg.inventP.resetBagCollision();
                mg.showBag = true;
                scene.setCursor(Runner.crosshair3);
            } else {
                mg.inventP.hideBagCollision();
                mg.showBag = false;
                scene.setCursor(Runner.crosshair);
            }
        }
        if (code.equals("m")) {
            if (!mg.showMap) {
                mg.gameMap.resetMapCollision();
                mg.showMap = true;
                scene.setCursor(Cursor.HAND);
            } else {
                mg.gameMap.hideMapCollision();
                mg.showMap = false;
                if (!mg.showBag || mg.showChar || !mg.showTalents) {
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
        if (mg.gameState == State.PLAY) {
            //Player Controls
            if (code == KeyCode.W) {
                upPressed = true;
            }
            if (code == KeyCode.SHIFT) {
                shift_pressed = true;
            }
            if (code == KeyCode.A) {
                leftPressed = true;
            }
            if (code == KeyCode.S) {
                downPressed = true;
            }
            if (code == KeyCode.D) {
                rightPressed = true;
            }
            if (code == KeyCode.M) {
                multiplayer = true;
            }
            if (code == KeyCode.DIGIT1) {
                OnePressed = true;
            }
            if (code == KeyCode.DIGIT2) {
                TwoPressed = true;
            }
            if (code == KeyCode.F) {
                f_pressed = true;
            }
            if (code == KeyCode.X) {
                X_pressed = true;
            }
        }
        if (code == KeyCode.H) {
            debugFps = true;
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if (mg.gameState == State.PLAY) {
            if (code == KeyCode.W) {
                upPressed = false;
            } else if (code == KeyCode.A) {
                leftPressed = false;
            } else if (code == KeyCode.S) {
                downPressed = false;
            } else if (code == KeyCode.D) {
                rightPressed = false;
            }
            if (code == KeyCode.SHIFT) {
                shift_pressed = false;
            }
            if (code == KeyCode.DIGIT1) {
                OnePressed = false;
            }
            if (code == KeyCode.DIGIT2) {
                TwoPressed = false;
            }
            if (code == KeyCode.X) {
                X_pressed = false;
            }
        }
        if (code == KeyCode.H) {
            debugFps = false;
            mg.player.mana = mg.player.maxMana;
        }
        if (code == KeyCode.F) {
            f_pressed = false;
        }
        if (code == KeyCode.M) {
            multiplayer = false;
        }
    }

    public void handleMouseMovement(MouseEvent event) {
        lastMousePosition.x = (int) event.getX();
        lastMousePosition.y = (int) event.getY();
    }

    public void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (mg.qPanel.expandButton.contains(lastMousePosition)) {
                mg.qPanel.expanded = !mg.qPanel.expanded;
            } else if (mg.showChar) {
                if (mg.inventP.combatStatsHitBox.contains(lastMousePosition)) {
                    mg.inventP.showCombatStats = true;
                } else if (mg.inventP.effectsHitBox.contains(lastMousePosition)) {
                    mg.inventP.showCombatStats = false;
                }
            }
            if (mg.showBag && mg.inventP.bagEquipSlotsBox.contains(mg.inputH.lastMousePosition)) {
                mg.inventP.showBagEquipSlots = !mg.inventP.showBagEquipSlots;
                if (mg.inventP.showBagEquipSlots) {
                    mg.inventP.bagPanelMover.y -= 30;
                } else {
                    mg.inventP.bagPanelMover.y += 30;
                }
            } else if (mg.showBag && mg.inventP.bagSortButton.contains(mg.inputH.lastMousePosition)) {
                mg.inventP.sortBagsRarity();
            }
            if (mg.showChar && mg.inventP.secondPanelButton.contains(mg.inputH.lastMousePosition)) {
                mg.inventP.activeCharacterPanel = 2;
            } else if (mg.showChar && mg.inventP.firstPanelButton.contains(mg.inputH.lastMousePosition)) {
                mg.inventP.activeCharacterPanel = 1;
            }
        }
    }

    public void handleScroll(ScrollEvent event) {
        if (mg.showMap) {
            mg.gameMap.zoom = (float) Math.max(3, Math.min((mg.gameMap.zoom + event.getDeltaY() / 80), 14));
        } else if (mg.drawCodex) {

            mg.ui.codex_scroll = Math.max(-1, Math.min((mg.ui.codex_scroll - event.getDeltaY() / 1_800), 0.322f));
        }
    }
}
