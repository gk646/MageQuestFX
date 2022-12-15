package gameworld;


import gameworld.entitys.Player;
import gameworld.maps.OverWorld;
import main.system.WorldRender;

import java.awt.Point;

public class World {
    public static int[][] worldData;
    public static Point worldSize;

    public static void setWorldData() {
        WorldRender.worldData = OverWorld.loadMap();
        WorldRender.worldSize = OverWorld.loadMapSize();
        Player.startingPoint = OverWorld.loadMapStartPoint();
    }
}
