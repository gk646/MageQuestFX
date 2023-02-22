package main;

import gameworld.ENT_Control;
import gameworld.NPC_Control;
import gameworld.PRJ_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.boss.BOS_Slime;
import gameworld.entities.damage.dmg_numbers.DamageNumber;
import gameworld.entities.damage.effects.TileBasedEffects;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.entities.multiplayer.ENT_Player2;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.MAP_UTILS;
import gameworld.world.WorldController;
import gameworld.world.effects.DayNightCycle;
import gameworld.world.objects.DROP;
import gameworld.world.objects.OBJ_Control;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
import input.InputHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import main.system.CollisionChecker;
import main.system.Multiplayer;
import main.system.Storage;
import main.system.ai.PathFinder;
import main.system.database.SQLite;
import main.system.enums.State;
import main.system.enums.Zone;
import main.system.rendering.WorldEnhancements;
import main.system.rendering.WorldRender;
import main.system.savegame.LoadGameState;
import main.system.sound.Sound;
import main.system.ui.Effects;
import main.system.ui.FonT;
import main.system.ui.UI;
import main.system.ui.inventory.UI_InventoryPanel;
import main.system.ui.maps.GameMap;
import main.system.ui.maps.MiniMap;
import main.system.ui.questpanel.UI_QuestPanel;
import main.system.ui.skillbar.UI_SkillBar;
import main.system.ui.talentpane.UI_TalentPanel;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class MainGame {


    private static final float FRAMES_PER_SECOND = 120;
    public static int SCREEN_WIDTH = 1_920;
    public static int SCREEN_HEIGHT = 1_080;
    public final int HALF_WIDTH;
    public final int HALF_HEIGHT;
    public static final List<ENTITY> ENTITIES = Collections.synchronizedList(new ArrayList<>());
    //---------VARIABLES----------
    public final List<PROJECTILE> PROJECTILES = Collections.synchronizedList(new ArrayList<>());

    public final List<ENTITY> PROXIMITY_ENTITIES = Collections.synchronizedList(new ArrayList<>());
    public final List<DamageNumber> damageNumbers = Collections.synchronizedList(new ArrayList<>());

    public Random random;
    //ITEMS
    public final List<DROP> WORLD_DROPS = Collections.synchronizedList(new ArrayList<>());
    public final ArrayList<ITEM> AMULET = new ArrayList<>();
    public final ArrayList<ITEM> BOOTS = new ArrayList<>();
    public final ArrayList<ITEM> CHEST = new ArrayList<>();
    public final ArrayList<ITEM> HEAD = new ArrayList<>();
    public final ArrayList<ITEM> OFFHAND = new ArrayList<>();
    public final ArrayList<ITEM> ONEHAND = new ArrayList<>();
    public final ArrayList<ITEM> PANTS = new ArrayList<>();
    public final ArrayList<ITEM> RELICS = new ArrayList<>();
    public final ArrayList<ITEM> RINGS = new ArrayList<>();
    public final ArrayList<ITEM> TWOHANDS = new ArrayList<>();
    public final ArrayList<ITEM> BAGS = new ArrayList<>();
    public final ArrayList<ITEM> MISC = new ArrayList<>();

    public final int tileSize = 48;
    public final UI ui = new UI(this);


    //---------Input-----------
    public final GraphicsContext gc;
    //----------SCREEN SETTINGS---------------

    public String player2Information = "";
    public InputHandler inputH;
    public int playerX, playerY;
    public CollisionChecker collisionChecker;
    public WorldController wControl;
    public WorldRender wRender;
    public ENT_Player2 ENTPlayer2;
    public Player player;
    public State gameState;
    public PathFinder pathF;

    public Storage imageSto;
    public SQLite sqLite;
    public boolean client = false, showBag, showChar, showTalents, loadingScreen, showMap;
    public PRJ_Control prj_control;
    public UI_InventoryPanel inventP;
    public UI_TalentPanel talentP;
    public NPC_Control npcControl;
    public MAP_UTILS map_utils;
    public GameMap gameMap;
    private final DayNightCycle cycle = new DayNightCycle(this);
    public UI_SkillBar sBar;
    public UI_QuestPanel qPanel;
    public boolean credits;
    public boolean drawVideoSettings, drawAudioSettings;
    public boolean drawKeybindings;
    public boolean drawGameplay;
    public DRP_DroppedItem dropI;
    public boolean drawCodex;
    public WorldEnhancements wAnim;
    public LoadGameState loadGameState;

    //---------System---------
    private MiniMap miniM;
    private Multiplayer multiplayer;
    public TileBasedEffects tileBase;
    private int counter = 0;
    private ENT_Control ent_control;
    public Sound sound;
    public OBJ_Control ob_control;


    /**
     * Main class for the game logic and center point for information
     */
    public MainGame(int width, int height, GraphicsContext gc) {
        this.gc = gc;
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
        HALF_WIDTH = SCREEN_WIDTH / 2;
        HALF_HEIGHT = SCREEN_HEIGHT / 2;
        this.loadingScreen = true;
        gameState = State.START;
        cycle.start();
    }


    void run() {
        loadGame(gc);
    }

    /**
     * Starts the 4 game threads
     */
    private void startThreads() {
        float logicCounter = 1_000_000_000 / 60.0f;
        Thread renderHelper = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            float difference1 = 0;
            float difference2 = 0;
            float fastRenderCounter = 1_000_000_000 / 360.0f;
            float fastRenderCounter2 = 1_000_000_000 / 120.0f;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / fastRenderCounter;
                difference1 += (firstTimeGate1 - lastTime1) / 1_000_000_000.0f;
                difference2 += (firstTimeGate1 - lastTime1) / fastRenderCounter2;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    inventP.interactWithWindows();
                    difference = 0;
                }
                if (difference2 >= 1) {
                    if (gameState == State.PLAY) {
                        if (showMap) {
                            gameMap.dragMap();
                            gameMap.getImage();
                        }
                        getPlayerTile();
                        player.pickupDroppedItem();
                        player.checkPlayerIsMoving();
                        inventP.interactWithWindows();
                        getPlayerTile();
                        tileBase.update();
                        wAnim.animateTiles();
                        wControl.uncoverWorldMap();
                        wControl.update();
                        qPanel.update();
                    }
                    difference2 = 0;
                }
                if (difference1 >= 0.5) {
                    synchronized (PROXIMITY_ENTITIES) {
                        proximitySorterENTITIES();
                    }
                    sound.update();
                    difference1 = 0;
                    // System.out.println(counter);
                    counter = 0;
                }
            }
        });
        renderHelper.start();
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(Duration.seconds(0.007_9), ae -> drawGame(gc));

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        Thread playerThread = new Thread(() -> {
            long firstTimeGate1;
            long lastTime1 = System.nanoTime();
            float difference = 0;
            while (true) {
                firstTimeGate1 = System.nanoTime();
                difference += (firstTimeGate1 - lastTime1) / logicCounter;
                lastTime1 = firstTimeGate1;
                if (difference >= 1) {
                    if (gameState == State.PLAY) {
                        player.update();
                        sBar.update();
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
                    if (gameState == State.PLAY || gameState == State.OPTION || gameState == State.TALENT) {
                        /*
                        if (inputH.debugFps && inputH.f_pressed) {
                            multiplayer.startMultiplayerClient();
                        }
                        if (inputH.debugFps && inputH.multiplayer) {
                            multiplayer.startMultiplayerServer();
                        }
                        if (multiplayer.multiplayerStarted) {
                            multiplayer.updateMultiplayerInput();
                        }

                         */
                        prj_control.update();
                        ent_control.update();
                        npcControl.update();
                        /*
                        if (multiplayer.multiplayerStarted) {
                            multiplayer.updateMultiplayerOutput();
                        }

                         */
                    }
                    difference = 0;
                }
            }
        });
        updateThread.start();
    }

    /**
     * The main painting method to draw the screen
     */

    private void drawGame(GraphicsContext gc) {
        //Debug
        long drawStart = System.nanoTime();
        //RENDER START
        if (gameState == State.PLAY || gameState == State.OPTION) {
            wRender.draw(gc);
            drawDroppedItems(gc);
            ent_control.draw(gc);
            prj_control.draw(gc);
            npcControl.draw(gc);
            //ENTPlayer2.draw(gc);
            player.draw(gc);
            drawDamageNumber(gc);
            wRender.drawSecondLayer(gc);
            miniM.draw(gc);
            ui.draw(gc);
            qPanel.draw(gc);
            sBar.draw(gc);
            wAnim.drawLayerOneTwo(gc);
            if (showMap) {
                gameMap.draw(gc);
            }
            if (showBag) {
                inventP.drawBagWindow(gc);
                inventP.drawBagTooltip(gc);
                inventP.drawDragAndDrop(gc);
            }
            if (showChar) {
                inventP.drawCharacterWindow(gc);
                inventP.drawCharTooltip(gc);
                inventP.drawDragAndDrop(gc);
            }
            if (showTalents) {
                talentP.drawTalentWindow(gc);
            }
        } else if (gameState == State.TITLE || gameState == State.TITLE_OPTION || loadingScreen) {
            ui.draw(gc);
        }
        //RENDER END
        if (inputH.debugFps) {
            gc.setFont(FonT.minecraftBold30);
            gc.setFill(Color.BLACK);
            gc.setFont(ui.maruMonica30);
            gc.fillText(("Draw Time" + (System.nanoTime() - drawStart)), 500, 600);
            gc.fillText((int) (Player.worldX + 24) / 48 + " " + (int) (Player.worldY + 24) / 48, 500, 700);
        }
    }

    private void drawDroppedItems(GraphicsContext gc) {
        synchronized (WORLD_DROPS) {
            Iterator<DROP> iterator = WORLD_DROPS.iterator();
            while (iterator.hasNext()) {
                DROP drop = iterator.next();
                if (drop.zone == WorldController.currentWorld && Math.abs(drop.worldPos.x - Player.worldX) + Math.abs(drop.worldPos.y - Player.worldY) < 1_500) {
                    drop.draw(gc);
                } else if (WORLD_DROPS.size() > 100) {
                    iterator.remove();
                }
            }
        }
    }


    /**
     * Loads the game and updates loading screen
     *
     * @param gc gc
     */
    private void loadGame(GraphicsContext gc) {
        FonT.minecraftBold30 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftBold-nMK1.otf"), 30);
        sqLite = new SQLite(this);
        loadGameState = new LoadGameState(this);
        sqLite.getConnection();
        tileBase = new TileBasedEffects(this);
        qPanel = new UI_QuestPanel(this);
        sBar = new UI_SkillBar(this);
        dropI = new DRP_DroppedItem(this);
        wAnim = new WorldEnhancements(this);

        ui.updateLoadingScreen(0, gc);
        SecureRandom secureRandom = new SecureRandom();
        long seed = secureRandom.nextLong();
        random = new Random(seed);
        // 0 %

        sound = new Sound();
        sound.loadSounds();
        inventP = new UI_InventoryPanel(this);
        wControl = new WorldController(this);

        //12 %
        ui.updateLoadingScreen(12, gc);
        ob_control = new OBJ_Control(this);
        wRender = new WorldRender(this);
        wControl.loadWorldData();
        //  wControl.makeOverWorldQuadrants();
        talentP = new UI_TalentPanel(this);
        DialogStorage.loadDialogs();

        //24%
        ui.updateLoadingScreen(12, gc);
        miniM = new MiniMap(this);
        ent_control = new ENT_Control(this);
        collisionChecker = new CollisionChecker(this);

        //36%
        ui.updateLoadingScreen(12, gc);
        imageSto = new Storage();
        imageSto.loadImages();
        prj_control = new PRJ_Control(this);
        player = new Player(this);

        //48%
        ui.updateLoadingScreen(12, gc);
        sqLite.readAllGameData();
        talentP.assignDescriptions();
        //60%
        ui.updateLoadingScreen(12, gc);
        ENTPlayer2 = new ENT_Player2(this);
        map_utils = new MAP_UTILS(this);

        talentP.createTalentNodes();
        //72%
        ui.updateLoadingScreen(12, gc);
        pathF = new PathFinder(this);
        pathF.instantiateNodes();
        Effects.loadEffects();

        //84%
        ui.updateLoadingScreen(12, gc);
        multiplayer = new Multiplayer(this, ENTPlayer2);
        player.updateEquippedItems();
        player.health = player.maxHealth;
        player.mana = player.maxMana;
        npcControl = new NPC_Control(this);
        gameMap = new GameMap(this);
        FonT.loadFonts();

        //100%
        loadGameState.loadGame();
        ui.updateLoadingScreen(16, gc);
        countItems();
        gameMap.getImage();
        gameState = State.TITLE;
        loadingScreen = false;
        startThreads();
        sound.INTRO.setCycleCount(MediaPlayer.INDEFINITE);
        sound.INTRO.play();
        debug();
    }

    private void debug() {
        ENTITIES.add(new BOS_Slime(this, 70 * 48, 89 * 48, 1, 150, Zone.Tutorial));
        for (int i = 0; i < 10; i++) {
            ENTITIES.add(new ENT_SkeletonWarrior(this, 4 * 48, 4 * 48, 1, Zone.Tutorial));
        }
        // inventP.bag_Slots.get(4).item = DRP_DroppedItem.cloneItemWithLevelQuality(BAGS.get(1), 100, 60);
        //ENTITIES.add(new ENT_Shooter(this, 35 * 48, 19 * 48, 111));
        //wControl.loadMap(Zone.GrassLands, 496, 496);
        wControl.loadMap(Zone.Tutorial, 4, 4);
        for (int i = 0; i < 10; i++) {
            dropI.dropRareItem(this, (490 - i) * 48, 485 * 48, 1, Zone.GrassLands);
        }
        for (int i = 0; i < 10; i++) {
            dropI.dropItem(this, (490 - i) * 48, 485 * 48, 1, Zone.GrassLands);
        }
        // ENTITIES.add(new BOS_Slime(this, 490 * 48, 490 * 48, 1, 140));
    }

    /**
     * Prints out the total item count across all categories
     */
    private void countItems() {
        System.out.println(-12 + MISC.size() + BAGS.size() + AMULET.size() + BOOTS.size() + CHEST.size() + HEAD.size() + OFFHAND.size() + ONEHAND.size() + PANTS.size() + RELICS.size() + RINGS.size() + TWOHANDS.size() + " total Items!");
    }

    /**
     * Filters the bigger Entities array to only have objects that are less than 2000 worldPixels away
     * Only used for the gameMap
     *
     * @see GameMap
     */
    private void proximitySorterENTITIES() {
        synchronized (ENTITIES) {
            PROXIMITY_ENTITIES.clear();
            for (ENTITY entity : ENTITIES) {
                if (Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 2_000) {
                    PROXIMITY_ENTITIES.add(entity);
                }
            }
        }
    }

    private void getPlayerTile() {
        playerX = (int) ((Player.worldX + 24) / 48);
        playerY = (int) ((Player.worldY + 24) / 48);
    }

    private void drawDamageNumber(GraphicsContext gc) {
        synchronized (damageNumbers) {
            Iterator<DamageNumber> iterator = damageNumbers.iterator();
            gc.setEffect(ui.shadow);
            while (iterator.hasNext()) {
                DamageNumber dmgN = iterator.next();
                dmgN.draw(gc);
                if (dmgN.offSetY <= -30) {
                    iterator.remove();
                }
            }
            gc.setEffect(null);
        }
    }
}

/*





 */
