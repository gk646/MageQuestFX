package gameworld;


import gameworld.entitys.Player;
import gameworld.maps.OverWorld;
import main.MainGame;

import java.awt.Point;

public class WorldController {
    //-----OVERWORLD
    public int[][] overWorldMapData;
    public Point overWorldSize;
    public Point overWorldStartPoint;
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
}
