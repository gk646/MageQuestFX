package gameworld;


import gameworld.entitys.Player;
import gameworld.maps.MapQuadrant;
import gameworld.maps.OverWorld;
import main.MainGame;

import java.awt.Point;

public class WorldController {
    //main game
    private final MainGame mg;
    //-----OVERWORLD
    private int[][] overWorldMapData;
    private Point overWorldSize;
    public MapQuadrant[] overworldMapQuadrants;

    //-----HELL
    public int[][] hell_MapData;
    public Point hell_Size;
    public Point hell_StartPoint;


    //-----VALHALLA
    public int[][] valhalla_MapData;
    public Point valhalla_Size;
    public Point valhalla_StartPoint;
    private Point overWorldStartPoint;


    public WorldController(MainGame mg) {
        this.mg = mg;
        this.overworldMapQuadrants = new MapQuadrant[450];
    }

    public void load_OverworldMap() {
        mg.wRender.worldData = overWorldMapData;
        mg.wRender.worldSize = overWorldSize;
        Player.startingPoint = overWorldStartPoint;
    }

    public void getWorldsData() {
        this.overWorldMapData = OverWorld.loadOverWorld();
        this.overWorldSize = OverWorld.loadMapSize();
        this.overWorldStartPoint = new Point((overWorldSize.x / 2) * mg.tileSize, (overWorldSize.y / 2) * mg.tileSize);
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


