package main.system.ui.skillbar;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.skillbar.skills.SKL_AutoShot;
import main.system.ui.skillbar.skills.SKL_EnergySphere;
import main.system.ui.skillbar.skills.SKL_RingSalvo;

public class UI_SkillBar {


    public final SKILL[] skills = new SKILL[6];
    private final int skillBarX = 576;
    private final int skillBarY = 1_005;


    public UI_SkillBar(MainGame mg) {
        skills[0] = new SKL_RingSalvo(mg);
        skills[1] = new SKL_EnergySphere(mg);
        skills[4] = new SKL_AutoShot(mg);
    }

    public void draw(GraphicsContext gc) {
        drawBackGround(gc);
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null) {
                skills[i].draw(gc, skillBarX + 5 + i * 55, skillBarY + 10);
            }
        }
    }

    private void drawBackGround(GraphicsContext gc) {
        gc.setFill(Colors.LightGrey);
        gc.fillRoundRect(skillBarX, skillBarY, 768, 75, 15, 15);
        gc.setFill(Colors.darkBackground);
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect(skillBarX + 50 + 10, skillBarY + 10, 50, 50, 5, 5);
        gc.strokeRoundRect(skillBarX + 100 + 15, skillBarY + 10, 50, 50, 5, 5);
        gc.strokeRoundRect(skillBarX + 150 + 20, skillBarY + 10, 50, 50, 5, 5);
        gc.strokeRoundRect(skillBarX + 200 + 25, skillBarY + 10, 50, 50, 5, 5);
        gc.strokeRoundRect(skillBarX + 250 + 30, skillBarY + 10, 50, 50, 5, 5);
        gc.strokeRoundRect(skillBarX + 300 + 35, skillBarY + 10, 50, 50, 5, 5);
    }


    public void update() {
        for (SKILL skill : skills) {
            if (skill != null) {
                skill.update();
            }
        }
    }
}
