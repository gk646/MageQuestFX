package gameworld.world;


import gameworld.world.maps.MAP_City1;
import gameworld.world.maps.MAP_OverWorld;
import gameworld.world.maps.MAP_Tutorial;
import main.MainGame;

import java.awt.Point;

public class WorldController {

    // WORLD CODES
    // 0 = Tutorial // 1 = Grass Lands // 2 = City 1 // 3 =
    //

    public final MapQuadrant[] overworldMapQuadrants = new MapQuadrant[100];
    //main game
    private final MainGame mg;
    //CURRENT WORLD
    public int currentWorld;
    //-----OVERWORLD
    public int[][] overWorldMapData;
    //-----HELL
    public int[][] hell_MapData;
    public Point hell_Size;
    public Point hell_StartPoint;
    //-----VALHALLA
    public int[][] valhalla_MapData;
    public Point valhalla_Size;
    public Point valhalla_StartPoint;
    private Point overWorldSize;
    //-----Tutorial
    private int[][] tutorialMapData;
    private Point tutorialSize;
    //-----CITY 1
    private int[][] city1_MapData;


    public WorldController(MainGame mg) {
        this.mg = mg;
    }

    public void load_OverWorldMap(int xTile, int yTile) {
        currentWorld = 1;
        clearWorldArrays();
        for (MapQuadrant quadrant : overworldMapQuadrants) {
            if (quadrant.spawned) {
                quadrant.spawned = false;
            }
        }
        mg.wRender.worldData = overWorldMapData;
        mg.wRender.worldSize = overWorldSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void load_tutorial(int xTile, int yTile) {
        currentWorld = 0;
        clearWorldArrays();
        mg.wRender.worldData = tutorialMapData;
        mg.wRender.worldSize = tutorialSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void load_city1(int xTile, int yTile) {
        currentWorld = 2;
        clearWorldArrays();
        mg.wRender.worldData = city1_MapData;
        mg.wRender.worldSize = tutorialSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
    }

    public void getWorldsData() {
        //Over world
        this.overWorldMapData = MAP_OverWorld.loadOverWorld();
        this.overWorldSize = MAP_OverWorld.loadMapSize();

        //Tutorial
        this.tutorialMapData = MAP_Tutorial.loadTutorial();
        this.tutorialSize = MAP_Tutorial.loadMapSize();
        //
        this.city1_MapData = MAP_City1.loadCity();
    }

    public void makeOverWorldQuadrants() {
        int size = overWorldSize.x / 10;
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 10; b++) {
                overworldMapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 30);
                counter++;
            }
        }
    }

    private void clearWorldArrays() {
        mg.npcControl.loadNPC(currentWorld);
        mg.droppedItems.clear();
        MainGame.ENTITIES.clear();
        mg.PRJControls.clear();
    }
}


