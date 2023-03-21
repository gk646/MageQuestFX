package gameworld.world.generation;

import gameworld.world.maps.Map;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMap {

    MainGame mg;

    int[] floorPaletV1_4Enhanced = new int[]{131, 132, 133, 144, 145, 146};

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
        BG = generateEntrance(BG, entrance);

        return new Map(new Point(sizeX, sizeX), Zone.EtherRealm, FG, BG1, BG);
    }


    private void generateRoom() {

    }


    private void generateEntrance(int[][] arr, int entrance) {
        int length = arr.length;
        if (entrance == 1) {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[x][y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        } else if (entrance == 2) {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[x][y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        } else {
            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 9; x++) {
                    arr[x][y] = getRandomArrayEntry(floorPaletV1_4Enhanced);
                }
            }
        }
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
}
