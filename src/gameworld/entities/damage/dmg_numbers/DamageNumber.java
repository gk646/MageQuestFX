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

package gameworld.entities.damage.dmg_numbers;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.system.ui.Colors;
import main.system.ui.FonT;

public class DamageNumber {

    private final int damage;
    private final DamageType type;
    private final int offSetX;
    private final boolean crit;
    public float offSetY = 35;
    private final ENTITY entity;

    public DamageNumber(int damage, DamageType type, ENTITY entity, boolean critical_Hit) {
        this.crit = critical_Hit;
        this.damage = damage;
        this.entity = entity;
        this.type = type;
        if (Math.random() >= 0.5) {
            offSetX = 45;
        } else {
            offSetX = -25;
        }
    }

    public void draw(GraphicsContext gc) {
        if (type == DamageType.DarkMagic) {
            gc.setFill(Colors.dark_magic_purple);
        } else if (type == DamageType.Fire) {
            gc.setFill(Colors.fire_red);
        } else if (type == DamageType.Arcane) {
            gc.setFill(Colors.arcane_blue);
        } else if (type == DamageType.Poison) {
            gc.setFill(Colors.poison_green);
        } else if (type == DamageType.PhysicalDMG) {
            gc.setFill(Colors.physical_grey);
        }
        if (crit) {
            gc.setFont(FonT.editUndo24);
            gc.fillText(String.valueOf(damage), entity.worldX + offSetX - Player.worldX + Player.screenX, entity.worldY + offSetY - Player.worldY + Player.screenY);
        } else {
            gc.setFont(FonT.editUndo19);
            gc.fillText(String.valueOf(damage), entity.worldX + offSetX - Player.worldX + Player.screenX, entity.worldY + offSetY - Player.worldY + Player.screenY);
        }
        offSetY -= 0.4;
    }
}
