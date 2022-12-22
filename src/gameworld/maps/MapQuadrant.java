package gameworld.maps;

import gameworld.entitys.Grunt;
import main.MainGame;

public class MapQuadrant {

    public int size, difficulty;
    public int numberOfEnemies;
    int startTileX, startTileY;
    MainGame mg;

    public MapQuadrant(int difficulty, MainGame mg, int startTileX, int startTileY, int size, int numberOfEnemies) {
        this.startTileX = startTileX;
        this.startTileY = startTileY;
        this.size = size;
        this.mg = mg;
        this.numberOfEnemies = numberOfEnemies;
        this.difficulty = difficulty;
        spawnEnemies();
    }


    public void spawnEnemies() {
        int spawnedEnemies = 0;
        int xTile, yTile;
        while (spawnedEnemies <= numberOfEnemies) {
            xTile = Math.min((int) (Math.random() * size) + startTileX, 499);
            yTile = Math.min((int) (Math.random() * size) + startTileY, 499);
            if (!mg.wRender.tileStorage[mg.wRender.worldData[xTile][yTile]].collision) {
                mg.ENTITIES.add(new Grunt(mg, xTile * mg.tileSize, yTile * mg.tileSize, 10, difficulty));
                spawnedEnemies++;
            }
        }
    }
}

