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

package gameworld.player.abilities.enemies;

import gameworld.player.EnemyProjectile;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class PRJ_AttackCone extends EnemyProjectile {


    public PRJ_AttackCone(int worldX, int worldY, int durationTicks, int sizeX, int sizeY, int offsetX, int offsetY, float damage) {
        this.worldPos = new Point2D.Double(worldX, worldY);
        this.duration = durationTicks;
        this.collisionBox = new Rectangle(offsetX, offsetY, sizeX, sizeY);
        this.weapon_damage_percent = damage;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
    }

    /**
     *
     */
    @Override
    public void update() {
        duration--;
        if (duration <= 0) {
            dead = true;
        }
    }
}
