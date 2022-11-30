package input;

import main.MainGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public MainGame mg;
    //Keys
    public boolean upPressed, downPressed, rightPressed, leftPressed, OnePressed, debugFps, multiplayer;

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
                    System.exit(1);
                } else if (mg.ui.commandNum == 2) {

                }
            }
        }

        if (code == '\u001B') {
            if (mg.gameState == mg.optionState) {
                mg.gameState = mg.playState;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (mg.gameState == mg.playState) {
                mg.gameState = mg.optionState;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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


        }
        if (code == KeyEvent.VK_C) {
            debugFps = true;
            mg.player.mana = mg.player.maxMana;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_C) {

            debugFps = false;
        }
        if (code == KeyEvent.VK_1) {
            OnePressed = false;
        }
    }
}
