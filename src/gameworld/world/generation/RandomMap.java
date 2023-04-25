/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
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

package gameworld.world.generation;

import gameworld.entities.boss.BOSS_Knight;
import gameworld.entities.boss.BOSS_Slime;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonShield;
import gameworld.entities.monsters.ENT_SkeletonSpearman;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.world.WorldController;
import gameworld.world.maps.Map;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMap {
    public boolean bossKilled;
    MainGame mg;
    ArrayList<Long> first = new ArrayList<>(), second = new ArrayList<>();
    int[] floorPaletV1_4Enhanced = new int[]{131, 132, 133, 144, 145, 146, 131, 132, 133, 131, 132, 133};
    int[] wallPaletV1_4Enhanced = new int[]{93, 107, 120, 93, 93, 93, 93, 93, 93};
    int[] wallTopPaletV1_4Enhanced = new int[]{80, 82, 80, 80, 80, 80, 80};

    int[] wallSidesPaletV1_4Enhanced = new int[]{182, 183};
    public boolean bossSpawned;
    public float etherRealmProgress = 100.0f;
    private int bossCountdown;

    public RandomMap(MainGame mg) {
        this.mg = mg;
    }

    public void loadRandomMap() {
        mg.ui.setLoadingScreen(0);
        int entrance = (int) (Math.random() * 3);
        int mapSize = (int) (150 + Math.random() * 75);
        if (entrance == 0) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 0), 1, mapSize / 2);
        } else if (entrance == 1) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 1), mapSize / 2, 1);
        } else {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 2), mapSize - 2, mapSize / 2);
        }
    }

    private boolean testMap(int[][] arr, Point entrancePoint) {
        return myBFS(arr, entrancePoint.x, entrancePoint.y) > 15000;
    }

    private Map getRandomMap(int mapSize, int roomSize, int corridorLength, int entranceNUm) {
        Point entrancePoint = new Point();
        switch (entranceNUm) {
            case 0 -> entrancePoint = new Point(1, mapSize / 2);
            case 1 -> entrancePoint = new Point(mapSize / 2, 1);
            case 2 -> entrancePoint = new Point(mapSize - 2, mapSize / 2);
        }
        Map map = generateMap(mapSize, roomSize, corridorLength, entranceNUm);
        while (!testMap(map.mapDataBackGround, entrancePoint)) {
            map = generateMap(mapSize, roomSize, corridorLength, entranceNUm);
        }

        //etherRealmProgress = 0;
        bossSpawned = false;
        spawnEntities(map.mapDataBackGround, entrancePoint);
        return map;
    }

    private void spawnEntities(int[][] arr, Point startPoint) {
        int xstart = startPoint.x * 48;
        int ystart = startPoint.y * 48;
        int arrLength = arr.length;
        double num;
        mg.ENTITIES.removeIf(entity -> entity.zone == Zone.EtherRealm);
        for (int y = 0; y < arrLength; y++) {
            for (int x = 0; x < arrLength; x++) {
                if (arrContainsNum(floorPaletV1_4Enhanced, arr[x][y])) {
                    num = Math.random();
                    if (num > 0.98) {
                        num = Math.random();
                        if (num < 0.2) {
                            mg.ENTITIES.add(new ENT_SkeletonWarrior(mg, x * 48, y * 48, mg.player.level, Zone.EtherRealm));
                        } else if (num < 0.4) {
                            mg.ENTITIES.add(new ENT_SkeletonArcher(mg, x * 48, y * 48, mg.player.level, Zone.EtherRealm));
                        } else if (num < 0.6) {
                            mg.ENTITIES.add(new ENT_SkeletonSpearman(mg, x * 48, y * 48, mg.player.level, Zone.EtherRealm));
                        } else if (num < 0.8) {
                            mg.ENTITIES.add(new ENT_SkeletonShield(mg, x * 48, y * 48, mg.player.level, Zone.EtherRealm));
                        }
                    }
                }
            }
        }
        mg.ENTITIES.removeIf(entity -> Math.abs(entity.worldX - xstart) + Math.abs(entity.worldY - ystart) < 500);
    }


    private Map generateMap(int sizeX, int roomSize, int corridorLength, int entranceNUm) {
        int[][] FG = getBlankMap(sizeX);
        int[][] BG1 = getBlankMap(sizeX);
        int[][] BG = getBlackMap(sizeX);
        generateEntrance(BG, entranceNUm);
        float[][] noiseData = getNoise(sizeX);
        getFloor(BG, noiseData, sizeX);
        generateWalls(BG);
        generateWallTop(BG, FG);
        return new Map(new Point(sizeX, sizeX), Zone.EtherRealm, FG, BG1, BG);
    }

    private float[][] getNoise(int size) {
        FastNoiseLite fastNoise = new FastNoiseLite();
        fastNoise.SetNoiseType(FastNoiseLite.NoiseType.Value);
        fastNoise.SetFrequency(0.12f);
        fastNoise.SetSeed(mg.secureRandom.nextInt());
        fastNoise.SetFractalOctaves(1);
        fastNoise.SetFractalLacunarity(0.7f);
        fastNoise.SetFractalGain(0);
        fastNoise.SetFractalWeightedStrength(0);
        fastNoise.SetFractalPingPongStrength(2);
        float[][] noiseMap = new float[size][size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                float value = fastNoise.GetNoise(x, y);
                noiseMap[x][y] = value;
            }
        }

        return noiseMap;
    }

    private void getFloor(int[][] floorArr, float[][] arr, int length) {
        for (int i = 0; i < length; i++) {
            for (int l = 0; l < length; l++) {
                if (arr[l][i] < 0.022) {
                    floorArr[l][i] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        }
    }


    private void generateEntrance(int[][] arr, int entrance) {
        int length = arr.length;
        if (entrance == 0) {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[x][length / 2 + 3 - y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        } else if (entrance == 1) {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[length / 2 - 4 + x][y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        } else {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[length - x - 1][length / 2 + 3 - y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        }
    }

    private void generateWalls(int[][] arr) {
        int arrLength = arr.length;
        int val;
        for (int y = 0; y < arrLength; y++) {
            for (int x = 0; x < arrLength; x++) {
                val = arr[x][y];
                if (arrContainsNum(floorPaletV1_4Enhanced, val)) {
                    if (arr[x][Math.max(y - 1, 0)] == 2215) {
                        arr[x][y - 1] = getRandomArrayEntry(wallPaletV1_4Enhanced);
                    } else if (arr[x][Math.min(arrLength - 1, y + 1)] == 2215) {
                        arr[x][y] = getRandomArrayEntry(wallPaletV1_4Enhanced);
                    }
                }
            }
        }
    }

    private void generateWallTop(int[][] arr, int[][] toparr) {
        int arrLength = arr.length;
        int val;
        for (int y = 0; y < arrLength; y++) {
            for (int x = 0; x < arrLength; x++) {
                val = arr[x][y];
                if (arrContainsNum(floorPaletV1_4Enhanced, val)) {
                    if (x >= 1 && arr[x - 1][y] == 2215) {
                        toparr[x - 1][y] = wallSidesPaletV1_4Enhanced[0];
                    }
                    if (x < arrLength - 1 && arr[x + 1][y] == 2215) {
                        toparr[x + 1][y] = wallSidesPaletV1_4Enhanced[1];
                    }
                }
                if (arrContainsNum(wallPaletV1_4Enhanced, val)) {
                    if (x >= 1 && y >= 1) {
                        toparr[x][y - 1] = getRandomArrayEntry(wallTopPaletV1_4Enhanced);
                        if (x < arrLength - 1 && y < arrLength - 1) {
                            if (arrContainsNum(wallPaletV1_4Enhanced, arr[x - 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                                toparr[x][y - 1] = 171;
                            }
                            if (arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                                toparr[x][y - 1] = 172;
                            }
                            if (arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y]) && arr[x - 1][y] == 2215 && arr[x][y + 1] == 2215) {
                                toparr[x][y] = 199;
                                if (toparr[x][y + 1] == -1) {
                                    toparr[x][y + 1] = 195;
                                }
                            } else if (arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y]) && arr[x + 1][y] == 2215 && arr[x][y + 1] == 2215) {
                                toparr[x][y] = 200;
                                if (toparr[x][y + 1] == -1) {
                                    toparr[x][y + 1] = 196;
                                }
                            }
                        }
                        if (arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                            toparr[x][y - 1] = 187;
                            if (y >= 2) {
                                if (arr[x][y - 2] == 2215 && toparr[x][y - 2] == -1) {
                                    toparr[x][y - 2] = 170;
                                }
                            }
                        }
                        if (x < arrLength - 1 && arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                            toparr[x][y - 1] = 186;
                            if (y >= 2) {
                                if (arr[x][y - 2] == 2215 && toparr[x][y - 2] == -1) {
                                    toparr[x][y - 2] = 169;
                                }
                            }
                        }
                        if (arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y])) {
                            arr[x][y] = 92;
                        }
                        if (x < arrLength - 1 && arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y])) {
                            arr[x][y] = 94;
                        }
                        /*
                        if (x < arrLength - 1 && y < arrLength - 1) {
                            if (arr[x - 1][y] == 2215 && toparr[x - 1][y] == -1) {
                                toparr[x - 1][y] = 182;
                            }
                            if (arr[x + 1][y] == 2215 && toparr[x + 1][y] == -1) {
                                toparr[x + 1][y] = 183;
                            }
                            }
                         */
                    }
                }
            }
        }
    }

    private boolean isEdgeTile(int[][] arr, int x, int y, int arrLength) {
        return false;
    }

    private boolean arrContainsNum(int[] arr, int val) {
        for (int j : arr) {
            if (j == val) {
                return true;
            }
        }
        return false;
    }

    private int getRandomArrayEntry(int[] arr) {
        int len = arr.length;
        return arr[ThreadLocalRandom.current().nextInt(len)];
    }


    private int[][] getBlackMap(int sizeX) {
        int[][] temp = new int[sizeX][sizeX];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeX; j++) {
                temp[i][j] = 2215;
            }
        }
        return temp;
    }

    private int[][] getBlankMap(int sizeX) {
        int[][] temp = new int[sizeX][sizeX];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeX; j++) {
                temp[i][j] = -1;
            }
        }
        return temp;
    }


    public int myBFS(int[][] map, int startX, int startY) {
        int val, val1, tileCounter = 0, newVal, newVal1, outTile = 2_215, mapSize = map.length;
        boolean[][] checked = new boolean[mapSize][mapSize];
        int[] checkArrX = new int[]{1, 0, -1, 0};
        int[] checkArrY = new int[]{0, 1, 0, -1};
        checked[startX][startY] = true;
        glm_vec2 tempVec;

        QueueMQ queueMQ = new QueueMQ(mapSize);
        queueMQ.add(new glm_vec2(startX, startY));

        while (!queueMQ.isEmpty()) {
            tempVec = queueMQ.pop();
            val = tempVec.x;
            val1 = tempVec.y;
            tileCounter++;
            for (int i = 0; i < 4; i++) {
                newVal = val + checkArrX[i];
                newVal1 = val1 + checkArrY[i];
                if (newVal < mapSize && newVal1 < mapSize && newVal >= 0 && newVal1 >= 0 && !checked[newVal][newVal1] && map[newVal][newVal1] != outTile) {
                    queueMQ.add(new glm_vec2(newVal, newVal1));
                    checked[newVal][newVal1] = true;
                }
            }
        }
        return tileCounter;
    }


    public void spawnBoss(int[][] arr) {
        if (WorldController.currentWorld == Zone.EtherRealm && !bossSpawned) {
            int num = 20 - (int) (Math.random() * 40);
            int num2 = 20 - (int) (Math.random() * 40);
            int xCo = mg.playerX + num;
            int yCo = mg.playerY + num2;
            int arrLength = arr.length;
            while (true) {
                if (xCo < arrLength && yCo < arrLength && xCo > 0 && yCo > 0 && arrContainsNum(floorPaletV1_4Enhanced, arr[xCo][yCo])) {
                    mg.pathF.setNodes(mg.playerX, mg.playerY, mg.playerX + num, mg.playerY + num2, 150);
                    if (mg.pathF.search()) {
                        num = (int) (Math.random() * 10);
                        if (num < 5) {
                            mg.ENTITIES.add(new BOSS_Knight(mg, xCo * 48, yCo * 48, mg.player.level, 23, Zone.EtherRealm));
                        } else if (num < 10) {
                            mg.ENTITIES.add(new BOSS_Slime(mg, xCo * 48, yCo * 48, mg.player.level, 23, Zone.EtherRealm));
                        }
                        bossSpawned = true;
                        break;
                    }
                }
                num = 20 - (int) (Math.random() * 40);
                num2 = 20 - (int) (Math.random() * 40);
                xCo = mg.playerX + num;
                yCo = mg.playerY + num2;
            }
        }
    }

    private void printMinMaxValue(float[][] noiseMap) {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;

        // Loop through the array and update the min and max values
        for (float[] row : noiseMap) {
            for (float value : row) {
                if (value < min) {
                    min = value;
                }
                if (value > max) {
                    max = value;
                }
            }
        }

        System.out.println("Max value: " + max);
        System.out.println("Min value: " + min);
    }
}

final class glm_vec2 {
    public int x, y;

    glm_vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

final class QueueMQ {
    int currentIndex = -1;
    glm_vec2[] queue;

    QueueMQ(int size) {
        queue = new glm_vec2[size * size];
    }

    public void add(glm_vec2 vec2) {
        queue[++currentIndex] = vec2;
    }

    public glm_vec2 pop() {
        return queue[currentIndex--];
    }

    public boolean isEmpty() {
        return currentIndex < 0;
    }
}

