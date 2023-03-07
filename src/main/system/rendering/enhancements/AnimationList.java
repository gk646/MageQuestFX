package main.system.rendering.enhancements;

import java.awt.Point;
import java.util.ArrayList;

public class AnimationList {

    public final int[] tileProgression;
    public final ArrayList<Point> tilesIndices = new ArrayList<>();
    public final ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public final ArrayList<Point> tilesIndices2 = new ArrayList<>();
    int speed;
    int[][] speedCounters = new int[3][500];


    public AnimationList(int[] list, int speed) {
        tileProgression = list;
        this.speed = speed;
    }


    public int progress(int i, int index, int array) {
        if (speedCounters[array][index] >= speed) {
            speedCounters[array][index] = (int) (Math.random() * 2);
            for (int j = 0; j < tileProgression.length; j++) {
                if (tileProgression[j] == i) {
                    if (j < tileProgression.length - 1) {
                        return tileProgression[j + 1];
                    } else {
                        return tileProgression[0];
                    }
                }
            }
        }
        speedCounters[array][index]++;
        return i;
    }
}
