package main.system.ui.skillbar.skills;

import gameworld.player.abilities.PRJ_IceLance;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.skillbar.SKILL;


public class SKL_IceLance extends SKILL {
    public SKL_IceLance(MainGame mg) {
        super(mg);
        totalCoolDown = 40;
        actualCoolDown = totalCoolDown;
        manaCost = 1;
        icon = setup("solarFlare");
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
        if (actualCoolDown >= totalCoolDown && mg.player.mana >= manaCost) {
            mg.player.mana -= manaCost;
            actualCoolDown = 0;
            mg.PROJECTILES.add(new PRJ_IceLance(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
        }
    }
}


