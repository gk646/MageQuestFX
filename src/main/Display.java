package main;

import entity.entitys.Player;
import handlers.KeyHandler;
import handlers.MouseHandler;
import projectile.Projectile;

import javax.swing.*;
import java.awt.*;


public class Display extends JPanel implements Runnable {

    //Screen setting
    public static final double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 900;

    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler();
    public MouseHandler mouseHandler = new MouseHandler();
    Projectile projectile = new Projectile(this, mouseHandler);

    public Player player = new Player(this, keyHandler);

    public Display() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
    }

    /**
     * Main game loop
     */
    @Override
    public void run() {
        double delta = 0;
        long firstTimeGate;
        double timer = 0;
        int fps = 0;
        int logic_ticks = 0;
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
                logic_ticks++;
            }
            if (delta >= 1) {
                repaint();
                delta--;
                fps++;
            }

            if (fpsCounter >= 1000000000) {
                System.out.println(fps + " " + logic_ticks);
                fpsCounter = 0;
                fps = 0;
                logic_ticks = 0;
            }


        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void update() {
        projectile.update();
        player.update();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        projectile.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
