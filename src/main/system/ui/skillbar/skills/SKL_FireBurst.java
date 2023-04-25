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

package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.FIRE.PRJ_FireBurst;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_FireBurst extends SKILL {


    public SKL_FireBurst(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        actualCoolDown = totalCoolDown;
        this.weapon_damage_percent = 15;
        manaCost = 15;
        type = DamageType.Fire;
        this.icon = setup("fireBurst");
        name = "Fire Burst";
        description = "Unleash a wave of searing flames that scorches all enemies in its path. While Fire Blast may not deal as much damage as other fire-based abilities, its intense heat lingers long after impact, leaving enemies smoldering with a burn debuff that deals additional damage over time.";
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void activate() {
        if (checkForActivation(2)) {
            for (int i = 0; i <= 7; i++) {
                mg.PROJECTILES.add(new PRJ_FireBurst(i, weapon_damage_percent));
            }
        }
    }
}
