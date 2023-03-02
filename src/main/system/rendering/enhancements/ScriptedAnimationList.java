package main.system.rendering.enhancements;

import main.system.rendering.WorldRender;

import java.awt.Point;

public class ScriptedAnimationList {
    public boolean finished;
    final int speed;
    final Point tilePos;
    final int[] list;
    int counter;
    private int speedCounter;

    public ScriptedAnimationList(int[] list, int speed, Point tilePos) {
        this.list = list;
        this.speed = speed;
        this.tilePos = tilePos;
    }


    public void progress() {
        if (!finished) {
            if (speedCounter >= speed) {
                speedCounter = 0;
                if (counter >= list.length) {
                    finished = true;
                    return;
                }
                WorldRender.worldData1[tilePos.x][tilePos.y] = list[counter];
                counter++;
            }
            speedCounter++;
        }
    }
}
