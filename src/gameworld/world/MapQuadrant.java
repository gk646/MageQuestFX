/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld.world;

import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import main.MainGame;
import main.system.enums.Zone;

class MapQuadrant {

    private final int startTileX;
    private final int startTileY;
    private final int size;
    private final MainGame mg;
    private final Zone zone;
    private boolean spawned;
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
                if (!mg.wRender.tileStorage[mapData[xTile][yTile]].collision) {
                    if (Math.random() > 0.4) {
                        mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty, zone));
                    } else {
                        mg.ENTITIES.add(new ENT_SkeletonArcher(mg, xTile * mg.tileSize, yTile * mg.tileSize, difficulty, zone));
                    }
                    spawnedEnemies++;
                }
            }
            spawned = true;
        }
    }
}

