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

package main.system.rendering.enhancements;

import java.awt.Point;
import java.util.ArrayList;

public class AnimationList {

    public final int[] tileProgression;
    public final ArrayList<Point> tilesIndices = new ArrayList<>();
    public final ArrayList<Point> tilesIndices1 = new ArrayList<>();
    public final ArrayList<Point> tilesIndices2 = new ArrayList<>();
    private final int speed;
    private final int[][] speedCounters = new int[3][1200];


    public AnimationList(int[] list, int speed) {
        tileProgression = list;
        this.speed = speed;
    }


    public int progress(int i, int index, int array) {
        if (speedCounters[array][index] >= speed) {
            speedCounters[array][index] = (int) (Math.random() * 2);
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
        speedCounters[array][index]++;
        return i;
    }
}
