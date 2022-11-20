package main;

import entity.entitys.Player;
import gameworld.WorldRender;
import handlers.KeyHandler;
import handlers.MotionHandler;
import handlers.MouseHandler;
import projectile.Projectile;

import javax.swing.*;
import java.awt.*;


public class MainGame extends JPanel implements Runnable {

    //Screen setting
    public static final double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1400;
    public static final int SCREEN_HEIGHT = 900;

    //World settings
    /*
    public final int worldWidth = 100 * 48;
    public final int worldHeight = 100 * 48;
     */
    //Game thread
    Thread gameThread;

    //Input handlers
    public MotionHandler motionHandler = new MotionHandler();
    KeyHandler keyHandler = new KeyHandler();
    public MouseHandler mouseHandler = new MouseHandler();

    //Instances of important classes
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    WorldRender wRender = new WorldRender(this);
    public Player player = new Player(this, keyHandler);
    Projectile projectile = new Projectile(this, motionHandler, mouseHandler);


    //Variables
    public int globalLogicTicks;

    /**
     * Main game loop class
     */
    public MainGame() {
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
            //12677853 fps with optimized render
            //
            repaint();
            fps++;
            if (timer >= 2) {
                update();
                timer = 0;
                logic_ticks++;
                globalLogicTicks = logic_ticks;
            }
            if (delta >= 1) {

                delta--;
            }

            if (fpsCounter >= 1000000000) {
                System.out.println(fps + " " + logic_ticks + " ");
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

    /**
     * Main update method
     */

    public void update() {
        projectile.update();
        player.update();

    }

    /**
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        wRender.draw(g2);
        projectile.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
