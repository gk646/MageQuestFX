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

package gameworld.entities.props;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;

import java.awt.Rectangle;
import java.util.Objects;

public class DeadWolf extends ENTITY {

    public DeadWolf(int x, int y, Zone zone) {
        this.worldX = x * 48;
        this.zone = zone;
        this.worldY = y * 48;
        this.entityImage1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/Entitys/enemies/wolf/death/5.png")));
        this.collisionBox = new Rectangle();
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(entityImage1, worldX - Player.worldX + Player.screenX, worldY - Player.worldY + Player.screenY);
    }
}
