package main.system.ui.skillpanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.skillbar.SKILL;
import main.system.ui.skillbar.skills.SKL_Filler;

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


    public UI_SkillPanel(MainGame mg) {
        whichPanel[0] = true;
        this.wholeSkillWindow = new Rectangle(skillPanelX, skillPanelY, 445, 524);
        for (int i = 0; i < 5; i++) {
            hitBoxesSideButtons[i] = new Rectangle(123, 123, 38, 43);
        }
        for (int i = 0; i < 10; i++) {
            arcaneSkills.add(new SKL_Filler(mg));
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
        gc.fillText("Spell Book", skillPanelX + 160, skillPanelY + 35);
        wholeSkillWindow.x = skillPanelX;
        wholeSkillWindow.y = skillPanelY;
        for (int i = 0; i < 5; i++) {
            hitBoxesSideButtons[i].x = skillPanelX + 407;
            hitBoxesSideButtons[i].y = skillPanelY + 51 + 51 * i;
        }
    }

    private void drawSkills(GraphicsContext gc, int x, int y) {
        x += 25;
        y += 100;
        gc.setFont(FonT.minecraftItalic12);
        gc.setFill(Colors.darkBackground);
        if (whichPanel[0]) {
            for (int i = 0; i < arcaneSkills.size(); i++) {
                arcaneSkills.get(i).drawSkillSlot(gc, x - 5, y - 40);
                gc.fillText(arcaneSkills.get(i).name, x + 65, y - 25);
                arcaneSkills.get(i + 1).drawSkillSlot(gc, x + 190, y - 40);
                gc.fillText(arcaneSkills.get(i + 1).name, x + 260, y - 25);
                i++;
                y += 85;
            }
        }
    }

    public void dragAndDrop(GraphicsContext gc) {
        if (whichPanel[0]) {

        }
    }
}
