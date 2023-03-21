package gameworld.world.generation;

import gameworld.world.maps.Map;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMap {

    MainGame mg;

    int[] floorPaletV1_4Enhanced = new int[]{131, 132, 133, 144, 145, 146};
    int[] wallPaletV1_4Enhanced = new int[]{93};

    public RandomMap(MainGame mg) {
        this.mg = mg;
    }

    public void loadRandomMap() {
        int entrance = (int) (Math.random() * 3);
        int mapSize = (int) (50 + Math.random() * 75);
        if (entrance == 0) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 1), 1, mapSize / 2);
        } else if (entrance == 1) {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 2), mapSize / 2, 1);
        } else {
            mg.wControl.loadMap(getRandomMap(mapSize, 0, 0, 3), mapSize - 2, mapSize / 2);
        }
    }

    public Map getRandomMap(int sizeX, int roomSize, int corridorLength, int entrance) {
        int[][] FG = getBlankMap(sizeX);
        int[][] BG1 = getBlankMap(sizeX);
        int[][] BG = getBlackMap(sizeX);
        generateEntrance(BG, entrance);
        FastNoiseLite fastNoise = new FastNoiseLite();
        fastNoise.SetFrequency(0.02f);
        fastNoise.SetFractalOctaves(6);
        fastNoise.SetFractalGain(0.5f);
        fastNoise.SetFractalLacunarity(2.0f);
        fastNoise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        float[][] noiseData = getNoise(sizeX, fastNoise);
        getFloor(BG, noiseData, sizeX);

        generateWalls(BG);
        return new Map(new Point(sizeX, sizeX), Zone.EtherRealm, FG, BG1, BG);
    }

    private float[][] getNoise(int size, FastNoiseLite fastNoise) {
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
                if (arr[l][i] > 0) {
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

    private boolean isEdgeTile(int[][] arr, int x, int y, int arrLength) {
        return true;
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
