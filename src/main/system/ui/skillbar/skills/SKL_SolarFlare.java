package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.FIRE.PRJ_SolarFlare;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_SolarFlare extends SKILL {
    public SKL_SolarFlare(MainGame mg) {
        super(mg);
        totalCoolDown = 900;
        actualCoolDown = totalCoolDown;
        manaCost = 75;
        weapon_damage_percent = 500;
        type = DamageType.Fire;
        icon = setup("solarFlare");
        name = "Solar Flare";
        description = "Channels the raw energy of the sun, creating a powerful beam of yellow light that burns through enemies in its path. If the target is burning, consumes all burn stacks and deals the remaining damage at once as critical hit.";
    }

    /**
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
        if (checkForActivation(1)) {
            mg.PROJECTILES.add(new PRJ_SolarFlare(weapon_damage_percent, mg.PROJECTILES));
        }
    }
}
