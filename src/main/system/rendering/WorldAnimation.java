package main.system.rendering;

import main.MainGame;

import java.awt.Point;
import java.util.ArrayList;

public class WorldAnimation {
    int[] bigFish = {1118, 1119, 1120, 1121, 1122, 1123, 1124, 1125};
    ArrayList<Point> bigFishTiles = new ArrayList<>();

    ArrayList<int[]> animationList = new ArrayList<>();

    MainGame mg;

    public WorldAnimation(MainGame mg) {
        this.mg = mg;
        addAnimations();
    }

    public static boolean contains(int[] arr, int value) {
        for (int arrValue : arr) {
            if (arrValue == value) {
                return true;
            }
        }
        return false;
    }

    public void cacheAnimationTiles() {
        for (int i = 0; i < WorldRender.worldData.length; i++) {
            for (int j = 0; j < WorldRender.worldData.length; j++) {
                if (contains(bigFish, WorldRender.worldData[i][j])) {
                    bigFishTiles.add(new Point(i, j));
                }
            }
        }
        for (int i = 0; i < WorldRender.worldData1.length; i++) {
            for (int j = 0; j < WorldRender.worldData1.length; j++) {
                if (contains(bigFish, WorldRender.worldData1[i][j])) {
                    bigFishTiles.add(new Point(i, j));
                }
            }
        }
        for (int i = 0; i < WorldRender.worldData2.length; i++) {
            for (int j = 0; j < WorldRender.worldData2.length; j++) {
                if (contains(bigFish, WorldRender.worldData2[i][j])) {
                    bigFishTiles.add(new Point(i, j));
                }
            }
        }
    }

    private void addAnimations() {
        animationList.add(bigFish);
    }
}
