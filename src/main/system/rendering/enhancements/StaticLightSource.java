package main.system.rendering.enhancements;

import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;

public class StaticLightSource {
    public final int[] tileProgression;

    public final ArrayList<Point> tilesIndices = new ArrayList<>();
    public final ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public final ArrayList<Point> tilesIndices2 = new ArrayList<>();


    public StaticLightSource(int[] list, Color color) {
        tileProgression = list;
    }
}
