package main.system.ui.skillpanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.SKILL;
import main.system.ui.skillbar.skills.SKL_Filler;
import main.system.ui.skillbar.skills.SKL_RingSalvo;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;

public class UI_SkillPanel {
    public boolean[] whichPanel = new boolean[5];
    private final Image a1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_first.png")));
    private final Image a2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_2.png")));
    private final Image a3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_3.png")));
    private final Image a4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_4.png")));
    private final Image a5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_5.png")));
    public MainGame mg;
    public Rectangle wholeSkillWindow;
    public Rectangle[] hitBoxesSideButtons = new Rectangle[5];
    public int skillPanelX = 500, skillPanelY = 250;
    ArrayList<SKILL> arcaneSkills = new ArrayList<>();
    ArrayList<SKILL> fireSkills = new ArrayList<>();
    ArrayList<SKILL> poisonSkills = new ArrayList<>();
    ArrayList<SKILL> iceSkills = new ArrayList<>();
    ArrayList<SKILL> darkSkills = new ArrayList<>();
    public Rectangle skillPanelMover = new Rectangle(409, 29);
    public int toolTipNumber;
    public SKILL draggedSKILL;


    public UI_SkillPanel(MainGame mg) {
        this.mg = mg;
        whichPanel[0] = true;
        this.wholeSkillWindow = new Rectangle(skillPanelX, skillPanelY, 445, 524);
        for (int i = 0; i < 5; i++) {
            hitBoxesSideButtons[i] = new Rectangle(123, 123, 32, 41);
        }
        for (int i = 0; i < 10; i++) {
            arcaneSkills.add(new SKL_RingSalvo(mg));
            fireSkills.add(new SKL_Filler(mg));
            poisonSkills.add(new SKL_Filler(mg));
            iceSkills.add(new SKL_Filler(mg));
            darkSkills.add(new SKL_Filler(mg));
        }
    }


    public void drawSkillPanel(GraphicsContext gc) {
        drawBackGround(gc, skillPanelX, skillPanelY);
        drawSkills(gc, skillPanelX, skillPanelY);
    }

