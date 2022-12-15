package main;

import gameworld.Entity;
import gameworld.Projectile;
import gameworld.entitys.Player;
import gameworld.entitys.Player2;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.system.AI.PathFinder;
import main.system.CollisionChecker;
import main.system.Multiplayer;
import main.system.WorldRender;
import main.system.ui.UI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;


public class MainGame extends JPanel implements Runnable {

    //----------SCREEN SETTINGS---------------
    public static float FRAMES_PER_SECOND = 120;
    public static int SCREEN_WIDTH = 1920;
    public static int SCREEN_HEIGHT = 1080;


    //---------VARIABLES----------
    public final ArrayList<Projectile> PROJECTILES = new ArrayList<>();
    public final ArrayList<Entity> ENTITIES = new ArrayList<>();
    public int gameState, tileSize = 48;
    public String player2Information = "";
    public boolean client = false;


    //Game thread
    public Thread gameThread;


    //---------Input-----------

    public final MotionHandler motionHandler = new MotionHandler();
    public final MouseHandler mouseHandler = new MouseHandler(motionHandler);
    public final KeyHandler keyHandler = new KeyHandler(this);

    //---------GAMESTATES-----------

    public final int titleOption = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionState = 2;
    public final int talentState = 3;
    public final int gameOver = 4;


    //---------System---------

    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public final WorldRender wRender = new WorldRender(this);
    public final Entity entity = new Entity(this);
    public final Player player = new Player(this, keyHandler, mouseHandler);
    public final Player2 player2 = new Player2(this);
    final Projectile projectile = new Projectile(this, mouseHandler);
    final Multiplayer multiplayer = new Multiplayer(this, player2);
    public final UI ui = new UI(this);
    public final PathFinder pathFinder = new PathFinder(this);

    /**
     * Main game loop class
     */
    public MainGame(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setFocusable(true);
        this.addMouseMotionListener(motionHandler);
        this.setOpaque(false);
        gameState = titleState;
    }

    /**
     * Main game loop
     */
    @Override
    public void run() {
        startThreads();
    }

    public void startThreads() {
        float logicvsFPS = 1000000000 / 60f;
        Thread updateThread = new Thread(() -> {
            long firstTimeGate1;
            double timer1 = 0;
            long lastTime1 = 0;
            double difference;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference = (firstTimeGate1 - lastTime1) / logicvsFPS;
                lastTime1 = firstTimeGate1;
                timer1 += difference;
                if (timer1 >= 1) {
                    update();
                    timer1 = 0;
                }
            }
        });
        updateThread.start();
        Thread drawThread = new Thread(() -> {
            long firstTimeGate1;
            double timer1 = 0;
            long lastTime1 = System.nanoTime();
            float difference;
            float interval;
            while (true) {
                interval = 1000000000 / FRAMES_PER_SECOND;
                firstTimeGate1 = System.nanoTime();
                difference = (firstTimeGate1 - lastTime1) / interval;
                lastTime1 = firstTimeGate1;
                timer1 += difference;
                if (timer1 >= 1) {
                    repaint();
                    timer1 = 0;
                }
            }
        });
        drawThread.start();
        Thread playerAProjectileThread = new Thread(() -> {
            long firstTimeGate1;
            double timer1 = 0;
            long lastTime1 = System.nanoTime();
            float difference;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference = (firstTimeGate1 - lastTime1) / logicvsFPS;
                lastTime1 = firstTimeGate1;
                timer1 += difference;
                if (timer1 >= 1) {
                    player.update();
                    projectile.update();
                    entity.updatePos();
                    timer1 = 0;
                }
            }
        });
        playerAProjectileThread.start();

    }

    /**
     * Game loop update method
     */

    public void update() {
        if (gameState == playState) {
            if (keyHandler.debugFps && keyHandler.fpressed) {
                multiplayer.startMultiplayerClient();
            }
            if (keyHandler.debugFps && keyHandler.multiplayer) {
                multiplayer.startMultiplayerServer();
            }
            if (multiplayer.multiplayerStarted) {
                multiplayer.updateMultiplayerInput();
            }
            if (!client) {
                entity.update();
            } else {
                entity.updatePos();
            }
            if (multiplayer.multiplayerStarted) {
                multiplayer.updateMultiplayerOutput();
            }
        }
    }
    /**
     * repaint method
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //Debug
        long drawStart = System.nanoTime();
        //RENDER START
        if (gameState == playState || gameState == optionState) {
            wRender.draw(g2);
            projectile.draw(g2);
            entity.draw(g2);
            player2.draw(g2);
            player.draw(g2);
            ui.draw(g2);
        } else if (gameState == titleState || gameState == titleOption || gameState == talentState) {
            ui.draw(g2);
        }

        //RENDER END

        long drawEnd = System.nanoTime();
        long difference = drawEnd - drawStart;
        if (keyHandler.debugFps) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
            g2.drawString(("Draw Time" + difference), 500, 600);
        }
        g2.dispose();

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

}
