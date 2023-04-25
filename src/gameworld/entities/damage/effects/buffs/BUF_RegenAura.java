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

package gameworld.entities.damage.effects.buffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;


public class BUF_RegenAura extends Effect {
    /**
     * amount is per second!
     *
     * @param duration
     * @param amount
     * @param tickRate
     * @param fromPlayer
     */
    public BUF_RegenAura(float duration, float amount, int tickRate, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        name = "Regen Aura";
        description = "Heals you for" + amount + "every second";
        icon = setupPicture("health_regen");
        this.tickRate = tickRate;
        this.effectType = EffectType.BUFF;
    }

    /**
     *
     */
    @Override
    public void tick(ENTITY entity) {
        ticker++;
        if (ticker == 10) {
            entity.setHealth(entity.getHealth() + (entity.maxHealth / 100.0f) * amount / 6);
            ticker = 0;
        }
        rest_duration--;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return new BUF_RegenAura(full_duration, amount, tickRate, fromPlayer, sourceProjectile);
    }
}

