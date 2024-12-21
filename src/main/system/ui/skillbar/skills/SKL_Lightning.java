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
import gameworld.player.abilities.ARCANE.PRJ_Lightning;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_Lightning extends SKILL {


    public SKL_Lightning(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        this.actualCoolDown = 600;
        this.weapon_damage_percent = 450;
        manaCost = 15;
        icon = setup("lightning");
        name = "Lightning Strike";
        this.type = DamageType.Arcane;
        description = "Lightning Strike is a powerful ability that allows the player to call forth bolts of lightning to strike down enemies with electrifying force.The lightning strike is especially effective against groups of enemies or those that are vulnerable to electricity.";
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void activate() {
        if (checkForActivation(3)) {
            mg.PROJECTILES.add(new PRJ_Lightning(weapon_damage_percent));
        }
    }
}
