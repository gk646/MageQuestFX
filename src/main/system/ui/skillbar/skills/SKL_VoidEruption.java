package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.DARK.PRJ_VoidEruption;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_VoidEruption extends SKILL {

    public SKL_VoidEruption(MainGame mg) {
        super(mg);
        totalCoolDown = 500;
        actualCoolDown = totalCoolDown;
        manaCost = 35;
        icon = setup("voidEruption");
        type = DamageType.DarkMagic;
        this.weapon_damage_percent = 700;
        name = "Void Eruption";
        description = "Void Eruption is a powerful ability that channels the dark energies of the void to unleash a devastating blast on enemies in the area. After a short build-up, the energy explodes in a shockwave of pure void energy, dealing massive damage to all enemies within the radius.";
    }

    /**
     * used for drawing the skill icon and the cooldown overlay
     *
     * @param gc graphics context
     * @param x  x start
     * @param y  y start
     */
    @Override
    public void draw(GraphicsContext gc, int x, int y) {
        drawIcon(gc, x, y);
        drawCooldown(gc, x, y);
    }

    /**
     *
     */
    @Override
    public void update() {
        updateCooldown();
    }

    /**
     *
     */
    @Override
    public void activate() {
        if (checkForActivation(2)) {
            mg.PROJECTILES.add(new PRJ_VoidEruption(weapon_damage_percent));
        }
    }
}
