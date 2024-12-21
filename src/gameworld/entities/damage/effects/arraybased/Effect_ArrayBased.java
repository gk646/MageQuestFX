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

package gameworld.entities.damage.effects.arraybased;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.player.PROJECTILE;

public class Effect_ArrayBased extends Effect {

    private boolean activated;

    public Effect_ArrayBased(float duration, float amount, boolean fromPlayer, int effectIndexAffected, EffectType effecttype, Class<? extends PROJECTILE> sourceProjectile) {
        super(duration, amount, fromPlayer, sourceProjectile);
        this.indexAffected = effectIndexAffected;
        this.effectType = effecttype;
    }

    @Override
    public void tick(ENTITY entity) {
        if (!activated) {
            activated = true;
            entity.updateEquippedItems();
        }
        rest_duration--;
    }

    @Override
    public Effect clone() {
        return new Effect_ArrayBased(full_duration, amount, fromPlayer, indexAffected, effectType, sourceProjectile);
    }
}

