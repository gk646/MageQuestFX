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

package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.Storage;
import main.system.enums.Zone;

public class DRP_Coin extends DROP {
    private Image coin1;
    private Image coin2;
    private Image coin3;
    private Image coin4;

    public final int amount;


    public DRP_Coin(int x, int y, int amount, Zone zone) {
        getImages();
        this.amount = amount;
        this.zone = zone;
        this.size = 10;
        this.worldPos.x = x + 5;
        this.worldPos.y = y + 5;
    }

    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        spriteCounter++;
        int spriteIndex = spriteCounter % 80 / 20;
        switch (spriteIndex) {
            case 0 -> gc.drawImage(coin1, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 1 -> gc.drawImage(coin2, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 2 -> gc.drawImage(coin3, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
            case 3 -> gc.drawImage(coin4, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
        }
    }

    /**
     * update method
     */
    @Override
    public void update() {

    }

    private void getImages() {
        coin1 = Storage.coin1;
        coin2 = Storage.coin2;
        coin3 = Storage.coin3;
        coin4 = Storage.coin4;
    }
}
