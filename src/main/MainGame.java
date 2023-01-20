package main;

import gameworld.Entity;
import gameworld.Item;
import gameworld.Projectile;
import gameworld.entities.Player2;
import gameworld.player.Player;
import gameworld.world.DroppedItem;
import gameworld.world.WorldController;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.system.CollisionChecker;
import main.system.Multiplayer;
import main.system.Utilities;
import main.system.WorldRender;
import main.system.ai.PathFinder;
import main.system.database.SQLite;
import main.system.ui.UI;
import main.system.ui.inventory.InventoryPanel;
import main.system.ui.talentpane.TalentPanel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;


public class MainGame extends JPanel implements Runnable {

    //----------SCREEN SETTINGS---------------
    public static float FRAMES_PER_SECOND = 120;
    public static int SCREEN_WIDTH = 1920;
    public static int SCREEN_HEIGHT = 1080;
    public int HALF_WIDTH;
    public int HALF_HEIGHT;


    //---------VARIABLES----------
    public final ArrayList<Projectile> PROJECTILES = new ArrayList<>();
    public final ArrayList<Entity> ENTITIES = new ArrayList<>();


    //ITEMS
    public final ArrayList<DroppedItem> droppedItems = new ArrayList<>();
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


    private final int talentState = 3;


    //---------Input-----------
    public final MouseHandler mouseH = new MouseHandler(motionH);
    public final KeyHandler keyHandler = new KeyHandler(this);


    //---------GAME-STATES-----------

    public final int titleOption = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionState = 2;
    private final Entity entity = new Entity(this);
    public final int gameOver = 4;


    //---------System---------
    public final Utilities utilities = new Utilities();
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public final WorldRender wRender = new WorldRender(this);
    public final WorldController wControl = new WorldController(this);
    public final Projectile projectile = new Projectile(this, mouseH);

    public final Player player = new Player(this, keyHandler, mouseH, motionH);
    public final Player2 player2 = new Player2(this);
    private final Multiplayer multiplayer = new Multiplayer(this, player2);
    public final PathFinder pathF = new PathFinder(this);

    public Storage imageSto;
    public final SQLite sqLite = new SQLite(this);
    public final UI ui = new UI(this);
    public boolean client = false, showBag, showChar, showTalents, loadingScreen;
    public Random random = new Random((long) (System.currentTimeMillis() * Math.random() * Math.random() * 3000));
    //Game thread
    private Thread gameThread;
    public InventoryPanel inventP;
    public TalentPanel talentP;

    /**
     * Main game loop class
     */
    public MainGame(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        HALF_WIDTH = SCREEN_WIDTH / 2;
        HALF_HEIGHT = SCREEN_HEIGHT / 2;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
        this.addMouseMotionListener(motionH);
        this.setOpaque(false);
        this.loadingScreen = true;
        gameState = -2;
    }

    /**
     * Main game loop
     */
    @Override
    public void run() {
        inventP = new InventoryPanel(this);

        ui.updateLoadingScreen(12);
        wControl.getWorldsData();
        wControl.makeOverworldQuadrants();
        ui.updateLoadingScreen(12);
        wControl.load_OverworldMap(495, 495);
        ui.updateLoadingScreen(12);
        imageSto = new Storage(this);
        imageSto.loadImages();
        ui.updateLoadingScreen(12);
        sqLite.readItemsFromDB();
        ui.updateLoadingScreen(12);
        pathF.instantiateNodes();
        ui.updateLoadingScreen(12);
        ui.updateLoadingScreen(12);
        talentP = new TalentPanel(this);
        player.updateEquippedItems();
        ui.updateLoadingScreen(100);
        loadingScreen = false;
        gameState = titleState;
        startThreads();
        gameThread.stop();
    }

    private void startThreads() {
        float logicCounter = 1000000000 / 60f;
        float fastRenderCounter = 1000000000 / 360f;
        Thread renderHelper = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / fastRenderCounter;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == playState) {
                        player.pickupDroppedItem();
                        inventP.interactWithWindows();
                    }
                    difference = 0;
                }
            }
        });
        renderHelper.start();
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
                        projectile.updateProjectilePos();
                    }
                    difference = 0;
                }
            }
        });
        playerThread.start();
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
    public synchronized void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //Debug
        long drawStart = System.nanoTime();
        //RENDER START
        if (gameState == playState || gameState == optionState) {
            wRender.draw(g2);
            drawDroppedItems(g2);
            projectile.draw(g2);
            entity.draw(g2);
            player2.draw(g2);
            player.draw(g2);
            ui.draw(g2);
            if (showBag) {
                inventP.drawBagWindow(g2);
                inventP.drawBagTooltip(g2);
            }
            if (showChar) {
                inventP.drawCharacterWindow(g2);
                inventP.drawCharTooltip(g2);
            }
            if (showBag || showChar) {
                inventP.drawDragAndDrop(g2);
            }
            if (showTalents) {
                talentP.drawTalentWindow(g2);
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
        } else if (gameState == titleState || gameState == titleOption || loadingScreen) {
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

    private void drawDroppedItems(Graphics2D g2) {
        if (droppedItems.size() != 0) {
            try {
                for (DroppedItem dropItem : droppedItems) {
                    if (dropItem.droppedIcon != null) {
                        dropItem.draw(g2);
                    }
                }
            } catch (ConcurrentModificationException ignored) {
            }
        }
    }
}
