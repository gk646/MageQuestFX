package main.system.ui.skillbar.skills;

import gameworld.entities.damage.DamageType;
import gameworld.player.abilities.PRJ_IceLance;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_IceLance extends SKILL {
    public SKL_IceLance(MainGame mg) {
        super(mg);
        totalCoolDown = 40;
        actualCoolDown = totalCoolDown;
        weapon_damage_percent = 75;
        manaCost = 1;
        type = DamageType.Ice;
        icon = setup("iceLance");
        name = "Ice Lance";
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
        if (checkForActivation(0)) {
            mg.PROJECTILES.add(new PRJ_IceLance(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y, weapon_damage_percent));
        }
    }
}


