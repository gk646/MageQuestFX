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

package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;


public class PlayerPrompts {

    private boolean F;
    private final MainGame mg;
    private final GeneralResourceLoader resc = new GeneralResourceLoader("ui/prompts/e");
    private int spriteCounter;
    public static int Ecounter = 0;

    public PlayerPrompts(MainGame mg) {
        this.mg = mg;
    }


    public void draw(GraphicsContext gc) {
        if (Ecounter < 60) {
            Ecounter++;
            spriteCounter++;
            switch (spriteCounter % 120 / 30) {
                case 0 -> gc.drawImage(resc.images1.get(0), Player.screenX, Player.screenY - 48);
                case 1 -> gc.drawImage(resc.images1.get(1), Player.screenX, Player.screenY - 48);
                case 2 -> gc.drawImage(resc.images1.get(2), Player.screenX, Player.screenY - 48);
                case 3 -> gc.drawImage(resc.images1.get(3), Player.screenX, Player.screenY - 48);
            }
        } else if (F) {

        }
    }

    public static void setETrue() {
        Ecounter = 0;
    }
}
