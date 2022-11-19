package main;

import entity.entitys.Player;
import gameworld.tiles.TileManager;
import handlers.KeyHandler;
import handlers.MotionHandler;
import handlers.MouseHandler;
import projectile.Projectile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class Display extends JPanel implements Runnable {

    //Screen setting
    public static final double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 900;

    //World settings
    public final int  worldWidth = 100*48;
    public final int worldHeight = 100 *48;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    public MouseHandler mouseHandler = new MouseHandler();


    public Player player = new Player(this, keyHandler);
    public MotionHandler motionHandler = new MotionHandler();
    Projectile projectile = new Projectile(this,motionHandler, mouseHandler);

    public Display() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.addMouseMotionListener(motionHandler);

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
            timer += (firstTimeGate - lastTime) / interval;
            lastTime = firstTimeGate;
            if (timer >= 2) {
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
        tileManager.draw(g2);
        projectile.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
