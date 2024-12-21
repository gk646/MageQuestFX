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

package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;

public class DamageEffect extends Effect {

    public DamageEffect(float duration, float amount, boolean fromPlayer, DamageType type, int tickRate, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        if (fromPlayer) {
            this.full_duration += (this.full_duration / 100.0f) * Player.playerEffects[5];
        }
        this.type = type;
        this.tickRate = tickRate;
    }

    @Override
    public void tick(ENTITY entity) {
        if (ticker >= tickRate) {
            ticker = 0;
            if (fromPlayer) {
                entity.getDamageFromPlayer(amount, type, true);
            } else {
                entity.getDamage(amount);
            }
            entity.hpBarOn = true;
        }
        ticker++;
        rest_duration--;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return new DamageEffect(full_duration, amount, fromPlayer, type, tickRate, sourceProjectile);
    }
}
