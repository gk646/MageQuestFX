package main.system.ui.skillpanel;

import javafx.scene.canvas.GraphicsContext;
import main.system.ui.skillbar.SKILL;

import java.awt.Rectangle;

class UI_SkillSlot {

    private final Rectangle boundBox;
    private final int SLOT_SIZE = 45;
    public boolean grabbed;
    private final SKILL skill;
    public int toolTipTimer;
    public String type = "+";

    public UI_SkillSlot(SKILL skill, int xCo, int yCo) {
        this.boundBox = new Rectangle(xCo, yCo, SLOT_SIZE, SLOT_SIZE);
        this.skill = skill;
    }


    public void drawIcon(GraphicsContext g2, int x, int y) {
        skill.drawIcon(g2, x, y);
    }


    public void drawSlot(GraphicsContext g2, int startX, int startY) {
        g2.strokeRoundRect(startX, startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
    }
}


