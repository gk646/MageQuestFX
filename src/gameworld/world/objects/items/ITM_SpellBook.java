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

package gameworld.world.objects.items;

import gameworld.entities.damage.DamageType;
import gameworld.quest.Dialog;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

import java.util.Objects;

public class ITM_SpellBook extends ITM_Usable {
    private final int index;
    private final SKILL skill;

    public ITM_SpellBook(int level, SKILL[] skills, int index) {
        this.skill = skills[index];
        this.index = index;

        if (skill.type == DamageType.Fire) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/fire.png")));
        } else if (skill.type == DamageType.Arcane) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/arcane.png")));
        } else if (skill.type == DamageType.DarkMagic) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/dark.png")));
        } else if (skill.type == DamageType.Poison) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/poison.png")));
        } else if (skill.type == DamageType.Ice) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/ice.png")));
        }
        this.rarity = 2;
        cost = skill.manaCost * Math.abs(level);
        this.name = "Spell Book: " + skill.name;
        this.type = 'X';
        if (skill.description != null) {
            this.stats = Dialog.insertNewLine(skill.description, 30);
        } else {
            stats = "...";
        }
        this.description = Dialog.insertNewLine("A book containing knowledge about the" + skill.type + "spell " + name + ". Press E to learn it!", 35);
    }

    @Override
    public void activate(MainGame mg) {
        for (int i = 0; i < mg.skillPanel.allSkills.length; i++) {
            if (mg.skillPanel.allSkills[i] != null && mg.skillPanel.allSkills[i].name.equals(skill.name)) {
                mg.skillPanel.addSKill(skill);
                break;
            }
        }
    }
}
