package gameworld.world.effects;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import main.MainGame;

public class DayNightCycle {
    private final MainGame mg;
    private final Light.Point light = new Light.Point();


    private final Lighting lighting = new Lighting();

    public DayNightCycle(MainGame mg) {
        this.mg = mg;
        light.setX(960);
        light.setY(540);
        light.setColor(Color.RED);
        lighting.setLight(light);
        lighting.setDiffuseConstant(12);
    }

    public void start() {
        // mg.gc.setEffect(lighting);
    }
}
/*

The best way to add a day/night cycle to a Java FX application is to use AnimationTimeline and the setFill() method. To darken the screen you can use a ColorAdjust effect with the brightness set to a negative value. You can then use the AnimationTimeline to change the brightness value over time to simulate a day/night cycle.
but i have a minimap or hotbar and inventory where i dont wan the brighness changed

In this case, you can use a Group to contain the UI elements that you don't want to be affected by the brightness change. Then, add the Group object to the root node of your Java FX application and apply the ColorAdjust effect to the root node. This way, only the elements outside of the Group will be affected by the brightness change. */