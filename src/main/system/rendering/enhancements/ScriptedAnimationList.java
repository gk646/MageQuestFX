package main.system.rendering.enhancements;

import main.system.rendering.WorldRender;

import java.awt.Point;

public class ScriptedAnimationList {
    public boolean finished;
    private final int speed;
    private final Point tilePos;
    private final int[] list;
    private int counter;
    private int speedCounter;

    public ScriptedAnimationList(int[] list, int speed, Point tilePos, int start) {
        this.list = list;
        this.speed = speed;
        this.tilePos = tilePos;
        speedCounter = start;
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
