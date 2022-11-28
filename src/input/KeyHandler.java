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
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
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
        if (code == KeyEvent.VK_C) {
            debugFps = true;
        }
        if (code == KeyEvent.VK_M) {
            multiplayer = true;
        }
        if (code == KeyEvent.VK_1) {
            OnePressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            if (mg.gameState == mg.playState) {
                mg.gameState = mg.pauseState;
            } else if (mg.gameState == mg.pauseState) {
                mg.gameState = mg.playState;
            }
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
