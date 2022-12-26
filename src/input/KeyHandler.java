package input;

import main.MainGame;

import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import static main.Runner.window;

public class KeyHandler implements KeyListener {
    private final MainGame mg;
    //Keys
    public boolean upPressed, downPressed, rightPressed, leftPressed, OnePressed, debugFps, multiplayer, fpressed, TwoPressed;

    public KeyHandler(MainGame mainGame) {
        this.mg = mainGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //TITLE SCREEN
        char code = e.getKeyChar();
        if (mg.gameState == mg.titleState) {
            if (code == ('w')) {
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code == ('s')) {
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 2) {
                    mg.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (mg.ui.commandNum == 0) {
                    mg.gameState = mg.playState;
                    window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                } else if (mg.ui.commandNum == 1) {
                    mg.gameState = mg.titleOption;
                    mg.ui.commandNum = 0;
                } else if (mg.ui.commandNum == 2) {
                    System.exit(1);
                }
            }
        }
        if (mg.gameState == mg.titleOption) {
            if (code == ('w')) {
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code == ('s')) {
                mg.ui.commandNum++;
                if (mg.ui.commandNum > 2) {
                    mg.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (mg.ui.commandNum == 0) {
                } else if (mg.ui.commandNum == 1) {
                    mg.gameState = mg.playState;
                } else if (mg.ui.commandNum == 2) {
                    System.exit(1);
                }
            }
            if (code == '\u001B') {
                mg.gameState = mg.titleState;
                mg.ui.commandNum = 0;
            }
        }
        if (mg.gameState == mg.optionState) {
            if (code == ('w')) {
                mg.ui.commandNum--;
                if (mg.ui.commandNum < 0) {
                    mg.ui.commandNum = 0;
                }
            }
            if (code == ('s')) {

                mg.ui.commandNum++;
                if (mg.ui.commandNum > 2) {
                    mg.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (mg.ui.commandNum == 0) {
                } else if (mg.ui.commandNum == 1) {
                    try {
                        mg.sqLite.savePlayerInventory();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(1);
                } else if (mg.ui.commandNum == 2) {

                }
            }
        }
        if (code == '\u001B') {
            if (mg.gameState == mg.optionState) {
                window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                mg.gameState = mg.playState;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (mg.gameState == mg.playState) {
                window.setCursor(new Cursor(Cursor.HAND_CURSOR));
                mg.gameState = mg.optionState;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        //Drawing talent window
        if (code == 'n') {
            if (!mg.showTalents) {
                mg.talentP.resetTalentCollision();
                mg.showTalents = true;
                window.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                mg.talentP.hideTalentCollision();
                mg.showTalents = false;
                window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
        //Drawing Inventor Panel
        if (code == 'c') {
            if (!mg.showChar) {
                mg.inventP.resetCharCollision();
                mg.showChar = true;
                window.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                mg.inventP.hideCharCollision();
                mg.showChar = false;
                window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
        //Drawing bag panel
        if (code == 'b') {
            if (!mg.showBag) {
                mg.inventP.resetBagCollision();
                mg.showBag = true;
                window.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                mg.inventP.hideBagCollision();
                mg.showBag = false;
                window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (mg.gameState == mg.playState) {
            //Player Controls
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_M) {
                multiplayer = true;
            }
            if (code == KeyEvent.VK_1) {
                OnePressed = true;
            }
            if (code == KeyEvent.VK_2) {
                TwoPressed = true;
            }
            if (code == KeyEvent.VK_F) {
                fpressed = true;
            }
        }
        if (code == KeyEvent.VK_H) {
            debugFps = true;
            mg.player.mana = mg.player.maxMana;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (mg.gameState == mg.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = false;
            } else if (code == KeyEvent.VK_A) {
                leftPressed = false;
            } else if (code == KeyEvent.VK_S) {
                downPressed = false;
            } else if (code == KeyEvent.VK_D) {
                rightPressed = false;
            }
            if (code == KeyEvent.VK_1) {
                OnePressed = false;
            }
            if (code == KeyEvent.VK_2) {
                TwoPressed = false;
            }
        }
        if (code == KeyEvent.VK_H) {
            debugFps = false;
            mg.player.mana = mg.player.maxMana;
        }
        if (code == KeyEvent.VK_F) {
            fpressed = false;
        }
        if (code == KeyEvent.VK_M) {
            multiplayer = false;
        }
    }
}
