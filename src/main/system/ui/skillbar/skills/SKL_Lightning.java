package main.system.ui.skillbar.skills;

import gameworld.player.PROJECTILE;
import gameworld.player.abilities.PRJ_Lightning;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_Lightning extends SKILL {


    public SKL_Lightning(MainGame mg) {
        super(mg);
        this.totalCoolDown = 600;
        this.actualCoolDown = 600;
        name = "Lightning Strike";
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        // drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void activate() {
        if (actualCoolDown == 600 && mg.player.mana >= 15) {
            PROJECTILE projectile = new PRJ_Lightning(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y);
            projectile.playStartSound();
            mg.PROJECTILES.add(projectile);
            mg.player.mana -= 15;
            actualCoolDown = 0;
        }
    }
}
