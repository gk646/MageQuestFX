package gameworld.world;

import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import main.MainGame;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

public class MapQuadrant {

    public final int startTileX;
    public final int startTileY;
    private final int size;
    private final MainGame mg;
    private final Zone zone;
    boolean spawned;
    private int difficulty;
    private int numberOfEnemies;

    public MapQuadrant(int difficulty, MainGame mg, int startTileX, int startTileY, int size, int numberOfEnemies, Zone zone) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.zone = zone;
        this.size = size;
        this.mg = mg;
        this.numberOfEnemies = numberOfEnemies;
        this.difficulty = difficulty;
    }

    public MapQuadrant(MainGame mg, int startTileX, int startTileY, int size, Zone zone) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.size = size;
        this.mg = mg;
        this.zone = zone;
    }


    public void spawnEnemies(int[][] mapData) {
        if (!spawned) {
            int spawnedEnemies = 0;
            int xTile, yTile;
            while (spawnedEnemies < numberOfEnemies) {
                xTile = Math.max(0, Math.min((int) (Math.random() * size + 1) + startTileX, 499));
                yTile = Math.max(0, Math.min((int) (Math.random() * size + 1) + startTileY, 499));
                if (!WorldRender.tileStorage[mapData[xTile][yTile]].collision) {
                    if (Math.random() > 0.4) {
                        MainGame.ENTITIES.add(new ENT_SkeletonWarrior(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty, zone));
                    } else {
                        MainGame.ENTITIES.add(new ENT_SkeletonArcher(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty, zone));
                    }
                    spawnedEnemies++;
                }
            }
            spawned = true;
        }
    }
}

