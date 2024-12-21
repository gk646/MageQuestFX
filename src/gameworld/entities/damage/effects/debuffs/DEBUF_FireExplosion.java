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
import gameworld.player.PROJECTILE;
import gameworld.player.abilities.FIRE.PRJ_BurnSource;
import gameworld.player.abilities.FIRE.PRJ_FireExplosion;

import java.util.List;

public class DEBUF_FireExplosion extends Effect {
    private final List<PROJECTILE> projectiles;

    public DEBUF_FireExplosion(List<PROJECTILE> projectiles) {
        this.projectiles = projectiles;
        this.rest_duration = 0;
    }

    /**
     * @param entity
     */
    @Override
    public void tick(ENTITY entity) {
        for (Effect effect : entity.BuffsDebuffEffects) {
            if (effect.sourceProjectile == PRJ_BurnSource.class) {
                effect.rest_duration = 0;
                projectiles.add(new PRJ_FireExplosion(110, (int) entity.worldX, (int) entity.worldY));
                break;
            }
        }
        rest_duration = 0;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return this;
    }
}
