/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
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
