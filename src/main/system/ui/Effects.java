package main.system.ui;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Effects {
    public static DropShadow blueGlow;
    public static DropShadow poisonGlow;

    public static void loadEffects() {
        blueGlow = new DropShadow();
        blueGlow.setRadius(30);
        blueGlow.setOffsetX(0);
        blueGlow.setOffsetY(0);
        blueGlow.setColor(Color.rgb(105, 182, 238, 0.8));
        blueGlow.setBlurType(BlurType.THREE_PASS_BOX);
    }
}
