package main.system.rendering.enhancements;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;

public class StaticLightSource {
    public int[] tileProgression;
    public Color color;
    public ArrayList<Point> tilesIndices = new ArrayList<>();
    public ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public ArrayList<Point> tilesIndices2 = new ArrayList<>();
    public DropShadow glow = new DropShadow();


    public StaticLightSource(int[] list, Color color) {
        tileProgression = list;
        glow.setRadius(55);
        glow.setOffsetX(0);
        glow.setOffsetY(0);
        glow.setColor(color);
        glow.setBlurType(BlurType.THREE_PASS_BOX);
        glow.setSpread(0.6);
    }
}
