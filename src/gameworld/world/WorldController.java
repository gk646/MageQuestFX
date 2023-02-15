package gameworld.world;


import gameworld.player.Player;
import gameworld.quest.SpawnTrigger;
import gameworld.quest.Trigger;
import gameworld.quest.Type;
import gameworld.world.maps.MapLoader;
import main.MainGame;
import main.system.WorldRender;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.ArrayList;

public class WorldController {

    private final MainGame mg;
    public static Zone currentWorld;
    public static ArrayList<SpawnTrigger> globalTriggers = new ArrayList<>();

    // OVERWORLD
    public final MapQuadrant[] overworldMapQuadrants = new MapQuadrant[100];
    // VALHALLA
    public final Point valhalla_Size = new Point(200, 200);
    public int[][] overWorldMapData;
    // CITY 1
    public final Point city1_Size = new Point(400, 400);
    // TUTORIAL
    public final Point tutorial_Size = new Point(100, 100);
    public int[][] valhalla_MapData;
    public Point valhalla_StartPoint;
    // DUNGEON TUTORIAL
    public final Point dungeonTutorial_Size = new Point(60, 60);
    public Point overWorldSize = new Point(500, 500);
    // HELL
    public int[][] hell_MapData;
    public int[][] city1_MapData;
    public int[][] tutorialMapData;
    public int[][] dungeonTutorial_MapData;


    public WorldController(MainGame mg) {
        this.mg = mg;
    }

    public void load_OverWorldMap(int xTile, int yTile) {
        currentWorld = Zone.GrassLands;
        clearWorldArrays();
        for (MapQuadrant quadrant : overworldMapQuadrants) {
            if (quadrant.spawned) {
                quadrant.spawned = false;
            }
        }
        WorldRender.worldData = overWorldMapData;
        mg.wRender.worldSize = overWorldSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void load_tutorial(int xTile, int yTile) {
        currentWorld = Zone.Tutorial;
        clearWorldArrays();
        WorldRender.worldData = tutorialMapData;
        mg.wRender.worldSize = tutorial_Size;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void load_city1(int xTile, int yTile) {
        currentWorld = Zone.City1;
        clearWorldArrays();
        WorldRender.worldData = city1_MapData;
        mg.wRender.worldSize = tutorial_Size;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void loadDungeonTutorial(int xTile, int yTile) {
        currentWorld = Zone.Dungeon_Tutorial;
        clearWorldArrays();
        WorldRender.worldData = dungeonTutorial_MapData;
        mg.wRender.worldSize = dungeonTutorial_Size;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void getWorldsData() {
        //Tutorial
        this.tutorialMapData = MapLoader.loadMapData("Tutorial", 100);
        MapLoader.getTriggers("Tutorial", Zone.Tutorial);

        //dungeon tutorial
        MapLoader.getTriggers("DungeonTutorial", Zone.Dungeon_Tutorial);
        this.dungeonTutorial_MapData = MapLoader.loadMapData("DungeonTutorial", 60);
        //city 1
        this.city1_MapData = MapLoader.loadMapData("City1", 100);
        //Over world
        this.overWorldMapData = MapLoader.loadMapData("Overworld", 500);
        addCustom();
    }


    private void addCustom() {
        globalTriggers.add(new SpawnTrigger(75, 89, 1, Trigger.SINGULAR, Type.BOSS_Slime, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(47, 9, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(37, 11, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(47, 16, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(37, 21, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(47, 22, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial));
        //top right
        globalTriggers.add(new SpawnTrigger(91, 21, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(86, 25, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(97, 27, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(94, 14, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(94, 4, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial));

        //middle right
        globalTriggers.add(new SpawnTrigger(93, 46, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(87, 45, 1, Trigger.SINGULAR, Type.Grunt, Zone.Tutorial));
        globalTriggers.add(new SpawnTrigger(97, 48, 1, Trigger.SINGULAR, Type.Shooter, Zone.Tutorial));
    }

    public void makeOverWorldQuadrants() {
        int size = overWorldSize.x / 10;
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 10; b++) {
                if (counter != 99) {
                    overworldMapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 30, Zone.GrassLands);
                    counter++;
                }
                overworldMapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 0, Zone.GrassLands);
            }
        }
    }

    private void clearWorldArrays() {
        mg.npcControl.loadNPC(currentWorld);
        mg.PROJECTILES.clear();
    }

    public void update() {
        for (SpawnTrigger trigger : globalTriggers) {
            if (trigger != null && trigger.zone == currentWorld) {
                trigger.activate(mg);
            }
        }
        if (currentWorld == Zone.Tutorial) {
            if (mg.playerX == 1 && mg.playerY == 1) {
                mg.wControl.load_city1(10, 10);
                mg.player.spawnLevel = 1;
            }
        }
        if (currentWorld == Zone.GrassLands) {
            if (mg.playerX == 499 && mg.playerY == 499) {
                mg.wControl.load_city1(10, 10);
            }
        }
        if (currentWorld == Zone.City1) {
            if (mg.playerX == 32 && mg.playerY == 0 ||
                    mg.playerX == 33 && mg.playerY == 0 ||
                    mg.playerX == 34 && mg.playerY == 0 ||
                    mg.playerX == 35 && mg.playerY == 0 ||
                    mg.playerX == 36 && mg.playerY == 0 ||
                    mg.playerX == 37 && mg.playerY == 0) {
                mg.wControl.load_OverWorldMap(495, 495);
            }
        }
        if (currentWorld == Zone.Tutorial) {
            if (mg.playerX == 71 && mg.playerY == 56) {
                loadDungeonTutorial(27, 0);
            }
        }
        if (currentWorld == Zone.Dungeon_Tutorial) {
            if (mg.playerX == 26 && mg.playerY == 0) {
                load_tutorial(71, 55);
            }
        }
    }

    public void loadSpawnLevel() {
        if (mg.player.spawnLevel == 0) {
            mg.wControl.load_tutorial(4, 4);
        } else if (mg.player.spawnLevel == 1) {
            mg.wControl.load_city1(40, 18);
        }
    }

    /**
     * @param playerLocation in awt Point
     * @return true if the player is further than 500 pixels from the given points
     */
    public boolean player_went_away(Point playerLocation) {
        return Point.distance(playerLocation.x, playerLocation.y, Player.worldX, Player.worldY) > 500;
    }
}


