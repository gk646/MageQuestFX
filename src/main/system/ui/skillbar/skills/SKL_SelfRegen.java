package main.system.ui.skillbar.skills;

import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_SelfRegen extends SKILL {

    public SKL_SelfRegen(MainGame mg) {
        super(mg);
        this.totalCoolDown = 3600;
        this.actualCoolDown = 3600;
    }

    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawCooldown(gc, x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public void activate() {
        if (mg.player.mana >= 15) {
            mg.player.mana -= 15;
        }
    }
}
