package main.system.ui.skillbar.skills;

import gameworld.player.abilities.PRJ_RingSalvo;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_RingSalvo extends SKILL {


    public SKL_RingSalvo(MainGame mg) {
        super(mg);
        this.totalCoolDown = 120;
        actualCoolDown = 120;
        this.coolDownCoefficient = 0;
        this.icon = setup("ring_salvo.png");
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void update() {
       super.updateCooldown();
    }

    @Override
    public void activate() {
        if (actualCoolDown == 120 && mg.player.mana >= 10) {
            for (int i = 0; i <= 7; i++) {
                mg.PROJECTILES.add(new PRJ_RingSalvo(i));
            }
            mg.player.mana -= 10;
            actualCoolDown = 0;
        }
    }
}
