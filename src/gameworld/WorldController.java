package gameworld;


import gameworld.entitys.Player;
import gameworld.maps.MapQuadrant;
import gameworld.maps.OverWorld;
import main.MainGame;

import java.awt.Point;

public class WorldController {
    //-----OVERWORLD
    public int[][] overWorldMapData;
    public Point overWorldSize;
    public Point overWorldStartPoint;
    public MapQuadrant[] mapQuadrants;

    //-----HELL
    public int[][] hell_MapData;
    public Point hell_Size;
    public Point hell_StartPoint;


    //-----VALHALLA
    public int[][] valhalla_MapData;
    public Point valhalla_Size;
    public Point valhalla_StartPoint;

    //main game
    public MainGame mg;


    public WorldController(MainGame mg) {
        this.mg = mg;
        this.mapQuadrants = new MapQuadrant[450];
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

    public void makeQuadrants() {
        int counter = 0;
        for (int i = 0; i <= 10; i++) {
            for (int b = 0; b <= 10; b++) {
                mapQuadrants[counter++] = new MapQuadrant(20 - (i + b), mg, (overWorldSize.x / 10) * i, (overWorldSize.y / 10) * b, overWorldSize.x / 10, 40);

            }
        }
        System.out.println(mg.ENTITIES.size());
    }

}


