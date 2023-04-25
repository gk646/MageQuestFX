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

package main.system.ui.skillbar;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.skills.SKL_AutoShot;
import main.system.ui.skillbar.skills.SKL_Filler;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class UI_SkillBar {


    public final SKILL[] skills = new SKILL[8];
    private final int skillBarX = 614;
    private final int skillBarY = 1_005;
    public final Rectangle[] hitBoxes = new Rectangle[8];
    private final Image xpBar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/xpbar.png")));
    private final Image skillSlot = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/slot.png")));

    private final Image character = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/character.png")));
    private final Image bag = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/bag.png")));
    private final Image skilltree = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/skilltree.png")));
    private final Image abilities = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/abilities.png")));
    private final Image map = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/map.png")));
    private final Image settings = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/settings.png")));
    private final Image mouseleft = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/mouseleft.png")));

    private final Image mouseright = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/mouseright.png")));

    public final Rectangle wholeSkillBar = new Rectangle(614, 1_000, 743, 85);
    public final Rectangle characterBox = new Rectangle(skillBarX + 64 * 7 + 110, skillBarY - 6, 32, 32);
    public final Rectangle bagBox = new Rectangle(skillBarX + 64 * 7 + 144, skillBarY - 6, 32, 32);
    public final Rectangle skilltreeBox = new Rectangle(skillBarX + 64 * 7 + 178, skillBarY - 6, 32, 32);

    public final Rectangle abilitiesBox = new Rectangle(skillBarX + 64 * 7 + 110, skillBarY + 28, 32, 32);
    public final Rectangle mapBox = new Rectangle(skillBarX + 64 * 7 + 144, skillBarY + 28, 32, 32);
    public final Rectangle settingsBox = new Rectangle(skillBarX + 64 * 7 + 178, skillBarY + 28, 32, 32);
    public boolean showNoticeBag, showNoticeChar, showNoticeTalent, showNoticeAbilities;
    private final MainGame mg;
    public boolean showNoticeMap;

    public UI_SkillBar(MainGame mg) {
        this.mg = mg;
        skills[0] = new SKL_Filler(mg);
        skills[1] = new SKL_Filler(mg);
        skills[2] = new SKL_Filler(mg);
        skills[3] = new SKL_Filler(mg);
        skills[4] = new SKL_Filler(mg);
        skills[5] = new SKL_AutoShot(mg);
        skills[6] = new SKL_Filler(mg);
        skills[7] = new SKL_Filler(mg);
        for (int i = 0; i < 8; i++) {
            if (i > 6) {
                hitBoxes[i] = new Rectangle(skillBarX + 12 + i * 64 + 20, skillBarY + 2, 50, 50);
            } else if (i > 4) {
                hitBoxes[i] = new Rectangle(skillBarX + 12 + i * 64 + 5, skillBarY + 2, 50, 50);
            } else {
                hitBoxes[i] = new Rectangle(skillBarX + 12 + i * 64, skillBarY + 2, 50, 50);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        drawBackGround(gc);
        Point mousePos = mg.inputH.lastMousePosition;
        for (int i = 0; i < skills.length; i++) {
            if (i > 6) {
                skills[i].draw(gc, skillBarX + 12 + i * 64 + 20, skillBarY + 2);
            } else if (i > 4) {
                skills[i].draw(gc, skillBarX + 12 + i * 64 + 5, skillBarY + 2);
            } else {
                skills[i].draw(gc, skillBarX + 12 + i * 64, skillBarY + 2);
            }

            if (hitBoxes[i].contains(mousePos) && mg.skillPanel.draggedSKILL == null) {
                skills[i].drawToolTip(gc, mousePos.x, mousePos.y);
                if (mg.inputH.shift_pressed && mg.inputH.mouse1Pressed) {
                    skills[i] = new SKL_Filler(mg);
                }
            }
        }
    }

    private void drawBackGround(GraphicsContext gc) {
        gc.setFill(Colors.mediumLightGreyTransparent);
        gc.fillRoundRect(skillBarX, skillBarY - 12, 693, 87, 15, 15);
        gc.setFill(Colors.white);
        gc.drawImage(skillSlot, skillBarX + 5, skillBarY - 5);
        gc.drawImage(skillSlot, skillBarX + 64 + 5, skillBarY - 5);
        gc.drawImage(skillSlot, skillBarX + 64 * 2 + 5, skillBarY - 5);
        gc.drawImage(skillSlot, skillBarX + 64 * 3 + 5, skillBarY - 5);
        gc.drawImage(skillSlot, skillBarX + 64 * 4 + 5, skillBarY - 5);

        gc.drawImage(skillSlot, skillBarX + 64 * 5 + 10, skillBarY - 5);
        gc.drawImage(skillSlot, skillBarX + 64 * 6 + 10, skillBarY - 5);

        gc.drawImage(skillSlot, skillBarX + 64 * 7 + 25, skillBarY - 5);
        gc.setFont(FonT.minecraftBoldItalic15);

        gc.fillText("1", skillBarX + 30, skillBarY + 71);
        gc.fillText("2", skillBarX + 64 + 30, skillBarY + 71);
        gc.fillText("3", skillBarX + 64 * 2 + 30, skillBarY + 71);
        gc.fillText("4", skillBarX + 64 * 3 + 30, skillBarY + 71);
        gc.fillText("5", skillBarX + 64 * 4 + 30, skillBarY + 71);

        gc.drawImage(mouseleft, skillBarX + 64 * 5 + 10 + 25, skillBarY + 55);
        gc.drawImage(mouseright, skillBarX + 64 * 6 + 10 + 25, skillBarY + 55);

        gc.fillText("Q", skillBarX + 64 * 7 + 30 + 20, skillBarY + 73);

        gc.drawImage(character, skillBarX + 64 * 7 + 110, skillBarY - 6);
        gc.drawImage(bag, skillBarX + 64 * 7 + 144, skillBarY - 6);
        gc.drawImage(skilltree, skillBarX + 64 * 7 + 178, skillBarY - 6);

        gc.drawImage(abilities, skillBarX + 64 * 7 + 110, skillBarY + 28);
        gc.drawImage(map, skillBarX + 64 * 7 + 144, skillBarY + 28);
        gc.drawImage(settings, skillBarX + 64 * 7 + 178, skillBarY + 28);

        gc.setFill(Colors.lightGreyMiddleAlpha);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.567f, MainGame.SCREEN_HEIGHT * 0.911f, 699, 9);
        gc.setFill(Colors.xpbarOrange);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.567f, MainGame.SCREEN_HEIGHT * 0.911f, (mg.player.experience / (float) mg.player.levelUpExperience) * 700, 9);
        gc.drawImage(xpBar, MainGame.SCREEN_HEIGHT * 0.564f, MainGame.SCREEN_HEIGHT * 0.908f);
        gc.setFill(Colors.notificationOrange);
        if (showNoticeChar) {
            gc.fillRect(skillBarX + 64 * 7 + 110 + 26, skillBarY - 6 - 2, 5, 5);
        }
        if (showNoticeBag) {
            gc.fillRect(skillBarX + 64 * 7 + 144 + 26, skillBarY - 6 - 2, 5, 5);
        }
        if (mg.talentP.pointsToSpend > 0) {
            gc.fillRect(skillBarX + 64 * 7 + 178 + 26, skillBarY - 6 - 2, 5, 5);
        }
        if (showNoticeAbilities) {
            gc.fillRect(skillBarX + 64 * 7 + 110 + 26, skillBarY + 30, 5, 5);
        }
        if (showNoticeMap) {
            gc.fillRect(skillBarX + 64 * 7 + 144 + 26, skillBarY + 30, 5, 5);
        }
    }


    public void update() {
        for (SKILL skill : skills) {
            skill.update();
        }
    }
}
