package main;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entitys.Player;
import gameworld.entitys.Player2;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.system.CollisionChecker;
import main.system.Multiplayer;
import main.system.WorldRender;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainGame extends JPanel implements Runnable {

    //----------SCREEN SETTINGS---------------
    public static double FRAMES_PER_SECOND = 120;
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;


    //---------VARIABLES----------
    public final static ArrayList<Projectile> PROJECTILES = new ArrayList<>();
    public static final ArrayList<Entity> ENTITIES = new ArrayList<>();
    public int gameState, globalLogicTicks;
    public String player2Information;
    public boolean validate;


    //Game thread
    Thread gameThread;

    //---------Input-----------

    public final MotionHandler motionHandler = new MotionHandler();
    public final MouseHandler mouseHandler = new MouseHandler(motionHandler);
    public final KeyHandler keyHandler = new KeyHandler(this);

    //---------GAMESTATES-----------

    public int playState = 1;
    public int pauseState = 2;
    public int titleState = 0;

    //---------System---------

    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public final WorldRender wRender = new WorldRender(this);
    final Entity entity = new Entity(this);
    public final Player player = new Player(this, keyHandler, mouseHandler, motionHandler);
    public final Player2 player2 = new Player2(this);
    final Projectile projectile = new Projectile(this, motionHandler, mouseHandler, keyHandler);
    final Multiplayer multiplayer = new Multiplayer(this, player2);
    public UI ui = new UI(this);

    /**
     * Main game loop class
     */
    public MainGame() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.addMouseMotionListener(motionHandler);
        this.setOpaque(true);
        this.setFocusable(true);
        gameState = titleState;
    }


    /**
     * Main game loop
     */
    @Override
    public void run() {
        double delta = 0;
        long firstTimeGate;
        double timer = 0;
        //int fps = 0;
        int logic_ticks = 0;
        // double fpsCounter = 0;
        long lastTime = System.nanoTime();
        double interval = 1000000000 / FRAMES_PER_SECOND;
        double timeDifference;
        int logicvsFPS = (int) (FRAMES_PER_SECOND / 60);
        while (gameThread != null) {
            firstTimeGate = System.nanoTime();
            timeDifference = (firstTimeGate - lastTime) / interval;
            delta += timeDifference;
            // fpsCounter += (firstTimeGate - lastTime);
            timer += timeDifference / logicvsFPS;
            lastTime = firstTimeGate;
            //12677853 fps with optimized render
            //18491828 fps with "old" render
            //
            if (timer >= 1) {
                globalLogicTicks = logic_ticks;
                update();
                timer = 0;
                logic_ticks++;

            }
            if (delta >= 1) {
                repaint();
                //     fps++;
                delta--;
            }
/*
            if (fpsCounter >= 1000000000) {
                System.out.println(fps + " " + logic_ticks + " ");
                fpsCounter = 0;
                fps = 0;
                logic_ticks = 0;
            }

 */

        }
    }


    /**
     * Game loop update method
     */

    public void update() {
        if (multiplayer.multiplayerStarted) {
            multiplayer.updateMultiInput();
        }
        if (gameState == playState) {
            if (keyHandler.debugFps && keyHandler.multiplayer) {
                multiplayer.startMultiplayer();
            }

            projectile.update();
            player.update();
            entity.update();
        }
        if (gameState == pauseState) {
            projectile.update();
            entity.update();

        }
        if (multiplayer.multiplayerStarted) {
            multiplayer.updateOutput();
        }

    }

    /**
     * repaint method
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Debug
        long drawStart = System.nanoTime();

        //RENDER START
        if (gameState == playState || gameState == pauseState) {
            wRender.draw(g2);
            projectile.draw(g2);
            entity.draw(g2);
            player2.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        } else if (gameState == titleState) {
            ui.draw(g2);
        }


        //RENDER END

        long drawEnd = System.nanoTime();
        long difference = drawEnd - drawStart;
        if (keyHandler.debugFps) {
            g2.setColor(Color.white);
            g2.drawString(("Draw Time" + difference), 500, 600);
        }
        g2.dispose();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

}
