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
import gameworld.player.abilities.FIRE.PRJ_PyroBlast;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_PyroBlast extends SKILL {
    public SKL_PyroBlast(MainGame mg) {
        super(mg);
        totalCoolDown = 300;
        actualCoolDown = totalCoolDown;
        manaCost = 10;
        weapon_damage_percent = 250;
        type = DamageType.Fire;
        icon = setup("pyroBlast");
        name = "Pyro Blast";
        description = "A powerful spell that unleashes a powerful fireball upon enemies, dealing big damage upon impact. Enemies hit by Pyro Blast also suffer a small burn effect, causing them to take additional fire damage over time.";
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
            mg.PROJECTILES.add(new PRJ_PyroBlast(weapon_damage_percent, mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
        }
    }
}

