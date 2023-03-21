package gameworld.world.generation;

import gameworld.world.maps.Map;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMap {

    MainGame mg;

    int[] floorPaletV1_4Enhanced = new int[]{131, 132, 133, 144, 145, 146, 131, 132, 133, 131, 132, 133};
    int[] wallPaletV1_4Enhanced = new int[]{93, 107, 120, 93, 93, 93, 93, 93, 93};
    int[] wallTopPaletV1_4Enhanced = new int[]{80, 82, 80, 80, 80, 80, 80};

    int[] wallSidesPaletV1_4Enhanced = new int[]{182, 183};

    public RandomMap(MainGame mg) {
        this.mg = mg;
    }

    public void loadRandomMap() {
        int entrance = (int) (Math.random() * 3);
        int mapSize = (int) (150 + Math.random() * 75);
        if (entrance == 0) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 1), 1, mapSize / 2);
        } else if (entrance == 1) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 2), mapSize / 2, 1);
        } else {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 3), mapSize - 2, mapSize / 2);
        }
    }

    public Map getRandomMap(int sizeX, int roomSize, int corridorLength, int entranceNUm) {
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
        if (entrance == 1) {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[x][length / 2 + 3 - y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        } else if (entrance == 2) {
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
                if (arrContainsNum(wallPaletV1_4Enhanced, val)) {
                    if (x >= 1 && y >= 1) {
                        toparr[x][y - 1] = getRandomArrayEntry(wallTopPaletV1_4Enhanced);
                        if (arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                            toparr[x][y - 1] = 187;
                        } else if (x < arrLength - 1 && arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])) {
                            toparr[x][y - 1] = 186;
                        }
                        if (x < arrLength - 1 && arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x - 1][y - 1]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y - 1])
                                && arr[x + 1][y + 1] == 2215 && arr[x][y + 1] == 2215) {
                            toparr[x][y] = 200;
                        } else if (x < arrLength - 1 && arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x + 1][y + 1]) && arrContainsNum(floorPaletV1_4Enhanced, arr[x][y + 1])
                                && arr[x - 1][y - 1] == 2215 && arr[x][y - 1] == 2215) {
                            toparr[x][y] = 199;
                        }
                    }
                }
                if (arrContainsNum(floorPaletV1_4Enhanced, val)) {
                    if (x >= 1 && arr[x - 1][y] == 2215) {
                        toparr[x - 1][y] = wallSidesPaletV1_4Enhanced[0];
                    }
                    if (x < arrLength - 1 && arr[x + 1][y] == 2215) {
                        toparr[x + 1][y] = wallSidesPaletV1_4Enhanced[1];
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
