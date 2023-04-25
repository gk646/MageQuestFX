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

package main.system.ui.talentpanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.Point;
import java.awt.Rectangle;

public class TalentNode {
    public Rectangle boundBox;
    public final TALENT talent;
    public final Point position;
    public boolean activated;
    public final int id;
    public int size = 0;


    public TalentNode(TALENT talent, int xCo, int yCo, int size, int activated) {
        this.size = size;
        if (size == 0) {
            this.boundBox = new Rectangle(944 + xCo, 524 + yCo, 32, 32);
        } else if (size == 2) {
            this.boundBox = new Rectangle(938 + xCo, 518 + yCo, 45, 45);
        } else if (size == 1) {
            this.boundBox = new Rectangle(935 + xCo, 515 + yCo, 50, 50);
        }

        this.talent = talent;
        this.position = new Point(xCo, yCo);
        this.id = talent.i_id;
        this.activated = activated == 1;
    }


    public void drawNode(GraphicsContext gc, int x, int y, Image image) {
        int drawx = position.x + x;
        int drawy = position.y + y;
        gc.drawImage(image, drawx, drawy);
        if (size == 1) {
            talent.drawIcon(gc, drawx + 8, drawy + 8);
        } else if (size == 2) {
            talent.drawIcon(gc, drawx + 6, drawy + 6);
        } else {
            talent.drawIcon(gc, drawx + 8, drawy + 8);
        }
        boundBox.x = drawx;
        boundBox.y = drawy;
    }
}


