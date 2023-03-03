package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_SolarFlare;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;

public class SKL_SolarFlare extends SKILL {
    public SKL_SolarFlare(MainGame mg) {
        super(mg);
        totalCoolDown = 900;
        actualCoolDown = totalCoolDown;
        manaCost = 75;
        damage = 50;
        type = DamageType.Fire;
        icon = setup("solarFlare");
        name = "Solar Flare";
        description = "Channels the raw energy of the sun, creating a powerful beam of yellow light that burns through enemies in its path. The Solar Flare is capable of dealing massive damage to any foes caught in its radius, with a blinding flash that temporarily disorients those who survive the initial impact.";
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
        if (actualCoolDown >= totalCoolDown && mg.player.getMana() >= manaCost) {
            mg.player.loseMana(manaCost);
            actualCoolDown = 0;
            mg.player.playCastAnimation(1);
            mg.PROJECTILES.add(new PRJ_SolarFlare());
        }
    }
}
