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

package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;

import java.util.Objects;

public class DRP_EtherMark extends DROP {
    private final Image etherMark = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/ether_mark.png")));
    public int amount;

    public DRP_EtherMark(int x, int y, int amount, Zone zone) {
        this.zone = zone;
        this.amount = amount;
        this.size = 30;
        this.worldPos.x = x + 5;
        this.worldPos.y = y + 5;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(etherMark, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
