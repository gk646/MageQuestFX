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

package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.FIRE.PRJ_SolarFlare;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_SolarFlare extends SKILL {
    public SKL_SolarFlare(MainGame mg) {
        super(mg);
        totalCoolDown = 900;
        actualCoolDown = totalCoolDown;
        manaCost = 75;
        weapon_damage_percent = 500;
        type = DamageType.Fire;
        icon = setup("solarFlare");
        name = "Solar Flare";
        description = "Channels the raw energy of the sun, creating a powerful beam of yellow light that burns through enemies in its path. If the target is burning, consumes all burn stacks and deals the remaining damage at once as critical hit.";
    }

    /**
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawIcon(gc, x, y);
        drawCooldown(gc, x, y);
    }

    /**
     *
     */
    @Override
    public void update() {
        updateCooldown();
    }

    /**
     *
     */
    @Override
    public void activate() {
        if (checkForActivation(1)) {
            mg.PROJECTILES.add(new PRJ_SolarFlare(weapon_damage_percent, mg.PROJECTILES));
        }
    }
}
