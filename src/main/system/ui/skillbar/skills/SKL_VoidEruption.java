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
import gameworld.player.abilities.DARK.PRJ_VoidEruption;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidEruption extends SKILL {

    public SKL_VoidEruption(MainGame mg) {
        super(mg);
        totalCoolDown = 500;
        actualCoolDown = totalCoolDown;
        manaCost = 35;
        icon = setup("voidEruption");
        type = DamageType.DarkMagic;
        this.weapon_damage_percent = 700;
        name = "Void Eruption";
        description = "Void Eruption is a powerful ability that channels the dark energies of the void to unleash a devastating blast on enemies in the area. After a short build-up, the energy explodes in a shockwave of pure void energy, dealing massive damage to all enemies within the radius.";
    }

    /**
     * used for drawing the skill icon and the cooldown overlay
     *
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
        if (checkForActivation(2)) {
            mg.PROJECTILES.add(new PRJ_VoidEruption(weapon_damage_percent));
        }
    }
}
