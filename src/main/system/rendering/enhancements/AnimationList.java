package main.system.rendering.enhancements;

import java.awt.Point;
import java.util.ArrayList;

public class AnimationList {

    public final int[] tileProgression;
    public final ArrayList<Point> tilesIndices = new ArrayList<>();
    public final ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public final ArrayList<Point> tilesIndices2 = new ArrayList<>();
    int speed;
    int speedCounter;

    public AnimationList(int[] list, int speed) {
        tileProgression = list;
        this.speed = speed;
    }


    public int progress(int i) {
        if (speedCounter >= speed) {
            speedCounter = 0;
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
        speedCounter++;
        return i;
    }
}
