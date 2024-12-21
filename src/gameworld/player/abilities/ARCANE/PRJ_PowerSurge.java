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

package gameworld.player.abilities.ARCANE;

import gameworld.entities.loadinghelper.ProjectilePreloader;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class PRJ_PowerSurge extends PROJECTILE {

    PRJ_PowerSurge() {
        resource = ProjectilePreloader.powerSurge;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 120 / 15) {
            case 0 -> gc.drawImage(resource.images1.get(0), Player.screenX - 12, Player.screenY - 12);
            case 1 -> gc.drawImage(resource.images1.get(1), Player.screenX - 12, Player.screenY - 12);
            case 2 -> gc.drawImage(resource.images1.get(2), Player.screenX - 12, Player.screenY - 12);
            case 3 -> gc.drawImage(resource.images1.get(3), Player.screenX - 12, Player.screenY - 12);
            case 4 -> gc.drawImage(resource.images1.get(4), Player.screenX - 12, Player.screenY - 12);
            case 5 -> gc.drawImage(resource.images1.get(5), Player.screenX - 12, Player.screenY - 12);
            case 6 -> gc.drawImage(resource.images1.get(6), Player.screenX - 12, Player.screenY - 12);
            case 7 -> gc.drawImage(resource.images1.get(7), Player.screenX - 12, Player.screenY - 12);
        }
        spriteCounter++;
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
