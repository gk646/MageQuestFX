package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.abilities.PRJ_EnergySphere;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_EnergySphere extends SKILL {


    public SKL_EnergySphere(MainGame mg) {
        super(mg);
        this.totalCoolDown = 120;
        actualCoolDown = 120;
        this.coolDownCoefficient = 0;
        this.icon = setup("energy_sphere");
        type = DamageType.Arcane;
        name = "Energy Sphere";
        damage = 0.5f;
        i_id = 0;
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
        if (actualCoolDown == 120 && mg.player.mana >= 10) {
            PROJECTILE projectile = new PRJ_EnergySphere(0.3f);
            projectile.playStartSound();
            mg.PROJECTILES.add(projectile);
            mg.player.playCastAnimation(2);
            mg.player.mana -= 10;
            actualCoolDown = 0;
            mg.player.getDurabilityDamageWeapon();
        }
    }
}
