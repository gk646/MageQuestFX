package main.system.rendering.enhancements;

import java.awt.Point;
import java.util.ArrayList;

public class AnimationList {

    public int[] tileProgression;
    public ArrayList<Point> tilesIndices = new ArrayList<>();
    int speed = 20;
    public ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public ArrayList<Point> tilesIndices2 = new ArrayList<>();
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
