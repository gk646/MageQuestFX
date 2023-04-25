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

package main.system.ui.talentpanel;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TALENT {
    public final int i_id;
    final String name;
    public final float[] effects = new float[Player.effectsSizeTotal];

    public final String imagePath;
    private final Image icon;
    String description;
    public int intellect;
    public int vitality;
    public int wisdom;
    public int agility;
    public int luck;
    public int charisma;
    public int endurance;
    public int strength;
    public int focus;
    public int armour;

    public TALENT(int i_id, String name, String imagePath, String description, String effect) {
        this.i_id = i_id;
        this.name = name;
        this.imagePath = imagePath;
        if (effect != null) {
            getEffect(effect);
            getStats(effect);
        }
        this.icon = setup(imagePath);
        this.description = description;
    }

    private void getEffect(String effect) {
        Pattern p;
        Matcher m;
        p = Pattern.compile("\\[(\\d+)](\\d+(\\.\\d+)?)");


        m = p.matcher(effect);
        while (m.find()) {
            effects[Integer.parseInt(m.group(1))] = Float.parseFloat(m.group(2));
        }
    }


    private void getStats(String effect) {
        Pattern p;
        Matcher m;
        if (effect.contains("INT")) {
            p = Pattern.compile("INT(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                intellect = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("VIT")) {
            p = Pattern.compile("VIT(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                vitality = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("WIS")) {
            p = Pattern.compile("WIS(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                wisdom = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("AGI")) {
            p = Pattern.compile("AGI(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                agility = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("LUC")) {
            p = Pattern.compile("LUC(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                luck = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("CHA")) {
            p = Pattern.compile("CHA(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                charisma = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("END")) {
            p = Pattern.compile("END(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                endurance = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("STR")) {
            p = Pattern.compile("STR(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                strength = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("FOC")) {
            p = Pattern.compile("FOC(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                focus = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("ARM")) {
            p = Pattern.compile("ARM(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                armour = Integer.parseInt(m.group(1));
            }
        }
    }

    public void drawIcon(GraphicsContext gc, int x, int y) {
        if (imagePath.contains("dark_magic")) {
            gc.drawImage(icon, x + 2, y);
        } else if (imagePath.contains("shoe")) {
            gc.drawImage(icon, x - 1, y + 1);
        } else if (imagePath.contains("magic_find")) {
            gc.drawImage(icon, x - 1, y - 3);
        } else {
            gc.drawImage(icon, x, y);
        }
    }


    private Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/TalentIcons/" + imagePath))));
    }
}

