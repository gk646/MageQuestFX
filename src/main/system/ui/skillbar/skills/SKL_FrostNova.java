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
import gameworld.player.abilities.ICE.PRJ_FrostNova;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_FrostNova extends SKILL {
    public SKL_FrostNova(MainGame mg) {
        super(mg);
        name = "Frost Nova";
        description = "";
        totalCoolDown = 600;
        weapon_damage_percent = 50;
        icon = setup("frostNova");
        actualCoolDown = totalCoolDown;
        type = DamageType.Ice;
        manaCost = 75;
        description = "Unleash a chilling wave of frost emanating from the player, instantly freezing all enemies within a circular area around the caster. Frozen foes become immobilized, their movements halted by the encasing ice. Frozen enemies struck by Glacial Thorns shatter and suffer 1000% Weapon damage as ice.";
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
        if (checkForActivation(0)) {
            mg.PROJECTILES.add(new PRJ_FrostNova(weapon_damage_percent));
        }
    }
}
