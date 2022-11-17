package Main;

import Entitys.Player;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements Runnable {
    //Screen setting

    public static final double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 900;
    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);

    public Display() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    /**Main game loop*/
    @Override
    public void run() {

        double delta = 0;
        long firstTimeGate = 0;
        double timer = 0;
        int fps = 0;
        int logicticks = 0;
        double fpsCounter = 0;
        long lastTime = System.nanoTime();
        double interval = 1000000000 / FRAMES_PER_SECOND;
        while (gameThread != null) {
            firstTimeGate = System.nanoTime();
            delta += (firstTimeGate - lastTime) / interval;
            fpsCounter += (firstTimeGate - lastTime);
            timer += delta;
            lastTime = firstTimeGate;
            if (timer >= 130000) {
                update();
                timer = 0;
                logicticks++;
            }
            if (delta >= 1) {
                repaint();
                delta--;
                fps++;
            }

            if (fpsCounter >= 1000000000) {
                System.out.println(fps+" "+logicticks);
                fpsCounter = 0;
                fps = 0;
                logicticks = 0;
            }


        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
       player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}
