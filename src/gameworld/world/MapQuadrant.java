package gameworld.world;

import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.monsters.ENT_Shooter;
import main.MainGame;

public class MapQuadrant {

    public final int startTileX;
    public final int startTileY;
    private final int size;
    private final MainGame mg;
    boolean spawned;
    private int difficulty;
    private int numberOfEnemies;

    public MapQuadrant(int difficulty, MainGame mg, int startTileX, int startTileY, int size, int numberOfEnemies) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.size = size;
        this.mg = mg;
        this.numberOfEnemies = numberOfEnemies;
        this.difficulty = difficulty;
    }

    public MapQuadrant(MainGame mg, int startTileX, int startTileY, int size) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.size = size;
        this.mg = mg;
    }


    public void spawnEnemies() {
        if (!spawned) {
            int spawnedEnemies = 0;
            int xTile, yTile;
            while (spawnedEnemies < numberOfEnemies) {
                xTile = Math.max(0, Math.min((int) (Math.random() * size + 1) + startTileX, 499));
                yTile = Math.max(0, Math.min((int) (Math.random() * size + 1) + startTileY, 499));
                if (!mg.wRender.tileStorage[mg.wControl.overWorldMapData[xTile][yTile]].collision) {
                    if (Math.random() > 0.4) {
                        MainGame.ENTITIES.add(new ENT_Grunt(xTile * mg.tileSize, yTile * mg.tileSize, difficulty));
                    } else {
                        MainGame.ENTITIES.add(new ENT_Shooter(xTile * mg.tileSize, yTile * mg.tileSize, difficulty));
                    }
                    spawnedEnemies++;
                }
            }
            spawned = true;
        }
    }
}

