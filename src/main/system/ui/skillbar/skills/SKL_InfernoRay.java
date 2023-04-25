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
import gameworld.player.abilities.FIRE.PRJ_InfernoRay;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_InfernoRay extends SKILL {


    public SKL_InfernoRay(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        actualCoolDown = totalCoolDown;
        this.weapon_damage_percent = 750;
        manaCost = 15;
        i_id = 17;
        type = DamageType.Fire;
        this.icon = setup("infernoRay");
        name = "Inferno Ray";
        description = "Inferno Ray: Unleash a devastating beam of searing flames that incinerates your enemies in its path. Channel the raw power of the inferno to deal massive fire damage to all foes caught within the fiery torrent, leaving only smoldering ashes in your wake.";
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
            mg.PROJECTILES.add(new PRJ_InfernoRay(weapon_damage_percent));
        }
    }
}
