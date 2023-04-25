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

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_MagicShield extends SKILL {
    private final int castTimeTotal;
    private int castTimeActive;

    public SKL_MagicShield(MainGame mg) {
        super(mg);
        this.coolDownCoefficient = 0;
        this.totalCoolDown = 7_200;
        actualCoolDown = totalCoolDown;
        manaCost = 25;
        castTimeTotal = 500;
        description = "This ability creates a shimmering, protective barrier around the character, made of pure magical energy. The Arcane Barrier not only absorbs incoming attacks, but it also actively repels enemies that come into contact with it, dealing damage and knocking them back.";
        name = "Arcane Barrier";
    }

    /**
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        //drawIcon(gc,x,y);
        drawCooldown(gc, x, y);
        drawCastBar(gc);
    }

    /**
     *
     */
    @Override
    public void update() {
        super.updateCooldown();
        super.updateCastTimer();
    }

    /**
     *
     */
    @Override
    public void activate() {

        if (actualCoolDown == totalCoolDown && castTimeActive == 0 && mg.player.getMana() >= 50) {
            castTimeActive++;
        }
        if (castTimeActive >= castTimeTotal) {
            //mg.player.BuffsDebuffEffects.add(new Buff_Effect(1_200, 100, true, 45));
            mg.player.loseMana(manaCost);
            mg.player.playCastAnimation(2);
            actualCoolDown = 0;
        }
    }
}
