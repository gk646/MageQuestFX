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
import gameworld.player.abilities.DARK.PRJ_VoidField;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidField extends SKILL {
    public SKL_VoidField(MainGame mg) {
        super(mg);
        totalCoolDown = 600;
        manaCost = 50;
        castTimeTotal = 60;
        castTimeActive = castTimeTotal;
        actualCoolDown = totalCoolDown;
        icon = setup("voidField");
        name = "Void Field";
        type = DamageType.DarkMagic;
        weapon_damage_percent = 2.0f;
        description = "Creates a localized zone of darkness and destruction. Upon activation, the caster summons a swirling void, consuming everything in its path. Enemies caught in the Void Field are taking damage over time and are weakened against further dark magic damage";
    }


    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawIcon(gc, x, y);
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

    @Override
    /*
     *
     */
    public String toString() {
        return weapon_damage_percent + "% Weapon Damage per Tick as " + type;
    }

    @Override
    public void drawToolTip(GraphicsContext gc, int startX, int startY) {
        if (toolTipTimer > 30) {
            gc.setFont(FonT.editUndo19);
            gc.setFill(Colors.LightGrey);
            gc.fillRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.238), startY - (MainGame.SCREEN_HEIGHT * 0.334), MainGame.SCREEN_HEIGHT * 0.231, MainGame.SCREEN_HEIGHT * 0.324f, 15, 15);
            //OUTLINE
            gc.drawImage(fancy, startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.324));
            gc.setLineWidth(1);
            setStrokeTypeColor(gc);
            gc.strokeRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.235), startY - (MainGame.SCREEN_HEIGHT * 0.331), MainGame.SCREEN_HEIGHT * 0.225, MainGame.SCREEN_HEIGHT * 0.318f, 15, 15);
            //NAME
            setTypeColor(gc);
            drawCenteredTextToolTip(gc, name, (float) (startY - (MainGame.SCREEN_HEIGHT * 0.306)), (int) (startX - (MainGame.SCREEN_HEIGHT * 0.238)));
            gc.setFont(FonT.minecraftBoldItalic14);
            gc.fillText("DMG:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.255));
            int y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.255));
            for (String string : Dialog.insertNewLine(this.toString(), 22).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.19), y1);
                y1 += 14;
            }
            gc.setFill(Colors.Blue);
            gc.fillText("Mana Cost: " + mg.player.getManaCost(manaCost), startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.275));
            gc.setFill(Colors.darkBackground);
            gc.fillText("CD: " + df.format(totalCoolDown / 60.0f) + "s", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.225));

            gc.fillText("Shadow Grasp:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.205));
            gc.fillText("Soul Siphon:", startX - (MainGame.SCREEN_HEIGHT * 0.228), startY - (MainGame.SCREEN_HEIGHT * 0.160));
            gc.setFont(FonT.minecraftItalic14);
            y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.190));
            for (String string : Dialog.insertNewLine("Slows enemies within the void field by 50% for 3 seconds.", 29).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.21), y1);
                y1 += 14;
            }


            y1 = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.145));
            for (String string : Dialog.insertNewLine("Makes all enemies vulnerable to dark magic damage by 30%.", 29).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.21), y1);
                y1 += 14;
            }

            gc.setFont(FonT.minecraftItalic12);
            int y = (int) (startY - (MainGame.SCREEN_HEIGHT * 0.11));
            for (String string : Dialog.insertNewLine(description, 37).split("\n")) {
                gc.fillText(string, startX - (MainGame.SCREEN_HEIGHT * 0.228), y += 12);
            }
        } else {
            toolTipTimer++;
        }
    }

    @Override
    public void activate() {
        if (checkForActivationCasting(3)) {
            mg.PROJECTILES.add(new PRJ_VoidField(300, weapon_damage_percent));
        }
    }
}
