package main.system.ui.skillbar.skills;

import gameworld.player.abilities.PRJ_EnergySphere;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

class SKL_AutoShot extends SKILL {


    public SKL_AutoShot(MainGame mg) {
        super(mg);
        this.totalCoolDown = 120;
        actualCoolDown = 120;
        this.coolDownCoefficient = 0;
        this.icon = setup("energy_sphere.png");
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
    }

    @Override
    public void update() {
        if (actualCoolDown < totalCoolDown) {
            actualCoolDown++;
        }
    }

    @Override
    public void activate() {
        if (actualCoolDown == 120 && mg.player.mana >= 10) {
            mg.PROJECTILES.add(new PRJ_EnergySphere(mg));
            mg.player.mana -= 10;
            actualCoolDown = 0;
            mg.player.getDurabilityDamageWeapon();
        }
    }
}