    private void drawBackGround(GraphicsContext gc, int x, int y) {
        if (whichPanel[0]) {
            gc.drawImage(a1, x, y);
        } else if (whichPanel[1]) {
            gc.drawImage(a2, x, y);
        } else if (whichPanel[2]) {
            gc.drawImage(a3, x, y);
        } else if (whichPanel[3]) {
            gc.drawImage(a4, x, y);
        } else if (whichPanel[4]) {
            gc.drawImage(a5, x, y);
        }
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBold16);
        gc.fillText("Skills", skillPanelX + 170, skillPanelY + 20);
        wholeSkillWindow.x = skillPanelX;
        wholeSkillWindow.y = skillPanelY;
        skillPanelMover.x = skillPanelX;
        skillPanelMover.y = skillPanelY;
        for (int i = 0; i < 5; i++) {
            hitBoxesSideButtons[i].x = skillPanelX + 407;
            hitBoxesSideButtons[i].y = skillPanelY + 51 + 45 * i;
            if (hitBoxesSideButtons[i].contains(mg.inputH.lastMousePosition)) {
                drawToolTip(gc, mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y, i);
            }
        }
    }

    private void drawSkills(GraphicsContext gc, int x, int y) {
        x += 25; //?
        y += 100;//?
        gc.setFont(FonT.minecraftItalic12);
        gc.setFill(Colors.darkBackground);
        if (whichPanel[0]) {
            for (int i = 0; i < arcaneSkills.size(); i++) {
                arcaneSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                arcaneSkills.get(i).hitBox.x = x + 2;
                arcaneSkills.get(i).hitBox.y = y - 33;
                gc.fillText(arcaneSkills.get(i).name, x + 65, y - 25);
                arcaneSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(arcaneSkills.get(i + 1).name, x + 260, y - 25);
                arcaneSkills.get(i + 1).hitBox.x = x + 197;
                arcaneSkills.get(i + 1).hitBox.y = y - 33;
                i++;
                y += 85;
            }
        } else if (whichPanel[1]) {
            for (int i = 0; i < poisonSkills.size(); i++) {
                poisonSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                gc.fillText(poisonSkills.get(i).name, x + 65, y - 25);
                poisonSkills.get(i).hitBox.x = x + 2;
                poisonSkills.get(i).hitBox.y = y - 33;
                poisonSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(poisonSkills.get(i + 1).name, x + 260, y - 25);
                poisonSkills.get(i + 1).hitBox.x = x + 197;
                poisonSkills.get(i + 1).hitBox.y = y - 33;
                i++;
                y += 85;
            }
        } else if (whichPanel[2]) {
            for (int i = 0; i < fireSkills.size(); i++) {
                fireSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                gc.fillText(fireSkills.get(i).name, x + 65, y - 25);
                fireSkills.get(i).hitBox.x = x + 2;
                fireSkills.get(i).hitBox.y = y - 33;
                fireSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(fireSkills.get(i + 1).name, x + 260, y - 25);
                fireSkills.get(i + 1).hitBox.x = x + 197;
                fireSkills.get(i + 1).hitBox.y = y - 33;

                i++;
                y += 85;
            }
        } else if (whichPanel[3]) {
            for (int i = 0; i < iceSkills.size(); i++) {
                iceSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                gc.fillText(iceSkills.get(i).name, x + 65, y - 25);
                iceSkills.get(i).hitBox.x = x + 2;
                iceSkills.get(i).hitBox.y = y - 33;
                iceSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(iceSkills.get(i + 1).name, x + 260, y - 25);
                iceSkills.get(i + 1).hitBox.x = x + 197;
                iceSkills.get(i + 1).hitBox.y = y - 33;
                i++;
                y += 85;
            }
        } else if (whichPanel[4]) {
            for (int i = 0; i < darkSkills.size(); i++) {
                darkSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                gc.fillText(darkSkills.get(i).name, x + 65, y - 25);
                darkSkills.get(i).hitBox.x = x + 2;
                darkSkills.get(i).hitBox.y = y - 33;
                darkSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(darkSkills.get(i + 1).name, x + 260, y - 25);
                darkSkills.get(i + 1).hitBox.x = x + 197;
                darkSkills.get(i + 1).hitBox.y = y - 33;
                i++;
                y += 85;
            }
        }
    }

    private void drawToolTip(GraphicsContext gc, int x, int y, int i) {
        gc.setFill(Colors.lightGreyMiddleAlpha);
        gc.fillRoundRect(x + 25, y - 15, 75, 20, 10, 10);
        gc.setFill(Colors.darkBackground);
        gc.strokeRoundRect(x + 25, y - 15, 75, 20, 10, 10);
        gc.setFont(FonT.minecraftBold14);
        switch (i) {
            case 0 -> gc.fillText("Arcane Magic", x + 25, y);
            case 1 -> gc.fillText("Poison Magic", x + 25, y);
            case 2 -> gc.fillText("Fire Magic", x + 25, y);
            case 3 -> gc.fillText("Frost Magic", x + 25, y);
            case 4 -> gc.fillText("Dark Magic", x + 25, y);
        }
    }

    public void dragAndDropSkillBar(GraphicsContext gc) {
        Point mousePos = mg.inputH.lastMousePosition;
        if (draggedSKILL != null) {
            gc.drawImage(draggedSKILL.icon, mg.inputH.lastMousePosition.x - 25, mg.inputH.lastMousePosition.y - 25);
            if (!mg.inputH.mouse1Pressed) {
                for (int i = 0; i < 8; i++) {
                    if (mg.sBar.hitBoxes[i].contains(mousePos)) {
                        for (SKILL skill : mg.sBar.skills) {
                            if (skill.name.equals(mg.sBar.skills[i].name)) {
                                draggedSKILL = null;
                                return;
                            }
                        }
                        mg.sBar.skills[i] = draggedSKILL;
                        draggedSKILL = null;
                        return;
                    }
                }
                draggedSKILL = null;
            }
        }
        if (draggedSKILL == null && mg.inputH.mouse1Pressed) {
            if (mg.skillPanel.whichPanel[0]) {
                for (SKILL skill : arcaneSkills) {
                    if (skill.hitBox.contains(mousePos)) {
                        draggedSKILL = skill;
                        return;
                    }
                }
            } else if (mg.skillPanel.whichPanel[1]) {
                for (SKILL skill : poisonSkills) {
                    if (skill.hitBox.contains(mousePos)) {
                        draggedSKILL = skill;
                        return;
                    }
                }
            } else if (mg.skillPanel.whichPanel[2]) {
                for (SKILL skill : fireSkills) {
                    if (skill.hitBox.contains(mousePos)) {
                        draggedSKILL = skill;
                        return;
                    }
                }
            } else if (mg.skillPanel.whichPanel[3]) {
                for (SKILL skill : iceSkills) {
                    if (skill.hitBox.contains(mousePos)) {
                        draggedSKILL = skill;
                        return;
                    }
                }
            } else if (mg.skillPanel.whichPanel[4]) {
                for (SKILL skill : darkSkills) {
                    if (skill.hitBox.contains(mousePos)) {
                        draggedSKILL = skill;
                        return;
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                if (mg.sBar.hitBoxes[i].contains(mousePos)) {
                    draggedSKILL = mg.sBar.skills[i];
                    return;
                }
            }
        }
    }
}