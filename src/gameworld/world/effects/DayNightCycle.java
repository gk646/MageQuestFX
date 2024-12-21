/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

The best way to add a day/night cycle to a Java FX application is to use AnimationTimeline and the setFill() method.To darken the screen you can use a ColorAdjust effect with the brightness set to a negative value. You can then use the AnimationTimeline to change the brightness value over time to simulate a day/night cycle.
but i have a minimap or hotbar and inventory where i dont wan the brighness changed

In this case, you can use a Group to contain the UI elements that you don't want to be affected by the brightness change. Then, add the Group object to the root node of your Java FX application and apply the ColorAdjust effect to the root node. This way, only the elements outside of the Group will be affected by the brightness change. */