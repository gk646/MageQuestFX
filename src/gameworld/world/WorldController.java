package gameworld.world;


import gameworld.world.maps.OverWorld;
import gameworld.world.maps.Tutorial;
import main.MainGame;

import java.awt.Point;

public class WorldController {

    //main game
    private final MainGame mg;
    //CURRENT WORLD
    public int currentWorld;

    //-----OVERWORLD
    public int[][] overWorldMapData;
    private Point overWorldSize;
    public MapQuadrant[] overworldMapQuadrants = new MapQuadrant[100];
    private Point overWorldStartPoint;
    private Point overWorldTeleportCity;
    //-----Tutorial
    private int[][] tutorialMapData;
    private Point tutorialSize;
    private Point tutorialStartPoint;
    private Point tutorialTeleportToOverWorld;
    //-----HELL
    public int[][] hell_MapData;
    public Point hell_Size;
    public Point hell_StartPoint;


    //-----VALHALLA
    public int[][] valhalla_MapData;
    public Point valhalla_Size;
    public Point valhalla_StartPoint;


    public WorldController(MainGame mg) {
        this.mg = mg;
    }

    public void load_OverworldMap(int xTile, int yTile) {
        mg.ENTITIES.clear();
        mg.PROJECTILES.clear();
        for (MapQuadrant quadrant : overworldMapQuadrants) {
            if (quadrant.spawned) {
                quadrant.spawned = false;
            }
        }
        mg.wRender.worldData = overWorldMapData;
        mg.wRender.worldSize = overWorldSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
        currentWorld = 1;
        mg.wRender.teleportPoint1 = overWorldTeleportCity;
    }

    public void load_tutorial(int xTile, int yTile) {
        mg.ENTITIES.clear();
        mg.PROJECTILES.clear();
        mg.wRender.worldData = tutorialMapData;
        mg.wRender.worldSize = tutorialSize;
        mg.player.setPosition(xTile * 48, yTile * 48);
        currentWorld = 0;
        mg.wRender.teleportPoint1 = overWorldTeleportCity;
    }

    public void getWorldsData() {
        //overworld
        this.overWorldMapData = OverWorld.loadOverWorld();
        this.overWorldSize = OverWorld.loadMapSize();
        this.overWorldStartPoint = new Point(23800, 23800);
        this.overWorldTeleportCity = new Point(500, 500);

        //tutorial
        this.tutorialMapData = Tutorial.loadTutorial();
        this.tutorialSize = Tutorial.loadMapSize();
        this.tutorialStartPoint = new Point(250, 250);
        this.tutorialTeleportToOverWorld = new Point(95, 95);
    }

    public void makeOverworldQuadrants() {
        int size = overWorldSize.x / 10;
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int b = 0; b < 10; b++) {
                overworldMapQuadrants[counter] = new MapQuadrant(19 - (i + b), mg, size * i, size * b, size, 30);
                counter++;
            }
        }
    }
}


