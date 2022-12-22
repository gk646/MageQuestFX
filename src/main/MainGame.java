package main;

import gameworld.Entity;
import gameworld.Item;
import gameworld.Projectile;
import gameworld.WorldController;
import gameworld.entitys.Player;
import gameworld.entitys.Player2;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.system.CollisionChecker;
import main.system.Multiplayer;
import main.system.WorldRender;
import main.system.ai.PathFinder;
import main.system.database.SQLite;
import main.system.ui.InventoryPanel;
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

    //ITEMS
    public final ArrayList<Item> AMULET = new ArrayList<>();
    public final ArrayList<Item> BOOTS = new ArrayList<>();
    public final ArrayList<Item> CHEST = new ArrayList<>();
    public final ArrayList<Item> HEAD = new ArrayList<>();
    public final ArrayList<Item> OFFHAND = new ArrayList<>();
    public final ArrayList<Item> ONEHAND = new ArrayList<>();
    public final ArrayList<Item> PANTS = new ArrayList<>();
    public final ArrayList<Item> RELICS = new ArrayList<>();
    public final ArrayList<Item> RINGS = new ArrayList<>();
    public final ArrayList<Item> TWOHANDS = new ArrayList<>();

    public final int tileSize = 48;
    public int gameState;
    public String player2Information = "";
    public final MotionHandler motionH = new MotionHandler();


    //Game thread
    public Thread gameThread;


    //---------Input-----------
    public final MouseHandler mouseH = new MouseHandler(motionH);
    public final KeyHandler keyHandler = new KeyHandler(this);


    //---------GAME-STATES-----------

    public final int titleOption = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionState = 2;
    public final int talentState = 3;
    public final int gameOver = 4;


    //---------System---------

    public final CollisionChecker collisionChecker = new CollisionChecker(this);

    public final WorldRender wRender = new WorldRender(this);
    public final WorldController wControl = new WorldController(this);
    public final Entity entity = new Entity(this);
    final Projectile projectile = new Projectile(this, mouseH);
    public final Player player = new Player(this, keyHandler, mouseH, motionH);
    public final Player2 player2 = new Player2(this);
    public final PathFinder pathF = new PathFinder(this);
    final Multiplayer multiplayer = new Multiplayer(this, player2);
    public final UI ui = new UI(this);
    public boolean client = false, showBag, showChar;
    public SQLite sqLite = new SQLite(this);
    public InventoryPanel inventP;


    /**
     * Main game loop class
     */
    public MainGame(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
        this.addMouseMotionListener(motionH);
        this.setOpaque(false);
        gameState = titleState;

    }

    /**
     * Main game loop
     */
    @Override
    public void run() {
        sqLite.readItemsFromDB();
        wControl.getWorldsData();
        wControl.load_OverworldMap();
        wControl.makeQuadrants();
        pathF.instantiateNodes();
        inventP = new InventoryPanel(this);
        startThreads();
    }

    public void startThreads() {
        float logicCounter = 1000000000 / 60f;
        Thread drawThread = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            float interval;
            while (true) {
                interval = 1000000000 / FRAMES_PER_SECOND;
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / interval;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    repaint();
                    difference = 0;
                }
            }
        });
        drawThread.start();
        Thread playerThread = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / logicCounter;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == playState) {
                        player.update();
                        entity.updatePosSingleplayer();
                        projectile.updateProjectilePos();
                    } else if (gameState == optionState) {
                        entity.updatePosSingleplayer();
                    }
                    difference = 0;
                }

            }
        });
        playerThread.start();
        Thread ProjectileThread = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / logicCounter;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == playState || gameState == optionState) {
                        projectile.update();
                        difference = 0;
                    }
                }
            }
        });
        //ProjectileThread.start();
        Thread updateThread = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = 0;
            float difference = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / logicCounter;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == playState || gameState == optionState || gameState == talentState) {
                        if (keyHandler.debugFps && keyHandler.fpressed) {
                            multiplayer.startMultiplayerClient();
                        }
                        if (keyHandler.debugFps && keyHandler.multiplayer) {
                            multiplayer.startMultiplayerServer();
                        }
                        if (multiplayer.multiplayerStarted) {
                            multiplayer.updateMultiplayerInput();
                        }
                        projectile.update();
                        if (!client) {
                            entity.update();
                        } else {
                            entity.updatePos();
                        }
                        if (multiplayer.multiplayerStarted) {
                            multiplayer.updateMultiplayerOutput();
                        }
                    }
                    difference = 0;
                }
            }
        });
        updateThread.start();
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
            if (showBag) {
                inventP.drawBagWindow(g2);
            }
            if (showChar) {
                inventP.drawCharacterWindow(g2);
            }
            if (showBag || showChar) {
                inventP.drawDragAndDrop(g2);
                inventP.interactWithWindows();
                inventP.getTooltip(g2);
            }
        }
        if (gameState == talentState) {
            wRender.draw(g2);
            projectile.draw(g2);
            entity.draw(g2);
            player2.draw(g2);
            player.draw(g2);
            g2.setColor(this.ui.lightBackgroundAlpha);
            g2.fillRect(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
            ui.draw(g2);
        } else if (gameState == titleState || gameState == titleOption) {
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
