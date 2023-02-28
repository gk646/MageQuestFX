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
    protected boolean drawOne, drawTwo, drawThree, drawFour, drawFive;
    private final Image a1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_first.png")));
    private final Image a2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_2.png")));
    private final Image a3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_3.png")));
    private final Image a4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_4.png")));
    private final Image a5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/inventory/spellbook_5.png")));
    public MainGame mg;
    public Rectangle wholeSkillWindow, one, two, three, four, five;
    public int skillPanelX = 500, skillPanelY = 250;
    ArrayList<SKILL> arcaneSkills = new ArrayList<>();
    ArrayList<SKILL> fireSkills = new ArrayList<>();
    ArrayList<SKILL> poisonSkills = new ArrayList<>();
    ArrayList<SKILL> iceSkills = new ArrayList<>();
    ArrayList<SKILL> darkSkills = new ArrayList<>();


    public UI_SkillPanel(MainGame mg) {
        drawOne = true;
        this.wholeSkillWindow = new Rectangle(skillPanelX, skillPanelY, 347, 408);
        one = new Rectangle(skillPanelX, skillPanelY, 38, 43);
        two = new Rectangle(skillPanelX, skillPanelY, 38, 43);
        three = new Rectangle(skillPanelX, skillPanelY, 38, 43);
        four = new Rectangle(skillPanelX, skillPanelY, 38, 43);
        five = new Rectangle(skillPanelX, skillPanelY, 38, 43);
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
        if (drawOne) {
            gc.drawImage(a1, x, y);
        } else if (drawTwo) {
            gc.drawImage(a2, x, y);
        } else if (drawThree) {
            gc.drawImage(a3, x, y);
        } else if (drawFour) {
            gc.drawImage(a4, x, y);
        } else if (drawFive) {
            gc.drawImage(a5, x, y);
        }
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBold16);
        gc.fillText("Spell Book", skillPanelX + 180, skillPanelY + 45);
    }

    private void drawSkills(GraphicsContext gc, int x, int y) {
        x += 25;
        y += 100;
        if (drawOne) {
            for (int i = 0; i < arcaneSkills.size(); i++) {
                arcaneSkills.get(i).drawIcon(gc, x, y);
                arcaneSkills.get(i + 1).drawIcon(gc, x + 140, y);
                i++;
            }
        }
    }

    public void dragAndDrop(GraphicsContext gc) {
        if (drawOne) {

        }
    }
}
