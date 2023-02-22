package main.system.rendering.enhancements;

import main.system.rendering.WorldRender;

import java.awt.Point;

public class ScriptedAnimationList {
    public boolean finished;
    int speed;
    Point tilePos;
    int[] list;
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
                counter++;
                speedCounter = 0;
                WorldRender.worldData1[tilePos.x][tilePos.y] = list[counter];
                if (counter >= list.length - 1) {
                    finished = true;
                }
            }
            speedCounter++;
        }
    }
}
