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
    public MapQuadrant[] overworldMapQuadrants;
    public MapQuadrant[] tutorialMapQuadrants;
    private Point overWorldStartPoint;
    //-----Tutorial
    private int[][] tutorialMapData;
    private Point tutorialSize;
    private Point tutorialStartPoint;

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
        this.overworldMapQuadrants = new MapQuadrant[450];
    }

    public void load_OverworldMap() {
        mg.wRender.worldData = overWorldMapData;
        mg.wRender.worldSize = overWorldSize;
        mg.player.setPosition(overWorldStartPoint.x, overWorldStartPoint.y);
        currentWorld = 1;
    }

    public void load_tutorial() {
        mg.wRender.worldData = tutorialMapData;
        mg.wRender.worldSize = tutorialSize;
        mg.player.setPosition(tutorialStartPoint.x, tutorialStartPoint.y);
        mg.ENTITIES.clear();
        currentWorld = 0;
    }

    public void getWorldsData() {
        //overworld
        this.overWorldMapData = OverWorld.loadOverWorld();
        this.overWorldSize = OverWorld.loadMapSize();
        this.overWorldStartPoint = new Point(23800, 23800);

        //tutorial
        this.tutorialMapData = Tutorial.loadTutorial();
        this.tutorialSize = Tutorial.loadMapSize();
        this.tutorialStartPoint = new Point(250, 250);
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


