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

package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;


public class DEBUF_Stun extends Effect {
    /**
     *
     */
    public DEBUF_Stun(float durationInTicks, float amount, int tickRate, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        super(durationInTicks, amount, fromPlayer, sourceProjectile);
        name = "Stunned!";
        description = "Unable to do anything for" + amount;
        icon = setupPicture("health_regen");
        this.effectType = EffectType.DEBUFF;
    }

    /**
     *
     */
    @Override
    public void tick(ENTITY entity) {
        entity.stunned = rest_duration > 1;
        rest_duration--;
    }

    @Override
    public Effect clone() {
        return new DEBUF_Stun(full_duration, amount, tickRate, fromPlayer, sourceProjectile);
    }
}

