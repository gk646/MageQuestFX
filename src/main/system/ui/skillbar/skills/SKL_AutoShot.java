package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_AutoShot;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_AutoShot extends SKILL {


    public SKL_AutoShot(MainGame mg) {
        super(mg);
        this.totalCoolDown = 30;
        actualCoolDown = 30;
        this.coolDownCoefficient = 0;
        this.damage = 1;
        this.type = DamageType.PoisonDMG;
        this.icon = setup("energy_sphere");
    }

    @Override
    public void draw(GraphicsContext gc, int skillBarX, int skillBarY) {
        drawIcon(gc, skillBarX, skillBarY);
        drawCooldown(gc, skillBarX, skillBarY);
        name = "Slime Ball";
    }

    @Override
    public void update() {
        super.updateCooldown();
    }

    @Override
    public void activate() {
        if (actualCoolDown == 30) {
            mg.PROJECTILES.add(new PRJ_AutoShot(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y, damage));
            actualCoolDown = 0;
            mg.player.getDurabilityDamageWeapon();
        }
    }
}
