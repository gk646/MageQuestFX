package main.system.ui.skillbar.skills;

import gameworld.player.abilities.PRJ_EnergySphere;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_EnergySphere_2 extends SKILL {
    public SKL_EnergySphere_2(MainGame mg) {
        super(mg);
        this.totalCoolDown = 110;
        actualCoolDown = totalCoolDown;
        manaCost = 45;
        this.coolDownCoefficient = 0;
        this.icon = setup("energy_sphere.png");
        i_id = 1;
        name = "Energy Sphere II";
        description = "EnergySphere is an arcane ability that conjures a pulsing orb of crackling energy. Upon activation, the sphere begins to radiate powerful arcane energy in all directions, inflicting continuous damage to all nearby enemies within a certain radius. ";
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
        if (actualCoolDown == 120 && mg.player.getMana() >= 45) {
            mg.PROJECTILES.add(new PRJ_EnergySphere(1.5f));
            mg.player.loseMana(manaCost);
            actualCoolDown = 0;
            mg.player.getDurabilityDamageWeapon();
        }
    }
}
