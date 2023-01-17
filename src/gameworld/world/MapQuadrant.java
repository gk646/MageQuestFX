package gameworld.world;

import gameworld.entities.Grunt;
import gameworld.entities.Shooter;
import main.MainGame;

public class MapQuadrant {

    private final int size;
    private final int difficulty;
    private final int numberOfEnemies;
    private final MainGame mg;
    public int startTileX;
    public int startTileY;
    boolean spawned;

    public MapQuadrant(int difficulty, MainGame mg, int startTileX, int startTileY, int size, int numberOfEnemies) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.size = size;
        this.mg = mg;
        this.numberOfEnemies = numberOfEnemies;
        this.difficulty = difficulty;
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
                        mg.ENTITIES.add(new Grunt(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty));
                    } else {
                        mg.ENTITIES.add(new Shooter(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty));
                    }
                    spawnedEnemies++;
                }
            }
            spawned = true;
        }
    }
}

