package main;

import gameworld.Entity;
import gameworld.Item;
import gameworld.NPC;
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
import main.system.ui.GameMap;
import main.system.ui.MiniMap;
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
    public final ArrayList<Entity> PROXIMITY_ENTITIES = new ArrayList<>();


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
    public MotionHandler motionH = new MotionHandler();


    private final int talentState = 3;


    //---------Input-----------
    public MouseHandler mouseH = new MouseHandler(motionH);
    public KeyHandler keyHandler = new KeyHandler(this);


    //---------GAME-STATES-----------

    public final int titleOption = -1;
    public final int titleState = 0;
    public final int playState = 1;
    public final int optionState = 2;
    public CollisionChecker collisionChecker;
    public final int gameOver = 4;


    //---------System---------
    public final Utilities utilities = new Utilities();
    public MiniMap miniM;
    public WorldController wControl;
    public WorldRender wRender;
    public Projectile projectile;
    public Entity entity;

    public Player player;
    public Player2 player2;
    private Multiplayer multiplayer;
    public PathFinder pathF;

    public Storage imageSto;
    public SQLite sqLite;
    public final UI ui = new UI(this);
    public boolean client = false, showBag, showChar, showTalents, loadingScreen, showMap;
    public Random random = new Random((long) (System.currentTimeMillis() * Math.random() * Math.random() * 3000));
    //Game thread
    private Thread gameThread;
    public InventoryPanel inventP;
    public TalentPanel talentP;
    public NPC npc;
    public GameMap gameMap;

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
        loadGame();
    }

    private void startThreads() {
        float logicCounter = 1000000000 / 60f;
        float fastRenderCounter = 1000000000 / 360f;
        Thread renderHelper = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            float difference1 = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / fastRenderCounter;
                difference1 += (firstTimeGate1 - lastTime1) / 1000000000f;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == playState) {
                        player.pickupDroppedItem();
                        inventP.interactWithWindows();
                        if (showMap) {
                            gameMap.dragMap();
                            gameMap.getImage();
                        }
                    }
                    difference = 0;
                }
                if (difference1 >= 1) {
                    proximitySorterENTITIES();
                    difference1 = 0;
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
                        npc.update();
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
    public void paintComponent(Graphics g) {
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
            npc.draw(g2);
            player2.draw(g2);
            player.draw(g2);
            miniM.draw(g2);
            ui.draw(g2);

            if (showMap) {
                gameMap.draw(g2);
            }
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
            g2.drawString((24 + player.worldX) / 48 + " " + (player.worldY + 24) / 48, 500, 650);
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

    private void loadGame() {
        // 0 %
        inventP = new InventoryPanel(this);
        wControl = new WorldController(this);

        //12 %
        ui.updateLoadingScreen(12);
        wRender = new WorldRender(this);
        wControl.getWorldsData();
        wControl.makeOverWorldQuadrants();

        //24%
        ui.updateLoadingScreen(12);
        miniM = new MiniMap(this);
        entity = new Entity(this);
        collisionChecker = new CollisionChecker(this);


        //36%
        ui.updateLoadingScreen(12);
        imageSto = new Storage(this);
        imageSto.loadImages();
        projectile = new Projectile(this, mouseH);
        player = new Player(this, keyHandler, mouseH, motionH);

        //48%
        ui.updateLoadingScreen(12);
        sqLite = new SQLite(this);
        sqLite.readItemsFromDB();

        //60%
        ui.updateLoadingScreen(12);
        player2 = new Player2(this);


        //72%
        ui.updateLoadingScreen(12);
        pathF = new PathFinder(this);
        pathF.instantiateNodes();

        //84%
        ui.updateLoadingScreen(12);
        multiplayer = new Multiplayer(this, player2);
        talentP = new TalentPanel(this);
        player.updateEquippedItems();
        player.health = player.maxHealth;
        player.mana = player.maxMana;
        gameMap = new GameMap(this);
        npc = new NPC(this);

        //100%
        ui.updateLoadingScreen(100);
        wControl.load_city1(25, 27);
        countItems();
        loadingScreen = false;
        gameState = titleState;
        startThreads();
        gameThread.stop();
    }

    private void countItems() {
        System.out.println(-12 + AMULET.size() + BOOTS.size() + CHEST.size() + HEAD.size() + OFFHAND.size() + ONEHAND.size() + PANTS.size() + RELICS.size() + RINGS.size() + TWOHANDS.size() + " total Items!");
    }

    private void proximitySorterENTITIES() {
        try {
            PROXIMITY_ENTITIES.clear();
            for (Entity entity : ENTITIES) {
                if (Math.abs(entity.worldX - player.worldX) + Math.abs(entity.worldY - player.worldY) < 2000) {
                    PROXIMITY_ENTITIES.add(entity);
                }
            }
        } catch (ConcurrentModificationException ignored) {

        }
    }
}
