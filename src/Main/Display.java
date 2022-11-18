package Main;

import Entitys.Player;
import Projectiles.FirstBullet;
import Projectiles.Projectile;

import javax.swing.*;
import java.awt.*;

import static Projectiles.Projectile.projectiles;

public class Display extends JPanel implements Runnable {
    //Screen setting

    public static final double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 900;
    Thread gameThread;

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    Player player = new Player(this, keyHandler);
    Projectile projectile = new Projectile(this, mouseHandler);
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
                System.out.println(fps + " " + logic_ticks+" "+mouseHandler.getMouseLocation());
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
        player.update();
        projectile.update();
        for(Projectile projectile1 : projectiles) {
            if (projectile1 != null) {
                projectile1.yPosition += projectile1.projectileSpeed;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        for(Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.draw(g2);
            }
        }
        g2.dispose();
    }
}
