package gameworld.world;


import gameworld.player.Player;
import gameworld.quest.SpawnTrigger;
import gameworld.world.maps.Map;
import main.MainGame;
import main.system.enums.GameMapType;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;
import main.system.ui.maps.MapMarker;
import main.system.ui.maps.MarkerType;

import java.awt.Point;
import java.util.ArrayList;

public class WorldController {
    private final MainGame mg;
    public static Zone currentWorld;
    public static int[][] currentMapCover;
    public static ArrayList<MapMarker> currentMapMarkers = new ArrayList<>();
    public static final ArrayList<SpawnTrigger> globalTriggers = new ArrayList<>();
    public final ArrayList<Map> MAPS = new ArrayList<>();

    public WorldController(MainGame mg) {
        this.mg = mg;
    }

    public void loadWorldData() {
        // CITY 1
        // MAPS.add(new Map("city1", new Point(100, 100), Zone.City1, GameMapType.MapCover));
        // TUTORIAL
        MAPS.add(new Map("Tutorial", new Point(100, 100), Zone.Tutorial, GameMapType.MapCover));
        // DUNGEON TUTORIAL
        MAPS.add(new Map("FirstDungeon", new Point(60, 60), Zone.Dungeon_Tutorial));
        MAPS.add(new Map("Clearing", new Point(100, 100), Zone.Clearing));
        //Overworld
        //   MAPS.add(new Map("OverWorld", new Point(500, 500), Zone.GrassLands, GameMapType.MapCover));
        loadArray();
    }

    public void loadMap(Zone zone, int xTile, int yTile) {
        for (Map map : MAPS) {
            if (map.zone == zone) {
                currentWorld = zone;
                clearWorldArrays();
                mg.wAnim.emptyAnimationLists();
                WorldRender.worldData = map.mapDataBackGround;
                WorldRender.worldData1 = map.mapDataBackGround2;
                WorldRender.worldData2 = map.mapDataForeGround;
                mg.wAnim.cacheMapEnhancements();
                currentMapMarkers = map.mapMarkers;
                mg.player.map = map;
                mg.wRender.worldSize = map.mapSize;
                currentMapCover = map.mapCover;
                mg.player.setPosition(xTile, yTile);
                return;
            }
        }
    }

    public Map getMap(Zone zone) {
        for (Map map : MAPS) {
            if (map.zone == zone) {
                return map;
            }
        }
        return null;
    }

    public void makeOverWorldQuadrants() {
        int size = getMap(Zone.GrassLands).mapSize.x / 10;
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 10; b++) {
                if (counter != 99) {
                    getMap(Zone.GrassLands).mapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 30, Zone.GrassLands);
                    counter++;
                }
                getMap(Zone.GrassLands).mapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 0, Zone.GrassLands);
            }
        }
    }

    private void clearWorldArrays() {

        mg.PROJECTILES.clear();
    }

    public void update() {
        for (SpawnTrigger trigger : globalTriggers) {
            if (trigger.zone == currentWorld && !trigger.triggered) {
                trigger.activate(mg);
            }
        }
        if (currentWorld == Zone.Tutorial) {
            if (mg.playerX == 1 && mg.playerY == 1) {
                loadMap(Zone.City1, 10, 10);
                mg.player.spawnLevel = 1;
            } else if (mg.playerX == 71 && mg.playerY == 56) {
                loadMap(Zone.Dungeon_Tutorial, 28, 4);
            } else if (mg.playerX == 99 && mg.playerY == 99) {
                loadMap(Zone.Clearing, 1, 1);
            }
        } else if (currentWorld == Zone.GrassLands) {
            if (mg.playerX == 499 && mg.playerY == 499) {
                loadMap(Zone.City1, 10, 10);
            }
        } else if (currentWorld == Zone.City1) {
            if (mg.playerX == 32 && mg.playerY == 0 ||
                    mg.playerX == 33 && mg.playerY == 0 ||
                    mg.playerX == 34 && mg.playerY == 0 ||
                    mg.playerX == 35 && mg.playerY == 0 ||
                    mg.playerX == 36 && mg.playerY == 0 ||
                    mg.playerX == 37 && mg.playerY == 0) {
                loadMap(Zone.GrassLands, 495, 495);
            }
        } else if (currentWorld == Zone.Dungeon_Tutorial) {
            if (mg.playerX == 27 && mg.playerY == 4) {
                loadMap(Zone.Tutorial, 71, 55);
            }
        }
    }


    private void loadArray() {
        for (Map map : MAPS) {
            globalTriggers.addAll(map.spawnTriggers);
        }
        System.out.println(globalTriggers.size() + " total Spawn Triggers!");
    }

    /**
     * @param playerLocation in awt Point
     * @return true if the player is further than 500 pixels from the given points
     */
    public boolean player_went_away(Point playerLocation) {
        return Point.distance(playerLocation.x, playerLocation.y, Player.worldX, Player.worldY) > 500;
    }

    public void uncoverWorldMap() {
        int playerX = mg.playerX;
        int playerY = mg.playerY;
        int radius = 7;
        int xMin = Math.max(0, playerX - radius);
        int xMax = Math.min(currentMapCover.length - 1, playerX + radius);
        int yMin = Math.max(0, playerY - radius);
        int yMax = Math.min(currentMapCover[0].length - 1, playerY + radius);
        int radiusSquared = radius * radius;
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                int dx = x - playerX;
                int dy = y - playerY;
                if (dx * dx + dy * dy <= radiusSquared && Math.random() > 0.95) {
                    currentMapCover[x][y] = 1;
                }
            }
        }
    }

    public void addMapMarker(String name, int xTile, int yTile, MarkerType type) {
        for (Map map : MAPS) {
            if (map.zone == currentWorld) {
                for (MapMarker marker : map.mapMarkers) {
                    if (marker.name().equals(name)) {
                        return;
                    }
                }
                map.mapMarkers.add(new MapMarker(name, xTile, yTile, type));
            }
        }
    }

    public void removeMapMarker(String s) {
        for (Map map : MAPS) {
            if (map.zone == currentWorld) {
                for (MapMarker marker : map.mapMarkers) {
                    if (marker.name().equals(s)) {
                        map.mapMarkers.remove(marker);
                        return;
                    }
                }
            }
        }
    }
}


